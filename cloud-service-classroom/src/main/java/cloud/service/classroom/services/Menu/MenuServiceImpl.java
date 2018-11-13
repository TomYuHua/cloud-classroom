package cloud.service.classroom.services.Menu;

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

import cloud.entity.classroom.every.Menu;
import cloud.entity.classroom.every.Role;
import cloud.entity.classroom.every.User;
import cloud.service.classroom.annotation.TargetDataSource;
import cloud.service.classroom.dao.cluster.MenuDao;
import cloud.service.classroom.interfaces.MenuService;
import cloud.service.classroom.services.Chapter.ChapterServiceImpl;


@Service
public class MenuServiceImpl implements MenuService
{
	private Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);

	@Autowired
	MenuDao MenuDao;
	
	@Override
	public List<Menu> selectAll() {
		{
			try
			{
				return MenuDao.selectAll();
			} catch (Exception e)
			{
				e.printStackTrace();
				log.error("RoleServiceImpl: 数据服务器异常 ", e);
				return null;
			}
		}
	}
}