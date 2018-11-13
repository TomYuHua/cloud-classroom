package cloud.entity.classroom.DTO;

import java.io.Serializable;
import java.util.List;

import cloud.entity.classroom.Resources.ResourceComents;
import cloud.entity.classroom.Resources.Resources;

public class ResourcesVo extends Resources implements Serializable {
	
	private Integer browseCount;
	
	private Integer downloadCount;
	
	private String fileSize;
	
	private List<ResourceComentsVo> resourceComentsVo;


   public List<ResourceComentsVo> getResourceComentsVo() {
		return resourceComentsVo;
	}


	public void setResourceComentsVo(List<ResourceComentsVo> resourceComentsVo) {
		this.resourceComentsVo = resourceComentsVo;
	}


public Integer getBrowseCount(){
	   return browseCount;
   }


  public void setBrowseCount(Integer browseCount){
	  this.browseCount=browseCount;
  }

  public Integer getDownloadCount(){
	   return downloadCount;
  }


 public void setDownloadCount(Integer downloadCount){
	  this.downloadCount=downloadCount;
 }

 public String getFileSize(){
	   return fileSize;
}


public void setFileSize(String fileSize){
	  this.fileSize=fileSize;
}



}
