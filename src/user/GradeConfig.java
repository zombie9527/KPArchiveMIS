package user;

import java.io.IOException;
//import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBoperate;

/**
 * Servlet implementation class GradeConfig
 */
public class GradeConfig extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GradeConfig() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		// PrintWriter out=response.getWriter();
		// HttpSession session = request.getSession(true);
		// session.getAttribute("userId");
		String grade = "";
		String[] id = new String[30];

		if (null != request.getParameterValues("adminGrade")) {
			id = request.getParameterValues("adminGrade");
			grade = "user";
		} else if (null != request.getParameterValues("userGrade")) {
			id = request.getParameterValues("userGrade");
			grade = "admin";
		} else {
			response.sendRedirect(request.getContextPath() + "/configAdmin.jsp");
			return;
		}

		// DBoperate upGrade=new DBoperate();
		// upGrade.initialize();
		for (int i = 0; i < id.length; i++) {
			// Integer userId=Integer.parseInt(id[i]);
			DBoperate.updateGrade(id[i], grade);
		}
		// upGrade.endClose();
		response.sendRedirect(request.getContextPath() + "/configAdmin.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
