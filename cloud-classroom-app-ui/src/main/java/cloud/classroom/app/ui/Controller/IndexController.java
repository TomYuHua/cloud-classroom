package cloud.classroom.app.ui.Controller;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import cloud.classroom.app.ui.service.Demo_UserService;
import cloud.classroom.app.ui.service.UserService;
import cloud.classroom.app.ui.service.interfaces.ChapterService;
import cloud.classroom.app.ui.service.interfaces.ResourceService;
import cloud.entity.classroom.Chapter.ChapterInfor;
import cloud.entity.classroom.DTO.ResourceComentsVo;
import cloud.entity.classroom.DTO.ResourcesBrower;
import cloud.entity.classroom.DTO.ResourcesVo;
import cloud.entity.classroom.DTO.UserVo;
import cloud.entity.classroom.Resources.Resources;
import cloud.entity.classroom.every.ResponseInfro;
import cloud.entity.classroom.every.User;

@Controller
public class IndexController
{
	private static Logger log = LoggerFactory.getLogger(IndexController.class);
	@Autowired
	Demo_UserService userservice;

	@Autowired
	UserService userService;

	@Autowired
	ChapterService chapterService;

	@Autowired
	ResourceService resourceService;

	@Value("${dfs-filesystem}")
	private String filesystem;

	private static final String userInfo = "userInfoId";

