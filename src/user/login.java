package user;

import hibernate.User;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBoperate;

public class login extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public login() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// Integer userId=Integer.parseInt(request.getParameter("userId"));
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");

		// DBoperate db=new DBoperate();

		// db.initialize();
		// Object obj=db.queryUser(userId);
		// db.endClose();
		User obj = DBoperate.queryUser(userId);
		if (obj != null) {
			User Item = (User) obj;
			if (password.equals(Item.getPasswd())) {
				// 如果不存在 session 会话，则创建一个 session 对象
				HttpSession session = request.getSession(true);
				/*
				 * 以下session 主要用来记录登录帐号信息
				 * grade权限值分别为('user','admin','superAdmin');
				 */
				session.setAttribute("grade", Item.getGrade()); // 判断是否为管理员
				session.setAttribute("userId", Item.getUserId());
				session.setAttribute("userName", Item.getUserName());
				out.print("passVerify"); // 通过验证
				// response.sendRedirect(request.getContextPath()+"/main.jsp");
			} else {
				out.print("密码错误！");
				// out.println("<script language=javascript>alert('密码错误，请重新输入！'); window.location='../login.jsp';</script>");
			}
		} else {
			out.print("帐号不存在！");
			// out.println("<script language=javascript>alert('账号不存在，请重新输入！'); window.location='../login.jsp';</script>");
		}
		out.close();

	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
