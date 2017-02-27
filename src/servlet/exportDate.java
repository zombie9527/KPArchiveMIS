package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.*;

public class exportDate extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public exportDate() {
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

		response.setContentType("text/html;charset=UTF-8");
		javax.servlet.ServletOutputStream out = response.getOutputStream();
		// 构建返回给用户的文件所在的路径
		String savePath = this.getServletConfig().getServletContext()
				.getRealPath("/")
				+ "backup\\backup.db";
		Date d = new Date();
		String realfilename = "备份"
				+ new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒").format(d
						.getTime()) + ".db";// 显示给用户的文件名

		System.out.println(realfilename);

		if (BackupDB.backupDatebase("root", "", "archives", savePath)) {
			System.out.println("备份成功");
		}

		// 设置返回头让用户下载文件
		java.io.File file = new java.io.File(savePath);
		if (!file.exists()) {
			System.out.println(file.getAbsolutePath() + "文件不存在");
			return;
		}
		// 读取文件流
		java.io.FileInputStream fileInputStream = new java.io.FileInputStream(
				file);
		// 下载文件
		// 设置相应头和下载保存的文件名
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(realfilename.getBytes("utf-8"), "iso8859-1") + "");
		if (fileInputStream != null) {
			int filelen = fileInputStream.available();
			// 文件太大时内存不能一次读出要循环
			byte a[] = new byte[filelen];
			fileInputStream.read(a);
			out.write(a);
		}
		fileInputStream.close();
		out.close();
		System.out.println("完成");
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

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("");
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
