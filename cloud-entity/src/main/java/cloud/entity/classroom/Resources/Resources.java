package cloud.entity.classroom.Resources;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cloud.entity.classroom.DTO.ResourceComentsVo;

public class Resources
{
	private Integer id;

	private Integer parentid;

	private Boolean isdocument;

	private String name;

	private Integer types;

	private String icon;

	private String createauthor;

	private Integer userid;

	private String resourcepath;

	private String describes;

	private Float scores;

	private Integer clickcount;

	private Boolean isopen;

	private String imgsrc;

	private Byte status;

	private Timestamp createtime;

	private String contents;
	
	private Integer directoriesid;
	

	private Integer orderType;
	

	private boolean isCollectedByLoginUser;
	

	private Integer sort;
	
	public Integer getSort() {
		return sort;
	}
	
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	

	 public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getDirectoriesid() {
		return directoriesid;
	}

	public void setDirectoriesid(Integer directoriesid) {
		this.directoriesid = directoriesid;
	}

	private List<ResourceComentsVo> resourceComents = new ArrayList<ResourceComentsVo>();

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
		this.name=name;
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
		this.icon =icon;
	}

	public String getCreateauthor()
	{
		return createauthor;
	}

	public void setCreateauthor(String createauthor)
	{
		this.createauthor = createauthor;
	}

	public Integer getUserid()
	{
		return userid;
	}

	public void setUserid(Integer userid)
	{
		this.userid = userid;
	}

	public String getResourcepath()
	{
		return resourcepath;
	}

	public void setResourcepath(String resourcepath)
	{
		this.resourcepath=resourcepath;
	}

	public String getDescribes()
	{
		return describes;
	}

	public void setDescribes(String describes)
	{
		this.describes = describes;
	}

	public Float getScores()
	{
		return scores;
	}

	public void setScores(Float scores)
	{
		this.scores = scores;
	}

	public Integer getClickcount()
	{
		return clickcount;
	}

	public void setClickcount(Integer clickcount)
	{
		this.clickcount = clickcount;
	}

	public Boolean getIsopen()
	{
		return isopen;
	}

	public void setIsopen(Boolean isopen)
	{
		this.isopen = isopen;
	}

	public String getImgsrc()
	{
		return imgsrc;
	}

	public void setImgsrc(String imgsrc)
	{
//		this.imgsrc = imgsrc == null ? null : imgsrc.trim();
		this.imgsrc = imgsrc;
	}

	public Byte getStatus()
	{
		return status;
	}

	public void setStatus(Byte status)
	{
		this.status = status;
	}

	public Date getCreatetime()
	{
		return createtime;
	}

	public void setCreatetime(Timestamp createtime)
	{
		this.createtime = createtime;
	}

	public String getContents()
	{
		return contents;
	}

	public void setContents(String contents)
	{
		this.contents = contents;
	}

	 public List<ResourceComentsVo> getResourceComents()
	 {
	 return resourceComents;
	 }
	
	 public void setResourceComents(List<ResourceComentsVo> resourceComents)
	 {
	     this.resourceComents = resourceComents;
	 }
    
	public boolean getIsCollectedByLoginUser() {
			return isCollectedByLoginUser;
	}

	public void setIsCollectedByLoginUser(boolean isCollectedByLoginUser) {
	    this.isCollectedByLoginUser = isCollectedByLoginUser;
	}

}