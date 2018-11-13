package cloud.common.helper;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Base64编解码 
 * 
 *  
 *
 */
public final class Base64Helper
{

	private Base64Helper()
	{
	}

	// code characters for values 0..63
	private static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();

	// lookup table for converting base64 characters to value in range 0..63
	private static byte[] codes = new byte[256];

	static
	{
		for (int i = 0; i < 256; i++)
		{
			codes[i] = -1;
		}
		for (int i = 'A'; i <= 'Z'; i++)
		{
			codes[i] = (byte) (i - 'A');
		}
		for (int i = 'a'; i <= 'z'; i++)
		{
			codes[i] = (byte) (26 + i - 'a');
		}
		for (int i = '0'; i <= '9'; i++)
		{
			codes[i] = (byte) (52 + i - '0');
		}
		codes['+'] = 62;
		codes['/'] = 63;
	}

	/**
	 * 功能：编码byte[]
	 * 
	 * @param data
	 * @return
	 */
	public static String encode(byte[] data)
	{
		return new String(encodeChars(data));
	}

	/**
	 * 功能：编码byte[]
	 * 
	 * @param data
	 *            源
	 * @return char[]
	 */
	private static char[] encodeChars(byte[] data)
	{
		char[] out = new char[((data.length + 2) / 3) * 4];
		for (int i = 0, index = 0; i < data.length; i += 3, index += 4)
		{
			boolean quad = false;
			boolean trip = false;

			int val = (0xFF & (int) data[i]);
			val <<= 8;
			if ((i + 1) < data.length)
			{
				val |= (0xFF & (int) data[i + 1]);
				trip = true;
			}
			val <<= 8;
			if ((i + 2) < data.length)
			{
				val |= (0xFF & (int) data[i + 2]);
				quad = true;
			}
			out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 1] = alphabet[val & 0x3F];
			val >>= 6;
			out[index + 0] = alphabet[val & 0x3F];
		}
		return out;
	}

	/**
	 * 功能：解码
	 * 
	 * @param encoded
	 * @return
	 */
	public static byte[] decode(String encoded)
	{
		if (encoded == null)
		{
			return null;
		}
		char[] base64Data = encoded.toCharArray();
		return decode(base64Data);
	}

	/**
	 * 功能：解码
	 * 
	 * @return byte[]
	 */
	private static byte[] decode(char[] data)
	{
		int tempLen = data.length;
		for (int ix = 0; ix < data.length; ix++)
		{
			if ((data[ix] > 255) || codes[data[ix]] < 0)
			{
				--tempLen; // ignore non-valid chars and padding
			}
		}
		int len = (tempLen / 4) * 3;
		if ((tempLen % 4) == 3)
		{
			len += 2;
		}
		if ((tempLen % 4) == 2)
		{
			len += 1;

		}
		byte[] out = new byte[len];

		int shift = 0; // # of excess bits stored in accum
		int accum = 0; // excess bits
		int index = 0;

		// we now go through the entire array (NOT using the 'tempLen' value)
		for (int ix = 0; ix < data.length; ix++)
		{
			int value = (data[ix] > 255) ? -1 : codes[data[ix]];

			if (value >= 0)
			{ // skip over non-code
				accum <<= 6; // bits shift up by 6 each time thru
				shift += 6; // loop, with new bits being put in
				accum |= value; // at the bottom.
				if (shift >= 8)
				{ // whenever there are 8 or more shifted in,
					shift -= 8; // write them out (from the top, leaving any
					out[index++] = // excess at the bottom for next iteration.
							(byte) ((accum >> shift) & 0xff);
				}
			}
		}

		// if there is STILL something wrong we just have to throw up now!
		if (index != out.length)
		{
			throw new Error("Miscalculated data length (wrote " + index + " instead of " + out.length + ")");
		}
		return out;
	}

	/**
	 * 功能：编码文件
	 * 
	 * @param file
	 *            源文件
	 */
	public static String encode(File file) throws IOException
	{
		if (!file.exists())
		{
			return null;
		} else
		{
			byte[] buffer = readBytes(file);
			return encode(buffer);
		}
	}

	/**
	 * 读取文件到byte[]
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private static byte[] readBytes(File file) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = null;
		InputStream fis = null;
		InputStream is = null;
		try
		{
			fis = new FileInputStream(file);
			is = new BufferedInputStream(fis);
			int count = 0;
			byte[] buf = new byte[16384];
			while ((count = is.read(buf)) != -1)
			{
				if (count > 0)
				{
					baos.write(buf, 0, count);
				}
			}
			b = baos.toByteArray();

		} finally
		{
			try
			{
				if (fis != null)
					fis.close();
				if (is != null)
					is.close();
				if (baos != null)
					baos.close();
			} catch (Exception e)
			{
				System.out.println(e);
			}
		}

		return b;
	}

}
