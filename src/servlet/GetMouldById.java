package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import keepin.KeepInContent;
import findmould.OperateMoulds;

public class GetMouldById extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8;");
		request.setCharacterEncoding("utf-8");

		int id = Integer.parseInt(request.getParameter("id"));
		KeepInContent.deleteFile(id);
		PrintWriter out = response.getWriter();
		
		//**********数据库中搜索***********
		
		out.write("OK");
	}
	/**
	 * id查找内容
	 * type
	 * 1	mould
	 * 2	keepin
	 */
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8;");
		int type = Integer.parseInt(request.getParameter("type"));
		int id = Integer.parseInt(request.getParameter("id"));
		
		PrintWriter out = response.getWriter();
		if(type ==1){
			out.write(OperateMoulds.getMouldById(id));
		}
		else{
			out.write(KeepInContent.getInById(id));
		}
		
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