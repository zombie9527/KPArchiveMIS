package hibernate;

import java.sql.Blob;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Fileinfo entity. @author MyEclipse Persistence Tools
 */

@Entity
public class Fileinfo implements java.io.Serializable {

	// Fields

	@Id
	@GeneratedValue
	private Integer fileId;
	private String fileName;
	private String timeName;
	private String realPath;
	private String timePath;
	private Blob file;
	private Integer fileDelete;
	private String fileTitle;
	private Date createTime;
	private String textContent;

	// Constructors

	/** default constructor */
	public Fileinfo() {
	}

	/** full constructor */
	public Fileinfo(String fileName, String timeName, String realPath,
			String timePath, Blob file, Integer fileDelete, String fileTitle,
			Date createTime, String textContent) {
		this.fileName = fileName;
		this.timeName = timeName;
		this.realPath = realPath;
		this.timePath = timePath;
		this.file = file;
		this.fileDelete = fileDelete;
		this.fileTitle = fileTitle;
		this.createTime = createTime;
		this.textContent = textContent;
	}

	// Property accessors

	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTimeName() {
		return this.timeName;
	}

	public void setTimeName(String timeName) {
		this.timeName = timeName;
	}

	public String getRealPath() {
		return this.realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public String getTimePath() {
		return this.timePath;
	}

	public void setTimePath(String timePath) {
		this.timePath = timePath;
	}

	public Blob getFile() {
		return this.file;
	}

	public void setFile(Blob file) {
		this.file = file;
	}

	public Integer getFileDelete() {
		return fileDelete;
	}

	public void setFileDelete(Integer fileDelete) {
		this.fileDelete = fileDelete;
	}

	public String getFileTitle() {
		return fileTitle;
	}

	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	@Override
	public String toString() {
		return "Fileinfo [fileId=" + fileId + ", fileName=" + fileName
				+ ", timeName=" + timeName + ", realPath=" + realPath
				+ ", timePath=" + timePath + ", file=" + file + ", fileDelete="
				+ fileDelete + ", fileTitle=" + fileTitle + ", createTime="
				+ createTime + ", textContent=" + textContent + "]";
	}

}