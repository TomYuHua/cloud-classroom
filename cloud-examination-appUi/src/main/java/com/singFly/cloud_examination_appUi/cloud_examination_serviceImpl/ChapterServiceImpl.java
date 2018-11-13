package com.singFly.cloud_examination_appUi.cloud_examination_serviceImpl;


import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.singFly.cloud_examination_DTO.ChapterVo;
import com.singFly.cloud_examination_appUi.cloud_examination_service.ChapterService;
import com.singFly.cloud_examination_chapter.Chapter;




@Service
public class ChapterServiceImpl implements ChapterService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${cloud-user-service}")
	private String SERVICE_NAME;

	
	@HystrixCommand(fallbackMethod = "fallbackAddChapter")
	public boolean addChapter(Chapter chapter) {
		// TODO Auto-generated method stub
		return restTemplate.postForObject("http://"+SERVICE_NAME +"/chapter/addChapter",chapter, boolean.class);
	}

	public boolean fallbackAddChapter(Chapter chapter) {
		// TODO Auto-generated method stub
		return false;
	}
    
	@HystrixCommand(fallbackMethod = "fallbackDeleteChapters")
	public boolean deleteChapters(List<Integer> ids) {
		// TODO Auto-generated method stub
		return restTemplate.postForObject("http://"+SERVICE_NAME +"/chapter/deleteChapters",ids, boolean.class);
	}
	
	public boolean fallbackDeleteChapters(List<Integer> ids) {
		// TODO Auto-generated method stub
		return false;
	}
    
	//@HystrixCommand(fallbackMethod = "fallbackSelectChapters")
	public List<ChapterVo> selectChapters(int id,int type) {
		// TODO Auto-generated method stub
			List<ChapterVo> lists=Arrays.asList(restTemplate.getForObject("http://"+SERVICE_NAME +"/chapter/selectChapters?id={id}&type={type}", ChapterVo[].class,id,type));
		      return lists;
	}
	
	 public List<ChapterVo> fallbackSelectChapters(int id,int type){
		// TODO Auto-generated method stub
		return null;
	}
    
	@HystrixCommand(fallbackMethod = "fallbackUpdateChapter")
	public boolean updateChapter(Chapter chapter) {
		// TODO Auto-generated method stub
		return restTemplate.postForObject("http://"+SERVICE_NAME +"/chapter/updateChapter",chapter,boolean.class);
	}
	
	public boolean fallbackUpdateChapter(Chapter chapter) {
		// TODO Auto-generated method stub
		return false;
	}
    
	@HystrixCommand(fallbackMethod = "fallbackGetChapter")
	public Chapter getChapter(int id) {
		// TODO Auto-generated method stub
		return restTemplate.postForObject("http://"+SERVICE_NAME +"/chapter/getChapter",id,Chapter.class);
	}
	
	public Chapter fallbackGetChapter(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
