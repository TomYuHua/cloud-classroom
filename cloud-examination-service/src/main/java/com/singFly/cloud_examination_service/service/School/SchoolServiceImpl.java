package com.singFly.cloud_examination_service.service.School;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Service;

import com.singFly.cloud_examination_DTO.SchoolVo;
import com.singFly.cloud_examination_school.School;
import com.singFly.cloud_examination_service.dao.cluster.SchoolDao;
import com.singFly.cloud_examination_service.interfaces.School.SchoolService;


@Service
public class SchoolServiceImpl implements SchoolService{

	
	@Autowired
	private SchoolDao schoolDao;
	
	@Override
	public List<School> getSchoolists(School school) {
		// TODO Auto-generated method stub
		return schoolDao.getSchoolists(school);
	}

	@Override
	public boolean operateSchool(SchoolVo school) {
		// TODO Auto-generated method stub
		if(school.getOprationType()==1){		
		return schoolDao.addSchool(school);
		  }else{
		return schoolDao.updateSchool(school);	
		}
	}

	@Override
	public boolean deleteSchool(List<Integer> ids) {
		// TODO Auto-generated method stub
		return schoolDao.deleteSchool(ids);
	}


	@Override
	public School getSchoolById(int id) {
		// TODO Auto-generated method stub
		return schoolDao.getSchoolById(id);
	}

}
