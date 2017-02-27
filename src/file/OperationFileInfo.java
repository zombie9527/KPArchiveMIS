package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Date;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Table;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import hibernate.Fileinfo;

public class OperationFileInfo {

	// 会话需要使用的变量
	private static SessionFactory sessionFactory;
	private static Session session;
	private static Transaction transaction;
	private static Configuration config;
	private static ServiceRegistry serviceRegistry;

	// 初始化一些变量
	static {
		config = new Configuration().configure();
		serviceRegistry = new ServiceRegistryBuilder().applySettings(
				config.getProperties()).buildServiceRegistry();
		sessionFactory = config.buildSessionFactory(serviceRegistry);
	}

	// 向数据库中保存一条文件信息
	public static void insertFileInfo(String filePath, String fileName,
			String timeName, String realPath, String timePath,
			Integer fileDelete, String fileTitle, Date createTime,
			String textContent) {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();

		// 将文件转换成Blob类型
		InputStream input = null;
		Blob blobFile = null;
		File file = new File(filePath);
		try {
			input = new FileInputStream(file);
			blobFile = Hibernate.getLobCreator(session).createBlob(input,
					input.available());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 创建新的文件信息对象
		Fileinfo fileinfo = new Fileinfo();
		fileinfo.setFileName(fileName);
		fileinfo.setTimeName(timeName);
		fileinfo.setRealPath(realPath);
		fileinfo.setTimePath(timePath);
		fileinfo.setFile(blobFile);
		fileinfo.setFileDelete(fileDelete);
		fileinfo.setFileTitle(fileTitle);
		fileinfo.setCreateTime(createTime);
		fileinfo.setTextContent(textContent);
		session.save(fileinfo);

		transaction.commit();
		session.close();
	}

	// 根据指定的id获得文件对象的信息
	public static String[] getFileInfo(int id) {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();

		Fileinfo ff = (Fileinfo) session.get(Fileinfo.class, id);
		// 建立返回的数组
		
		String[] json = { ff.getFileTitle(), ff.getTimePath(),
				ff.getRealPath() ,ff.getFileDelete().toString()};

		transaction.commit();
		session.close();

		return json;
	}

	// 返回指定id的fileinfo对象
	public static Fileinfo ReadFileinfo(int id) {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();

		// 从数据库中读取指定id的file
		Fileinfo f = (Fileinfo) session.get(Fileinfo.class, id);
		session.flush();

		transaction.commit();
		session.close();

		return f;
	}

	// 获取表中一共有多少条记录
	public static int GetTableInfoCount() {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();

		String hql = "select count(*) from Fileinfo";
		String o = session.createQuery(hql).uniqueResult().toString();

		transaction.commit();
		session.close();

		return Integer.parseInt(o);
	}

	// 获取表中第一条记录的id
	public static int GetFirstInfoId() {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();

		Query q = session.createQuery("from Fileinfo order by id");
		q.setMaxResults(1);
		Fileinfo t = (Fileinfo) q.uniqueResult();

		transaction.commit();
		session.close();

		return t.getFileId();
	}
	//删除数据 `fileDelete`设为1
	public static void DeleteFiles(String [] ids){
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
		for (int i = 0; i < ids.length; i++) {
			
			Fileinfo def = (Fileinfo) session.get(Fileinfo.class, Integer.parseInt(ids[i]));
			def.setFileDelete(1);
			session.save(def);
		}
		transaction.commit();
		session.close();
	}
	/**
	 * 重命名文件
	 * @param newname	新文件名字
	 * @param id		数据库编号
	 * @param savePath	存储路径
	 */
	public static String FileRename(String newname,int id,String savePath){
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
		Fileinfo namef = (Fileinfo) session.get(Fileinfo.class, id);
		File oldfile=new File(savePath+"/"+namef.getRealPath()); 
		
		
		namef.setFileTitle(newname);
		
		newname+=namef.getFileName().substring(namef.getFileName().indexOf("."));
		
		namef.setFileName(newname);	
		 
		newname=namef.getRealPath().substring(0,namef.getRealPath().lastIndexOf("/")+1)+newname;
		
		namef.setRealPath(newname);

		
		File newfile=new File(savePath+"/"+newname); 
		
		oldfile.renameTo(newfile);
		
		session.save(namef);
		transaction.commit();
		session.close();
		
		return namef.getRealPath();
	}
	// 这个main用于方法测试
	public static void main(String args[]) {

	}

}
