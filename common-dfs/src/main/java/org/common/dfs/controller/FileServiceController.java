package org.common.dfs.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

//import com.xk.common.ThumbImgInfo;
import org.common.dfs.common.NameValuePair;
import org.common.dfs.constant.Constant;
import org.common.dfs.fastdfs.FileInfo;
import org.common.dfs.fastdfs.ProtoCommon;
import org.common.dfs.pool.FdfsClient;
import org.common.dfs.pool.FdfsClientFactory;
import org.common.dfs.pool.FdfsPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cloud.common.constant.FileServiceConstant;
import cloud.common.helper.Base64Helper;
import cloud.common.helper.FileHelper;
import cloud.common.util.ImgConvertUtil;
import cloud.common.util.MessageNotifyUtil;

/**
 * 文件服务器Controller
 * 
 * 
 *
 */
// @Controller
@RestController
@RequestMapping("")
public class FileServiceController
{
	private static Logger log = LoggerFactory.getLogger(FileServiceController.class);
	/**
	 * 连接池
	 */
	private static FdfsPool fdfsPool = null;

	/**
	 * 初始化连接池
	 */
	static
	{
		try
		{
			FdfsClientFactory factory = new FdfsClientFactory(Constant.CONF_FILE_PATH);
			GenericObjectPoolConfig config = new GenericObjectPoolConfig();
			config.setMaxTotal(Constant.MAX_TOTAL);
			fdfsPool = new FdfsPool(config, factory);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 获取连接池中连接对象
	 * 
	 * @return
	 */
	private synchronized FdfsClient getFdfsClient()
	{
		// 尝试次数
		int count = 0;
		FdfsClient fdfsClient = null;
		while (true)
		{
			fdfsClient = fdfsPool.getResource();
			if (fdfsClient == null)
			{
				count++;
			} else
			{
				break;
			}
			// 尝试三次
			if (count >= FileServiceConstant.MAX_TRY_COUNT)
			{
				break;
			}
		}
		return fdfsClient;
	}

	@ResponseBody
	@RequestMapping(value = "/uploads", method = { RequestMethod.GET })
	public String uploadFiles()
	{
		log.info("nishizhu");
		return "nishi";
	}

	/**
	 * 小文件一次性上传
	 * 
	 * @param request
	 * @param fileStr
	 *            base64文件字符串
	 * @param fileExtName
	 *            文件后缀名
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/upload", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public MessageNotifyUtil uploadFile(HttpServletRequest request, @RequestParam(FileServiceConstant.FILE_STR) String fileStr,
			@RequestParam(FileServiceConstant.FILE_EXT_NAME) String fileExtName) throws Exception
	{
		log.info("nishizhu 进来 upload方法啦````````````````````````````````````");
		// 返回的对象
		MessageNotifyUtil messageNotifyUtil = new MessageNotifyUtil();
		// 消息map
		Map<String, Object> map = Maps.newHashMap();
		// 文件的base64编码
		// String fileStr = request.getParameter(FileServiceConstant.FILE_STR);
		// 文件后缀名
		// String fileExtName =
		// request.getParameter(FileServiceConstant.FILE_EXT_NAME);
		// 上传是否成功标志
		int hasSuccess = 0;
		// 本次上传成功字节数
		long uploadSize = 0;
		// 转码字节流
		byte[] buffer = null;
		try
		{
			// fastjson 解码速度赶不上base64
			// buffer = JSON.parseObject(fileStr, byte[].class);
			buffer = Base64Helper.decode(fileStr);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		// 如果转码失败，则上传失败
		if (buffer == null)
		{
			messageNotifyUtil.setCode(2002);
			// messageNotifyUtil.setMsg("上传失败，文件字符串不是byte[]类型");
			messageNotifyUtil.setMsg("上传失败，文件字符串不是base64编码");
			return messageNotifyUtil;
		}
		// 判断文件名字
		if (StringUtils.isBlank(fileExtName))
		{
			messageNotifyUtil.setCode(2015);
			// messageNotifyUtil.setMsg("上传失败，文件字符串不是byte[]类型");
			messageNotifyUtil.setMsg("上传失败，文件名称不能位空");
			return messageNotifyUtil;
		}
		// fileid
		String fileid = null;
		FdfsClient client = getFdfsClient();
		if (client == null)
		{
			messageNotifyUtil.setCode(2014);
			messageNotifyUtil.setMsg("不能连接文件服务器");
			return messageNotifyUtil;
		}
		// 创建fileid
		// 尝试次数
		int count = 0;
		while (true)
		{
			try
			{
				// 上传一个0字节，获取一个fileid
				fileid = client.upload_appender_file1(new byte[] {}, fileExtName, null);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			if (fileid == null)
			{
				count++;
				fdfsPool.returnResource(client);
				client = getFdfsClient();
			} else
			{
				break;
			}
			// 尝试三次
			if (count >= FileServiceConstant.MAX_TRY_COUNT)
			{
				count = 0;
				break;
			}
		}
		// 如果fileid位空则上传失败
		if (StringUtils.isBlank(fileid))
		{
			messageNotifyUtil.setCode(2003);
			messageNotifyUtil.setMsg("上传失败，不能创建fileid");
			fdfsPool.returnResource(client);
			return messageNotifyUtil;
		}
		map.put(FileServiceConstant.FILE_ID, fileid);
		// 空文件
		if (buffer.length == 0)
		{
			messageNotifyUtil.setCode(200);
			messageNotifyUtil.setMsg("上传成功");
			map.put(FileServiceConstant.UPLOAD_SIZE, uploadSize);
			messageNotifyUtil.setAppendMsg(map);
			fdfsPool.returnResource(client);
			return messageNotifyUtil;
		}
		// 上传文件
		try
		{
			hasSuccess = uploadFile(fileid, buffer);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		FileInfo fileInfo = client.get_file_info1(fileid);
		if (fileInfo != null)
		{
			uploadSize = fileInfo.getFileSize();
			map.put(FileServiceConstant.UPLOAD_SIZE, uploadSize);
		}
		if (hasSuccess == 0)
		{
			// 上传失败，需要删除fileid
			client.delete_file1(fileid);
			// 返回信息
			messageNotifyUtil.setCode(2001);
			messageNotifyUtil.setMsg("上传失败");
			fdfsPool.returnResource(client);
			return messageNotifyUtil;

		} else
		{
			messageNotifyUtil.setCode(200);
			messageNotifyUtil.setMsg("上传成功");
			messageNotifyUtil.setAppendMsg(map);
			fdfsPool.returnResource(client);
			return messageNotifyUtil;
		}
	}

	/**
	 * 文件续传
	 * 
	 * @param request
	 * @param fileStr
	 *            文件base64字符串
	 * @param fileid
	 *            文件id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadAppendByOffset", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public MessageNotifyUtil uploadFileAppend(HttpServletRequest request, @RequestParam(FileServiceConstant.FILE_STR) String fileStr,
			@RequestParam(value = FileServiceConstant.FILE_ID, required = false) String fileid,
			@RequestParam(value = FileServiceConstant.FILE_EXT_NAME, required = false) String fileExtName,
			@RequestParam(value = "offset", required = false) int offset, @RequestParam(value = "length", required = false) int length)
			throws Exception
	{
		// fileid
		String newFileid = null;
		// 返回的对象
		MessageNotifyUtil messageNotifyUtil = new MessageNotifyUtil();
		// 消息map
		Map<String, Object> map = Maps.newHashMap();
		// 文件的base64编码
		// String fileStr = request.getParameter(FileServiceConstant.FILE_STR);
		// 文件在服务器上的id
		// String fileid = request.getParameter(FileServiceConstant.FILE_ID);
		// 上传是否成功标志
		int hasSuccess = 0;
		// 本次上传成功字节数
		long uploadSize = 0;
		// 转码字节流
		byte[] buffer = null;
		try
		{
			// fastjson 解码速度赶不上base64
			// buffer = JSON.parseObject(fileStr, byte[].class);
			buffer = Base64Helper.decode(fileStr);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		// 如果转码失败，则上传失败
		if (buffer == null)
		{
			messageNotifyUtil.setCode(2002);
			// messageNotifyUtil.setMsg("上传失败，文件字符串不是byte[]类型");
			messageNotifyUtil.setMsg("上传失败，文件字符串不是base64编码");
			return messageNotifyUtil;
		}
		// 获取连接
		FdfsClient client = getFdfsClient();
		if (client == null)
		{
			messageNotifyUtil.setCode(2014);
			messageNotifyUtil.setMsg("不能连接文件服务器");
			return messageNotifyUtil;
		}
		// 判断fileid是否操作，如果没有是第一包，否则是其他包
		if (StringUtils.isBlank(fileid))
		{
			// 判断文件名字
			if (StringUtils.isBlank(fileExtName))
			{
				messageNotifyUtil.setCode(2015);
				// messageNotifyUtil.setMsg("上传失败，文件字符串不是byte[]类型");
				messageNotifyUtil.setMsg("上传失败，文件名称不能位空");
				return messageNotifyUtil;
			}
			// 创建fileid
			// 尝试次数
			int count = 0;
			while (true)
			{
				try
				{
					// 上传一个0字节，获取一个fileid
					newFileid = client.upload_appender_file1(new byte[] {}, fileExtName, null);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				if (newFileid == null)
				{
					count++;
					fdfsPool.returnResource(client);
					client = getFdfsClient();
				} else
				{
					break;
				}
				// 尝试三次
				if (count >= FileServiceConstant.MAX_TRY_COUNT)
				{
					count = 0;
					break;
				}
			}
			// 如果fileid位空则上传失败
			if (StringUtils.isBlank(newFileid))
			{
				messageNotifyUtil.setCode(2003);
				messageNotifyUtil.setMsg("上传失败，不能创建fileid");
				fdfsPool.returnResource(client);
				return messageNotifyUtil;
			}
		} else
		{
			newFileid = fileid;
		}
		map.put(FileServiceConstant.FILE_ID, newFileid);
		// 空文件
		if (buffer.length == 0)
		{
			messageNotifyUtil.setCode(200);
			messageNotifyUtil.setMsg("上传成功");
			map.put(FileServiceConstant.UPLOAD_SIZE, uploadSize);
			messageNotifyUtil.setAppendMsg(map);
			return messageNotifyUtil;
		}
		// 续传文件
		try
		{
			hasSuccess = uploadFile(newFileid, buffer, offset, length);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		// 获取文件信息
		FileInfo fileInfo = client.get_file_info1(newFileid);
		if (fileInfo != null)
		{
			uploadSize = fileInfo.getFileSize();
			map.put(FileServiceConstant.UPLOAD_SIZE, uploadSize);
		}
		if (hasSuccess == 0)
		{
			messageNotifyUtil.setCode(2001);
			messageNotifyUtil.setMsg("上传失败");
			fdfsPool.returnResource(client);
			return messageNotifyUtil;

		} else
		{
			messageNotifyUtil.setCode(200);
			messageNotifyUtil.setMsg("上传成功");
			messageNotifyUtil.setAppendMsg(map);
			fdfsPool.returnResource(client);
			return messageNotifyUtil;
		}
	}

	/**
	 * 文件续传
	 * 
	 * @param request
	 * @param fileStr
	 *            文件base64字符串
	 * @param fileid
	 *            文件id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadAppend", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public MessageNotifyUtil uploadFileAppend(HttpServletRequest request, @RequestParam(FileServiceConstant.FILE_STR) String fileStr,
			@RequestParam(value = FileServiceConstant.FILE_ID, required = false) String fileid,
			@RequestParam(value = FileServiceConstant.FILE_EXT_NAME, required = false) String fileExtName) throws Exception
	{
		// fileid
		String newFileid = null;
		// 返回的对象
		MessageNotifyUtil messageNotifyUtil = new MessageNotifyUtil();
		// 消息map
		Map<String, Object> map = Maps.newHashMap();
		// 文件的base64编码
		// String fileStr = request.getParameter(FileServiceConstant.FILE_STR);
		// 文件在服务器上的id
		// String fileid = request.getParameter(FileServiceConstant.FILE_ID);
		// 上传是否成功标志
		int hasSuccess = 0;
		// 本次上传成功字节数
		long uploadSize = 0;
		// 转码字节流
		byte[] buffer = null;
		try
		{
			// fastjson 解码速度赶不上base64
			// buffer = JSON.parseObject(fileStr, byte[].class);
			buffer = Base64Helper.decode(fileStr);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		// 如果转码失败，则上传失败
		if (buffer == null)
		{
			messageNotifyUtil.setCode(2002);
			// messageNotifyUtil.setMsg("上传失败，文件字符串不是byte[]类型");
			messageNotifyUtil.setMsg("上传失败，文件字符串不是base64编码");
			return messageNotifyUtil;
		}
		// 获取连接
		FdfsClient client = getFdfsClient();
		if (client == null)
		{
			messageNotifyUtil.setCode(2014);
			messageNotifyUtil.setMsg("不能连接文件服务器");
			return messageNotifyUtil;
		}
		// 判断fileid是否操作，如果没有是第一包，否则是其他包
		if (StringUtils.isBlank(fileid))
		{
			// 判断文件名字
			if (StringUtils.isBlank(fileExtName))
			{
				messageNotifyUtil.setCode(2015);
				// messageNotifyUtil.setMsg("上传失败，文件字符串不是byte[]类型");
				messageNotifyUtil.setMsg("上传失败，文件名称不能位空");
				return messageNotifyUtil;
			}
			// 创建fileid
			// 尝试次数
			int count = 0;
			while (true)
			{
				try
				{
					// 上传一个0字节，获取一个fileid
					newFileid = client.upload_appender_file1(new byte[] {}, fileExtName, null);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				if (newFileid == null)
				{
					count++;
					fdfsPool.returnResource(client);
					client = getFdfsClient();
				} else
				{
					break;
				}
				// 尝试三次
				if (count >= FileServiceConstant.MAX_TRY_COUNT)
				{
					count = 0;
					break;
				}
			}
			// 如果fileid位空则上传失败
			if (StringUtils.isBlank(newFileid))
			{
				messageNotifyUtil.setCode(2003);
				messageNotifyUtil.setMsg("上传失败，不能创建fileid");
				fdfsPool.returnResource(client);
				return messageNotifyUtil;
			}
		} else
		{
			newFileid = fileid;
		}
		map.put(FileServiceConstant.FILE_ID, newFileid);
		// 空文件
		if (buffer.length == 0)
		{
			messageNotifyUtil.setCode(200);
			messageNotifyUtil.setMsg("上传成功");
			map.put(FileServiceConstant.UPLOAD_SIZE, uploadSize);
			messageNotifyUtil.setAppendMsg(map);
			return messageNotifyUtil;
		}
		// 续传文件
		try
		{
			hasSuccess = uploadFile(newFileid, buffer);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		// 获取文件信息
		FileInfo fileInfo = client.get_file_info1(newFileid);
		if (fileInfo != null)
		{
			uploadSize = fileInfo.getFileSize();
			map.put(FileServiceConstant.UPLOAD_SIZE, uploadSize);
		}
		if (hasSuccess == 0)
		{
			messageNotifyUtil.setCode(2001);
			messageNotifyUtil.setMsg("上传失败");
			fdfsPool.returnResource(client);
			return messageNotifyUtil;

		} else
		{
			messageNotifyUtil.setCode(200);
			messageNotifyUtil.setMsg("上传成功");
			messageNotifyUtil.setAppendMsg(map);
			fdfsPool.returnResource(client);
			return messageNotifyUtil;
		}
	}

	/**
	 * 生成缩略图
	 * 
	 * @param request
	 * @param fileid
	 *            文件id
	 * @param thumbImgList
	 *            缩略图信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/thumbnail", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public MessageNotifyUtil thumbnail(HttpServletRequest request, @RequestParam(FileServiceConstant.FILE_ID) String fileid,
			@RequestParam(FileServiceConstant.THUMB_IMG_LIST) String thumbImgListJson) throws Exception
	{
		// 返回的对象
		MessageNotifyUtil messageNotifyUtil = new MessageNotifyUtil();
		// 文件在服务器上的id
		// String fileid = request.getParameter(FileServiceConstant.FILE_ID);
		// 缩略图
		// String thumbImgListJson =
		// request.getParameter(FileServiceConstant.THUMB_IMG_LIST);
		List<ThumbImgInfo> list = JSON.parseArray(thumbImgListJson, ThumbImgInfo.class);
		// 如果list为空或者0或者fileid为空
		if (list == null || list.isEmpty() || StringUtils.isBlank(fileid))
		{
			messageNotifyUtil.setCode(2011);
			messageNotifyUtil.setMsg("生成缩略图失败，缩略图信息字符串错误");
			return messageNotifyUtil;
		}
		// 判断是否图片
		if ((StringUtils.isBlank(fileid)) || (!FileHelper.isPic(fileid)))
		{
			messageNotifyUtil.setCode(2013);
			messageNotifyUtil.setMsg("该文件不是图片文件");
			return messageNotifyUtil;
		}
		byte[] buffer = null;
		FdfsClient client = getFdfsClient();
		if (client == null)
		{
			messageNotifyUtil.setCode(2014);
			messageNotifyUtil.setMsg("不能连接文件服务器");
			return messageNotifyUtil;
		}
		try
		{
			buffer = client.download_file1(fileid);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if (buffer == null || buffer.length == 0)
		{
			messageNotifyUtil.setCode(2010);
			messageNotifyUtil.setMsg("生成缩略图失败");
			fdfsPool.returnResource(client);
			return messageNotifyUtil;
		}
		// 获取文件以前的元数据
		NameValuePair[] meta_list = client.get_metadata1(fileid);
		// 新元数据
		NameValuePair[] new_meta_list = null;
		NameValuePair nameValuePair;
		// 新元数据要插入的初始位置
		int origin = 0;
		if (meta_list == null || (meta_list.length == 0))
		{
			new_meta_list = new NameValuePair[1];
		} else
		{
			origin = meta_list.length;
			new_meta_list = Arrays.copyOf(meta_list, origin + 1);
		}
		// 读入BufferedImage
		InputStream in = new ByteArrayInputStream(buffer);
		BufferedImage img = javax.imageio.ImageIO.read(in);
		//
		BufferedImage outImg = null;
		ByteArrayOutputStream out = null;
		ThumbImgInfo thumbImgInfo = null;
		byte[] new_buffer = null;
		ImgConvertUtil imgConvertUtil = new ImgConvertUtil();
		String new_fileid = null;
		// 文件后缀名
		String fileExtName = FileHelper.getExtension(fileid, true);
		// 是否操作失败
		boolean isFailure = false;
		// 上传是否成功标志
		int hasSuccess = 0;
		// 遍历生成缩略图
		for (int i = 0; i < list.size(); i++)
		{
			new_fileid = client.upload_appender_file1(new byte[] {}, fileExtName, null);
			// 不能生成fileid
			if (StringUtils.isBlank(new_fileid))
			{
				isFailure = true;
				break;
			}
			// 获取要生成的缩略图信息列表
			thumbImgInfo = list.get(i);
			// 缩放图片ByteArrayOutputStream流
			outImg = imgConvertUtil.convert(img, thumbImgInfo.getWidth(), thumbImgInfo.getHeight(), thumbImgInfo.isChangeScale());
			// 更新图片信息
			thumbImgInfo.setWidth(outImg.getWidth(null));
			thumbImgInfo.setHeight(outImg.getHeight(null));
			// 将缩放图片缓存放到
			out = new ByteArrayOutputStream();
			ImageIO.write(outImg, FileHelper.getExtension(fileid, true), out);
			// 转出byte[]数组
			new_buffer = out.toByteArray();
			// 上传缩略图文件
			hasSuccess = uploadFile(new_fileid, new_buffer);
			// 上传失败
			if (hasSuccess == 0)
			{
				isFailure = true;
				break;
			}
			// 上传成功，更新缩略图的fileid
			thumbImgInfo.setFileid(new_fileid);
		}
		// 缩略图生成失败
		if (isFailure)
		{
			// 删除已上传的缩略图
			for (int i = 0; i < list.size(); i++)
			{
				thumbImgInfo = list.get(i);
				if (StringUtils.isNotBlank(thumbImgInfo.getFileid()))
				{
					// client.delete_file1(thumbImgInfo.getFileid());
				}
			}
			messageNotifyUtil.setCode(2010);
			messageNotifyUtil.setMsg("生成缩略图失败");
			fdfsPool.returnResource(client);
			return messageNotifyUtil;
		}
		// 生成缩略图都成功，更新原文件的元数据
		nameValuePair = new NameValuePair();
		nameValuePair.setName(FileServiceConstant.THUMB_IMG_LIST);
		nameValuePair.setValue(JSON.toJSONString(list));
		new_meta_list[origin] = nameValuePair;
		// 插入元数据
		client.set_metadata1(fileid, new_meta_list, ProtoCommon.STORAGE_SET_METADATA_FLAG_OVERWRITE);
		messageNotifyUtil.setCode(200);
		messageNotifyUtil.setMsg("生成缩略图成功");
		fdfsPool.returnResource(client);
		return messageNotifyUtil;
	}

	/**
	 * 获取缩略图信息
	 * 
	 * @param request
	 * @param fileid
	 *            文件id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getThumbInfo", method = { RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public MessageNotifyUtil getThumbInfo(HttpServletRequest request, @RequestParam(FileServiceConstant.FILE_ID) String fileid) throws Exception
	{
		// 返回的对象
		MessageNotifyUtil messageNotifyUtil = new MessageNotifyUtil();
		// 消息map
		Map<String, Object> map = Maps.newHashMap();
		// 文件在服务器上的id
		// String fileid = request.getParameter(FileServiceConstant.FILE_ID);
		// 判断是否图片
		if ((StringUtils.isBlank(fileid)) || (!FileHelper.isPic(fileid)))
		{
			messageNotifyUtil.setCode(2013);
			messageNotifyUtil.setMsg("该文件不是图片文件");
			return messageNotifyUtil;
		}
		FdfsClient client = getFdfsClient();
		if (client == null)
		{
			messageNotifyUtil.setCode(2014);
			messageNotifyUtil.setMsg("不能连接文件服务器");
			return messageNotifyUtil;
		}
		// 获取文件以前的元数据
		NameValuePair[] meta_list = client.get_metadata1(fileid);
		NameValuePair nameValuePair;
		// 没有原数据
		if (meta_list == null || (meta_list.length == 0))
		{
			messageNotifyUtil.setCode(2012);
			messageNotifyUtil.setMsg("获取缩略图信息失败");
			fdfsPool.returnResource(client);
			return messageNotifyUtil;
		}
		String thumbImgListJson = null;
		// 获取缩略图信息JOSN
		for (int i = 0; i < meta_list.length; i++)
		{
			nameValuePair = meta_list[i];
			if (StringUtils.equals(FileServiceConstant.THUMB_IMG_LIST, nameValuePair.getName()))
			{
				thumbImgListJson = nameValuePair.getValue();
				break;
			}
		}
		if (StringUtils.isBlank(thumbImgListJson))
		{
			messageNotifyUtil.setCode(2012);
			messageNotifyUtil.setMsg("获取缩略图信息失败");
			fdfsPool.returnResource(client);
			return messageNotifyUtil;
		}
		List<ThumbImgInfo> list = JSON.parseArray(thumbImgListJson, ThumbImgInfo.class);
		map.put(FileServiceConstant.THUMB_IMG_LIST, JSON.toJSONString(list));
		messageNotifyUtil.setCode(200);
		messageNotifyUtil.setMsg("获取缩略图信息成功");
		messageNotifyUtil.setAppendMsg(map);
		fdfsPool.returnResource(client);
		return messageNotifyUtil;
	}

	/**
	 * 获取已传文件大小
	 * 
	 * @param request
	 * @param fileid
	 *            文件id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSize", method = { RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public MessageNotifyUtil getUploadSize(HttpServletRequest request, @RequestParam(FileServiceConstant.FILE_ID) String fileid) throws Exception
	{
		// 返回的对象
		MessageNotifyUtil messageNotifyUtil = new MessageNotifyUtil();
		// 消息map
		Map<String, Object> map = Maps.newHashMap();
		// 文件在服务器上的id
		// String fileid = request.getParameter(FileServiceConstant.FILE_ID);
		// 获取文件信息
		FdfsClient client = getFdfsClient();
		if (client == null)
		{
			messageNotifyUtil.setCode(2014);
			messageNotifyUtil.setMsg("不能连接文件服务器");
			return messageNotifyUtil;
		}
		FileInfo fileInfo = client.get_file_info1(fileid);
		if (fileInfo != null)
		{
			messageNotifyUtil.setCode(200);
			messageNotifyUtil.setMsg("操作成功");
			map.put(FileServiceConstant.FILE_SIZE, fileInfo.getFileSize());
			messageNotifyUtil.setAppendMsg(map);
			fdfsPool.returnResource(client);
			return messageNotifyUtil;
		} else
		{
			messageNotifyUtil.setCode(2004);
			messageNotifyUtil.setMsg("获取文件大小失败");
			fdfsPool.returnResource(client);
			return messageNotifyUtil;
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param request
	 * @param fileid
	 *            文件id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public MessageNotifyUtil deleteFile(HttpServletRequest request, @RequestParam(FileServiceConstant.FILE_ID) String fileid) throws Exception
	{
		// 返回的对象
		MessageNotifyUtil messageNotifyUtil = new MessageNotifyUtil();
		// 文件在服务器上的id
		// String fileid = request.getParameter(FileServiceConstant.FILE_ID);
		// 删除是否成功标志
		int hasSuccess = 1;
		FdfsClient client = getFdfsClient();
		if (client == null)
		{
			messageNotifyUtil.setCode(2014);
			messageNotifyUtil.setMsg("不能连接文件服务器");
			return messageNotifyUtil;
		}
		try
		{
			hasSuccess = client.delete_file1(fileid);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if (hasSuccess == 0)
		{
			messageNotifyUtil.setCode(200);
			messageNotifyUtil.setMsg("删除成功");
		} else
		{
			messageNotifyUtil.setCode(2005);
			messageNotifyUtil.setMsg("删除失败");
		}
		fdfsPool.returnResource(client);
		return messageNotifyUtil;
	}

	/**
	 * 下载整个文件，只能是小文件
	 * 
	 * @param request
	 * @param fileid
	 *            文件id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/download", method = { RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public MessageNotifyUtil downloadFile(HttpServletRequest request, @RequestParam(FileServiceConstant.FILE_ID) String fileid) throws Exception
	{
		// 返回的对象
		MessageNotifyUtil messageNotifyUtil = new MessageNotifyUtil();
		// 消息map
		Map<String, Object> map = Maps.newHashMap();
		// 文件在服务器上的id
		// String fileid = request.getParameter(FileServiceConstant.FILE_ID);
		byte[] buffer = null;
		try
		{
			buffer = downloadFile(fileid, 0, 0);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if (buffer == null)
		{
			messageNotifyUtil.setCode(2006);
			messageNotifyUtil.setMsg("下载失败");
			return messageNotifyUtil;
		} else
		{
			messageNotifyUtil.setCode(200);
			messageNotifyUtil.setMsg("下载成功");
			// fastjson 解码速度赶不上base64
			// map.put(Constant.FILE_STR, JSON.toJSONString(buffer));
			map.put(FileServiceConstant.FILE_STR, Base64Helper.encode(buffer));
			map.put(FileServiceConstant.DOWNLOAD_SIZE, buffer.length);
			messageNotifyUtil.setAppendMsg(map);
			return messageNotifyUtil;
		}
	}

	/**
	 * 下载文件，跳过字节数
	 * 
	 * @param request
	 * @param fileid
	 *            文件id
	 * @param skipSizeStr
	 *            跳过字节数
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadAppend", method = { RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public MessageNotifyUtil downloadFileAppend(HttpServletRequest request, @RequestParam(FileServiceConstant.FILE_ID) String fileid,
			@RequestParam(FileServiceConstant.SKIP_SIZE) String skipSizeStr) throws Exception
	{
		// 返回的对象
		MessageNotifyUtil messageNotifyUtil = new MessageNotifyUtil();
		// 消息map
		Map<String, Object> map = Maps.newHashMap();
		// 文件在服务器上的id
		// String fileid = request.getParameter(FileServiceConstant.FILE_ID);
		// 跳过的字节数
		// String skipSizeStr =
		// request.getParameter(FileServiceConstant.SKIP_SIZE);
		long skipSize = 0L;
		try
		{
			skipSize = Long.parseLong(skipSizeStr);
		} catch (NumberFormatException e)
		{
			messageNotifyUtil.setCode(2008);
			messageNotifyUtil.setMsg("下载失败，跳过的字节数不是long类型字符串");
			e.printStackTrace();
			return messageNotifyUtil;
		}
		FdfsClient client = getFdfsClient();
		FileInfo fileInfo = client.get_file_info1(fileid);
		if (fileInfo == null)
		{
			messageNotifyUtil.setCode(2004);
			messageNotifyUtil.setMsg("获取文件大小失败");
			fdfsPool.returnResource(client);
			return messageNotifyUtil;
		}
		if (fileInfo.getFileSize() <= skipSize)
		{
			messageNotifyUtil.setCode(2007);
			messageNotifyUtil.setMsg("下载失败，跳过的字节数大于文件大小");
			fdfsPool.returnResource(client);
			return messageNotifyUtil;
		}
		byte[] buffer = null;
		long downSize = fileInfo.getFileSize() - skipSize;
		try
		{
			buffer = downloadFile(fileid, skipSize, downSize);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if (buffer == null)
		{
			messageNotifyUtil.setCode(2006);
			messageNotifyUtil.setMsg("下载失败");
			fdfsPool.returnResource(client);
			return messageNotifyUtil;
		} else
		{
			messageNotifyUtil.setCode(200);
			messageNotifyUtil.setMsg("下载成功");
			// fastjson 解码速度赶不上base64
			// map.put(Constant.FILE_STR, JSON.toJSONString(buffer));
			map.put(FileServiceConstant.FILE_STR, Base64Helper.encode(buffer));
			map.put(FileServiceConstant.DOWNLOAD_SIZE, buffer.length);
			messageNotifyUtil.setAppendMsg(map);
			fdfsPool.returnResource(client);
			return messageNotifyUtil;
		}
	}

	/**
	 * 续下文件，下载固定字节大小
	 * 
	 * @param request
	 * @param fileid
	 *            文件id
	 * @param skipSizeStr
	 *            跳过的字节数
	 * @param fixedSizeStr
	 *            要下载的字节数，实际返回大小可能小于
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadFixed", method = { RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public MessageNotifyUtil downloadFileAppendFixed(HttpServletRequest request, @RequestParam(FileServiceConstant.FILE_ID) String fileid,
			@RequestParam(FileServiceConstant.SKIP_SIZE) String skipSizeStr, @RequestParam(FileServiceConstant.FIXED_SIZE) String fixedSizeStr)
			throws Exception
	{
		// 返回的对象
		MessageNotifyUtil messageNotifyUtil = new MessageNotifyUtil();
		// 消息map
		Map<String, Object> map = Maps.newHashMap();
		// 文件在服务器上的id
		// String fileid = request.getParameter(FileServiceConstant.FILE_ID);
		// 跳过的字节数
		// String skipSizeStr =
		// request.getParameter(FileServiceConstant.SKIP_SIZE);
		// 需要下载的字节数
		// String fixedSizeStr =
		// request.getParameter(FileServiceConstant.FIXED_SIZE);
		long skipSize = 0L;
		long downloadSize = 0L;
		try
		{
			skipSize = Long.parseLong(skipSizeStr);
			downloadSize = Long.parseLong(fixedSizeStr);
		} catch (NumberFormatException e)
		{
			messageNotifyUtil.setCode(2009);
			messageNotifyUtil.setMsg("下载失败，跳过的字节数或者下载的字节数不是long类型字符串");
			e.printStackTrace();
			return messageNotifyUtil;
		}
		FdfsClient client = getFdfsClient();
		if (client == null)
		{
			messageNotifyUtil.setCode(2014);
			messageNotifyUtil.setMsg("不能连接文件服务器");
			return messageNotifyUtil;
		}
		FileInfo fileInfo = client.get_file_info1(fileid);
		if (fileInfo == null)
		{
			messageNotifyUtil.setCode(2004);
			messageNotifyUtil.setMsg("获取文件大小失败");
			fdfsPool.returnResource(client);
			return messageNotifyUtil;
		}
		if (fileInfo.getFileSize() <= skipSize)
		{
			messageNotifyUtil.setCode(2007);
			messageNotifyUtil.setMsg("下载失败，跳过的字节数大于文件大小");
			fdfsPool.returnResource(client);
			return messageNotifyUtil;
		}
		long maxSize = (fileInfo.getFileSize() - skipSize);
		long downSize = maxSize > downloadSize ? downloadSize : maxSize;
		byte[] buffer = null;
		try
		{
			buffer = downloadFile(fileid, skipSize, downSize);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if (buffer == null)
		{
			messageNotifyUtil.setCode(2006);
			messageNotifyUtil.setMsg("下载失败");
			fdfsPool.returnResource(client);
			return messageNotifyUtil;
		} else
		{
			messageNotifyUtil.setCode(200);
			messageNotifyUtil.setMsg("下载成功");
			// fastjson 解码速度赶不上base64
			// map.put(Constant.FILE_STR, JSON.toJSONString(buffer));
			map.put(FileServiceConstant.FILE_STR, Base64Helper.encode(buffer));
			map.put(FileServiceConstant.DOWNLOAD_SIZE, buffer.length);
			messageNotifyUtil.setAppendMsg(map);
			fdfsPool.returnResource(client);
			return messageNotifyUtil;
		}
	}

	/**
	 * 追加文件到指定fileid，会尝试3次，失败三次后不上传
	 * 
	 * @param fileid
	 *            文件id
	 * @param buffer
	 *            字节
	 * @return 上传是否成功
	 * @throws Exception
	 */
	private int uploadFile(String fileid, byte[] buffer, int offset, int length) throws Exception
	{
		// 上传次数
		int count = 0;
		// 上传是否成果，默认失败
		int hasSuccess = 0;
		FdfsClient client = getFdfsClient();
		while (true)
		{
			// 0表示成功；非0表示失败
			int result = client.append_file1(fileid, buffer, offset, length);
			count++;
			if (result == 0)
			{
				hasSuccess = 1;
				break;
			}
			// 失败3次不上传
			if (count >= FileServiceConstant.MAX_TRY_COUNT)
			{
				break;
			}
		}
		fdfsPool.returnResource(client);
		return hasSuccess;
	}

	/**
	 * 追加文件到指定fileid，会尝试3次，失败三次后不上传
	 * 
	 * @param fileid
	 *            文件id
	 * @param buffer
	 *            字节
	 * @return 上传是否成功
	 * @throws Exception
	 */
	private int uploadFile(String fileid, byte[] buffer) throws Exception
	{
		// 上传次数
		int count = 0;
		// 上传是否成果，默认失败
		int hasSuccess = 0;
		FdfsClient client = getFdfsClient();
		while (true)
		{
			// 0表示成功；非0表示失败
			int result = client.append_file1(fileid, buffer);
			count++;
			if (result == 0)
			{
				hasSuccess = 1;
				break;
			}
			// 失败3次不上传
			if (count >= FileServiceConstant.MAX_TRY_COUNT)
			{
				break;
			}
		}
		fdfsPool.returnResource(client);
		return hasSuccess;
	}

	/**
	 * 下载文件，尝试3次
	 * 
	 * @param fileid
	 *            文件id
	 * @param skipSize
	 *            跳过的字节数
	 * @param downloadSize
	 *            需要下载的字节数，如果是0，下载全部
	 * @return 返回 byte[]数组
	 * @throws Exception
	 */
	private byte[] downloadFile(String fileid, long skipSize, long downSize) throws Exception
	{
		// 下载次数
		int count = 0;
		byte[] buffer = null;
		FdfsClient client = getFdfsClient();
		if (client == null)
		{
			return buffer;
		}
		while (true)
		{
			buffer = client.download_file1(fileid, skipSize, downSize);
			if (buffer == null)
			{
				count++;
			} else
			{
				break;
			}
			// 失败3次不下载
			if (count >= FileServiceConstant.MAX_TRY_COUNT)
			{
				break;
			}
		}
		fdfsPool.returnResource(client);
		return buffer;
	}
}