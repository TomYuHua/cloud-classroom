package cloud.service.classroom.services.RolePrivilege;

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
import cloud.service.classroom.annotation.TargetDataSource;
import cloud.service.classroom.dao.cluster.PrivilegeDao;
import cloud.service.classroom.dao.cluster.RolePrivilegeDao;
import cloud.service.classroom.interfaces.RolePrivilegeService;
import cloud.service.classroom.services.Chapter.ChapterServiceImpl;
import cloud.service.classroom.services.Privilege.PrivilegeServiceImpl;
import cloud.service.classroom.services.Role.RoleServiceImpl;

@Service
public class RolePrivilegeServiceImpl implements RolePrivilegeService
{

	private Logger log = LoggerFactory.getLogger(RolePrivilegeServiceImpl.class);

	@Autowired
	RolePrivilegeDao roleprivilegedao;

	@Override
	public int insert(RolePrivilege record)
	{
		try
		{
			return roleprivilegedao.insert(record);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("Role explore", e);
			return -1;
		}
	}

	@Override
	public List<RolePrivilege> selectAll()
	{
		try
		{
			return roleprivilegedao.selectAll();
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("RolePrivilegeServiceImpl: 数据服务器异常 ", e);
			return null;
		}
	}

	@Override
	public boolean delete(List<Integer> list)
	{
		try
		{
			roleprivilegedao.delete(list);
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<RolePrivilege> selectPiece(Integer roleid)
	{
		try
		{
			return roleprivilegedao.selectPiece(roleid);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("RolePrivilegeServiceImpl: 数据服务器异常 ", e);
			return null;
		}
	}

}
