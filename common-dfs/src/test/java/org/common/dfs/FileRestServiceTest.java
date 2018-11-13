package org.common.dfs;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.common.dfs.controller.ThumbImgInfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cloud.common.constant.FileServiceConstant;
import cloud.common.helper.Base64Helper;

import cloud.common.helper.FileHelper;
import cloud.common.helper.HttpHelper;
import cloud.common.util.MessageNotifyUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FileRestServiceTest.class)
public class FileRestServiceTest
{

	/**
	 * 上传测试
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpload() throws Exception
	{
		File file = new File("D:/1.jpg");

		Map<String, String> paramMap = Maps.newHashMap();
		paramMap.put("fileStr", Base64Helper.encode(file));
		paramMap.put("fileExtName", FileHelper.getExtension(file.getName(), true));
		paramMap.put("fileSize", String.valueOf(file.length()));
		String url = "http://localhost:8080/upload";
		String result = HttpHelper.URLPost(url, paramMap, "utf-8");
		System.out.println(result);
	}

	/**
	 * 下载测试
	 *
	 * @throws Exception
	 */
	@Test
	public void testDownload() throws Exception
	{
		String fileid = "group1/M00/00/00/wKgBylk6JfOEfptaAAAAAAAAAAA047.jpg";
		String url = "http://127.0.0.1:8080/download";
		Map<String, String> paramMap = Maps.newHashMap();
		paramMap.put("fileid", fileid);
		String result = HttpHelper.URLGet(url, paramMap, "utf-8");
		MessageNotifyUtil messageNotifyUtil = JSON.parseObject(result, MessageNotifyUtil.class);
		if (messageNotifyUtil.getCode() == 200)
		{
			String fileStr = (String) messageNotifyUtil.getAppendMsg().get("fileStr");
			byte[] buffer = Base64Helper.decode(fileStr);
			File file = new File("D:/Downlod/" + fileid.replaceAll("/", "_"));
			OutputStream fos = null;
			OutputStream os = null;
			try
			{
				fos = new FileOutputStream(file);
				os = new BufferedOutputStream(fos);
				os.write(buffer);
			} finally
			{
				try
				{
					if (os != null)
						os.close();
					if (fos != null)
						fos.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 生成缩略图
	 *
	 * @throws Exception
	 */
	@Test
	public void testThumbnail() throws Exception
	{
		String fileid = "group1/M00/00/00/wKgBylk6YWiESiv5AAAAAAAAAAA439.jpg";
		Map<String, String> paramMap = Maps.newHashMap();
		paramMap.put("fileid", fileid);
		ThumbImgInfo thumbImgInfo = new ThumbImgInfo();
		List<ThumbImgInfo> list = Lists.newArrayList();
		thumbImgInfo = new ThumbImgInfo();
		thumbImgInfo.setWidth(500);
		thumbImgInfo.setHeight(500);
		list.add(thumbImgInfo);
		thumbImgInfo = new ThumbImgInfo();
		thumbImgInfo.setWidth(300);
		thumbImgInfo.setHeight(300);
		list.add(thumbImgInfo);
		paramMap.put("thumbImgList", JSON.toJSONString(list));
		String url = "http://127.0.0.1:8080/thumbnail";
		String result = HttpHelper.URLPost(url, paramMap, "utf-8");
		System.out.println(result);
	}

	/**
	 * 获取缩略图信息
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetThumbInfo() throws Exception
	{
		String fileid = "group1/M00/00/00/wKgBylk6YWiESiv5AAAAAAAAAAA439.jpg";
		Map<String, String> paramMap = Maps.newHashMap();
		paramMap.put("fileid", fileid);
		String url = "http://127.0.0.1:8080/getThumbInfo";
		String result = HttpHelper.URLGet(url, paramMap, "utf-8");
		MessageNotifyUtil messageNotifyUtil = JSON.parseObject(result, MessageNotifyUtil.class);
		String thumbImgListJson = (String) messageNotifyUtil.getAppendMsg().get("thumbImgList");
		List<ThumbImgInfo> list = JSON.parseArray(thumbImgListJson, ThumbImgInfo.class);
		for (int i = 0; i < list.size(); i++)
		{
			System.out.println(list.get(i).getFileid());
		}
	}

	/**
	 * 获取已上传文件大小
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetSize() throws Exception
	{
		String fileid = "group1/M00/00/00/wKgBylk6YWiESiv5AAAAAAAAAAA439.jpg";
		Map<String, String> paramMap = Maps.newHashMap();
		paramMap.put("fileid", fileid);
		String url = "http://127.0.0.1:8080/getSize";
		String result = HttpHelper.URLGet(url, paramMap, "utf-8");
		MessageNotifyUtil messageNotifyUtil = JSON.parseObject(result, MessageNotifyUtil.class);
		System.out.println(messageNotifyUtil.getAppendMsg().get(FileServiceConstant.FILE_SIZE));
	}

	/**
	 * 删除文件
	 *
	 * @throws Exception
	 */
	@Test
	public void testDelete() throws Exception
	{
		String fileid = "group1/M00/00/00/wKgBylk6aAWEaIY5AAAAAAAAAAA525.jpg";
		Map<String, String> uriVariables = Maps.newHashMap();
		uriVariables.put("fileid", fileid);
		String url = "http://127.0.0.1:8080/delete";
		String result = HttpHelper.URLPost(url, uriVariables, "utf-8");
		MessageNotifyUtil messageNotifyUtil = JSON.parseObject(result, MessageNotifyUtil.class);
		System.out.println(messageNotifyUtil.getMsg());
	}
}