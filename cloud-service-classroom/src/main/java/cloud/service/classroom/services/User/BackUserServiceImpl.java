package cloud.service.classroom.services.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cloud.entity.classroom.Enum.RoleId;
import cloud.entity.classroom.every.Student;
import cloud.entity.classroom.every.Teacher;
import cloud.entity.classroom.every.User;
import cloud.service.classroom.annotation.TargetDataSource;
import cloud.service.classroom.dao.cluster.StudentDao2;
import cloud.service.classroom.dao.cluster.TeacherDao2;
import cloud.service.classroom.dao.cluster.UserDao;
import cloud.service.classroom.interfaces.BackUserService;

@Service
public class BackUserServiceImpl implements BackUserService
{

	@Autowired
	private UserDao userDao;

	@Autowired
	private TeacherDao2 teacherDao;

	@Autowired
	private StudentDao2 studentDao;


	@Override
	public boolean addUser(User user)
	{

		try
		{
			if (user.getUserType() == 0)
			{
				userDao.insertUser(user);
			} else if (user.getUserType() == 1)
			{
				Student student = new Student();
				student = user.getStudent();
				if (studentDao.insertStudent(student) > 0)
				{
					user.setFieldTargetId(student.getId());
				}
				userDao.insertUser(user);
				
			} else if (user.getUserType() == 2)
			{
				Teacher teacher = new Teacher();
				teacher = user.getTeacher();

				if (teacherDao.insertTeacher(teacher) > 0)
				{
					user.setFieldTargetId(teacher.getTeacherId());
				}
				userDao.insertUser(user);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}


	@Override
	public boolean bindUser(User user)
	{
      
		try
		{   
			Map<String,Object> params=new HashMap<String,Object>();
			List<Integer> list = new ArrayList<Integer>();
		
			list.add(new Integer(user.getUserId()));
		
			if (user.getUserType() == 1)
			{
				Student student = new Student();
				student = user.getStudent();
				params.put("list", list);
				params.put("userRoleId",RoleId.studentRole.getIdNumber());
				
				if (studentDao.insertStudent(student) > 0)
				{
					user.setFieldTargetId(student.getId());
				}
				userDao.updateUser(user);
			     userDao.deleteUserRole(list);
				userDao.insertUserRole(params);
			} else if (user.getUserType() == 2)
			{
				Teacher teacher = new Teacher();
				teacher = user.getTeacher();
				params.put("list", list);
				params.put("userRoleId",RoleId.teacherRole.getIdNumber());
				if (teacherDao.insertTeacher(teacher) > 0)
				{
					user.setFieldTargetId(teacher.getTeacherId());
				}
				userDao.updateUser(user);
			    userDao.deleteUserRole(list);
				userDao.insertUserRole(params);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
    

	@Override
	public boolean changeUserRole(String items, Integer userRoleId, Integer createUserId)
	{ try{
		String[] item = items.split(",");
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < item.length; i++)
		{
			list.add(new Integer(item[i]));
		}

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("list",list);
		params.put("userRoleId",userRoleId);
		params.put("createUserId",createUserId);
	      userDao.deleteUserRole(list);
		userDao.insertUserRole(params);
	    }catch(Exception e){
               // rollback the Transaction
              TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
             return false;
	     } 
		     return true;
	   }

}