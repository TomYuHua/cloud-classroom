package cloud.entity.classroom.every;

import java.util.List;

public class ResponseInfro<E> {
   
public List<E> dataPage;
	
	public PageInfro pageInfro;
	
	public String error;

	
	public List<E> getDataPage() {
		return dataPage;
	}

	public void setDataPage(List<E> dataPage) {
		this.dataPage = dataPage;
	}

	public PageInfro getPageInfro() {
		return pageInfro;
	}

	public void setPageInfro(PageInfro pageInfro) {
		this.pageInfro = pageInfro;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
