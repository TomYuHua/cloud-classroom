package org.common.dfs.common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Hashtable;

import org.common.dfs.constant.Constant;

@SuppressWarnings("all")
public class IniFileReader
{
	private Hashtable paramTable;
	private String conf_filename;

	public IniFileReader(String conf_filename) throws FileNotFoundException, IOException
	{
		this.conf_filename = conf_filename;
		loadFromFile(conf_filename);
	}

	public String getConfFilename()
	{
		return this.conf_filename;
	}

	public String getStrValue(String name)
	{
		Object obj;
		obj = this.paramTable.get(name);
		if (obj == null)
		{
			return null;
		}
		if (obj instanceof String)
		{
			return (String) obj;
		}
		return (String) ((ArrayList) obj).get(0);
	}

	/**
	 * get int value from config file
	 * 
	 * @param name
	 *            item name in config file
	 * @param default_value
	 *            the default value
	 * @return int value
	 */
	public int getIntValue(String name, int default_value)
	{
		String szValue = this.getStrValue(name);
		if (szValue == null)
		{
			return default_value;
		}
		return Integer.parseInt(szValue);
	}

	/**
	 * get boolean value from config file
	 * 
	 * @param name
	 *            item name in config file
	 * @param default_value
	 *            the default value
	 * @return boolean value
	 */
	public boolean getBoolValue(String name, boolean default_value)
	{
		String szValue = this.getStrValue(name);
		if (szValue == null)
		{
			return default_value;
		}
		return szValue.equalsIgnoreCase("yes") || szValue.equalsIgnoreCase("on") || szValue.equalsIgnoreCase("true")
				|| szValue.equals("1");
	}

	/**
	 * get all values from config file
	 * 
	 * @param name
	 *            item name in config file
	 * @return string values (array)
	 */
	public String[] getValues(String name)
	{
		Object obj;
		String[] values;
		obj = this.paramTable.get(name);
		if (obj == null)
		{
			return null;
		}
		if (obj instanceof String)
		{
			values = new String[1];
			values[0] = (String) obj;
			return values;
		}
		Object[] objs = ((ArrayList) obj).toArray();
		values = new String[objs.length];
		System.arraycopy(objs, 0, values, 0, objs.length);
		return values;
	}

	private void loadFromFile(String conf_filename) throws FileNotFoundException, IOException
	{
		//FileReader fReader;
		BufferedReader buffReader;
		String line;
		String[] parts;
		String name;
		String value;
		Object obj;
		ArrayList valueList;
		
		
		URL url = Constant.class.getResource(Constant.FDFS_CONFIG_PROP);
	    URLConnection urlc = url.openConnection();
	    InputStream is = urlc.getInputStream();
		InputStreamReader inputStreamReader=new InputStreamReader(is,"UTF-8");

		 
		//InputStreamReader inputStreamReader=new InputStreamReader(new FileInputStream(conf_filename),"UTF-8");
		//fReader = new FileReader(conf_filename);
		buffReader = new BufferedReader(inputStreamReader);
		this.paramTable = new Hashtable();
		try
		{
			while ((line = buffReader.readLine()) != null)
			{
				line = line.trim();
				if (line.length() == 0 || line.charAt(0) == '#')
				{
					continue;
				}
				parts = line.split("=", 2);
				if (parts.length != 2)
				{
					continue;
				}
				name = parts[0].trim();
				value = parts[1].trim();
				obj = this.paramTable.get(name);
				if (obj == null)
				{
					this.paramTable.put(name, value);
				} else if (obj instanceof String)
				{
					valueList = new ArrayList();
					valueList.add(obj);
					valueList.add(value);
					this.paramTable.put(name, valueList);
				} else
				{
					valueList = (ArrayList) obj;
					valueList.add(value);
				}
			}
		} finally
		{
			inputStreamReader.close();
		}
	}
}
