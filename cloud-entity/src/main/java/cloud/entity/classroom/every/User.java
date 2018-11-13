package cloud.entity.classroom.every;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class User implements Serializable
{

	private static final long serialVersionUID = 1L;

	private Integer userId;

	private String userName;

	private String name;

	private Date createTime;

	private Date loginTime;

	private String nickName;

	private Integer loginCount;

	private Integer sex;

	private Integer fieldTargetId;

	private String phone;

	private String email;

	private String idCard;

	private String igeUrl;

	private String introduction;

	private Integer userType;

	private String passWord;

	private String inputCode;

	private String inputPassWord;



	private String imgSrc;
	
	private List<Integer> types;
	
	private List<String> menuNoLists;

	public Integer getState()
	{
		return state;
	}

	public void setState(Integer state)
	{
		this.state = state;
	}

	private Integer state;

	public Student student;

	public Teacher teacher;

	public Integer page;

	public Integer getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	public Integer getScoresForOneResource() {
		return scoresForOneResource;
	}

	public void setScoresForOneResource(Integer scoresForOneResource) {
		this.scoresForOneResource = scoresForOneResource;
	}

	public Integer rows;
	
	public Integer userRoleId;
	
	
	public Integer scoresForOneResource;

	public Integer getPage()
	{
		return page;
	}

	public void setPage(Integer page)
	{
		this.page = page;
	}

	public Integer getRows()
	{
		return rows;
	}

	public void setRows(Integer rows)
	{
		this.rows = rows;
	}

	public Integer getUserId()
	{
		return userId;
	}

	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}

	public Date getLoginTime()
	{
		return loginTime;
	}

	public void setLoginTime(Date loginTime)
	{
		this.loginTime = loginTime;
	}

	public Integer getLoginCount()
	{
		return loginCount;
	}

	public void setLoginCount(Integer loginCount)
	{
		this.loginCount = loginCount;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getNickName()
	{
		return nickName;
	}

	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Integer getSex()
	{
		return sex;
	}

	public void setSex(Integer sex)
	{
		this.sex = sex;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getIdCard()
	{
		return idCard;
	}

	public void setIdCard(String idCard)
	{
		this.idCard = idCard;
	}

	public Integer getFieldTargetId()
	{
		return fieldTargetId;
	}

	public void setFieldTargetId(Integer fieldTargetId)
	{
		this.fieldTargetId = fieldTargetId;
	}

	public String getIgeUrl()
	{
		return igeUrl;
	}

	public void setIgeUrl(String igeUrl)
	{
		this.igeUrl = igeUrl;
	}

	public String getIntroduction()
	{
		return introduction;
	}

	public void setIntroduction(String introduction)
	{
		this.introduction = introduction;
	}

	public Integer getUserType()
	{
		return userType;
	}

	public void setUserType(Integer userType)
	{
		this.userType = userType;
	}

	public String getPassWord()
	{
		return passWord;
	}

	public void setPassWord(String passWord)
	{
		this.passWord = passWord;
	}

	public String getInputCode()
	{
		return inputCode;
	}

	public void setInputCode(String inputCode)
	{
		this.inputCode = inputCode;
	}

	public String getInputPassWord()
	{
		return inputPassWord;
	}

	public void setInputPassWord(String inputPassWord)
	{
		this.inputPassWord = inputPassWord;
	}

	public Student getStudent()
	{
		return student;
	}

	public void setStudent(Student student)
	{
		this.student = student;
	}

	public Teacher getTeacher()
	{
		return teacher;
	}

	public void setTeacher(Teacher teacher)
	{
		this.teacher = teacher;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getImgSrc()
	{
		return imgSrc;
	}

	public void setImgSrc(String imgSrc)
	{
		this.imgSrc = imgSrc;
	}

	public List<Integer> getTypes()
	{
		return types;
	}

	public void setTypes(List<Integer> types)
	{
		this.types = types;
	}
	
	public List<String> getMenuNoLists() {
		return menuNoLists;
	}

	public void setMenuNoLists(List<String> menuNoLists) {
		this.menuNoLists = menuNoLists;
	}

}
