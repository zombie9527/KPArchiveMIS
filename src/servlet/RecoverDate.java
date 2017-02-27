package servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import office2swf.Office2Swf;
import office2swf.StartOpenOffice;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import readWord.ReadWordText;
import file.OperationFileInfo;
import db.*;

public class RecoverDate extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8;");
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置响应的编码格式
		response.setContentType("text/html;charset=utf-8;");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		String fileRealPath = "";// 原文件名存放的真实地址

		// 构建容器中上传的数据文件所在的物理路径，
		String savePath = this.getServletConfig().getServletContext()
				.getRealPath("/")
				+ "backup\\";
		// 构建数据还原时数据库中的原始文档所在的userFile路径
		String userFilePath = this.getServletConfig().getServletContext()
				.getRealPath("/")
				+ "userFile\\";
		// 没有就创建路径
		File file = new File(savePath);
		if (!file.isDirectory()) {
			file.mkdirs();
		}

		try {
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			upload.setHeaderEncoding("UTF-8");
			// 获取多个上传文件
			List fileList = fileList = upload.parseRequest(request);
			// 遍历上传文件写入磁盘
			Iterator it = fileList.iterator();
			while (it.hasNext()) {
				Object obit = it.next();
				if (obit instanceof DiskFileItem) {
					DiskFileItem item = (DiskFileItem) obit;
					// 如果item是文件上传表单域
					// 获得文件名及路径
					String fileName = item.getName();
					if (fileName != null) {
						fileRealPath = savePath + fileName;// 文件真实名的存储路径
						System.out.println("导入数据的位置" + fileRealPath);
						// 将原文件以真实文件名保存
						BufferedInputStream in = new BufferedInputStream(
								item.getInputStream());
						BufferedOutputStream outStream = new BufferedOutputStream(
								new FileOutputStream(new File(fileRealPath)));
						Streams.copy(in, outStream, true);

					}
				}
			}
		} catch (org.apache.commons.fileupload.FileUploadException ex) {
			ex.printStackTrace();
			System.out.println("没有文件上传");
			return;
		}
		// 恢复数据
		System.out.println("开始恢复数据库");
		if (RecoverDB.recoverDatebase("root", "", "archives", fileRealPath,
				userFilePath)) {
			System.out.println("恢复数据库完成");
			response.getWriter().write("1");
			out.flush();
			out.close();
		}
	}
}
