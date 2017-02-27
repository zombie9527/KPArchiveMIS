package user;

import hibernate.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.DBoperate;

/**
 * Servlet implementation class register
 */
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
		super();
		// TODO Auto-generated constructor stub
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

		// Date date = new java.util.Date();// 获取当前时间
		// 生成AbstractUser对象
		User user = new User(userId, password, "user", "user",
				new java.sql.Date(new java.util.Date().getTime()));
		System.out.println(user.toString());
		// DBoperate reg=new DBoperate();
		// reg.initialize();
		// db.saveUser(user);
		User uR = DBoperate.queryUser(userId);

		if (uR == null) {
			DBoperate.saveUser(user);
			out.print("auditPass");
			// out.println("<script language=javascript>alert('注册成功！');window.location='../login.jsp';</script>");
		} else {
			out.print("该帐号已被注册!");
			// out.println("<script language=javascript>alert('该账户已注册，请更改账号后重新注册！'); window.location='../register.jsp';</script>");
		}
		// reg.endClose();
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
