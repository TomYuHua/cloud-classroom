package cloud.service.classroom.services.User;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.search.MessageNumberTerm;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import cloud.entity.classroom.DTO.UserVo;
import cloud.entity.classroom.Enum.RoleId;
import cloud.entity.classroom.every.Menu;
import cloud.entity.classroom.every.Student;
import cloud.entity.classroom.every.Teacher;
import cloud.entity.classroom.every.User;
import cloud.service.classroom.annotation.TargetDataSource;
import cloud.service.classroom.dao.cluster.ClassDao;
import cloud.service.classroom.dao.cluster.MenuDao;
import cloud.service.classroom.dao.cluster.StudentDao2;
import cloud.service.classroom.dao.cluster.TeacherDao2;
import cloud.service.classroom.dao.cluster.UserDao;
import cloud.service.classroom.dao.cluster.UserVoDao;
import cloud.service.classroom.dao.master.StudentDao;
import cloud.service.classroom.interfaces.UserService2;

@Service
public class UserServiceImpl2 implements UserService2
{

	@Autowired
	private ClassDao classDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserVoDao userVoDao;

	@Autowired
	private StudentDao2 studentDao;

	@Autowired
	private TeacherDao2 teacherDao;
	
	@Autowired
	private MenuDao menuDao;


	@Override
	public User getUserByUserName(String userName)
	{  
		User user=userDao.getUserByUserName(userName);
		List<Menu> menuLists=menuDao.selectMenuByUserName(userName);
	    List<String> menuNoLists=new ArrayList<>();
	    for(Menu menu:menuLists){
        menuNoLists.add(menu.getMenuno());
	   }
	    user.setMenuNoLists(menuNoLists);
		 return  user;
			
	}


	@Override
	public boolean resetPassWord(User user)
	{
		try
		{

			userDao.resetPassWord(user);
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}

		return true;
	}


	@Override
	public boolean userResetPassWord(User user)
	{
		try
		{

			if (userDao.resetPassWord(user) > 0)
			{
				return true;
			} else
			{
				return false;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}


	@Override
	public boolean updateUser(User user)
	{
		try
		{
			if (user.getUserType() == 0)
			{
				userDao.updateUser(user);
			} else if (user.getUserType() == 1)
			{
				// Student student=user.student;
				Student student = new Student();
				student = user.getStudent();
				if (studentDao.updateStudent(student) > 0)
					;
				userDao.updateUser(user);
			} else if (user.getUserType() == 2)
			{
				Teacher teacher = user.teacher;
				if (teacherDao.updateTeacher(teacher) > 0)
					;
				userDao.updateUser(user);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}


	@Override
	public User getUser(User user)
	{
		User person = new User();
		try
		{
			if (user.getUserType() == 0)
			{
				person = userDao.getUser(user.getUserId());
			} else if (user.getUserType() == 1)
			{
				Student student = new Student();
				person.setStudent(student);
				person = userDao.getStudent(user.getUserId());
			} else if (user.getUserType() == 2)
			{
				Teacher teacher = new Teacher();
				person.setTeacher(teacher);
				person = userDao.getTeacher(user.getUserId());
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return person;
	}


	@Override
	public Page<User> getPage(Integer page, Integer rows, User user) throws Exception
	{
		if ((page != null) && (rows != null))
		{
			PageHelper.startPage(page, rows, true);
		}
		Page<User> users = userDao.getByPage(user);
		// System.out.println("-------------------" + userPage.toString() +
		// "-----------");
		return users;
	}


	@Override
	public boolean auditUser(Map<String, Object> params)
	{
		try
		{
			userDao.auditUser(params);
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}


	@Override
	public boolean makeRole(Integer id, Integer type)
	{
		try
		{ Map<String,Object> params=new HashMap<String,Object>();
		  List<Integer> list = new ArrayList<Integer>();
		  list.add(id);
		  params.put("list", list);
			userDao.makeRole(id, type);
	      if(type==1){
			params.put("userRoleId",RoleId.studentRole.getIdNumber());
;
	      }else if(type==2){
	    	params.put("userRoleId",RoleId.teacherRole.getIdNumber());
		  }
			userDao.insertUserRole(params);
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}


	@Override
	public boolean insertUser(User user)
	{
		try
		{
			userDao.insertUser(user);
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public User checkExistMail(String email)
	{

		return userDao.checkExistMail(email);

	}

	@Override
	public boolean deleteUser(Integer id)
	{

		try
		{
			User user = userDao.getUser(id);
			if (user.getUserType() == 0)
			{
				userDao.deleteUser(id);
			} else if (user.getUserType() == 1)
			{
				if (studentDao.deleteStudent(user.getFieldTargetId()) > 0)
					;
				userDao.deleteUser(id);
			} else if (user.getUserType() == 2)
			{
				if (teacherDao.deleteTeacher(user.getFieldTargetId()) > 0)
					;
				userDao.deleteUser(id);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}


	@Override
	public List<User> getFamousTeacher(Integer m, Integer n)
	{
		// User user=new User();
		// Teacher teacher=new Teacher();
		// user.setTeacher(teacher);
		List<User> list = userDao.getFamousTeacher(m, n);
		return list;
	}


	@Override
	public UserVo getTeacherInfro(Integer userId)
	{
		UserVo userVo = userVoDao.selectTeacherStatic(userId);
		// UserVo userVo=userVoDao.selectTeacherTeaching(userId);
		// userVo.setEbookNum(user.getEbookNum());
		// userVo.setVideoNum(user.getVideoNum());
		// userVo.setDownloadsNum(user.getDownloadsNum());
		// userVo.setBrowersNum(user.getBrowersNum());
		return userVo;
	}


	@Override
	public Integer selectTypes(String userName)
	{
		return userDao.selectTypes(userName);
	}

	
	@Override
	public User getByNamePassword(String userName, String password)
	{
		// TODO 自动生成的方法存根
		return userDao.getByNamePassword(userName, password);
	}


	@Override
	public User checkUserType(String userName)
	{
		return userDao.checkUserType(userName);
	}
    
	 
	@Override
	public int IsExistsUser(String account)
	{
		 
		return userDao.IsExistsUser(account);
	}
	
	
	@Override
	public User selectScores(Integer id,Integer userId)
	{
        return userDao.selectScores(id, userId);
	}
}
