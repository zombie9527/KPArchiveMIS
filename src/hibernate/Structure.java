package hibernate;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Structure implements java.io.Serializable{
	// Fields
	@Id
	@GeneratedValue
	private Integer strucId;
	private String content;
	// Constructors
	/** default constructor */
	public Structure(){}
	/** full constructor */
	public Structure(String content){
		this.content = content;
	}
	// Property accessors
	public Integer getStrucId() {
		return strucId;
	}

	public void setStrucId(Integer strucId) {
		this.strucId = strucId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "structure[content="+content+"]";
	}
	public String toJSON() {
		return "{'content':"+content+"}";
	}

	
	
	
}
