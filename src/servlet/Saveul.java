package servlet;


import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import filestruct.OperateStructure;

public class Saveul extends HttpServlet {
	private String ulcontent = "";
	private String filepath = "";
	private String filename = "";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		out.print(OperateStructure.getContent());
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException , IOException {

		response.setContentType("text/html;charset=utf-8;");
		request.setCharacterEncoding("utf-8");

		ulcontent = request.getParameter("content");
		OperateStructure.savaContent(ulcontent);
		

	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void init() throws ServletException {
		filename = "filecontent.txt";
		filepath = this.getServletConfig().getServletContext().getRealPath("/");
		super.init();
	}
}
