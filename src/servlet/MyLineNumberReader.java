package servlet;

import java.io.*;

import org.apache.poi.hwpf.extractor.WordExtractor;
//import org.textmining.text.extraction.WordExtractor;

public class MyLineNumberReader {
	private Reader fr;
	private int lineNumber = 0; // 记录当前的行数

	MyLineNumberReader(Reader fr) {
		this.fr = fr;
	}

	public String readLine() {
		int num = 0;
		StringBuffer sb = new StringBuffer();
		try {
			while ((num = fr.read()) != -1) {
				if (num == '\r')
					continue;
				else if (num == '\n') {
					lineNumber++; // 读取一行，行号加1
					return sb.toString(); // 返回读取的一行字符串
				} else {
					sb.append((char) num);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		// 防止文本末尾没加回车换行符，以丢失文本内容
		if (sb.length() > 0) {
			lineNumber++;
			return sb.toString();
		}
		return null;
	}

	// 关闭输入流对象
	public void close() {
		try {
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 设置当前的行数
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	// 获取当前的行数
	public int getLineNumber() {
		return lineNumber;
	}

}

/*
 * 搜索文本内容的类 输入的内容为要查找的目录和查找的内容
 */
class SearchContent {
	private File f = null; // 要查找的目录对象
	private String filename = null; // 要查找的目录路径
	// private BufferedWriter bw=null;
	private PrintWriter out = null;
	private String findtxt = null; // 要查找的文本内容
	private String fileType = null; // 要查找的文件类型
	WordExtractor extractor = null;
	int pathlong = 0;

	/**
	 * 构造函数，
	 * 
	 * @param f
	 *            要查找目录的对象
	 * @param findtxt
	 *            要查找的关键字
	 * @param fileType
	 *            要查找的文件类型
	 */
	SearchContent(String filename, String findtxt, String fileType,
			PrintWriter out, int pathlong) {
		this.filename = filename;
		this.findtxt = findtxt;
		this.fileType = fileType;
		this.out = out;
		this.pathlong = pathlong;
		
	}

	// 暴露的公共接口，开始在指定的目录中搜索关键字
	public void startSearchContent() {
		try {
			// bw=new BufferedWriter(
			// new FileWriter( "log.txt"));
			
			f = new File(filename);
			
			// 搜索关键字的内容
			listFile(f);
			
			// 关闭文件流
			// bw.close();

			//System.out.println("搜索完毕，搜索结果已保存在log.txt文件中");
		} catch (Exception e) {
			System.out.println("搜索出错！！！");
		}

	}

	/*
	 * 通过递归搜索目录，搜索过程分两种情况： 1.如果是目录，则通过递归继续查找目录下的文件
	 * 2.如果是文件，则先判断是否是fileType类型文件，如果是的话就搜索文件内容
	 */
	private void listFile(File f){
		File[] files = f.listFiles();
		
		for (int x = 0; x < files.length; x++) {
			if (files[x].isDirectory())
				listFile(files[x]);
			else {
				// 判断文件名是否以fileType结尾
				if (files[x].getName().endsWith(fileType)
						|| files[x].getName().endsWith("docx")) {
					FindTxt(files[x]);
					
				}
			}
		}
	}

	/*
	 * 从文件中搜索制定的内容，分下面几步 1.使用自定义的山寨版LineNumberReader类，读取文件的每一行 2.
	 */
	private void FindTxt(File f){
		FileInputStream br = null;
		String content = "";
		
		try {
			br = new FileInputStream(f);
			
			extractor = new WordExtractor(br);
			
			String s = null;
			
			
			s = extractor.getText();
			
			// 文本行中是否包含制定的字符串
			if (s.contains(findtxt)) {
				String absolutepath = f.getAbsolutePath();

				int endpath = absolutepath.lastIndexOf('\\');
				int point = absolutepath.lastIndexOf('.');
				content += "<li><a herf='#' class='file' data-time="
						+ absolutepath.substring(pathlong, point) + ">"
						+ absolutepath.substring(endpath + 1, point) + "</a>";
				content += "<a class='downfile' href="
						+ "servlet/DownloadFile?filename="
						+ absolutepath.substring(pathlong) + "></a>" + "</li>";
				out.write(content);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
