package cloud.service.classroom.dao.cluster;

import java.util.List;

import cloud.entity.classroom.DTO.ResourceComentsVo;
import cloud.entity.classroom.Resources.ResourceComents;

public interface CommentsDao {
   List<ResourceComentsVo> getTreeNode(Integer id);
   
   List<ResourceComentsVo> queryTreeNode(Integer parentId);
   
   int addComments(ResourceComents resourceComents);
   
   
   int deleteComments(Integer id);
   
   float getAveScore(Integer resourceId);
}
