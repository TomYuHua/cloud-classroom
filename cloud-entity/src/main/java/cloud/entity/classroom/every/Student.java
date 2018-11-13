package cloud.entity.classroom.every;

import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable {
	
	   private Integer id;
	   
	   private String studentNo;
	        
       private String  province;
   	
       private String  city;
   	
       private String  area;
        
       private String addr;
        
       private int classId;
       
       private int gradeId;
       
private String  street;
	   
	   private String schoolNo;
	   
	   private String typeId;
	   
	   private String idNumber;
	   
	   private String description;
	   
	   private String name;
	   
	   private String sex;
	   
	   private Date birthday;
	   
	   private String birthPlace;
	   
	   private String homeTown;
	   
	   private String nation;
	   
	   private String nationality;
	   
	   private String overSeasChinese;
	   
	   private String politicalStatus;
	   
	   private String health;
	   
	   private String pic;
	   
	   private String spell;
	   
	   private String formerName;
	   
	   private String valid;
	   
	   private String joinYear;
	   
	   private String className;
	   
	   private String gradeName;
       
       public int getClassId() {
		return classId;
	  }

       public int getGradeId() {
		return gradeId;
	  }

	  public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	  }

	  public void setClassId(int classId) {
		this.classId = classId;
	  }

	   
	   public String getClassName() {
		return className;
	  }

	public void setClassName(String className) {
		this.className = className;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getJoinYear() {
		 return joinYear;
	 }

	public void setJoinYear(String joinYear) {
		 this.joinYear = joinYear;
	 }

	  public Integer getId(){
		   return id;
	   }
	   
	   public void setId(Integer id){
		   this.id=id;
	   }




	  public String getSchoolNo() {
	 	return schoolNo;
	 }

	public void setSchoolNo(String schoolNo) {
		this.schoolNo = schoolNo;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getHomeTown() {
		return homeTown;
	}

	public void setHomeTown(String homeTown) {
		this.homeTown = homeTown;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getOverSeasChinese() {
		return overSeasChinese;
	}

	public void setOverSeasChinese(String overSeasChinese) {
		this.overSeasChinese = overSeasChinese;
	}

	public String getPoliticalStatus() {
		return politicalStatus;
	}

	public void setPoliticalStatus(String politicalStatus) {
		this.politicalStatus = politicalStatus;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
	}

	public String getFormerName() {
		return formerName;
	}

	public void setFormerName(String formerName) {
		this.formerName = formerName;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}
	   
	 public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
    
 	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
    public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}


	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
