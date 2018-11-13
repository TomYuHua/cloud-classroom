package cloud.classroom.app.ui.AdminBackend.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.ScatteringByteChannel;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.STRING;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

import cloud.classroom.app.ui.annotation.Permission;
import cloud.classroom.app.ui.service.interfaces.ChapterService;
import cloud.classroom.app.ui.service.interfaces.ResourceAdminService;
import cloud.classroom.app.ui.service.interfaces.ResourceService;
import cloud.common.helper.Base64Helper;
import cloud.common.helper.FileHelper;
import cloud.common.helper.HttpHelper;
import cloud.common.util.MessageNotifyUtil;
import cloud.entity.classroom.Resources.Resources;
import cloud.entity.classroom.every.User;

@Permission
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/Resources")
public class ResourceAdminController
{
	@RequestMapping("/videos")
	public String index()
	{
		return "/AdminBackend/videos";
	}

	private static Logger log = LoggerFactory.getLogger(ResourceAdminController.class);

	@Autowired
	public ResourceAdminService resourceAdminService;

	@Autowired
	ChapterService chapterService;

	@Value("${dfs-filesystem}")
	private String filesystem;

	private static final String userInfo = "userInfoId";

	@RequestMapping(value = "/resourceRight", method = { RequestMethod.GET })
	@ResponseBody
	public JSONObject resourceRight()
	{
		JSONObject jsonObj = new JSONObject();

		jsonObj.put("string", filesystem);

		return jsonObj;
	}

	@RequestMapping(value = "/insert", method = { RequestMethod.POST })
	@ResponseBody
	public JSONObject insert(@RequestBody(required = false) Resources record)
	{

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("int", resourceAdminService.insert(record));

		return jsonObj;
	}

