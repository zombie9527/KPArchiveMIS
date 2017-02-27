package servlet;

import office2swf.*;

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

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import readWord.ReadWordText;

import file.OperationFileInfo;

public class Uploadify extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8;");
		doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置响应的编码格式
		response.setContentType("text/html;charset=utf-8;");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		// 取得当前请求的时间戳，用于设置文件路径等信息
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdfFolderName = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMddHHmmss");
		String newfolderName = sdfFolderName.format(date);// 日期文件夹名称
		String newfileName = sdfFileName.format(date);// 时间戳文件名称
		String fileRealPath = "";// 时间戳文件存放的真实地址
		String fileRealPath1 = "";// 原文件名存放的真实地址
		String firstFileName = "";

		// 构建容器中上传的文件所在的物理路径，是一个文件夹路径，保存着所有和此文件相关的文件
		String savePath = this.getServletConfig().getServletContext()
				.getRealPath("/")
				+ "userFile\\" + newfolderName + "\\" + newfileName + "\\";
		// 创建路径
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
						firstFileName = item.getName().substring(
								item.getName().lastIndexOf("\\") + 1);
						String formatName = firstFileName
								.substring(firstFileName.lastIndexOf("."));// 后缀
						fileRealPath = savePath + newfileName + formatName;// 时间戳名的真实存储位置
						fileRealPath1 = savePath + fileName;// 文件真实名的存储路径

						// 输出查看
						System.out.println("------------\n文件名:" + fileName
								+ "\n文件夹名:" + newfolderName + "\n时间戳:"
								+ newfileName + "\n------------");

						// 将原文件以时间戳命名保存
						BufferedInputStream in = new BufferedInputStream(
								item.getInputStream());
						BufferedOutputStream outStream = new BufferedOutputStream(
								new FileOutputStream(new File(fileRealPath)));
						Streams.copy(in, outStream, true);

						// 将原文件以真实文件名保存
						in = new BufferedInputStream(item.getInputStream());
						outStream = new BufferedOutputStream(
								new FileOutputStream(new File(fileRealPath1)));
						Streams.copy(in, outStream, true);

						// 取得文件的标题
						String fileTitle = fileName.substring(0,
								fileName.lastIndexOf("."));
						// 取得word文档内容，非word文档直接置为空
						String textContent = null;
						System.out
								.println("文件后缀"
										+ fileName.substring(fileName
												.lastIndexOf(".") + 1));
						if (fileName.substring(fileName.lastIndexOf("."))
								.equals(".doc")) {
							textContent = ReadWordText
									.readWordDoc(fileRealPath1);
						} else if (fileName
								.substring(fileName.lastIndexOf(".")).equals(
										".docx")) {
							textContent = ReadWordText
									.readWordDocx(fileRealPath1);
						} else {
							textContent = "";
						}
						// 取得现在时间
						java.sql.Date createTime = new java.sql.Date(
								new java.util.Date().getTime());

						// 将新上传的文件的数据存入数据库
						// 参数为：原文件路径(用于file字段的存储)、带后缀的文件名、swf时间戳文件名、时间戳路径、原文件路径、文件标题、创建时间、doc/docx文本内容
						
						OperationFileInfo.insertFileInfo(fileRealPath1,
								fileName, newfileName + ".swf", newfolderName
										+ "/" + newfileName + "/" + fileName,
								newfolderName + "/" + newfileName + "/"
										+ newfileName, 0, fileTitle,
								createTime, textContent);
						
//						 System.out.println(fileRealPath1);
//						 System.out.println(fileName);
//						 System.out.println(newfileName+".swf");
//						 System.out.println(newfolderName+"/"+newfileName+"/"+fileName);
//						 System.out.println(newfolderName+"/"+newfileName+"/"+newfileName);
//						 System.out.println(fileTitle);
//						 System.out.println(createTime);
//						 System.out.println(textContent);

					}
				}
			}
		} catch (org.apache.commons.fileupload.FileUploadException ex) {
			ex.printStackTrace();
			System.out.println("没有文件上传");
			return;
		}
		response.getWriter().write("1");
		out.flush();
		out.close();

		/*
		 * 开始对原文件的类型进行转换，一下子上传完成后台转不过来不行啊， 转一个3M的docword就要等好几分钟 转换所需要的事件太长了
		 */
		Office2Swf d = new Office2Swf(fileRealPath);
		d.conver();
	}

	@Override
	public void destroy() {
		// 请求处理完毕自动OpenOffice进程
		StartOpenOffice.stop();
		System.out.println("destroy");
		super.destroy();
	}

	@Override
	public void init() throws ServletException {
		// 用户请求时自动启动OpenOffice
		if (StartOpenOffice.start() == 1) {
			System.out.println("OpenOffice服务启动成功");
		}
		System.out.println("init");
		super.init();
	}

}