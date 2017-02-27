package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import file.OperationFileInfo;

public class GetFileInfo extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetFileInfo() {
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

		response.setContentType("text/html;charset=UTF-8");// 这个一定要在定义out之前设置
		PrintWriter out = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");
		// 构建响应json字符串
		String str = "[";
		// 以数组形式构建
		// 获得记录的总数
		int count = OperationFileInfo.GetTableInfoCount();
		// 获得第一条记录的id
		int start = OperationFileInfo.GetFirstInfoId();
		System.out.println(count + "  " + start);
		for (int i = start; i < start + count; i++) {
			String[] f = OperationFileInfo.getFileInfo(i);
			if (Integer.parseInt(f[3]) == 0) {
				str += "{'fileTitle':'" + f[0] + "', 'timePath':'" + f[1]
						+ "', 'realPath':'" + f[2] + "', 'id':'" + i +"'}";
				if (i < start + count - 1) {
					str += ", ";
				}
			}
		}
		str += "]";
//		System.out.println(str);
		out.print(str);
		out.flush();
		out.close();
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
	/**
	 * 重命名文件
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");// 这个一定要在定义out之前设置
		PrintWriter out = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");
		request.setCharacterEncoding("utf-8");
		String filepath = this.getServletConfig().getServletContext().getRealPath("/")+ "userFile";
		String newname = request.getParameter("name");
		int nameid = Integer.parseInt( request.getParameter("id"));
		String path = OperationFileInfo.FileRename(newname,nameid,filepath);
		out.print("servlet/DownloadFile?filename="+path);
		
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
