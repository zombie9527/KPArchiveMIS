package hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@SuppressWarnings("serial")
public class Keepin implements java.io.Serializable {
	
	@Id
	@GeneratedValue
	private Integer keepid;
	private String name;
	private String content;
	public Keepin(){}
	public Keepin(String name, String content){
		this.name  = name;
		this.content = content;
	}
	public Integer getKeepid() {
		return keepid;
	}
	public void setKeepid(Integer id) {
		this.keepid = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String toString(){
		return "Moulds[mouldid="+keepid+",title="+name+",content="+content+"]";
	}
	
}