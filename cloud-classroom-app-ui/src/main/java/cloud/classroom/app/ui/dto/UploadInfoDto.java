package cloud.classroom.app.ui.dto;

public class UploadInfoDto
{

	private Integer id;

	private Integer parentid;

	private Boolean isdocument;

	private String name;

	private Integer types;

	 

	private String resourcepath;

	private boolean isUpload;

	private Integer uploadSize;

	public Boolean getIsUpload()
	{
		return isUpload;
	}

	public void setIsUpload(Boolean isUpload)
	{
		this.isUpload = isUpload;
	}

	public Integer getUploadSize()
	{
		return uploadSize;
	}

	public void setUploadSize(Integer ploadSize)
	{
		this.uploadSize = uploadSize;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getParentId()
	{
		return parentid;
	}

	public void setParentId(Integer parentid)
	{
		this.parentid = parentid;
	}

	public Boolean getIsdocument()
	{
		return isdocument;
	}

	public void setIsdocument(Boolean isdocument)
	{
		this.isdocument = isdocument;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Integer getTypes()
	{
		return types;
	}

	public void setTypes(Integer types)
	{
		this.types = types;
	}

	public String getResourcepath()
	{
		return resourcepath;
	}

	public void setResourcepath(String resourcepath)
	{
		this.resourcepath = resourcepath == null ? null : resourcepath.trim();
	}

}
