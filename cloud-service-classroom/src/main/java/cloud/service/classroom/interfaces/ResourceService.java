package cloud.service.classroom.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;

import cloud.entity.classroom.DTO.DateResourcesVo;
import cloud.entity.classroom.DTO.ResourceComentsVo;
import cloud.entity.classroom.DTO.ResourcesBrower;
import cloud.entity.classroom.DTO.ResourcesVo;
import cloud.entity.classroom.Resources.Collections;
import cloud.entity.classroom.Resources.ResourceComents;
import cloud.entity.classroom.Resources.Resources;
import cloud.entity.classroom.every.User;

public interface ResourceService
{
	List<Resources> selectAll();

	List<Resources> selectLimit(Integer m, Integer n);

	List<Resources> selectYouLike(Integer userId, Integer m, Integer n);

	List<Resources> getResourcesList(Resources resources);

	List<Resources> GetNLevelChildNode(Integer rootId, Integer n);

	List<Resources> GetChildNodeList(Integer parentId);

	List<ResourcesVo> getClusterResources();

	List<Resources> getChildChpaterList(Integer id);

	Resources getResourcesById(Integer id);

	boolean addComments(ResourceComentsVo resourceComents);

	boolean makeResourceEva(Resources resources);

	boolean makeGreate(ResourceComents resourceComents);

	boolean deleteComments(Integer id);

	Page<ResourcesVo> getPageByTeacher(Integer userId);

	List<ResourcesVo> getCollection(Integer userId, Integer types);

	List<Resources> getParallelChpaterList(Integer currentId);

	List<Resources> showResources(Resources resources);

	boolean addCollections(Integer id, Integer userId,Integer types);

	boolean changeClickCountState(ResourcesBrower resourcesBrower);
	
	

	List<Resources> getPersonalDownload(Integer userId);

	List<Resources> getPersonalCourse(Integer id, Integer types);

	List<Resources> selectLimitByTimeOrder(Integer types, Integer m, Integer n);
	
	Collections isCollectedByLoginUser(Integer resourceId,Integer userId);
		
	List<Resources> getHeadlineList(Integer id);
	
	boolean deleteCollections(Integer id);
	
	boolean isExsitDocument(Integer currentId);
	
	List<ResourceComentsVo> getTreeNode(Integer id);
	
	List<ResourceComentsVo> queryTreeNode(Integer id);
	
	List<DateResourcesVo> getResourcesDate(Integer userId);

	List<ResourcesBrower> getBrowerByUserId(Integer userId);
    

}
