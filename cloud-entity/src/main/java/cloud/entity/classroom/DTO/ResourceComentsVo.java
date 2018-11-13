package cloud.entity.classroom.DTO;

import java.util.ArrayList;
import java.util.List;

import cloud.entity.classroom.Resources.ResourceComents;

public class ResourceComentsVo extends ResourceComents  {
   
	private String replyTo;
	
	private String nickName;
	
	private String userImg;
	
	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	private List<ResourceComentsVo> nodes = new ArrayList<ResourceComentsVo>();

	public List<ResourceComentsVo> getNodes() {
		return nodes;
	}

	public void setNodes(List<ResourceComentsVo> nodes) {
		this.nodes = nodes;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
