package cloud.classroom.app.ui.service.interfaces;

import java.util.List;
import cloud.entity.classroom.DTO.DateResourcesVo;
import cloud.entity.classroom.DTO.ResourceComentsVo;
import cloud.entity.classroom.DTO.ResourcesBrower;
import cloud.entity.classroom.DTO.ResourcesVo;
import cloud.entity.classroom.Resources.Collections;
import cloud.entity.classroom.Resources.Resources;
import cloud.entity.classroom.every.ResponseInfro;

public interface ResourceService
{
	List<Resources> selectAll();

	List<Resources> selectLimit(Integer m, Integer n);

	List<Resources> selectYouLike(Integer userId, Integer m, Integer n);

	Resources showResources(Integer id);

	List<Resources> getResourcesList(Resources resources);

	List<Resources> GetNLevelChildNode(Integer rootId, Integer n);

	List<Resources> GetChildNodeList(Integer parentId);

	List<ResourcesVo> getClusterResources();

	List<Resources> getChildChpaterList(Integer id);

	Resources getResourcesById(Integer id);

	boolean addComments(ResourceComentsVo resourcesComents);

	ResponseInfro getPageByTeacher(Integer userId);

	boolean deleteComments(Integer id);

	boolean makeResourceEva(Resources resources);

	List<ResourcesVo> getCollections(Integer userId, Integer types);

	List<Resources> getParallelChpaterList(Integer currentId);

	boolean addCollections(Integer id, Integer userId,Integer types);

	List<ResourceComentsVo> getCommentsByResourceId(Integer id);

	List<DateResourcesVo> getLastLearn(Integer userId);

	List<Resources> getPersonalDownload(Integer userId);

	List<Resources> getPersonalCourse(Integer id, Integer types);

	List<Resources> selectLimitByTimeOrder(Integer types, Integer m, Integer n);
	
	boolean changeClickCountState (ResourcesBrower resourcesBrower);
	
	Collections getCollectionsByKey(Integer resourceId,Integer userId);
	
	List<Resources> getHeadlineList(Integer id);
	
	boolean deleteCollections(Integer id);

	boolean isExsitDocument(Integer currentId);
}
