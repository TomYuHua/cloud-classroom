package cloud.entity.classroom.Enum;

public enum RoleId {
  
	    teacherRole(16,0),
	    studentRole(17,1);
	
	   private Integer idNumber ;
	  
	   private int index ;
	 
	   private RoleId(Integer idNumber,int index ){
	       this.idNumber = idNumber;
	       this.index = index ;
	    }
	     
	  public Integer getIdNumber() {
	       return idNumber;
	   }
	    
	  
	  public void setIdNumber(Integer idNumber) {
	        this.idNumber = idNumber;
	  }
	    
	  public int getIndex() {
	       return index;
	  }
	 
	  public void setIndex(int index) {
	        this.index = index;
	   }
	     

}
