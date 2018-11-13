package cloud.entity.classroom.every;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Role implements Serializable
{
	private Integer roleid;
	
	private String rolename;
	
	private Timestamp createtime;
	
	private String describes;

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	
}