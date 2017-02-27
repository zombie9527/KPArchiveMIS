package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		javax.servlet.ServletOutputStream out = response.getOutputStream();
		@SuppressWarnings("deprecation")
		// 构建返回给用户的文件所在的路径
		String filepath = request.getRealPath("/") + "userFile/";
		String filename = new String(request.getParameter("filename"));
		String realfilename = filename.substring(24);// 显示给用户的文件名

		System.out.println(realfilename);
		System.out.println("DownloadFile filepath:" + filepath);
		System.out.println("DownloadFile filename:" + filename);

		java.io.File file = new java.io.File(filepath + filename);
		if (!file.exists()) {
			System.out.println(file.getAbsolutePath() + "文件不存在");
			return;
		}
		// 读取文件流
		java.io.FileInputStream fileInputStream = new java.io.FileInputStream(
				file);
		// 下载文件
		// 设置相应头和下载保存的文件名
		if (filename != null && filename.length() > 0) {
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(realfilename.getBytes("utf-8"), "iso8859-1")
					+ "");
			if (fileInputStream != null) {
				int filelen = fileInputStream.available();
				// 文件太大时内存不能一次读出要循环
				byte a[] = new byte[filelen];
				fileInputStream.read(a);
				out.write(a);
			}
			fileInputStream.close();
			out.close();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}