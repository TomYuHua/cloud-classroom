package cloud.entity.classroom.DTO;

import java.util.Date;
import java.util.List;

import cloud.entity.classroom.Resources.Resources;

public class DateResourcesVo {
	
	public List<Resources> batchResources;
	
	public List<Resources> getBatchResources() {
		return batchResources;
	}

	public void setBatchResources(List<Resources> batchResources) {
		this.batchResources = batchResources;
	}

	public Date watchTime;



	public Date getWatchTime() {
		return watchTime;
	}

	public void setWatchTime(Date watchTime) {
		this.watchTime =watchTime;
	}

}
