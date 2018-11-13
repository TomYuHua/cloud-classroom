package com.singFly.cloud_examination_service.service.Clazz;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.singFly.cloud_examination_DTO.ClazzVo;
import com.singFly.cloud_examination_clazz.Clazz;
import com.singFly.cloud_examination_service.dao.cluster.ClazzDao;
import com.singFly.cloud_examination_service.interfaces.Clazz.ClazzService;

@Service
public class ClazzServiceImpl implements ClazzService {

@Autowired	
private ClazzDao clazzDao;
	
	@Override
	public List<ClazzVo> getClazzLists(Integer schoolId, String clazzName) {
		// TODO Auto-generated method stub
		return clazzDao.getClazzLists(schoolId, clazzName);
	}

	@Override
	public boolean addClazz(Clazz clazz) {
		// TODO Auto-generated method stub
		return clazzDao.addClazz(clazz);
	}
	
	@Override
	public boolean deleteClazz(List<Integer> ids) {
		// TODO Auto-generated method stub
		return clazzDao.deleteClazz(ids);
	}

	@Override
	public boolean updateClazz(Clazz clazz) {
		// TODO Auto-generated method stub
		return clazzDao.updateClazz(clazz);
	}
	
	@Override
	public ClazzVo  getClazzById(int id){
		return clazzDao.getClazzById(id);
		
	}
	
	
	

}
