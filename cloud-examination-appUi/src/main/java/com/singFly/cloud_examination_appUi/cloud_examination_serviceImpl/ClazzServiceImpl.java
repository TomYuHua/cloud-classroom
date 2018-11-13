package com.singFly.cloud_examination_appUi.cloud_examination_serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.singFly.cloud_examination_DTO.ClazzVo;
import com.singFly.cloud_examination_appUi.cloud_examination_service.ClazzService;
import com.singFly.cloud_examination_clazz.Clazz;
import com.singFly.cloud_examination_idsBean.IdsBean;
import com.singFly.cloud_examination_question.Question;



@Service
public class ClazzServiceImpl implements ClazzService {
	
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${cloud-user-service}")
	private String SERVICE_NAME;
	
	public List<ClazzVo> getClazzLists(Integer schoolId,String clazzName){
         
        return Arrays.asList(restTemplate.getForObject("http://"+SERVICE_NAME +"/clazz/getClazzLists?schoolId={schoolId}&clazzName={clazzName}",ClazzVo[].class,schoolId,clazzName));
	 }

	@Override
	public boolean addClazz(String clazzName,int schoolId) {
		// TODO Auto-generated method stub
		 Clazz clazz=new Clazz();
		 clazz.setClazzName(clazzName);
		 clazz.setSchoolId(schoolId);
		return restTemplate.postForObject("http://"+SERVICE_NAME +"/clazz/addClazz",clazz,boolean.class);
	}

	@Override
	public boolean deleteClazz(IdsBean idsBean) {
		
		// TODO Auto-generated method stub
        List<Integer> ids=idsBean.getIds();
		return restTemplate.postForObject("http://"+SERVICE_NAME +"/clazz/deleteClazz",ids,boolean.class);
	}

	@Override
	public boolean updateClazz(Clazz clazz) {
		// TODO Auto-generated method stub
	   return restTemplate.postForObject("http://"+SERVICE_NAME +"/clazz/updateClazz",clazz,boolean.class);
	}
	
      @Override
	 public ClazzVo getClazzById(int id) {
	 return restTemplate.getForObject("http://"+SERVICE_NAME +"/clazz/getClazzById?id={id}",ClazzVo.class,id);
		
	}
}
