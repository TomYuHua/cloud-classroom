package cloud.entity.classroom.DTO;

import java.sql.Timestamp;
import java.util.Date;

public class ResourcesBrower {
	
	private Integer id;
	 
	private Integer resourceId;
	
	private  Timestamp createTime;
	
	private Integer resourceType;
	
	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	private Integer userId;
	
	
	private Date watchTime;
	
	public Date getWatchTime() {
		return watchTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setWatchTime(Date watchTime) {
		this.watchTime = watchTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}



}
