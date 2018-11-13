package cloud.service.classroom.interfaces;

import java.util.List;

import cloud.entity.classroom.every.Document;
import cloud.entity.classroom.every.Video;

public interface VideoService {
	
	public boolean auditVideo(Integer id,String type);
	
	public boolean insertVideo(Video video);
	
	public boolean deleteVideo(Integer id);
	
	public boolean updateVideo(Video video);
	
	public List<Video> getByPage(int page,int rows,Video video)throws Exception;

	
	
}
