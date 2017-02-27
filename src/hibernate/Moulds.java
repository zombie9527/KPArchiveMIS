package hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Moulds implements java.io.Serializable {
	
	@Id
	@GeneratedValue
	private Integer mouldid;
	private String title;
	private String content;
	public Moulds(){}
	public Moulds(String title, String content){
		this.setTitle(title);
		this.setContent(content);
	}
	public Integer getMouldid() {
		return mouldid;
	}
	public void setMouldid(Integer mouldid) {
		this.mouldid = mouldid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String toString(){
		return "Moulds[mouldid="+mouldid+",title="+title+",content="+content+"]";
	}
	
}
