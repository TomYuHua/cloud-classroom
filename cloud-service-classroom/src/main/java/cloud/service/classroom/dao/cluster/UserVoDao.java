package cloud.service.classroom.dao.cluster;

import cloud.entity.classroom.DTO.UserVo;

public interface UserVoDao {
  
	
	UserVo selectTeacherStatic(Integer userId);
	
	UserVo selectTeacherTeaching(Integer userId);
}
