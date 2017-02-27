

/*
 * 由于不能转换为word文件，这个已经废了
 */







package office2swf;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

public class pdf2doc {
	private static final int environment = 1;// 环境1：windows,2:linux
	private String fileString;// 要转换的文件的真实路径
	private String outputPath = "";// 输入路径，如果不设置就输出在默认位置
	private String fileName;// 文件的名称包括后缀
	private File pdfFile = null;
	private File docFile = null;
	String path = System.getProperty("user.dir");
	String pdfswf = path + "\\SWFTools\\pdf2swf.exe ";// pdf2swf.exe文件的路径，注意后面有一个空格，用于启动服务
	String pngswf = path + "\\SWFTools\\png2swf.exe ";// pdf2swf.exe文件的路径，注意后面有一个空格，用于启动服务
	String jpegswf = path + "\\SWFTools\\jpeg2swf.exe ";// pdf2swf.exe文件的路径，注意后面有一个空格，用于启动服务

	String fileType = "";// 记录要转换的文件的类型

	public pdf2doc(String fileString) {
		ini(fileString);
	}

	/*
	 * 初始化 @param fileString
	 */
	private void ini(String fileString) {
		this.fileString = fileString;
		fileName = fileString.substring(0, fileString.lastIndexOf("."));
		fileType = fileString.substring(fileString.lastIndexOf(".") + 1)
				.toLowerCase();// 获取到文件的类型
		System.out.println("文件类型：" + fileType);
		pdfFile = new File(fileString);
		docFile = new File(fileName + ".doc");
		System.out.println("\n pdf文件地址:" + pdfFile + "\n doc文件地址:" + docFile);
	}

	/*
	 * PDF转为DOC @param file
	 */
	private void pdf2doc() throws Exception {
		if (pdfFile.exists()) {
			if (!docFile.exists()) {
				OpenOfficeConnection connection = new SocketOpenOfficeConnection(
						8100);
				try {
					System.out.println("开始进行doc2pdf转换");
					connection.connect();
					DocumentConverter converter = new OpenOfficeDocumentConverter(
							connection);
					converter.convert(pdfFile, docFile);
					connection.disconnect();
					System.out.println("****doc转换成功，doc输出：" + docFile.getPath()
							+ "****");
				} catch (java.net.ConnectException e) {
					// ToDo Auto-generated catch block
					e.printStackTrace();
					System.out.println("****pdf2doc转换异常，openoffice服务未启动！****");
					throw e;
				} catch (com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e) {
					e.printStackTrace();
					System.out.println("****pdf2doc转换异常，读取转换文件失败****");
					throw e;
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
			} else {
				System.out.println("****已经转换为doc，不需要再进行转化****");
			}
		} else {
			System.out.println("****openoffice转换器异常，需要转换的文档不存在，无法转换****");
		}
	}

	/*
	 * 转换主方法
	 */
	public boolean conver() {

		try {
			pdf2doc();
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		return true;
	}

}