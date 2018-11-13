package cloud.service.classroom.services.Resources;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.druid.sql.visitor.functions.If;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cloud.entity.classroom.DTO.DateResourcesVo;
import cloud.entity.classroom.DTO.ResourceComentsVo;
import cloud.entity.classroom.DTO.ResourcesBrower;
import cloud.entity.classroom.DTO.ResourcesVo;
import cloud.entity.classroom.Resources.Collections;
import cloud.entity.classroom.Resources.ResourceComents;
import cloud.entity.classroom.Resources.Resources;
import cloud.entity.classroom.every.User;
import cloud.service.classroom.annotation.TargetDataSource;
import cloud.service.classroom.dao.cluster.CommentsDao;
import cloud.service.classroom.dao.cluster.DateResourcesDao;
import cloud.service.classroom.dao.cluster.ResourcesBrowerDao;
import cloud.service.classroom.dao.cluster.ResourcesDao;
import cloud.service.classroom.dao.cluster.ResourcesListDao;
import cloud.service.classroom.interfaces.ResourceService;
import cloud.service.classroom.services.Chapter.ChapterServiceImpl;

@Service
public class ResourceServiceImpl implements ResourceService
{
	private Logger log = LoggerFactory.getLogger(ResourceServiceImpl.class);

	@Autowired
	private ResourcesDao resourcesDao;

	@Autowired
	private ResourcesListDao resourcesListDao;

	@Autowired
	private CommentsDao commentsDao;

	@Autowired
	private ResourcesBrowerDao resourcesBrowerDao;

	@Autowired
	private DateResourcesDao dateResourcesDao;

	@Value("${dfs-filesystem}")
	private String filesystem;




	@Override
	public List<Resources> selectAll()
	{
		try
		{
			return resourcesDao.selectAll();
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("ResourceServiceImpl: 数据服务器异常 ", e);
			return null;
		}
	}

	@Override
	public List<Resources> selectLimit(Integer m, Integer n)
	{
		try
		{
			return resourcesDao.selectLimit(m, n);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("ResourceServiceImpl: 数据服务器异常 ", e);
			return null;
		}
	}

	@Override
	public List<Resources> selectYouLike(Integer userId, Integer m, Integer n)
	{
		try
		{
			return resourcesDao.selectYouLike(userId, m, n);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("ResourceServiceImpl: 数据服务器异常 ", e);
			return null;
		}
	}




	@Override
	public List<Resources> getResourcesList(Resources resources)
	{
		try
		{
			return resourcesDao.getResourcesList(resources);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("ResourceServiceImpl: 数据服务器异常 ", e);
			return null;
		}
	}

