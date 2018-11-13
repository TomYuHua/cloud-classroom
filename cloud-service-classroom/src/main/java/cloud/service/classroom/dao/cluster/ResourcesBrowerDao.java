package cloud.service.classroom.dao.cluster;

import java.util.List;

import cloud.entity.classroom.DTO.DateResourcesVo;
import cloud.entity.classroom.DTO.ResourcesBrower;

public interface ResourcesBrowerDao {
	

	
	public  List<ResourcesBrower>  getBrowerByUserId(Integer userId);
	
	public  int inertResourcesBrower(ResourcesBrower resourcesBrower);

	

}
