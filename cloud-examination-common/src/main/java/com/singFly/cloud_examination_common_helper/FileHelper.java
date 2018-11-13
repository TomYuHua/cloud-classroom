package com.singFly.cloud_examination_common_helper;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FileHelper {
	
	private FileHelper()
	{
	}

	/**
	 * xls格式的excel
	 */
	public static final String XLS = "xls";

	/**
	 * xlsx格式的excel
	 */
	public static final String XLSX = "xlsx";

	/**
	 * 符号.
	 */
	public static final String SYMBOL_DOT = ".";

	/**
	 * 主流图片格式
	 */
	public static final String[] imageExtArr = { "png", "gif", "jpeg", "jpg", "bmp" };

	/**
	 * 判断文件是否为Excel
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static boolean isExcel(String fileName)
	{
		String extension = getExtension(fileName);
		if (XLS.equalsIgnoreCase(extension) || XLSX.equalsIgnoreCase(extension))
		{
			return true;
		}
		return false;
	}

	/**
	 * 判断文件是否为图片
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static boolean isPic(String fileName)
	{
		String extension = getExtension(fileName);
		List<String> imageExtList = Arrays.asList(imageExtArr);
		if (imageExtList.contains(extension))
		{
			return true;
		}
		return false;
	}

	/**
	 * 获取文件扩展名，扩展名最小化
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String getExtension(String fileName)
	{
		return fileName.lastIndexOf(SYMBOL_DOT) == -1 ? ""
				: fileName.substring(fileName.lastIndexOf(SYMBOL_DOT) + 1).toLowerCase();
	}

	/**
	 * 获取文件扩展名，是否转小写
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExtension(String fileName, boolean lowerCase)
	{
		if (lowerCase)
		{
			return fileName.lastIndexOf(SYMBOL_DOT) == -1 ? ""
					: fileName.substring(fileName.lastIndexOf(SYMBOL_DOT) + 1);
		}
		return fileName.lastIndexOf(SYMBOL_DOT) == -1 ? ""
				: fileName.substring(fileName.lastIndexOf(SYMBOL_DOT) + 1).toLowerCase();
	}

	/**
	 * 获取文件基本名
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String getBaseName(String fileName)
	{
		if (fileName.lastIndexOf(SYMBOL_DOT) == -1)
		{
			return fileName;
		} else
		{
			return fileName.substring(0, fileName.lastIndexOf(SYMBOL_DOT));
		}
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean isExist(String path)
	{
		return new File(path).exists();
	}


}
