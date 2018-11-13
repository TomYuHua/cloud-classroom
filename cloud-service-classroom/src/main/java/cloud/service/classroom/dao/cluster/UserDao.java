package cloud.service.classroom.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;

import cloud.entity.classroom.DTO.UserVo;
import cloud.entity.classroom.every.User;

public interface UserDao
{

	String checkPassWord(String userName);

	User getByNamePassword(@Param("userName") String userName, @Param("password") String password);

	int resetPassWord(User user);

	int insertUser(User user);

	int deleteUser(Integer id);

	int updateUser(User user);

	User getUser(Integer userId);

	User getStudent(Integer userId);

	User getTeacher(Integer userId);

	Page<User> getByPage(User user);

	int auditUser(Map<String, Object> params);

	int makeRole(@Param("id") Integer id, @Param("type") Integer type);

	User getUserByUserName(String userName);

	User checkExistMail(String email);

	List<User> getFamousTeacher(@Param("m") Integer m, @Param("n") Integer n);

	Integer selectTypes(String userName);

	User checkUserType(String userName);

	
	int deleteUserRole(List<Integer> list);
	
	int insertUserRole(Map<String,Object> params);


	int IsExistsUser(String account);
	
	User selectScores(@Param("id") Integer id,@Param("userId") Integer userId);


}
