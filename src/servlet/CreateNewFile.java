package servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import office2swf.*;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import readWord.ReadWordText;
import file.OperationFileInfo;
import filestruct.OperateStructure;
import office2swf.Office2Swf;
import findmould.OperateMoulds;
import toPDF.HTMLtoPDF;
import office2swf.pdf2doc;

public class CreateNewFile extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8;");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		//**********数据库中搜索***********
		
		out.write(OperateMoulds.getContent());
		
		
	}
	/**
	 * 生成
	 */
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8;");
		
		PrintWriter out = response.getWriter();
		
		// 取得当前请求的时间戳，用于设置文件路径等信息
		String fileName = request.getParameter("title");
		String html = request.getParameter("content");
		
		System.out.println(fileName);
		
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdfFolderName = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMddHHmmss");
		String newfolderName = sdfFolderName.format(date);// 日期文件夹名称
		String newfileName = sdfFileName.format(date);// 时间戳文件名称
		String fileRealPath = "";// 时间戳文件存放的真实地址
		String fileRealPath1 = "";// 原文件名存放的真实地址
		
//		构建上传的文件所在的物理路径，是一个文件夹路径，保存着所有和此文件相关的文件
		String savePath = this.getServletConfig().getServletContext()
				.getRealPath("/")
				+ "userFile\\" + newfolderName + "\\" + newfileName + "\\";
		// 创建路径
		File file = new File(savePath);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
		
		fileRealPath = savePath + newfileName + ".pdf";// 时间戳名的真实存储位置
//		String docfileRealPath = savePath + newfileName + ".doc";// 时间戳名的真实存储位置
		fileRealPath1 = savePath + fileName + ".pdf";// 文件真实名的存储路径
//		String docfileRealPath1 = savePath + fileName + ".doc";// 文件真实名的存储路径
		// 输出查看
		System.out.println("\n时间戳文件存储位置:" + fileRealPath + "\n真实存储位置:"
						+ fileRealPath1 + "\n------------");
		
//		将html转换为pdf文件保存到指定路径
		try {
			HTMLtoPDF jt = new HTMLtoPDF();
			jt.doConversion2(html, savePath+fileName+".pdf");
			
	//		将pdf文件转换为word文件
//			*****如果有转word的好方法就把这里的注释去掉
//			pdf2doc pd = new pdf2doc(fileRealPath1);
//			pd.conver();
			
			// 将生成的pdf文件以时间戳命名保存
			FileInputStream in = new FileInputStream(new File(fileRealPath1));
			FileOutputStream outStream = new FileOutputStream(new File(fileRealPath));
			Streams.copy(in, outStream, true);
			// 将生成的word文件以时间戳命名保存
//			*****如果有转word的好方法就把这里的注释去掉
//			FileInputStream in = new FileInputStream(new File(docfileRealPath1));
//			FileOutputStream outStream = new FileOutputStream(new File(docfileRealPath));
//			Streams.copy(in, outStream, true);
			
			// 取得文件的标题
			String fileTitle = fileName;
			// 取得word文档内容，非word文档直接置为空
			String textContent = "";
//			*****如果有转word的好方法就把这里的注释去掉
//			textContent = ReadWordText.readWordDoc(docfileRealPath1);
			
			// 取得现在时间
			java.sql.Date createTime = new java.sql.Date(
					new java.util.Date().getTime());
			
			// 将新上传的文件的数据存入数据库
			// 参数为：原文件路径(用于file字段的存储)、带后缀的文件名、swf时间戳文件名、时间戳路径、doc原文件路径、文件标题、创建时间、doc/docx文本内容
			
			OperationFileInfo.insertFileInfo(
					fileRealPath1,
					fileName+".pdf", 
					newfileName + ".swf", 
					newfolderName + "/" + newfileName + "/" + fileName+".pdf",
					newfolderName + "/" + newfileName + "/" + newfileName,
					0,
					fileTitle,
					createTime, 
					textContent);
		} catch (Exception e) {
			out.write("2");
			e.printStackTrace();
		}
		out.flush();
		out.close();

	//	将pdf文件转换为swf文件
		Office2Swf d = new Office2Swf(fileRealPath);
		if(d.conver()){
			//获取到记录的条数
			int count = OperationFileInfo.GetTableInfoCount();
			String ulcontent = OperateStructure.getContent();
			ulcontent += "<li class='filepdf tree-empty'><span class='toggler'></span><a class='file' title='双击打开' data-id='"+count+"' data-time='"+newfolderName + "/" + newfileName + "/" + newfileName+"'>"+fileName+"</a><a title='点击下载' href='servlet/DownloadFile?filename="+newfolderName + "/" + newfileName + "/" + fileName+".doc' class='downfile'></a></li>";
			OperateStructure.savaContent(ulcontent);
			out.write("1");
		}else{
			out.write("2");
		}
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