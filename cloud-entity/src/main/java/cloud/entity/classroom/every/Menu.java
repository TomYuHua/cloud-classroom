package cloud.entity.classroom.every;

import java.io.Serializable;
import java.util.Date;

public class Menu implements Serializable
{
	private Integer menuid;
	
	private Integer applicationid;
	
	private String menuno;
	
	private String applicationcode;
	
	private String menuparentno;
	
	private Integer menuorder;
	
	private String menuname;
	
	private String menuurl;
	
	private String menuicon;
	
	private Byte isvisible;

	public Integer getMenuid() {
		return menuid;
	}

	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}

	public Integer getApplicationid() {
		return applicationid;
	}

	public void setApplicationid(Integer applicationid) {
		this.applicationid = applicationid;
	}

	public String getMenuno() {
		return menuno;
	}

	public void setMenuno(String menuno) {
		this.menuno = menuno;
	}

	public String getApplicationcode() {
		return applicationcode;
	}

	public void setApplicationcode(String applicationcode) {
		this.applicationcode = applicationcode;
	}

	public String getMenuparentno() {
		return menuparentno;
	}

	public void setMenuparentno(String menuparentno) {
		this.menuparentno = menuparentno;
	}

	public Integer getMenuorder() {
		return menuorder;
	}

	public void setMenuorder(Integer menuorder) {
		this.menuorder = menuorder;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public String getMenuurl() {
		return menuurl;
	}

	public void setMenurul(String menuurl) {
		this.menuurl = menuurl;
	}

	public String getMenuicon() {
		return menuicon;
	}

	public void setMenuicon(String menuicon) {
		this.menuicon = menuicon;
	}

	public Byte getIsvisible() {
		return isvisible;
	}

	public void setIsvisible(Byte isvisible) {
		this.isvisible = isvisible;
	}

	
}