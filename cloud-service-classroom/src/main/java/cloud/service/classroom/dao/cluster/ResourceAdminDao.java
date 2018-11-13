package cloud.service.classroom.dao.cluster;

import cloud.entity.classroom.Resources.Resources;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

public interface ResourceAdminDao
{
	int insert(Resources resources);
	
	int insertfile(Resources record);
	
	boolean delete(List<Integer> list);
	
	Resources getContent(Integer id);
	
	boolean changeResources(Resources resources);
	
	boolean changeResourcesText(Resources resources);
	
	boolean changefile(Resources resources);
	
	boolean checkResources(Resources resources);
	
	List<Resources> selectAll();
	
	int uploadfiles(Resources resources);
	
	int uploadtext(Resources resources);
}