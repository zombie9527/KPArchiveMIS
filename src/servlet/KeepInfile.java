package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import keepin.KeepInContent;

public class KeepInfile extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8;");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		//**********数据库中搜索***********
		
		out.write(KeepInContent.getContent());
		
		
	}
	/**
	 * 存入
	 */
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		String fileName = request.getParameter("title");
		String html = request.getParameter("content");

		KeepInContent.insertIn(fileName, html);
		out.write("1");
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}
}