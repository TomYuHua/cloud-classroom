package cloud.entity.classroom.Chapter;

public class Chapterdirectories
{
	private Integer directoriesid;

	private Integer parentid;

	private String name;

	private Integer types;

	private String icon;

	private Boolean istextbook;

	private Boolean isopen;

	public Integer getDirectoriesid()
	{
		return directoriesid;
	}

	public void setDirectoriesid(Integer directoriesid)
	{
		this.directoriesid = directoriesid;
	}

	public Integer getParentid()
	{
		return parentid;
	}

	public void setParentid(Integer parentid)
	{
		this.parentid = parentid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name == null ? null : name.trim();
	}

	public Integer getTypes()
	{
		return types;
	}

	public void setTypes(Integer types)
	{
		this.types = types;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon == null ? null : icon.trim();
	}

	public Boolean getIstextbook()
	{
		return istextbook;
	}

	public void setIstextbook(Boolean istextbook)
	{
		this.istextbook = istextbook;
	}

	public Boolean getIsopen()
	{
		return isopen;
	}

	public void setIsopen(Boolean isopen)
	{
		this.isopen = isopen;
	}
}