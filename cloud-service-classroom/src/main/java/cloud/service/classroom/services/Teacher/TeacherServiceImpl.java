package cloud.service.classroom.services.Teacher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cloud.entity.classroom.every.Teacher;
import cloud.entity.classroom.every.User;
import cloud.service.classroom.annotation.TargetDataSource;
import cloud.service.classroom.dao.cluster.TeacherDao2;
import cloud.service.classroom.dao.cluster.UserDao;
import cloud.service.classroom.interfaces.TeacherService2;
 

@Service
public class TeacherServiceImpl implements TeacherService2 {
	
	
	@Autowired
	private TeacherDao2 teacherDao;
	@Autowired
	private UserDao userDao;

	/**
	 * 
	 * 
	 * @param student
	 * @return
	 * 
	 * 
	 * 		事务的传播性........................................................................
	 *         public enum Propagation { REQUIRED(0), SUPPORTS(1), MANDATORY(2),
	 *         REQUIRES_NEW(3), NOT_SUPPORTED(4), NEVER(5), NESTED(6); }
	 *         REQUIRED：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。
	 *         SUPPORTS：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
	 *         MANDATORY：如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。
	 *         REQUIRES_NEW：创建一个新的事务，如果当前存在事务，则把当前事务挂起。
	 *         NOT_SUPPORTED：以非事务方式运行，如果当前存在事务，则把当前事务挂起。
	 *         NEVER：以非事务方式运行，如果当前存在事务，则抛出异常。
	 *         NESTED：如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于REQUIRED
	 * 
	 *         隔离级别...........................................................................
	 *         public enum Isolation { DEFAULT(-1), READ_UNCOMMITTED(1),
	 *         READ_COMMITTED(2), REPEATABLE_READ(4), SERIALIZABLE(8); }
	 * 
	 *         DEFAULT：这是默认值，表示使用底层数据库的默认隔离级别。对大部分数据库而言，通常这值就是：READ_COMMITTED。
	 *         READ_UNCOMMITTED：该隔离级别表示一个事务可以读取另一个事务修改但还没有提交的数据。该级别不能防止脏读和不可重复读，因此很少使用该隔离级别。
	 *         READ_COMMITTED：该隔离级别表示一个事务只能读取另一个事务已经提交的数据。该级别可以防止脏读，这也是大多数情况下的推荐值。
	 *         REPEATABLE_READ：该隔离级别表示一个事务在整个过程中可以多次重复执行某个查询，并且每次返回的记录都相同。即使在多次查询之间有新增的数据满足该查询，这些新增的记录也会被忽略。该级别可以防止脏读和不可重复读。
	 *         SERIALIZABLE：所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，该级别可以防止脏读、不可重复读以及幻读。但是这将严重影响程序的性能。通常情况下也不会用到该级别
	 * 
	 */
//	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED) // 事务的传播性
 
	@Override
	public boolean insertTeacher(User user)
	{
		try{
			if((userDao.insertUser(user))>0){
				teacherDao.insertTeacher(user.getTeacher());
		   	}
		}catch (Exception e){
			e.printStackTrace();
			  return false;
	    	}
		    return true;
          }
	
	//@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
 
	@Override
	public List<User> getByPage(int page, int rows,User user)
	{
		Page<User> teacherPage = PageHelper.startPage(page, rows, true);
		List<User> teachers = teacherDao.getByPage(user);
		System.out.println("-------------------" + teacherPage.toString() + "-----------");
		return teachers;
	}

	
	
	//@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
 
	@Override
	public User getTeacher(Integer id)
	throws Exception{
         User user=teacherDao.getTeacher(id);
	
		     return user;
	}

	
	//@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED) // 事务的传播性
 
	@Override
	public boolean deleteTeacher(Integer id)
	{
		 try{
				if(teacherDao.deleteTeacher(id)>0)
				{
				  userDao.deleteUser(id);
				 }
		     	}catch (Exception e)
			    {
				e.printStackTrace();
					return false;
				}
		           return true;
		    	}
			

		  

	
	
	
/*	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED) // 事务的传播性
 
	@Override
	public boolean updateTeacher(User user)
	{
		try{
		   int i=userDao.updateUser(user);
		     if(i>0){
		    	 teacherDao.updateTeacher(user);
		     }
		    
     	}catch(Exception e){
     		e.printStackTrace();
     		 return false;
     	  }
	     
		      return true;
          }

	*/
	


	
	
	
}
	