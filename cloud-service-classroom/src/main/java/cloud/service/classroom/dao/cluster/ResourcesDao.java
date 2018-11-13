package cloud.service.classroom.dao.cluster;

import cloud.entity.classroom.Resources.ResourceComents;
import cloud.entity.classroom.Resources.Resources;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ResourcesDao
{
	List<Resources> showResources(Resources resources);

	List<Resources> selectAll();

	List<Resources> selectLimitByTimeOrder(@Param("types") Integer types, @Param("m") Integer m, @Param("n") Integer n);

	List<Resources> selectLimit(@Param("m") Integer m, @Param("n") Integer n);

	List<Resources> selectYouLike(@Param("userId") Integer userId, @Param("m") Integer m, @Param("n") Integer n);

	List<Resources> getResourcesList(Resources resources);

	List<Resources> GetNLevelChildNode(@Param("rootId") Integer rootId, @Param("n") Integer n);

	List<Resources> GetChildNodeList(@Param("parentId") Integer parentId);

	Resources getResourcesById(@Param("id") Integer id);

	int makeResourceEva(Resources resources);

	int addComments(ResourceComents resourceComents);

	int makeGreate(ResourceComents resourceComents);

	int updateScores(@Param("id") Integer id, @Param("scores") float scores);

	List<Resources> getChildChpaterList(Integer id);

	List<Resources> getParallelChpaterList(Integer currentId);

	int addCollections(@Param("id")Integer id,@Param("userId")Integer userId,@Param("types")Integer types);

	List<Resources> getBatchResourcesById(Integer id);

	List<Resources> getPersonalDownload(Integer userId);

	List<Resources> getPersonalCourse(@Param("id") Integer id, @Param("types") Integer types);
	
	List<Resources> getHeadlineList(Integer id);
	
	int deleteCollections(Integer id);
	
	int deleteBacthCollections(List<Integer> ids);
	
	List<Resources> getLowerResourcesList(@Param("currentId") Integer currentId);
	
	int updateResourceClickCount(Integer id);
}