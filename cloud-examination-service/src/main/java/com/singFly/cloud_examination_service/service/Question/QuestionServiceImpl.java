package com.singFly.cloud_examination_service.service.Question;



import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import com.singFly.cloud_examination_DTO.QuestionParameters;
import com.singFly.cloud_examination_DTO.QuestionVo;
import com.singFly.cloud_examination_service.dao.cluster.QuestionDao;
import com.singFly.cloud_examination_service.interfaces.Question.QuestionService;




@Service
public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	private QuestionDao questionDao;
	
	public boolean operateQuestion(QuestionVo question){
		boolean result=false;
		int type=question.getOperationType();
		try{
			switch (type) {
			case 0:
				result=questionDao.addQuestion(question);
				break;

			case 1:
				result=questionDao.updateQuestion(question);
				break;
			}

		    if(result){
		    	return true;
		    }else{
		        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		    	return false;
		     }
		   }catch(Exception e){
			   e.printStackTrace();
			   TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			    return false;
		 }
	}
	
	
	
     public boolean deleteQuestions(List<Integer> ids){
           return questionDao.deleteQuestions(ids);
     }
     
     public List<QuestionVo> getQuestions(QuestionParameters query){
    	 List<QuestionVo> list=new LinkedList<>();
   
    	 switch (Integer.valueOf(query.getOprationType())) {
			case 0:
				list=questionDao.selectUnFiQu(query);
				break;

			case 1:
				list=questionDao.selectFiQu(query);
				break;
				
			case 2:
				list=questionDao.selectErQu(query);
				break;
			}
    	 return list;
     }



 
}
