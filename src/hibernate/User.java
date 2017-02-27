package hibernate;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Items entity. @author MyEclipse Persistence Tools
 */

@Entity
public class User implements java.io.Serializable {

	// Fields

	@Id
	@GeneratedValue
	private Integer Id;
	private String userId;
	private String passwd;
	private String userName;
	private String grade;
	private Date createTime;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(String userId, String passwd, String userName, String grade,
			Date createTime) {
		this.userId = userId;
		this.passwd = passwd;
		this.userName = userName;
		this.grade = grade;
		this.createTime = createTime;
	}

	// Property accessors

	public Integer getId() {
		return this.Id;
	}

	public void setId(Integer Id) {
		this.Id = Id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "User [Id=" + Id + ",userId=" + userId + ", passwd=" + passwd
				+ ", userName=" + userName + ", grade=" + grade
				+ ", createTime=" + createTime + "]";
	}

	public String toJSON() {
		return "{\"userId\":\"" + userId + "\",\"userName\":\"" + userName
				+ "\",\"grade\":\"" + grade + "\",\"createTime\":\""
				+ createTime + "\"}";
	}

}