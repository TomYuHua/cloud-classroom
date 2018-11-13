package cloud.entity.classroom.every;

import java.io.Serializable;

public class RequestParameter implements  Serializable {
	private Integer id;
	
	private String  items;
	
	
	private String type;


	public String getItems() {
		return items;
	}


	public void setItems(String items) {
		this.items = items;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}



	

}
