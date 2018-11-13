package cloud.service.classroom.dao.cluster;

import java.util.List;

import cloud.entity.classroom.DTO.DateResourcesVo;

public interface DateResourcesDao {

	
	public  List<DateResourcesVo>  getResourcesDate(Integer userId);
}
