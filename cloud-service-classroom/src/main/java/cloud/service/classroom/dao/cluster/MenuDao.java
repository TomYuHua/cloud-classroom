package cloud.service.classroom.dao.cluster;

import cloud.entity.classroom.every.Menu;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

public interface MenuDao
{
	
	List<Menu> selectAll();
	
	List<Menu> selectMenuByUserName(String userName);
}