	@Override
	public List<Resources> GetNLevelChildNode(Integer rootId, Integer n)
	{
		List<Resources> list = new ArrayList<Resources>();
		try
		{
			list = resourcesDao.GetNLevelChildNode(rootId, n);
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Resources> GetChildNodeList(Integer parentId)
	{
		return resourcesDao.GetChildNodeList(parentId);
	}


	@Override
	public Resources getResourcesById(Integer id)
	{

		Resources resources = resourcesDao.getResourcesById(id);
		if (null != resources.getScores())
		{
			float f = resources.getScores();
			BigDecimal b = new BigDecimal(f);
			float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
			resources.setScores(f1);
		}
		return resources;
	}

	@Override
	public boolean addComments(ResourceComentsVo resourceComents)
	{
		// Date now = new Date();
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd
		// HH:mm:ss");
		// String day = dateFormat.format(now);
		try
		{
			// Date l = dateFormat.parse(day);
			String a = "c";
			// resourceComents.setCreateTime(l);
			String p = "a";

			Timestamp time = new Timestamp(System.currentTimeMillis()); 
			resourceComents.setCreateTime(time);
			commentsDao.addComments(resourceComents);
			float scores = commentsDao.getAveScore(resourceComents.getResourceId());
			resourcesDao.updateScores(resourceComents.getResourceId(), scores);
		} catch (Exception e)
		{

			e.printStackTrace();
			return false;
		}
		return true;

	}

	@Override
	public boolean deleteComments(Integer id)
	{
		try
		{
			commentsDao.deleteComments(id);
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean makeResourceEva(Resources resources)
	{
		if (resourcesDao.makeResourceEva(resources) > 0)
			;

		return true;
	}

	@Override
	public boolean makeGreate(ResourceComents resourceComents)
	{
		if (resourcesDao.makeGreate(resourceComents) > 0)
			;

		return true;
	}


	@Override
	public List<ResourcesVo> getClusterResources()
	{
		return resourcesListDao.getClusterResources();
	};


	@Override
	public List<Resources> getChildChpaterList(Integer id)
	{

		return resourcesDao.getChildChpaterList(id);
	}




	@Override
	public Page<ResourcesVo> getPageByTeacher(Integer userId)
	{
	
		Page<ResourcesVo> resourceList = resourcesListDao.getResourcesByTeacher(userId);
		return resourceList;
	}


	@Override
	public List<ResourcesVo> getCollection(Integer userId, Integer types)
	{
		List<ResourcesVo> list = new ArrayList<ResourcesVo>();
		try
		{
			list = resourcesListDao.getCollection(userId, types);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;

	}


	@Override
	public List<Resources> getParallelChpaterList(Integer currentId)
	{
		return resourcesDao.getParallelChpaterList(currentId);
	}

	@Override
	public List<Resources> showResources(Resources resources)
	{

		try
		{
			return resourcesDao.showResources(resources);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("none of resources", e);
			return null;
		}
	}

	@Override
	public boolean addCollections(Integer id, Integer userId,Integer types)
	{  try{
		if (resourcesDao.addCollections(id, userId,types) > 0)
		{
			return true;
		} else
		{
			return false;
		}
	}catch (Exception e) {
		e.printStackTrace();
		return false;
	  }
	}


	@Override
	public boolean changeClickCountState(ResourcesBrower resourcesBrower){
		try{Timestamp time = new Timestamp(System.currentTimeMillis()); 
		resourcesBrower.setCreateTime(time);
		if(resourcesBrowerDao.inertResourcesBrower(resourcesBrower)>0){
			if(resourcesDao.updateResourceClickCount(resourcesBrower.getResourceId())>0){
			return true;

			} else{
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		}else{
			return false;
		}
		}catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return false;
		}
	}
	

	@Override
	public List<ResourceComentsVo> getTreeNode(Integer id)
	{
		List<ResourceComentsVo> comments = commentsDao.getTreeNode(id);
	    return comments;
	}
	
	@Override
	public List<ResourceComentsVo> queryTreeNode(Integer id)
	{
	
		
		List<ResourceComentsVo> childComments = commentsDao.queryTreeNode(id);
     	return childComments;
	}


	@Override
	public List<DateResourcesVo> getResourcesDate(Integer userId)
	{

		List<DateResourcesVo> list1 = dateResourcesDao.getResourcesDate(userId);
		return list1;
	}
	
	
	@Override
	public List<ResourcesBrower> getBrowerByUserId(Integer userId)
	{


		List<ResourcesBrower> list2 = resourcesBrowerDao.getBrowerByUserId(userId);
		return list2;
	}

	
	




	@Override
	public List<Resources> getPersonalDownload(Integer userId)
	{
		return resourcesDao.getPersonalDownload(userId);
	}




	@Override
	public List<Resources> getPersonalCourse(Integer id, Integer types)
	{
		return resourcesDao.getPersonalCourse(id, types);
	}
    

	@Override
	public List<Resources> selectLimitByTimeOrder(Integer types, Integer m, Integer n)
	{
		return resourcesDao.selectLimitByTimeOrder(types, m, n);
	}
	

	@Override
	public Collections isCollectedByLoginUser(Integer resourceId,Integer userId){
          return resourcesListDao.isCollectedByLoginUser(resourceId, userId);
	 }
	

	@Override
	public List<Resources> getHeadlineList(Integer id){
		List<Resources> headList=new ArrayList<>();
		try{
			headList= resourcesDao.getHeadlineList(id);
  }catch (Exception e) {
	  e.printStackTrace();
      }
		return  headList;
	}
	
	@Override
	public boolean deleteCollections(Integer id) {
     if(resourcesDao.deleteCollections(id)>0){
	  return true;
	 }else{
	  return false;
	 }
	}

	@Override
	public boolean isExsitDocument(Integer currentId){
		List<Resources> resources=resourcesDao.getLowerResourcesList(currentId);
	    if(resources.size()==0){
	    	return false;
	    }else{
	    	return true;
	    }
	}




}