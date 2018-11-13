package cloud.service.classroom.interfaces;

import java.util.List;

import cloud.entity.classroom.every.Document;

public interface DocumentService {
	
	public boolean auditDocument(Integer id,String type);
	
	public boolean insertDocument(Document document);

	public boolean deleteDocument(Integer id);
	
	public boolean updateDocument(Document document);
	
	public List<Document> getByPage(int page,int rows,Document document)throws Exception;
}
