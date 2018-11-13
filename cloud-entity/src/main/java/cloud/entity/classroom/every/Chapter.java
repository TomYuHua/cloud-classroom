package cloud.entity.classroom.every;

public class Chapter {
     
	private Integer id;
	
	private Integer pId;
	
	private String name;
	
	public Integer getId(){
	    return id;
	}
	   
	public void setId(Integer id){
		this.id=id;
	}
	   
    public Integer getPId(){
	    return pId;
	}
	   
    public void setPId(Integer pId){
	    this.pId=pId;
	}
	   
    public String getName(){
	   return name;
	}
	   
     public void setName(String name){
	   this.name=name;
	}
}
