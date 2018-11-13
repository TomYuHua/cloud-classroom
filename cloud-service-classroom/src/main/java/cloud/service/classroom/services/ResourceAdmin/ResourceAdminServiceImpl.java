package cloud.service.classroom.services.ResourceAdmin;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cloud.entity.classroom.DTO.DateResourcesVo;
import cloud.entity.classroom.DTO.ResourceComentsVo;
import cloud.entity.classroom.DTO.ResourcesBrower;
import cloud.entity.classroom.DTO.ResourcesVo;
import cloud.entity.classroom.Resources.ResourceComents;
import cloud.entity.classroom.Resources.Resources;
import cloud.entity.classroom.every.User;
import cloud.service.classroom.annotation.TargetDataSource;
import cloud.service.classroom.dao.cluster.CommentsDao;
import cloud.service.classroom.dao.cluster.DateResourcesDao;
import cloud.service.classroom.dao.cluster.ResourceAdminDao;
import cloud.service.classroom.dao.cluster.ResourcesBrowerDao;
import cloud.service.classroom.dao.cluster.ResourcesDao;
import cloud.service.classroom.dao.cluster.ResourcesListDao;
import cloud.service.classroom.interfaces.ResourceAdminService;
import cloud.service.classroom.interfaces.ResourceService;
import cloud.service.classroom.services.Chapter.ChapterServiceImpl;
import cloud.service.classroom.services.Resources.ResourceServiceImpl;

@Service
public class ResourceAdminServiceImpl implements ResourceAdminService {
    private Logger log = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Autowired
    ResourceAdminDao resourcesAdminDao;
    
    @Autowired
    ResourcesDao resourcesDao;

    @Value("${dfs-filesystem}")
    private String filesystem;
    
    private static final String userInfo = "userInfoId";

    @Override
    public int insert(Resources record) {
        try {
            return resourcesAdminDao.insert(record);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ResourceServiceImpl: 数据服务器异常 ", e);
            return -1;
        }
    }

     // 事务的传播性
    @Override
    public List<Resources> selectAll() {
        try {
            return resourcesAdminDao.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ResourceServiceImpl: 数据服务器异常 ", e);
            return null;
        }
    }
    

    

    @Override
    public boolean delete(List<Integer> id) {

        try {
           boolean result= resourcesAdminDao.delete(id);
           if(result){ resourcesDao.deleteBacthCollections(id);
           }else{
               TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();   
           }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
         }
        return true;
    }
    
    @Override
    public String getContent(Integer id) {
    	
    	Resources resource = resourcesAdminDao.getContent(id);
    	String content = resource.getContents();
    	
    	System.out.println(content);
    	
    	return content;
    }

    @Override
    public boolean changeResources(Resources resources) {
    	      
    	try{
    	if (resources.getTypes() == 3) {
    		if (resourcesAdminDao.changeResourcesText(resources)) {
    			return true;
    		} else {
    			return false;
    		}
    		
    	}else {
    		if (resourcesAdminDao.changeResources(resources)) {
    	           return true;
    	       } else {
    	           return false;
    	       }
    	}
    	    }catch(Exception e){
    		  e.printStackTrace();
    		  return false;
    	}
    }

    @Override
    public boolean changefile(Resources resources) {

        if (resourcesAdminDao.changefile(resources)) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public boolean checkResources(Resources resources) {
        log.info("checkResources:" + resources.getStatus());
        return resourcesAdminDao.checkResources(resources);
    }

    @Override
    public int insertfile(Resources record) {
        try {
        	record.setIsdocument(true);
        	record.setTypes(0);
        	record.setScores((float)0);
        	record.setClickcount(0);
        	record.setStatus((byte)0);
        	
            return resourcesAdminDao.insert(record);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ResourceServiceImpl: 数据服务器异常 ", e);
            return -1;
        }
    }
    
    @Override
    public int uploadfiles(List<Resources> resources) {
		
	try {
		for (int i = 0; i != resources.size(); ++i)
			{
				resourcesAdminDao.uploadfiles(resources.get(i));
			}
		return 1;
    } catch (Exception e) {
    	e.printStackTrace();
    	log.error("ResourceServiceImpl: 数据服务器异常 ", e);
    	return -1;
    	}
    }
    
    @Override
    public int uploadtext(Resources resources) {
    try{
		resourcesAdminDao.uploadtext(resources);

		return 1;
    } catch (Exception e) {
    	e.printStackTrace();
    	log.error("ResourceServiceImpl: 数据服务器异常 ", e);
    	return -1;
    	}
    }
}