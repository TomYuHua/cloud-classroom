package cloud.classroom.app.ui.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import cloud.entity.classroom.DTO.UserVo;
import cloud.entity.classroom.every.User;
import cloud.entity.classroom.user.Users;

public interface UserService
{

	public User getUserByUserName(String UserName);

	public boolean updateUser(User user);

	public boolean insertUser(User user);

	public boolean resetPassWord(User user);

	public boolean userResetPassWord(User user);

	public User getUserDetail(Integer userId, Integer userType);

	public boolean deleteUser(String items);

	public User checkExistMail(String email);

	public JSONObject getUserPage(User user);

	public String bindType(User user);

	public List<User> getFamousTeacher(Integer m, Integer n);

	public UserVo getTeacherInfro(Integer userId);

	public User getByNamePassword(String userName, String password);



	public int IsExistsUser(String account);
	
	public User selectScores(Integer id,Integer userId);

	public User checkUserType(String userName);

}
