package keepin;

import java.util.Iterator;
import java.util.List;

import hibernate.Keepin;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class KeepInContent {
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
	/**
	 * 插入暂存
	 * @param title
	 * @param content
	 */
	public static void insertIn(String title,String content){
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
		Keepin kin = new Keepin();
		kin.setName(title);
		kin.setContent(content);
		
		session.save(kin);
		
		transaction.commit();
		session.close();
	}
	/**
	 * 获取模板
	 * @return
	 */
	public static String getContent(){
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		int i = 0;
		List list = null;
		Keepin mo;
		String strSql = "";
		String title = "[",content = "[";
		
		strSql = "from Keepin order by id";
		Query q = session.createQuery(strSql);
		list = q.list();
		Iterator it=list.iterator();
		while(it.hasNext()){
			mo =  (Keepin)it.next();
			if(i!=0){
				title += ",";
				content += ",";
			}
			title += "\""+mo.getName()+"\"";
			content += mo.getKeepid();
			i++;
		}
		title += "]";
		content += "]";
		transaction.commit();
		session.close();
		
		return "{\"titles\":"+title+",\"contents\":"+content+"}";
	}
	public static String getInById(int id){
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		Keepin f = (Keepin) session.get(Keepin.class, id);
		session.flush();
		transaction.commit();
		session.close();

		return f.getContent();
	}
	public static void deleteFile(int id){
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		Keepin f = (Keepin) session.get(Keepin.class, id);
		session.delete(f);
		session.flush();
		transaction.commit();
		session.close();
		
	}
//	public static void main(String args[]) {
//		KeepInContent d = new KeepInContent();
//		d.insertIn("新建","ni ma ge bi");
//		System.out.println(d.getContent());
//	}
}
