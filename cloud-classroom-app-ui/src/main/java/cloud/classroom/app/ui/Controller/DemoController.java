package cloud.classroom.app.ui.Controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import cloud.classroom.app.ui.Mail;
import cloud.classroom.app.ui.exception.RestFullException;
import cloud.classroom.app.ui.service.Demo_UserService;
import cloud.common.helper.Base64Helper;
import cloud.common.helper.FileHelper;
import cloud.common.helper.HttpHelper;
import cloud.common.util.MessageNotifyUtil;
import cloud.entity.classroom.user.Users;

@Controller
@RequestMapping("/hello")
public class DemoController
{
	private static Logger log = LoggerFactory.getLogger(DemoController.class);

	@Autowired
	Demo_UserService userservice;

	@RequestMapping("/getAll")
	public List<Users> getAll() throws Exception
	{

		return userservice.selectAll();

		// throw new Exception();

	}

	@RequestMapping("/hello")
	public String hello() throws Exception
	{
		try
		{
			Mail mail = new Mail();
			mail.sendSimpleMail();
		} catch (Exception e)
		{
			log.error("发送失败 ", e);
		}

		// throw new Exception();
		return "你是煮";

	}

	@ResponseBody
	@RequestMapping("/hellorest")
	public String HelloRest() throws RestFullException
	{
		throw new RestFullException("yaya");
		// return "你是煮";
	}

	@RequestMapping("/")
	public String index(ModelMap map)
	{
		int a = -1;
		try
		{
			a = userservice.getCount();

		} catch (Exception e)
		{
			// TODO: handle exception
		}

		map.addAttribute("host", "http://baidu.com");
		map.addAttribute("countUser", a);
		return "DemoIndex";
	}

	@RequestMapping("uploadtest")
	public String UploadTest(ModelMap map)
	{
		return "/UploadIndex";
	}


	@RequestMapping(value = "uploadbigfiles", method = RequestMethod.POST)
	@ResponseBody
	public String uploadfiles(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "uploadfile", required = false) List<MultipartFile> files,
			@RequestParam(value = "parentid", required = false) String parentid)
	{

		response.setContentType("text/html;charset=utf-8");

		Map<String, Object> map = this.multipleUpload(request, files);

		String result = JSON.toJSONString(map);
		System.out.println(result);
		return result;
	}

	@Value("${dfs-fdfs-file-api}")
	private String dfsFdfsFileApi;
	Map<String, String> paramMap = Maps.newHashMap();

	protected Map<String, Object> multipleUpload(HttpServletRequest request, List<MultipartFile> files)// throws
																										// IllegalStateException,
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
				List<MultipartFile> fileList = multiValueMap.get("uploadfile");
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