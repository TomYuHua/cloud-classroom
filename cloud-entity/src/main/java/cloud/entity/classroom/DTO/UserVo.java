package cloud.entity.classroom.DTO;

import java.io.Serializable;

import cloud.entity.classroom.every.User;

public class UserVo extends User implements Serializable  {
  
	
	  private static final long serialVersionUID = 1L;  
	  
	  private Integer ebookNum;
	  
	  private Integer videoNum;
	  
	  private Integer browersNum;
	  
	  private Integer downloadsNum;
	                
	  private Integer documentNum;
	  
	  

	public Integer getDocumentNum() {
		return documentNum;
	}

	public void setDocumentNum(Integer documentNum) {
		this.documentNum = documentNum;
	}

	public Integer getEbookNum() {
		return ebookNum;
	}

	public void setEbookNum(Integer ebookNum) {
		this.ebookNum = ebookNum;
	}

	public Integer getVideoNum() {
		return videoNum;
	}

	public void setVideoNum(Integer videoNum) {
		this.videoNum = videoNum;
	}

	public Integer getBrowersNum() {
		return browersNum;
	}

	public void setBrowersNum(Integer browersNum) {
		this.browersNum = browersNum;
	}

	public Integer getDownloadsNum() {
		return downloadsNum;
	}

	public void setDownloadsNum(Integer downloadsNum) {
		this.downloadsNum = downloadsNum;
	}
}
