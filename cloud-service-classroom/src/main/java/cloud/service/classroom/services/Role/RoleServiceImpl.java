package cloud.service.classroom.services.Role;

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

import cloud.entity.classroom.every.Role;
import cloud.entity.classroom.every.User;
import cloud.service.classroom.annotation.TargetDataSource;
import cloud.service.classroom.dao.cluster.CommentsDao;
import cloud.service.classroom.dao.cluster.ResourcesDao;
import cloud.service.classroom.dao.cluster.RoleDao;
import cloud.service.classroom.interfaces.RoleService;
import cloud.service.classroom.services.Chapter.ChapterServiceImpl;


@Service
public class RoleServiceImpl implements RoleService
{
	private Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	RoleDao roleDao;
	
	@Override
	public int insert(Role record)
	{
		try
		{
			return roleDao.insert(record);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("Role explore", e);
			return -1;
		}
	}

	@Override
	public List<Role> selectAll() {
		{
			try
			{
				return roleDao.selectAll();
			} catch (Exception e)
			{
				e.printStackTrace();
				log.error("RoleServiceImpl: 数据服务器异常 ", e);
				return null;
			}
		}
	}

	@Override
	public boolean delete(List<Integer> list) {
		try
		{
			roleDao.delete(list);
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean change(Role role) {
		
		if (roleDao.change(role) > 0)
		{
			return true;
		}else{
			return false;
		}
	}
}