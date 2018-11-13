package cloud.common.util;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * 图片转换成固定的长度宽度
 * 
 *  
 *
 */
public class ImgConvertUtil
{

	private int width;
	private int height;
	private int scaleWidth;
	private double support = (double) 3.0;
	private double PI = (double) 3.14159265358978;
	private double[] contrib;
	private double[] normContrib;
	private double[] tmpContrib;
	private int nDots;
	private int nHalfDots;

	/**
	 * 图片缩放，生成新图片的缓存，不改变比例
	 * 
	 * @param srcImage
	 *            原图片文件缓存
	 * @param formatWideth
	 *            新图片宽度
	 * @param formatHeight
	 *            新图片高度
	 * @return
	 */
	public BufferedImage convert(BufferedImage srcImage, int formatWideth, int formatHeight)
	{
		return convert(srcImage, formatWideth, formatHeight, false);
	}

	/**
	 * 图片缩放，生成新图片的缓存
	 * 
	 * @param srcImage
	 *            原图片文件缓存
	 * @param formatWideth
	 *            新图片宽度
	 * @param formatHeight
	 *            新图片高度
	 * @param changeScale
	 *            是否改变宽高比例
	 * @return
	 */
	public BufferedImage convert(BufferedImage srcImage, int formatWideth, int formatHeight, boolean changeScale)
	{
		// 要转换的宽高
		int changeToWideth = 0;
		int changeToHeight = 0;
		if (changeScale)
		{
			changeToWideth = formatWideth;
			changeToHeight = formatHeight;
		} else
		{
			// 图片的宽高
			int imageWideth = srcImage.getWidth(null);
			int imageHeight = srcImage.getHeight(null);
			if (imageWideth > 0 && imageHeight > 0)
			{
				if (imageWideth / imageHeight >= formatWideth / formatHeight)
				{
					if (imageWideth > formatWideth)
					{
						changeToWideth = formatWideth;
						changeToHeight = (imageHeight * formatWideth) / imageWideth;
					} else
					{
						changeToWideth = imageWideth;
						changeToHeight = imageHeight;
					}
				} else
				{
					if (imageHeight > formatHeight)
					{
						changeToHeight = formatHeight;
						changeToWideth = (imageWideth * formatHeight) / imageHeight;
					} else
					{
						changeToWideth = imageWideth;
						changeToHeight = imageHeight;
					}
				}
			} else
			{
				new Exception("图片错误");
			}
		}
		return imageZoomOut(srcImage, changeToWideth, changeToHeight);
	}

	/**
	 * 图片缩放，生成新图片，不改变比例
	 * 
	 * @param fromFileStr
	 *            原图片地址
	 * @param saveFileStr
	 *            新图片地址
	 * @param formatWideth
	 *            新图片宽度
	 * @param formatHeight
	 *            新图片高度
	 * @throws Exception
	 */
	public void convert(String fromFileStr, String saveFileStr, int formatWideth, int formatHeight) throws Exception
	{
		convert(fromFileStr, saveFileStr, formatWideth, formatHeight, false);
	}

	/**
	 * 图片缩放，生成新图片
	 * 
	 * @param fromFileStr
	 *            原图片地址
	 * @param saveFileStr
	 *            新图片地址
	 * @param formatWideth
	 *            新图片宽度
	 * @param formatHeight
	 *            新图片高度
	 * @param changeScale
	 *            是否改变宽高比例
	 * @throws Exception
	 */
	public void convert(String fromFileStr, String saveFileStr, int formatWideth, int formatHeight, boolean changeScale)
			throws Exception
	{
		BufferedImage srcImage;
		File fromFile = new File(fromFileStr);
		// 创建image
		srcImage = javax.imageio.ImageIO.read(fromFile);
		// 要转换的宽高
		int changeToWideth = 0;
		int changeToHeight = 0;
		if (changeScale)
		{
			changeToWideth = formatWideth;
			changeToHeight = formatHeight;
		} else
		{
			// 图片的宽高
			int imageWideth = srcImage.getWidth(null);
			int imageHeight = srcImage.getHeight(null);
			if (imageWideth > 0 && imageHeight > 0)
			{
				if (imageWideth / imageHeight >= formatWideth / formatHeight)
				{
					if (imageWideth > formatWideth)
					{
						changeToWideth = formatWideth;
						changeToHeight = (imageHeight * formatWideth) / imageWideth;
					} else
					{
						changeToWideth = imageWideth;
						changeToHeight = imageHeight;
					}
				} else
				{
					if (imageHeight > formatHeight)
					{
						changeToHeight = formatHeight;
						changeToWideth = (imageWideth * formatHeight) / imageHeight;
					} else
					{
						changeToWideth = imageWideth;
						changeToHeight = imageHeight;
					}
				}
			} else
			{
				new Exception("图片错误");
			}
		}
		// 转换
		srcImage = imageZoomOut(srcImage, changeToWideth, changeToHeight);
		// 保存新图片
		File saveFile = new File(saveFileStr);
		ImageIO.write(srcImage, "JPEG", saveFile);
	}

