package com.singFly.cloud_examination_common_constant;
/**
 * 常量类
 * 
 * 
 *
 */
public final class FileServiceConstant
{

	/**
	 * base64格式的文件byte[]字符串
	 */
	public static final String FILE_STR = "fileStr";

	/**
	 * 文件后缀名
	 */
	public static final String FILE_EXT_NAME = "fileExtName";

	/**
	 * 文件大小
	 */
	public static final String FILE_SIZE = "fileSize";

	/**
	 * 文件在文件服务器上的ID
	 */
	public static final String FILE_ID = "fileid";

	/**
	 * 本次上传成功的文件大小
	 */
	public static final String UPLOAD_SIZE = "uploadSize";

	/**
	 * 需要生成的缩略图信息
	 */
	public static final String THUMB_IMG_LIST = "thumbImgList";

	/**
	 * 本次的下载字节数
	 */
	public static final String DOWNLOAD_SIZE = "downloadSize";

	/**
	 * 跳过的字节数
	 */
	public static final String SKIP_SIZE = "skipSize";

	/**
	 * 需要下载的固定字节数，实际返回的数据可能会小于这个数
	 */
	public static final String FIXED_SIZE = "fixedSize";

	/**
	 * 最大尝试次数
	 */
	public static final int MAX_TRY_COUNT = 3;

}