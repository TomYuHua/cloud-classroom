package cloud.service.classroom.interfaces;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.github.pagehelper.Page;

import cloud.entity.classroom.every.Menu;

public interface MenuService
{
	
	List<Menu> selectAll();
}