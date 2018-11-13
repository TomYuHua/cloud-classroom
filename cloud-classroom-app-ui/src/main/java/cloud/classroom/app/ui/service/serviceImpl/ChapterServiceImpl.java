package cloud.classroom.app.ui.service.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import cloud.classroom.app.ui.service.interfaces.ChapterService;
import cloud.entity.classroom.Chapter.Chapterdirectories;
import cloud.entity.classroom.every.User;

@Service
public class ChapterServiceImpl implements ChapterService
{

	@Autowired
	RestTemplate restTemplate;

	@Value("${cloud-user-service}")
	private String SERVICE_NAME;
	

	@HystrixCommand(fallbackMethod = "fallbackSelectTwoLevelChapterInfo")
	@Override
	public List<Chapterdirectories> SelectTwoLevelChapterInfo()
	{
		List<Chapterdirectories> list = Arrays
				.asList(restTemplate.getForObject("http://" + SERVICE_NAME + "/chapter/gettwoLevelchapterinfo", Chapterdirectories[].class));
		return list;
	}

	public List<Chapterdirectories> fallbackSelectTwoLevelChapterInfo()
	{

		System.out.println("HystrixCommand fallbackSelectTwoLevelChapterInfo handle!");
		return null;
	}

	@Override
	@HystrixCommand(fallbackMethod = "fallbackSelectChapterInfo")
	public List<Chapterdirectories> SelectChapterInfo(Integer parentId)
	{
		List<Chapterdirectories> list = Arrays
				.asList(restTemplate.getForObject("http://" + SERVICE_NAME + "/chapter/getchapterinfo?parentId={parentId}", Chapterdirectories[].class,parentId));
		return list;
	}

	public List<Chapterdirectories> fallbackSelectChapterInfo(Integer parentId)
	{

		System.out.println("HystrixCommand fallbackSelectChapterInfo handle!");
		return null;
	}
}