	@RequestMapping("/")
	public ModelAndView index(ModelMap map, HttpSession session, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			List<Chapter> result = ChapterList();
			Object a = session.getAttribute(userInfo);

			HttpSession sessions = request.getSession();

			User user = (User) sessions.getAttribute(userInfo);
			if (user != null)
			{  String img = user.getImgSrc();
			if (img != null && img != "")
			{
				if (user.getImgSrc().contains(filesystem) == false)
				{
					String imgPath = filesystem + img;
					user.setImgSrc(imgPath);
				}

			}
				User typeChecker = userService.checkUserType(user.getUserName());
				map.addAttribute("typeChecker", typeChecker);
			}
			map.addAttribute("user", user);
			map.addAttribute("result", result);
			map.addAttribute("userinfo", user);
		} catch (Exception e)
		{
			e.printStackTrace();
			// log.error("首页Index出错", e);
		}
		return new ModelAndView("/Index");
		// return "Index";
	}

	public List<Chapter> ChapterList()
	{

		List<Chapter> result = new ArrayList<Chapter>();
		try
		{

			List<Resources> list = resourceService.GetNLevelChildNode(0, 2);
			if (list.size() > 0)
			{
				// Stream<Resources> stream=list.stream().filter((x)->{return
				// x.getIsdocument() ==true&&x.getParentid() == 0;});

				Stream<Resources> stream = list.stream().filter((x) -> {
					return x.getIsdocument() == true;
				});
				List<Resources> oneLevel = new ArrayList<Resources>();
				List<Resources> twoLevel = new ArrayList<Resources>();

				Map<Boolean, List<Resources>> onemap = stream.collect(Collectors.partitioningBy(o -> o.getParentId() == 0));
				oneLevel = onemap.get(true);
				twoLevel = onemap.get(false);
				// Map<Boolean, List<Resources>> twomap =
				// stream.collect(Collectors.partitioningBy(o -> o.getParentid()
				// != 0));

				// twoLevel = twomap.get(true);

				for (Resources chapter : oneLevel)
				{
					Chapter newChapter = new Chapter();
					Boolean ishot = false;
					for (Resources c : twoLevel)
					{

						if (chapter.getId() == c.getParentId())
						{
							if (ishot == false)
							{
								try
								{
									newChapter.hot = twoLevel.stream().filter((x) -> {
										return x.getParentId() == chapter.getId();
									}).max((m, n) -> ((m.getClickcount() == null ? 0 : m.getClickcount())
											- (n.getClickcount() == null ? 0 : n.getClickcount()))).get();
									// twoLevel.sort(c);
								} catch (Exception e)
								{
									log.error("", e);
								}

							}
							ishot = true;
							newChapter.getListChapter().add(c);
						}
					}
					newChapter.chapterdirectories = chapter;
					result.add(newChapter);
				}

			}

		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("加载树形菜单出错", e);

		}
		return result;
	}

	@RequestMapping("/allchapter")
	public String AllChapter(ModelMap map, Integer id)
	{
		map.addAttribute("result", ChapterList());
		return "/resource/resourcelist";
	}

	@RequestMapping("/getebookcontent/{id}")
	public String getResourcesContent(@PathVariable("id")String id, Model model, HttpServletRequest request)
	{
		User user = new User();
		Resources resources = new Resources();
		List<ResourcesVo> listresources = new ArrayList<ResourcesVo>();

		try
		{
			HttpSession sessions = request.getSession();
			user = (User) sessions.getAttribute(userInfo);
			if (user != null)
			{

				String img = user.getImgSrc();
				if (img != null && img != "")
				{
					if (user.getImgSrc().contains(filesystem) == false)
					{
						String imgPath = filesystem + img;
						user.setImgSrc(imgPath);
					}

				}

				ResourcesBrower resourcesBrower = new ResourcesBrower();
				resourcesBrower.setResourceId(Integer.valueOf(id));
				resourcesBrower.setResourceType(3);
				resourcesBrower.setUserId(user.getUserId());

				resourceService.changeClickCountState(resourcesBrower);

			}

			resources = resourceService.getResourcesById(Integer.valueOf(id));
			if (resources != null)
			{
				String img = resources.getImgsrc();
				if (img != null && img != "")
				{
					String imgPath = filesystem + img;
					resources.setImgsrc(imgPath);
				}

			}
			listresources = resourceService.getClusterResources();

		} catch (Exception e)
		{
			log.error("", e);
			// TODO: handle exception
		}
		if (user != null)
		{
			User typeChecker = userService.checkUserType(user.getUserName());
			model.addAttribute("typeChecker", typeChecker);
		}


		model.addAttribute("listresources", listresources);
		model.addAttribute("resources", resources);
		model.addAttribute("user", user);
		model.addAttribute("result", ChapterList());
		return "/resource/view";
	}

	@RequestMapping("/getCommentsByResourceId")
	public String getCommentsByResourceId(String id, Model model, HttpServletRequest request)
	{
		User user = new User();
		List<ResourceComentsVo> resourceComents = new ArrayList<ResourceComentsVo>();

		try
		{
			HttpSession sessions = request.getSession();
			user = (User) sessions.getAttribute(userInfo);
			if (user != null)
			{
				String img = user.getImgSrc();
				if (img != null && img != "")
				{
					if (user.getImgSrc().contains(filesystem) == false)
					{
						String imgPath = filesystem + img;
						user.setImgSrc(imgPath);
					}

				}

			}
			resourceComents = resourceService.getCommentsByResourceId(Integer.valueOf(id));

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		model.addAttribute("userInfor", user);
		model.addAttribute("resourceComents", resourceComents);
		return "/resource/comments";
	}

	@RequestMapping("/getvideocontent/{id}")
	public String getvideocontent(@PathVariable("id") int id, Model model, HttpServletRequest request)
	{
		Resources resources = new Resources();
		User user = new User();

		try
		{
			HttpSession sessions = request.getSession();
			user = (User) sessions.getAttribute(userInfo);
			if (user != null)
			{
				String img = user.getImgSrc();
				if (img != null && img != "")
				{
					if (user.getImgSrc().contains(filesystem) == false)
					{
						String imgPath = filesystem + img;
						user.setImgSrc(imgPath);
					}

				}

				ResourcesBrower resourcesBrower = new ResourcesBrower();
				resourcesBrower.setResourceId(Integer.valueOf(id));
				resourcesBrower.setResourceType(1);
				resourcesBrower.setUserId(user.getUserId());
				resourceService.changeClickCountState(resourcesBrower);
			}

			resources = resourceService.getResourcesById((Integer) id);

			if (resources != null)
			{

				String img = resources.getImgsrc();
				if (img != null && img != "")
				{
					String imgPath = filesystem + img;
					resources.setImgsrc(imgPath);
				}

			}

		} catch (Exception e)
		{
			log.error("", e);
		}

		if (user != null)
		{
			User typeChecker = userService.checkUserType(user.getUserName());
			model.addAttribute("typeChecker", typeChecker);
		}

		model.addAttribute("result", ChapterList());
		model.addAttribute("resources", resources);
		model.addAttribute("user", user);
		return "/resource/video";
	}

	@RequestMapping(value = "/ChangePassWord/{userName}")
	public String changePassWord(@PathVariable("userName") String userName, Model model)
	{

		model.addAttribute("userName", userName);
		return "ChangePassWord";
	}

	@RequestMapping("/currentchapter/{currentid}")
	public String CurrentChapter(@PathVariable("currentid") int currentid, ModelMap map, HttpServletRequest request)
	{
		HttpSession sessions = request.getSession();
		User user = (User) sessions.getAttribute(userInfo);
		if (user != null)
		{
			User typeChecker = userService.checkUserType(user.getUserName());
			map.addAttribute("typeChecker", typeChecker);
		}
		map.addAttribute("currentid", currentid);
		map.addAttribute("result", ChapterList());
		map.addAttribute("user", user);

		return "/resource/course";

	}

	@RequestMapping("/getScoreSituations")
	public String getScoreSituations(String id, String userId, ModelMap map, HttpServletRequest request)
	{
		boolean isMakeScores = false;
		if (userId != null)
		{
			User userInfor = userService.selectScores(Integer.valueOf(id), Integer.valueOf(userId));
			if (userInfor != null)
			{
				isMakeScores = true;
			}
		}
		map.addAttribute("isMakeScores", isMakeScores);
		return "/resource/commentsA";
	}

	@RequestMapping("/coursevideo")
	public String CourseVideo(ModelMap map, @RequestParam(defaultValue = "1") Integer orderType, @RequestParam(defaultValue = "0") Integer currentid,
			@RequestParam(defaultValue = "1") Integer types)
	{
		ChapterInfor chapterInfor = new ChapterInfor();
		List<Resources> chapterList = new ArrayList<>();
		List<Resources> headLineList = new ArrayList<>();
		if (currentid!= 0)
		{
			Resources res = resourceService.getResourcesById(currentid);
			chapterInfor.setChooseId(currentid);
			chapterInfor.setChpaterName(res.getName());
			headLineList = resourceService.getHeadlineList(currentid);
			boolean isExsitDocment=resourceService.isExsitDocument(currentid);
			if (isExsitDocment==true)
			{
				chapterInfor.setUpperChapterId(currentid);
				chapterList = resourceService.getChildChpaterList(currentid);

			} else
			{
				chapterInfor.setUpperChapterId(res.getParentId());
				chapterList = resourceService.getParallelChpaterList(currentid);

			}
		} else
		{
			chapterInfor.setUpperChapterId(currentid);
			chapterList = resourceService.getChildChpaterList(currentid);
			chapterInfor.setChooseId(currentid);
			chapterInfor.setChpaterName("全部章节");
		}

		Resources resources = new Resources();

		if (currentid != 0)
		{
			resources.setId(currentid);
		}
		resources.setOrderType(orderType);
		resources.setTypes(types);
		List<Resources> resourcesList = resourceService.getResourcesList(resources);
		for (Resources resource : resourcesList)
		{
			if (null != resource.getImgsrc())
			{
				String imgPath = filesystem + resource.getImgsrc();
				resource.setImgsrc(imgPath);
			}
		}
		map.addAttribute("orderType", orderType);
		map.addAttribute("chapterList", chapterList);
		map.addAttribute("chapterInfor", chapterInfor);
		map.addAttribute("resourcesList", resourcesList);
		map.addAttribute("headLineList", headLineList);
		return "/resource/courseVideo";

	}

	@RequestMapping("/courseview")
	public String CourseView(ModelMap map, @RequestParam(defaultValue = "1") Integer orderType, @RequestParam(defaultValue = "0") Integer currentid,
			@RequestParam(defaultValue = "3") Integer types)
	{
		ChapterInfor chapterInfor = new ChapterInfor();
		List<Resources> chapterList = new ArrayList<>();
		List<Resources> headLineList = new ArrayList<>();
		if (currentid != 0)
		{
			Resources res = resourceService.getResourcesById(currentid);
			chapterInfor.setChooseId(currentid);
			chapterInfor.setChpaterName(res.getName());
			headLineList = resourceService.getHeadlineList(currentid);
			boolean isExsitDocment=resourceService.isExsitDocument(currentid);
			if (isExsitDocment==true)
			{
				chapterInfor.setUpperChapterId(currentid);
				chapterList = resourceService.getChildChpaterList(currentid);

			} else
			{
				chapterInfor.setUpperChapterId(res.getParentId());
				chapterList = resourceService.getParallelChpaterList(currentid);

			}
		} else
		{
			chapterInfor.setUpperChapterId(currentid);
			chapterList = resourceService.getChildChpaterList(currentid);
			chapterInfor.setChooseId(currentid);
			chapterInfor.setChpaterName("全部章节");
		}

		Resources resources = new Resources();

		if (currentid != 0)
		{
			resources.setId(currentid);
		}
		resources.setOrderType(orderType);
		resources.setTypes(types);
		List<Resources> resourcesList = resourceService.getResourcesList(resources);
		for (Resources resource : resourcesList)
		{
			if (null != resource.getImgsrc())
			{
				String imgPath = filesystem + resource.getImgsrc();
				resource.setImgsrc(imgPath);
			}
		}
		map.addAttribute("orderType", orderType);
		map.addAttribute("chapterList", chapterList);
		map.addAttribute("chapterInfor", chapterInfor);
		map.addAttribute("resourcesList", resourcesList);
		map.addAttribute("headLineList", headLineList);
		return "/resource/courseView";

	}

	@RequestMapping("/resouceVedeo")
	public String resouceVedeo(Model model)
	{
		model.addAttribute("result", ChapterList());
		return "/resource/video2";
	}

	@RequestMapping("/personal")
	public String PersonalCenter(ModelMap map, HttpSession session, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			User user = new User();

			HttpSession sessions = request.getSession();
			user = (User) sessions.getAttribute(userInfo);
			if (user != null)
			{
				String img = user.getImgSrc();
				if (img != null && img != "")
				{
					if (user.getImgSrc().contains(filesystem) == false)
					{
						String imgPath = filesystem + img;
						user.setImgSrc(imgPath);
					}

				}

			}

			map.addAttribute("user", user);

			// map.addAttribute("result", ChapterList());

		} catch (Exception e)
		{
			log.error("个人信息主页加载出错··························", e);
		}

		return "/PersonalCenter/Index";
	}


	@RequestMapping("/selectYouLike")
	@ResponseBody
	public List<Resources> SelectYouLike(int m, int n, HttpServletRequest request)
	{
		HttpSession sessions = request.getSession();
		User user = (User) sessions.getAttribute(userInfo);
		// 用户登录时
		List<Resources> list = new ArrayList<Resources>();
		// 推荐 用户没登录时
		if (user != null)
		{
			list = resourceService.selectYouLike(user.getUserId(), (Integer) m, (Integer) n);
		}

		if (list.size() < 5 && list.size() > 0)
		{

			Integer i = 5 - list.size();
			List<Resources> newlist = resourceService.selectLimitByTimeOrder(1, 0, i);
			list.addAll(newlist);

		} else if (list.size() == 0)
		{
			list = resourceService.selectLimitByTimeOrder(1, 0, 5);
		}

		try
		{
			for (Resources resources : list)
			{
				String imgPath = filesystem + resources.getImgsrc();
				resources.setImgsrc(imgPath);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("SelectYouLike", e);
		}

		return list;
	}

	
	@RequestMapping("/selectLimit")
	@ResponseBody
	public List<Resources> selectLimit(int m, int n)
	{
		List<Resources> list = resourceService.selectLimit((Integer) m, (Integer) n);
		try
		{
			for (Resources resources : list)
			{
				String imgPath = filesystem + resources.getImgsrc();
				resources.setImgsrc(imgPath);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("selectLimit", e);
		}
		return list;
	}

	@RequestMapping(value = "/getTeacherInfro/{userId}", method = RequestMethod.GET)
	public String getTeacherInfro(@PathVariable("userId") Integer userId,Model model,HttpServletRequest request)
	{

		try
		{
			ResponseInfro information = resourceService.getPageByTeacher(userId);
			

			UserVo user = userService.getTeacherInfro(userId);
			if (null != user.getImgSrc())
			{
				String imgPath = filesystem + user.getImgSrc();
				user.setImgSrc(imgPath);
			}
			HttpSession sessions = request.getSession();
			User userInfor = (User) sessions.getAttribute(userInfo);
		
			if (userInfor != null)
			{    	String img = userInfor.getImgSrc();
			if (img != null && img != "")
			{
				if (userInfor.getImgSrc().contains(filesystem) == false)
				{
					String imgPath = filesystem + img;
					userInfor.setImgSrc(imgPath);
				}

			}
			User typeChecker = userService.checkUserType(userInfor.getUserName());
			model.addAttribute("user",userInfor);
			model.addAttribute("typeChecker",typeChecker);
			}
			model.addAttribute("result",ChapterList());
			model.addAttribute("systermPath", filesystem);
			model.addAttribute("information", information);
			model.addAttribute("teacher", user);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("getTeacherInfro", e);
		}
		return "/teacher";
	}

	public class Chapter
	{
		public Resources chapterdirectories;
		public Resources hot;

		public Resources getChapterdirectories()
		{
			return chapterdirectories;
		}

		public void setChapterdirectories(Resources chapterdirectories)
		{
			this.chapterdirectories = chapterdirectories;
		}

		public Resources getHot()
		{
			return hot;

		}

		public void setHot(Resources hot)
		{
			this.hot = hot;

		}

		List<Resources> listChapter = new ArrayList<Resources>();

		public List<Resources> getListChapter()
		{
			return listChapter;
		}

		public void setListChapter(List<Resources> listChapter)
		{
			this.listChapter = listChapter;
		}
	}

}