	@RequestMapping(value = "/insertfile", method = { RequestMethod.POST })
	@ResponseBody
	public JSONObject insertfile(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) Resources record)
	{
		User user = new User();

		HttpSession sessions = request.getSession();
		user = (User) sessions.getAttribute(userInfo);

		record.setUserid(user.getUserId());
		record.setCreateauthor(user.getName());
		record.setSort(Integer.valueOf(record.getSort()));

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("int", resourceAdminService.insertfile(record));

		return jsonObj;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public JSONObject delete(@RequestParam(required = false, name = "ids") String ids)
	{
		JSONObject jsonObj = new JSONObject();

		try
		{
			String[] item = ids.split(",");
			List<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < item.length; i++)
			{
				list.add(new Integer(item[i]));
			}

			if (resourceAdminService.delete(list))
			{
				jsonObj.put("string", "success");
			} else
			{
				jsonObj.put("string", "false");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return jsonObj;
	}

	@RequestMapping(value = "/changefile", method = { RequestMethod.POST })
	@ResponseBody
	public JSONObject changeResources(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "name", required = false) String name, @RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "sort", required = false) String sort)
	{
		Resources resources = new Resources();
		JSONObject jsonObj = new JSONObject();
		resources.setName(name);
		resources.setId(Integer.parseInt(id));
		resources.setSort(Integer.parseInt(sort));

		if (resourceAdminService.changefile(resources))
		{
			jsonObj.put("string", "ok");
		} else
		{
			jsonObj.put("string", "false");
		}

		return jsonObj;
	}

	@RequestMapping(value = "/getcontent", method = { RequestMethod.POST })
	@ResponseBody
	public JSONObject getContent(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "Types", required = true) String types, @RequestParam(value = "id", required = true) String id)
	{
		JSONObject jsonObj = new JSONObject();
		if (Integer.parseInt(types) == 3)
		{
			String content = resourceAdminService.getContent(Integer.parseInt(id));

			jsonObj.put("content", content);
			jsonObj.put("state", true);
		} else
		{
			jsonObj.put("state", false);
		}

		return jsonObj;
	}

	@RequestMapping(value = "/change", method = { RequestMethod.POST })
	@ResponseBody
	public JSONObject changeResources(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "note", required = false) String describes, @RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "img", required = false) List<MultipartFile> img, @RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "sort", required = false) String sort, @RequestParam(value = "Types", required = true) int types,
			@RequestParam(value = "text", required = false) String text)
	{
		String imgsrc;
		String path = "img";
		Resources resources = new Resources();
		JSONObject jsonObj = new JSONObject();

    
		if (!img.get(0).isEmpty())
		{
			Map<String, Object> map = this.multipleUpload(request, img, path);
			List<Map> list = (List<Map>) map.get("uploadlist");
			String status = (String) list.get(0).get("isSuccess");

			if (status.equals("success"))
			{
				imgsrc = (String) list.get(0).get("fileid");
				resources.setImgsrc(imgsrc);
			}
		}

		resources.setName(name);
		resources.setDescribes(describes);
		resources.setId(Integer.valueOf(id));
		resources.setSort(Integer.valueOf(sort));
        resources.setTypes(types);
	     if (types == 3)
		{
			resources.setContents(text);
		}

		if (resourceAdminService.changeResources(resources))
		{
			jsonObj.put("string", "ok");
		} else
		{
			jsonObj.put("string", "false");
		}

		return jsonObj;
	}

	@RequestMapping(value = "/check", method = { RequestMethod.POST })
	@ResponseBody
	public JSONObject checkResources(@RequestBody(required = false) Resources resources)
	{
		JSONObject jsonObj = new JSONObject();

		if (resourceAdminService.checkResources(resources))
		{
			jsonObj.put("string", "ok");
		} else
		{
			jsonObj.put("string", "false");
		}

		return jsonObj;
	}

	@RequestMapping("/selectAll")
	@ResponseBody
	public JSONObject selectAll()
	{

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("list", resourceAdminService.selectAll());

		return jsonObj;
	}

	@RequestMapping("/uploadtext")
	@ResponseBody
	private boolean uploadtext(String name, String parentId, User user, int types, String describes, String text, String imgsrc, String sort)
	{
		int parentid = Integer.parseInt(parentId);
		Timestamp d = new Timestamp(System.currentTimeMillis());

		Resources resources = new Resources();
		resources.setName(name);
		resources.setCreateauthor(user.getName());
		resources.setIsdocument(false);
		resources.setUserid(user.getUserId());
		resources.setParentId(parentid);
		resources.setTypes(types);
		resources.setDescribes(describes);
		resources.setContents(text);
		resources.setClickcount(0);
		resources.setIsopen(false);
		resources.setStatus((byte) 0);
		resources.setCreatetime(d);
		resources.setImgsrc(imgsrc);
		resources.setSort(Integer.valueOf(sort));

		resourceAdminService.uploadtext(resources);

		return true;
	}

	@RequestMapping("/uploadfiles")
	@ResponseBody
	private boolean uploadfiles(List<MultipartFile> files, String name, String parentId, User user, int types, String describes, String fileid,
			String imgsrc, String sort)
	{
		int parentid = Integer.parseInt(parentId);
		Timestamp d = new Timestamp(System.currentTimeMillis());
		List<Resources> resources = new ArrayList<Resources>();

		for (int i = 0; i != files.size(); ++i)
		{
			Resources resource = new Resources();
			resource.setName(name);
			resource.setCreateauthor(user.getName());
			resource.setUserid(user.getUserId());
			resource.setParentId(parentid);
			resource.setTypes(types);
			resource.setDescribes(describes);
			resource.setIsdocument(false);
			resource.setClickcount(0);
			resource.setIsopen(false);
			resource.setStatus((byte) 0);
			resource.setResourcepath(fileid);
			resource.setCreatetime(d);
			resource.setImgsrc(imgsrc);
			resource.setSort(Integer.valueOf(sort));
			resources.add(resource);
		}

		resourceAdminService.uploadfiles(resources);

		return true;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadbigfiles", method = RequestMethod.POST)
	public String uploadfiles(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "uploadfile", required = false) List<MultipartFile> files,
			@RequestParam(value = "parentid", required = true) String parentId, @RequestParam(value = "note", required = false) String describes,
			@RequestParam(value = "Types", required = true) int types, @RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "text", required = false) String text, @RequestParam(value = "img", required = false) List<MultipartFile> img,
			@RequestParam(value = "sort", required = true) String sort)
	{
		String result;
		String path = null;
		String imgsrc = null;
		User user = new User();

		HttpSession sessions = request.getSession();
		user = (User) sessions.getAttribute(userInfo);

		response.setContentType("text/html;charset=utf-8");

		path = "img";
		Map<String, Object> map_img = this.multipleUpload(request, img, path);
		List<Map> list_img = (List<Map>) map_img.get("uploadlist");
		String status_img = (String) list_img.get(0).get("isSuccess");

		if (status_img.equals("success"))
		{
			imgsrc = (String) list_img.get(0).get("fileid");
		}

		if (types == 3)
		{
			boolean re3 = uploadtext(name, parentId, user, 3, describes, text, imgsrc, sort);

			result = "{\"state\":true}";
		} else
		{
			path = "uploadfile";
			Map<String, Object> map = this.multipleUpload(request, files, path);

			List<Map> list = (List<Map>) map.get("uploadlist");
			String status = (String) list.get(0).get("isSuccess");
			String fileid = (String) list.get(0).get("fileid");

			if (status.equals("success"))
			{
				switch (types)
				{

				case 1:
					boolean re1 = uploadfiles(files, name, parentId, user, 1, describes, fileid, imgsrc, sort);
					break;

				case 2:
					boolean re2 = uploadfiles(files, name, parentId, user, 2, describes, fileid, imgsrc, sort);
					break;

				}

				result = "{\"state\":true}";
			} else
			{
				result = "{\"state\":false}";
			}
		}

		System.out.println(result);
		return result;
	}

	@Value("${dfs-fdfs-file-api}")
	private String dfsFdfsFileApi;
	Map<String, String> paramMap = Maps.newHashMap();

	protected Map<String, Object> multipleUpload(HttpServletRequest request, List<MultipartFile> files, String path)// throws
	// IOException
	{
		// 失败 上传中 成功
		Map<String, Object> map = new HashMap();
		try
		{

			for (MultipartFile file : files)
			{
				if (file == null)
				{
					map.put("status", -1);
					map.put("statusInfo", "上传参数不能为空");
					return map;
				}
			}
			ServletContext sc = request.getSession().getServletContext();
			// 创建一个通用的多部分解析器
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(sc);
			// 判断 request 是否有文件上传,即多部分请求
			if (multipartResolver.isMultipart(request))
			{
				// 转换成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				// Enumeration params = multiRequest.getParameterNames();
				// String upDir = null;
				// while (params.hasMoreElements())
				// {
				// String name = (String) params.nextElement();
				// upDir = multiRequest.getParameter("upDir");
				// }
				// 取得request中的所有文件名
				// Iterator<String> iter = multiRequest.getFileNames();
				List<Map> list = new ArrayList<>();
				MultiValueMap<String, MultipartFile> multiValueMap = multiRequest.getMultiFileMap();
				List<MultipartFile> fileList = multiValueMap.get(path);
				for (MultipartFile file : fileList)
				{
					// 这里上传多张图片
					StringBuffer url = new StringBuffer("");
					Map<String, String> currentMap = new HashMap<>();
					if (file != null)
					{
						// 取得当前上传文件的文件名称
						String originalFilename = file.getOriginalFilename();
						// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
						if (originalFilename != null && originalFilename.trim() != "")
						{
							String suffix = "";

							if (originalFilename.indexOf(".") > -1)
							{
								suffix = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
							}

							// if (isPassSize(file.getSize())) {
							// map.put("stateInfo",fileSize);
							// return map;
							// }
							// 重命名上传后的文件名
							// String fileName = originalFilename + suffix;

							InputStream in = file.getInputStream();
							// 创建一个缓冲区
							byte buffer[] = new byte[1024 * 1024];
							// 判断输入流中的数据是否已经读完的标识
							int len = 0;
							long fileSize = file.getSize();
							// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
							Integer uploadSize = 0;
							String fileid = "";
							Integer offset = 0;
							String fileName = originalFilename.trim();

							while ((len = in.read(buffer)) > 0)
							{
								fileid = UploadFile(buffer, len, fileid, offset, fileName);
								if (fileid == "")
								{
									currentMap.put("uploadSize", String.valueOf(uploadSize));
									break;

								} else
								{
									uploadSize += len;
								}

							}
							currentMap.put("fileName", fileName);
							currentMap.put("fileid", fileid);
							if (fileSize == uploadSize)
							{
								currentMap.put("isSuccess", "success");
								currentMap.put("uploadSize", String.valueOf(uploadSize));

							} else
							{
								currentMap.put("isSuccess", "fail");

							}
							// map1.put("imagePath", allUrl.toString());
							// map1.put("imageName", "");

							list.add(currentMap);
						}
					}
				}
				map.put("status", 0);
				map.put("statusInfo", "success");
				map.put("uploadlist", list);
			}
		} catch (Exception e)
		{
			map.put("status", -2);
			map.put("statusInfo", e.getMessage());
			e.printStackTrace();
			log.error("", e);
			// TODO: handle exception
		}

		return map;
	}

	private String UploadFile(byte[] buffer, int len, String fileid, Integer offset, String fileName)
	{

		Integer i = 0;
		String returnFileid = "";
		while (true)
		{

			if (i > 3)
			{
				break;
			}

			try
			{
				paramMap.clear();
				paramMap.put("fileStr", Base64Helper.encode(buffer));
				paramMap.put("fileExtName", FileHelper.getExtension(fileName, true));
				paramMap.put("fileid", fileid);
				paramMap.put("offset", String.valueOf(offset));
				paramMap.put("length", String.valueOf(len));
				String result = HttpHelper.URLPost(dfsFdfsFileApi + "/uploadAppendByOffset", paramMap, "utf-8");

				if (result != "")
				{
					MessageNotifyUtil message = JSON.parseObject(result, MessageNotifyUtil.class);
					returnFileid = message.getAppendMsg().get("fileid").toString();
					System.out.println(result);
					// isSuccess = 1;
					break;
				} else
				{
					// 上传失败 重新传3次
					System.out.println("....................上传不成功 返回值为空......................." + offset.toString());
				}
			} catch (Exception e)
			{
				// 上传失败 重新传3次

				e.printStackTrace();
				log.error("", e);
				// TODO: handle exception
			}
			i++;

		}
		return returnFileid;
	}
}
