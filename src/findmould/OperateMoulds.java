package findmould;

import java.util.Iterator;
import java.util.List;

import hibernate.Moulds;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class OperateMoulds {
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
	 * 插入模板
	 * @param title
	 * @param content
	 */
	public static void insertMould(String title,String content){
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
		Moulds moulds = new Moulds();
		moulds.setTitle(title);
		moulds.setContent(content);
		
		session.save(moulds);
		
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
		Moulds mo;
		String strSql = "";
		String title = "[",content = "[";
		
		strSql = "from Moulds order by id";
		Query q = session.createQuery(strSql);
		list = q.list();
		Iterator it=list.iterator();
		while(it.hasNext()){
			mo =  (Moulds)it.next();
			if(i!=0){
				title += ",";
				content += ",";
			}
			title += "\""+mo.getTitle()+"\"";
			content += mo.getMouldid();
			i++;
		}
		title += "]";
		content += "]";
		transaction.commit();
		session.close();
		
		return "{\"titles\":"+title+",\"contents\":"+content+"}";
	}
	public static String getMouldById(int id){

		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		Moulds f = (Moulds) session.get(Moulds.class, id);
		session.flush();
		transaction.commit();
		session.close();

		return f.getContent();
	}public static void main(String args[]) {
		OperateMoulds d = new OperateMoulds();
		d.insertMould("新建","ni ma ge bi");
		System.out.println(d.getContent());
	}
}
