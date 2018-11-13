package cloud.service.classroom.interfaces;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;

import cloud.entity.classroom.DTO.UserVo;
import cloud.entity.classroom.every.User;

public interface UserService2
{

	public boolean deleteUser(Integer ids);

	public User getByNamePassword(String userName, String password);

	public User getUserByUserName(String userName);

	public boolean resetPassWord(User user);

	public User getUser(User user) throws Exception;

	public Page<User> getPage(Integer page, Integer rows, User user) throws Exception;

	public boolean auditUser(Map<String, Object> params);

	public boolean makeRole(Integer id, Integer type);

	public boolean insertUser(User user);

	public boolean userResetPassWord(User user);

	public boolean updateUser(User user);

	public User checkExistMail(String email);

	public List<User> getFamousTeacher(Integer m, Integer n);

	public UserVo getTeacherInfro(Integer userId);

	public Integer selectTypes(String userName);

	public User checkUserType(String userName);

	public int IsExistsUser(String account);
	
	public User selectScores(Integer id,Integer userId);
}
