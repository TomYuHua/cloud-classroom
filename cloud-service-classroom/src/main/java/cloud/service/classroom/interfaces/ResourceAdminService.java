package cloud.service.classroom.interfaces;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;

import cloud.entity.classroom.Resources.Resources;

public interface ResourceAdminService
{
	int insert(Resources record);
	
	int insertfile(Resources record);
	
	boolean delete(List<Integer> list);
	
	String getContent(Integer id);
	
	boolean changeResources(Resources resources);
	
	boolean changefile(Resources resources);
	
	boolean checkResources(Resources resources);
	
	List<Resources> selectAll();
	
	int uploadfiles(List<Resources> resources);
	
	int uploadtext(Resources resources);
	
}
