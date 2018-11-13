package cloud.service.classroom.services.UserRole;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cloud.entity.classroom.every.RolePrivilege;
import cloud.entity.classroom.every.UserRole;
import cloud.service.classroom.annotation.TargetDataSource;
import cloud.service.classroom.dao.cluster.UserRoleDao;
import cloud.service.classroom.interfaces.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	private Logger log = LoggerFactory.getLogger(UserRoleServiceImpl.class);

	@Autowired
	UserRoleDao userroledao;
	
	@Override
	public int insert(UserRole record) {
		try
		{
			return userroledao.insert(record);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("User Role explore", e);
			return -1;
		}
	}

	@Override
	public boolean delete(List<Integer> list) {
		try
		{
			userroledao.delete(list);
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<UserRole> selectAll() {
		try
		{
			return userroledao.selectAll();
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("UserRoleServiceImpl: 数据服务器异常 ", e);
			return null;
		}
	}
}
