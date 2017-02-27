package util;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
//import org.hibernate.mapping.List;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import hibernate.User;

public class DBoperate {

	// 会话需要使用的变量
	private static SessionFactory sessionFactory;
	private static Session session;
	private static Transaction transaction;
	private static Configuration config;
	private static ServiceRegistry serviceRegistry;

	/*
	 * 初始化
	 */
	// 初始化一些变量
	static {
		config = new Configuration().configure();
		serviceRegistry = new ServiceRegistryBuilder().applySettings(
				config.getProperties()).buildServiceRegistry();
		sessionFactory = config.buildSessionFactory(serviceRegistry);
	}

	public static void saveUser(User user) {
		session = sessionFactory.openSession();
		// 开启事务
		transaction = session.beginTransaction();
		// 保存对象进入数据库
		session.save(user);
		// 提交事务
		transaction.commit();
		session.close();
	}

	public static User queryUser(String userId) {
		session = sessionFactory.openSession();
		// 开启事务
		transaction = session.beginTransaction();
		// c查询
		// User user=(User) session.load(User.class,userId);
		String queryStr = "from User user where user.userId='" + userId + "'";
		Query query = session.createQuery(queryStr);
		User user = (User) query.uniqueResult();
		// 提交事务
		transaction.commit();
		session.close();
		// System.out.println("1");
		return user;
	}

	public static List queryList(String grade) {
		session = sessionFactory.openSession();
		// 开启事务
		transaction = session.beginTransaction();
		// c查询
		// User user=(User) session.load(User.class,userId);
		String queryStr = "from User user where user.grade='" + grade + "'";
		Query query = session.createQuery(queryStr);
		// 提交事务
		List list = query.list();
		transaction.commit();
		session.close();
		return list;
	}

	public static void updateGrade(String userId, String grade) {
		session = sessionFactory.openSession();
		// 开启事务
		transaction = session.beginTransaction();
		// 更新数据
		String upStr = "update User user set user.grade='" + grade
				+ "'where user.userId='" + userId + "'";
		Query query = session.createQuery(upStr);
		query.executeUpdate();
		// 提交事务
		transaction.commit();
		session.close();
	}

	public static void updateUserName(String userId, String userName) {
		session = sessionFactory.openSession();
		// 开启事务
		transaction = session.beginTransaction();
		// 更新数据
		String upStr = "update User user set user.userName='" + userName
				+ "'where user.userId='" + userId + "'";
		Query query = session.createQuery(upStr);
		query.executeUpdate();
		// 提交事务
		transaction.commit();
		session.close();
	}

	public static void updatePasswd(String userId, String passwd) {
		session = sessionFactory.openSession();
		// 开启事务
		transaction = session.beginTransaction();
		// 更新数据
		String upStr = "update User user set user.passwd='" + passwd
				+ "'where user.userId='" + userId + "'";
		Query query = session.createQuery(upStr);
		query.executeUpdate();
		// 提交事务
		transaction.commit();
		session.close();

	}

}
