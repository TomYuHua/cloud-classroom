package cloud.service.classroom.services.Filetype;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cloud.entity.classroom.every.Filetype;
import cloud.entity.classroom.every.User;
import cloud.service.classroom.annotation.TargetDataSource;
import cloud.service.classroom.dao.cluster.FileTypeDao;
import cloud.service.classroom.dao.cluster.StudentDao2;
import cloud.service.classroom.interfaces.FiletypeService;


@Service
public class FiletypeServiceImpl implements FiletypeService {
	
	@Autowired
	private FileTypeDao filetypeMapper;
	
	
	
	 // 事务的传播性
	@Override
    public boolean insert(Filetype record)	{
		try{
			filetypeMapper.insert(record);
		   }catch (Exception e){
			e.printStackTrace();
			  return false;
	    	}
		    return true;
          }
	
	
	
	
	
	
	 // 事务的传播性
	@Override
    public boolean delete(Integer id){
		try{
			filetypeMapper.delete(id);
		   }catch (Exception e){
			e.printStackTrace();
			  return false;
	    	}
		    return true;
          }
	
	 // 事务的传播性
	@Override
    public boolean update(Filetype filetype){		
    try{
		filetypeMapper.update(filetype);
	   }catch (Exception e){
		e.printStackTrace();
		  return false;
 	}
	    return true;
   }
    
    
    @Override
    public Filetype getFileType(Integer id){
    	Filetype filetype=filetypeMapper.getFileType(id);
    	return filetype;
    }
    
    
    
    
    
    @Override
    public List<Filetype> getByPage(int page,int rows,Filetype filetype)
    {
		Page<Filetype> filetypePage = PageHelper.startPage(page, rows, true);
		List<Filetype> filetypes = filetypeMapper.getByPage(filetype);
		System.out.println("-------------------" + filetypePage .toString() + "-----------");
		return filetypes;
    }





}
