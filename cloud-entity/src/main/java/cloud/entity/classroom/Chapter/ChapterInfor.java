package cloud.entity.classroom.Chapter;

public class ChapterInfor {
	
	private Integer chooseId;
	
	private Integer upperChapterId;
	
	private Integer state;
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	private String chpaterName;

	public Integer getChooseId() {
		return chooseId;
	}

	public void setChooseId(Integer chooseId) {
		this.chooseId = chooseId;
	}

	public Integer getUpperChapterId() {
		return upperChapterId;
	}

	public void setUpperChapterId(Integer upperChapterId) {
		this.upperChapterId = upperChapterId;
	}

	public String getChpaterName() {
		return chpaterName;
	}

	public void setChpaterName(String chpaterName) {
		this.chpaterName = chpaterName;
	}

}
