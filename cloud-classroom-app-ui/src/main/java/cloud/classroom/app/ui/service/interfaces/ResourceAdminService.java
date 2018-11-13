package cloud.classroom.app.ui.service.interfaces;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cloud.entity.classroom.Resources.Resources;

public interface ResourceAdminService
{
	int insert(Resources record);
	
	int insertfile(Resources record);
	
	boolean delete(List<Integer> list);
	
	boolean changeResources(Resources resources);
	
	String getContent(Integer id);
	
	boolean changefile(Resources resources);
	
	boolean checkResources(Resources resources);

	List<Resources> selectAll();
	
	boolean uploadfiles(List<Resources> resources);
	
	boolean uploadtext(Resources resources);

}
