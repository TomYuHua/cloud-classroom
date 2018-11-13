package cloud.entity.classroom.Resources;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ResourceComents {
	
	private Integer id;
	
	private Integer resourceId;
	
	private Integer parentId;
	
	private String contents;
	
	private Integer userId;
	
	private  Timestamp  createTime;
	
    private float  score;

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
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

	public Integer getParentId() {
		return parentId;
	}

	public void setPareentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}



	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Timestamp  getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp  createTime) {
		this.createTime = createTime;
	}

}
