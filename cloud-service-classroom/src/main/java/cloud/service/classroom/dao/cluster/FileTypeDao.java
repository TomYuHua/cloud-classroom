package cloud.service.classroom.dao.cluster;

import java.util.List;

import cloud.entity.classroom.every.Filetype;

public interface FileTypeDao {
	
	int insert(Filetype fileType);
	
	int delete(Integer id);
	
	Filetype getFileType(Integer id);
    
	
	int update(Filetype filetype);

	List<Filetype> getByPage(Filetype filetype);
}
