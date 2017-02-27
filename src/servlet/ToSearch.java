package servlet;

import hibernate.Fileinfo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import file.OperationFileInfo;


public class ToSearch extends HttpServlet {
	/**
	 * 搜索操作
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8;");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		String clientName = new String(request.getParameter("content")
				.getBytes("ISO-8859-1"), "utf-8");
		/* 打开文档搜索
		
		String filepath = this.getServletConfig().getServletContext().getRealPath("/")+ "userFile\\";

		SearchContent searchsontent = new SearchContent(filepath, clientName,
				"doc", out, filepath.length());
		searchsontent.startSearchContent();
		*/
		
		
		//**********数据库中搜索***********
		
		// 获得记录的总数
		String content = "";
		int count = OperationFileInfo.GetTableInfoCount();
		// 获得第一条记录的id
		int start = OperationFileInfo.GetFirstInfoId();
		System.out.println(count + "  " + start);
		for (int i = start; i < start + count; i++) {
			Fileinfo f = OperationFileInfo.ReadFileinfo(i);
			//System.out.println(f.getFileDelete().toString().equals("0"));
			if (f.getFileDelete().toString().equals("0")) {
				if (f.getTextContent().contains(clientName)) {
					content += "<li><a class='file' title='双击打开' data-time="
							+ f.getTimePath() + ">"
							+ f.getFileTitle() + "</a>";
					content += "<a class='downfile' href="
							+ "servlet/DownloadFile?filename="
							+ f.getRealPath() + "></a>" + "</li>";
				}
			}
		}
		out.write(content);
		
		
	}
	/**
	 * 删除操作
	 */
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8;");
		String ids[] = request.getParameter("arr").split(",");
		OperationFileInfo.DeleteFiles(ids);
		
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
