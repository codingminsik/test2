package MinSik.dto;

public class Article extends Dto {
	public String writeName;
	public String title;
	public String body;
	public int wrtierNo;
	
	public Article(int id, String regDate, String updateDate, String writeName, String title, String body, int wrtierNo) {
		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.writeName = writeName;
		this.title = title;
		this.body = body;
		this.wrtierNo = wrtierNo;
	}
}
