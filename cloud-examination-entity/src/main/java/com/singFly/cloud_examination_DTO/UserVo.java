package com.singFly.cloud_examination_DTO;



import com.singFly.cloud_examination_student.Student;
import com.singFly.cloud_examination_teacher.Teacher;
import com.singFly.cloud_examination_user.User;

public class UserVo  extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Student student;
	
	public Teacher teacher;
	
	private int operationType;
	
	private String inputPassWord;


	private String inputCode;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
    
	
	public int getOperationType() {
		return operationType;
	}

	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}
	
	public String getInputCode() {
		return inputCode;
	}

	public void setInputCode(String inputCode) {
		this.inputCode = inputCode;
	}
	
	public String getInputPassWord() {
		return inputPassWord;
	}

	public void setInputPassWord(String inputPassWord) {
		this.inputPassWord = inputPassWord;
	}
}
