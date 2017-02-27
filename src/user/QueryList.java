package user;

import hibernate.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBoperate;

/**
 * Servlet implementation class register
 */
public class QueryList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QueryList() {
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

		// DBoperate query=new DBoperate();
		// query.initialize();
		if (null != request.getParameter("grade")) {
			String grade = request.getParameter("grade");
			List list = DBoperate.queryList(grade);

			if (null != list && list.size() > 0) {
				String json = "{\"list\":[";
				for (int i = 0; i < list.size(); i++) {
					json = json + ((User) list.get(i)).toJSON();
					if (i != list.size() - 1) {
						json = json + ",";
					}
				}
				json = json + "]}";
				// System.out.println(json);
				out.print(json);
			} else {
				out.print("null");
			}
		} else if (null != request.getParameter("userId")) {
			// Integer userId=Integer.parseInt(request.getParameter("userId"));
			String userId = request.getParameter("userId");
			User userPd = (User) DBoperate.queryUser(userId);
			if (null != userPd) {
				String json = "{\"list\":[";
				json = json + userPd.toJSON();
				json = json + "]}";
				System.out.println(json);
				out.print(json);
			} else {
				out.print("null");
			}
		}

		// query.endClose();

		// out.println("<script language=javascript>alert('修改成功！'); window.location='../main.jsp';</script>");

		out.flush();
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
