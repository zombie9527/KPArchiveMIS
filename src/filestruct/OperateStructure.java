package filestruct;

import hibernate.Structure;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class OperateStructure {
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
		public static void savaContent(String content){
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Structure structure = (Structure)session.get(Structure.class, 1);
			
			if(structure==null){
				structure = new Structure();
				structure.setStrucId(1);
			}
			structure.setContent(content);
			
			session.save(structure);
			// 提交事务
			transaction.commit();
			session.close();
		}
		public static String getContent(){
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Structure structure = (Structure)session.get(Structure.class, 1);
			
			transaction.commit();
			session.close();
			if(structure==null){
				return "";
			}
			return structure.getContent();
		}
		public static void main(String args[]) {
			//savaContent("uuuuuuuuuuuu");
			System.out.println(getContent());
		}
}
