package cloud.entity.classroom.classinfos;

import java.io.Serializable;

public class ClassInfo implements Serializable
{

	private int id;

	private String className;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}
}
