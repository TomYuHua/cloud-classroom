package cloud.service.classroom.dao.cluster;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.bouncycastle.jce.provider.CertStoreCollectionSpi;

import com.github.pagehelper.Page;
import com.mysql.cj.core.io.IntegerBoundsEnforcer;


import cloud.entity.classroom.DTO.ResourcesVo;
import cloud.entity.classroom.Resources.Collections;

public interface ResourcesListDao {

	
	List<ResourcesVo> getClusterResources();
	
	Page<ResourcesVo> getResourcesByTeacher(Integer userId);
	
	List<ResourcesVo> getCollection(@Param("userId")Integer userId,@Param("types")Integer types);
	
	Collections isCollectedByLoginUser(@Param("resourceId")Integer resourceId,@Param("userId")Integer userId);
}
