package cloud.service.classroom.interfaces;

import java.util.List;

import cloud.entity.classroom.every.Filetype;

public interface FiletypeService {
	
	
	public boolean insert(Filetype record);
	
	public boolean delete(Integer id);
	
	public boolean update(Filetype filetype);
	
	public Filetype getFileType(Integer id);
	
	public List<Filetype> getByPage(int page,int rows,Filetype filetype);

}
