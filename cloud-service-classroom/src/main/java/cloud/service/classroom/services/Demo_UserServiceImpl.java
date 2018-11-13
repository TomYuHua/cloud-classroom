package cloud.service.classroom.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cloud.entity.classroom.classinfos.ClassInfo;
import cloud.entity.classroom.user.Users;
import cloud.service.classroom.annotation.TargetDataSource;
import cloud.service.classroom.dao.cluster.ClassDao;
import cloud.service.classroom.dao.cluster.UsersDao;
import cloud.service.classroom.interfaces.Demo_UserService;

@Service
public class Demo_UserServiceImpl implements Demo_UserService
{
	@Autowired
	private ClassDao classDao;

	@Autowired
	private UsersDao userDao;
    
	//@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED) // 事务的传播性
	//@TargetDataSource(dataSource = "clusterDataSource")
	@Override
	public void insert()
	{
		ClassInfo info = new ClassInfo();
		info.setClassName("aa");
		classDao.insert(info);

	}

	//@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED) // 事务的传播性
	//@TargetDataSource(dataSource = "clusterDataSource")
	@Override
	public Integer getcount()
	{
		return userDao.getCount();

	}

	//@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED) // 事务的传播性
	//@TargetDataSource(dataSource = "clusterDataSource")
	@Override
	public int insert(Users record)
	{
		return userDao.insert(record);

	}

	//@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED) // 事务的传播性
	//@TargetDataSource(dataSource = "clusterDataSource")
	@Override
	public List<Users> selectAll()
	{

		return userDao.selectAll();
	}

}
