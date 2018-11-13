package cloud.entity.classroom.every;

import java.io.Serializable;
import java.util.Date;

public class Privilege implements Serializable
{
	private Integer privilegeid;
	
	private String privilegemaster;
	
	private String privilegemastervalue;
	
	private String privilegeaccess;
	
	private String privilegeaccessvalue;
	
	private String privilegeoperation;

	public Integer getPrivilegeid() {
		return privilegeid;
	}

	public void setPrivilegeid(Integer privilegeid) {
		this.privilegeid = privilegeid;
	}

	public String getPrivilegemaster() {
		return privilegemaster;
	}

	public void setPrivilegemaster(String privilegemaster) {
		this.privilegemaster = privilegemaster;
	}

	public String getPrivilegemastervalue() {
		return privilegemastervalue;
	}

	public void setPrivilegemastervalue(String privilegemastervalue) {
		this.privilegemastervalue = privilegemastervalue;
	}

	public String getPrivilegeaccess() {
		return privilegeaccess;
	}

	public void setPrivilegeaccess(String privilegeaccess) {
		this.privilegeaccess = privilegeaccess;
	}

	public String getPrivilegeaccessvalue() {
		return privilegeaccessvalue;
	}

	public void setPrivilegeaccessvalue(String privilegeaccessvalue) {
		this.privilegeaccessvalue = privilegeaccessvalue;
	}

	public String getPrivilegeoperation() {
		return privilegeoperation;
	}

	public void setPrivilegeoperation(String privilegeoperation) {
		this.privilegeoperation = privilegeoperation;
	}

	
}