	/**
	 * 
	 * @param srcBufferImage
	 * @param w
	 * @param h
	 * @return
	 */
	private BufferedImage imageZoomOut(BufferedImage srcBufferImage, int w, int h)
	{
		width = srcBufferImage.getWidth();
		height = srcBufferImage.getHeight();
		scaleWidth = w;
		// 如果要生成的长宽跟原图一样，不需要处理
		if (DetermineResultSize(w, h) == 1)
		{
			return srcBufferImage;
		}
		//
		CalContrib();
		BufferedImage pbOut = HorizontalFiltering(srcBufferImage, w);
		BufferedImage pbFinalOut = VerticalFiltering(pbOut, h);
		return pbFinalOut;
	}

	/**
	 * 判断是否不需要转换
	 * 
	 * @param w
	 * @param h
	 * @return
	 */
	private int DetermineResultSize(int w, int h)
	{
		double scaleH, scaleV;
		scaleH = (double) w / (double) width;
		scaleV = (double) h / (double) height;
		if (scaleH == 1.0 && scaleV == 1.0)
		{
			return 1;
		}
		return 0;
	}

	/**
	 * 转换
	 */
	private void CalContrib()
	{
		nHalfDots = (int) ((double) width * support / (double) scaleWidth);
		nDots = nHalfDots * 2 + 1;
		try
		{
			contrib = new double[nDots];
			normContrib = new double[nDots];
			tmpContrib = new double[nDots];
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		int center = nHalfDots;
		contrib[center] = 1.0;
		double weight = 0.0;
		int i = 0;
		for (i = 1; i <= center; i++)
		{
			contrib[center + i] = Lanczos(i, width, scaleWidth, support);
			weight += contrib[center + i];
		}
		for (i = center - 1; i >= 0; i--)
		{
			contrib[i] = contrib[center * 2 - i];
		}
		weight = weight * 2 + 1.0;
		for (i = 0; i <= center; i++)
		{
			normContrib[i] = contrib[i] / weight;
		}
		for (i = center + 1; i < nDots; i++)
		{
			normContrib[i] = normContrib[center * 2 - i];
		}
	}

	/**
	 * Lanczos采样放缩
	 * 
	 * @param i
	 * @param inWidth
	 * @param outWidth
	 * @param Support
	 * @return
	 */
	private double Lanczos(int i, int inWidth, int outWidth, double Support)
	{
		double x;
		x = (double) i * (double) outWidth / (double) inWidth;
		return Math.sin(x * PI) / (x * PI) * Math.sin(x * PI / Support) / (x * PI / Support);
	}

	/**
	 * 水平过滤
	 * 
	 * @param bufImage
	 * @param iOutW
	 * @return
	 */
	private BufferedImage HorizontalFiltering(BufferedImage bufImage, int iOutW)
	{
		int dwInW = bufImage.getWidth();
		int dwInH = bufImage.getHeight();
		int value = 0;
		BufferedImage pbOut = new BufferedImage(iOutW, dwInH, BufferedImage.TYPE_INT_RGB);
		int startX;
		int start;
		int X;
		int y;
		int stop;
		int stopX;
		for (int x = 0; x < iOutW; x++)
		{
			X = (int) (((double) x) * ((double) dwInW) / ((double) iOutW) + 0.5);
			y = 0;
			startX = X - nHalfDots;
			if (startX < 0)
			{
				startX = 0;
				start = nHalfDots - X;
			} else
			{
				start = 0;
			}
			stopX = X + nHalfDots;
			if (stopX > (dwInW - 1))
			{
				stopX = dwInW - 1;
				stop = nHalfDots + (dwInW - 1 - X);
			} else
			{
				stop = nHalfDots * 2;
			}
			if (start > 0 || stop < nDots - 1)
			{
				CalTempContrib(start, stop);
				for (y = 0; y < dwInH; y++)
				{
					value = HorizontalFilter(bufImage, startX, stopX, start, stop, y, tmpContrib);
					pbOut.setRGB(x, y, value);
				}
			} else
			{
				for (y = 0; y < dwInH; y++)
				{
					value = HorizontalFilter(bufImage, startX, stopX, start, stop, y, normContrib);
					pbOut.setRGB(x, y, value);
				}
			}
		}
		return pbOut;
	}

	/**
	 * 水平过滤
	 * 
	 * @param bufImg
	 * @param startX
	 * @param stopX
	 * @param start
	 * @param stop
	 * @param y
	 * @param pContrib
	 * @return
	 */
	private int HorizontalFilter(BufferedImage bufImg, int startX, int stopX, int start, int stop, int y,
			double[] pContrib)
	{
		double valueRed = 0.0;
		double valueGreen = 0.0;
		double valueBlue = 0.0;
		int valueRGB = 0;
		int i, j;
		for (i = startX, j = start; i <= stopX; i++, j++)
		{
			valueRGB = bufImg.getRGB(i, y);
			valueRed += GetRedValue(valueRGB) * pContrib[j];
			valueGreen += GetGreenValue(valueRGB) * pContrib[j];
			valueBlue += GetBlueValue(valueRGB) * pContrib[j];
		}
		valueRGB = ComRGB(Clip((int) valueRed), Clip((int) valueGreen), Clip((int) valueBlue));
		return valueRGB;
	}

	/**
	 * 垂直过滤
	 * 
	 * @param pbImage
	 * @param iOutH
	 * @return
	 */
	private BufferedImage VerticalFiltering(BufferedImage pbImage, int iOutH)
	{
		int iW = pbImage.getWidth();
		int iH = pbImage.getHeight();
		int value = 0;
		BufferedImage pbOut = new BufferedImage(iW, iOutH, BufferedImage.TYPE_INT_RGB);
		int startY;
		int start;
		int Y;
		int stop;
		int stopY;
		for (int y = 0; y < iOutH; y++)
		{
			Y = (int) (((double) y) * ((double) iH) / ((double) iOutH) + 0.5);
			startY = Y - nHalfDots;
			if (startY < 0)
			{
				startY = 0;
				start = nHalfDots - Y;
			} else
			{
				start = 0;
			}
			stopY = Y + nHalfDots;
			if (stopY > (int) (iH - 1))
			{
				stopY = iH - 1;
				stop = nHalfDots + (iH - 1 - Y);
			} else
			{
				stop = nHalfDots * 2;
			}
			if (start > 0 || stop < nDots - 1)
			{
				CalTempContrib(start, stop);
				for (int x = 0; x < iW; x++)
				{
					value = VerticalFilter(pbImage, startY, stopY, start, stop, x, tmpContrib);
					pbOut.setRGB(x, y, value);
				}
			} else
			{
				for (int x = 0; x < iW; x++)
				{
					value = VerticalFilter(pbImage, startY, stopY, start, stop, x, normContrib);
					pbOut.setRGB(x, y, value);
				}
			}
		}
		return pbOut;
	}

	/**
	 * 垂直过滤
	 * 
	 * @param pbInImage
	 * @param startY
	 * @param stopY
	 * @param start
	 * @param stop
	 * @param x
	 * @param pContrib
	 * @return
	 */
	private int VerticalFilter(BufferedImage pbInImage, int startY, int stopY, int start, int stop, int x,
			double[] pContrib)
	{
		double valueRed = 0.0;
		double valueGreen = 0.0;
		double valueBlue = 0.0;
		int valueRGB = 0;
		int i, j;
		for (i = startY, j = start; i <= stopY; i++, j++)
		{
			valueRGB = pbInImage.getRGB(x, i);
			valueRed += GetRedValue(valueRGB) * pContrib[j];
			valueGreen += GetGreenValue(valueRGB) * pContrib[j];
			valueBlue += GetBlueValue(valueRGB) * pContrib[j];
		}
		valueRGB = ComRGB(Clip((int) valueRed), Clip((int) valueGreen), Clip((int) valueBlue));
		return valueRGB;

	}

	/**
	 * 转换
	 * 
	 * @param start
	 * @param stop
	 */
	private void CalTempContrib(int start, int stop)
	{
		double weight = 0;
		int i = 0;
		for (i = start; i <= stop; i++)
		{
			weight += contrib[i];
		}
		for (i = start; i <= stop; i++)
		{
			tmpContrib[i] = contrib[i] / weight;
		}
	}

	/**
	 * 获取RGB中R值
	 * 
	 * @param rgbValue
	 * @return
	 */
	private int GetRedValue(int rgbValue)
	{
		int temp = rgbValue & 0x00ff0000;
		return temp >> 16;
	}

	/**
	 * 获取RGB中G值
	 * 
	 * @param rgbValue
	 * @return
	 */
	private int GetGreenValue(int rgbValue)
	{
		int temp = rgbValue & 0x0000ff00;
		return temp >> 8;
	}

	/**
	 * 获取RGB中B值
	 * 
	 * @param rgbValue
	 * @return
	 */
	private int GetBlueValue(int rgbValue)
	{
		return rgbValue & 0x000000ff;
	}

	/**
	 * 计算RGB值
	 * 
	 * @param redValue
	 * @param greenValue
	 * @param blueValue
	 * @return
	 */
	private int ComRGB(int redValue, int greenValue, int blueValue)
	{
		return (redValue << 16) + (greenValue << 8) + blueValue;
	}

	/**
	 * RGB颜色剪切
	 * 
	 * @param x
	 * @return
	 */
	int Clip(int x)
	{
		if (x < 0)
		{
			return 0;
		} else if (x > 255)
		{
			return 255;
		}
		return x;
	}

}
