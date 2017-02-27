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

public class Office2Swf {
	private static final int environment = 1;// 环境1：windows,2:linux
	private String fileString;// 要转换的文件的真实路径
	private String outputPath = "";// 输入路径，如果不设置就输出在默认位置
	private String fileName;// 文件的名称包括后缀
	private File pdfFile = null;
	private File swfFile = null;
	private File docFile = null;
	private File pngFile = null;
	private File jpegFile = null;
	// String pdfswf =
	// "D:/Program Files/SWFTools/pdf2swf.exe ";//pdf2swf.exe文件的路径，注意后面有一个空格，用于启动服务
	// String pngswf =
	// "D:/Program Files/SWFTools/png2swf.exe ";//pdf2swf.exe文件的路径，注意后面有一个空格，用于启动服务
	// String jpegswf =
	// "D:/Program Files/SWFTools/jpeg2swf.exe ";//pdf2swf.exe文件的路径，注意后面有一个空格，用于启动服务
	String path = System.getProperty("user.dir");
	String pdfswf = path + "\\SWFTools\\pdf2swf.exe ";// pdf2swf.exe文件的路径，注意后面有一个空格，用于启动服务
	String pngswf = path + "\\SWFTools\\png2swf.exe ";// pdf2swf.exe文件的路径，注意后面有一个空格，用于启动服务
	String jpegswf = path + "\\SWFTools\\jpeg2swf.exe ";// pdf2swf.exe文件的路径，注意后面有一个空格，用于启动服务

	String fileType = "";// 记录要转换的文件的类型

	public Office2Swf(String fileString) {
		ini(fileString);
	}

	/*
	 * 重新设置 file @param fileString
	 */
	public void setFile(String fileString) {
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
		// 根据文件的类型进行操作
		if (fileType.equals("png")) {
			pngFile = new File(fileName + ".png");
		} else if (fileType.equals("jpg")) {
			jpegFile = new File(fileName + ".jpg");
		} else if (fileType.equals("jpeg")) {
			jpegFile = new File(fileName + ".jpeg");
		} else {
			docFile = new File(fileString);
			pdfFile = new File(fileName + ".pdf");
		}
		swfFile = new File(fileName + ".swf");
	}

	/*
	 * 转为PDF @param file
	 */
	private void doc2pdf() throws Exception {
		if (docFile.exists()) {
			if (!pdfFile.exists()) {
				OpenOfficeConnection connection = new SocketOpenOfficeConnection(
						8100);
				try {
					System.out.println("开始进行doc2pdf转换");
					connection.connect();
					DocumentConverter converter = new OpenOfficeDocumentConverter(
							connection);
					converter.convert(docFile, pdfFile);
					connection.disconnect();
					System.out.println("****pdf转换成功，PDF输出：" + pdfFile.getPath()
							+ "****");
				} catch (java.net.ConnectException e) {
					// ToDo Auto-generated catch block
					e.printStackTrace();
					System.out.println("****swf转换异常，openoffice服务未启动！****");
					throw e;
				} catch (com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e) {
					e.printStackTrace();
					System.out.println("****swf转换器异常，读取转换文件失败****");
					throw e;
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
			} else {
				System.out.println("****已经转换为pdf，不需要再进行转化****");
			}
		} else {
			System.out.println("****swf转换器异常，需要转换的文档不存在，无法转换****");
		}
	}
	
	/*
	 * PDF转为DOC @param file
	 */
	public void pdf2doc() throws Exception {
		if (docFile.exists()) {
			if (!pdfFile.exists()) {
				OpenOfficeConnection connection = new SocketOpenOfficeConnection(
						8100);
				try {
					System.out.println("开始进行doc2pdf转换");
					connection.connect();
					DocumentConverter converter = new OpenOfficeDocumentConverter(
							connection);
					converter.convert(docFile, pdfFile);
					connection.disconnect();
					System.out.println("****pdf转换成功，PDF输出：" + pdfFile.getPath()
							+ "****");
				} catch (java.net.ConnectException e) {
					// ToDo Auto-generated catch block
					e.printStackTrace();
					System.out.println("****swf转换异常，openoffice服务未启动！****");
					throw e;
				} catch (com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e) {
					e.printStackTrace();
					System.out.println("****swf转换器异常，读取转换文件失败****");
					throw e;
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
			} else {
				System.out.println("****已经转换为pdf，不需要再进行转化****");
			}
		} else {
			System.out.println("****swf转换器异常，需要转换的文档不存在，无法转换****");
		}
	}

	/*
	 * 转换成swf
	 */
	private void pdf2swf() throws Exception {
		Runtime r = Runtime.getRuntime();
		if (!swfFile.exists()) {
			if (pdfFile.exists()) {
				if (environment == 1)// windows环境处理
				{
					try {
						Process p = r.exec(pdfswf + pdfFile.getPath() + " -o "
								+ swfFile.getPath()
								+ " -f -T 9 -t -s storeallcharacters");
						System.out.print(loadStream(p.getInputStream()));
						System.err.print(loadStream(p.getErrorStream()));
						System.out.print(loadStream(p.getInputStream()));
						System.err.println("***swf转换成功，文件输出："
								+ swfFile.getPath() + "****");
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
				} else if (environment == 2)// linux环境处理
				{
					try {
						Process p = r.exec(pdfswf + pdfFile.getPath() + " -o "
								+ swfFile.getPath()
								+ " -f -T 9 -t -s storeallcharacters");
						System.out.print(loadStream(p.getInputStream()));
						System.err.print(loadStream(p.getErrorStream()));
						System.err.println("****swf转换成功，文件输出："
								+ swfFile.getPath() + "****");
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
				}
			} else {
				System.out.println("****pdf不存在，无法转换****");
			}
		} else {
			System.out.println("****swf已存在不需要转换****");
		}
	}

	/*
	 * png转swf
	 */
	private void png2swf() throws Exception {
		Runtime r = Runtime.getRuntime();
		if (!swfFile.exists()) {
			if (pngFile.exists()) {
				if (environment == 1) {
					try {
						Process p = r.exec(pngswf + pngFile.getPath() + " -o "
								+ swfFile.getPath() + " -T 9");
						System.out.print(loadStream(p.getInputStream()));
						System.err.print(loadStream(p.getErrorStream()));
						System.out.print(loadStream(p.getInputStream()));
						System.err.println("****swf转换成功" + swfFile.getPath()
								+ "****");
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
				} else if (environment == 2)// linux环境
				{
					try {
						Process p = r.exec("png2swf " + pngFile.getPath()
								+ " -o " + swfFile.getPath() + " -T 9");
						System.out.print(loadStream(p.getInputStream()));
						System.err.print(loadStream(p.getErrorStream()));
						System.err.println("****swf转换成功" + swfFile.getPath()
								+ "****");
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
				}
			} else {
				System.out.println("****png不存在，无法转换****");
			}
		} else {
			System.out.println("****swf已存在不需要转换****");
		}
	}

	/*
	 * jpg/jpeg转swf
	 */
	private void jpeg2swf() throws Exception {
		Runtime r = Runtime.getRuntime();
		if (!swfFile.exists()) {
			if (jpegFile.exists()) {
				if (environment == 1) {
					try {

						Process p = r.exec(jpegswf + jpegFile.getPath()
								+ " -o " + swfFile.getPath() + " -T 9");
						System.out.print(loadStream(p.getInputStream()));
						System.err.print(loadStream(p.getErrorStream()));
						System.out.print(loadStream(p.getInputStream()));
						System.err.println("****swf转换成功" + swfFile.getPath()
								+ "****");
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
				} else if (environment == 2) {
					try {
						Process p = r.exec("jpeg2swf " + jpegFile.getPath()
								+ " -o " + swfFile.getPath() + " -T 9");
						System.out.print(loadStream(p.getInputStream()));
						System.err.print(loadStream(p.getErrorStream()));
						System.err.println("****swf转换成功" + swfFile.getPath()
								+ "****");
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
				}
			} else {
				System.out.println("****jpeg文件不存在****");
			}
		} else {
			System.out.println("****swf已存在不需要转换****");
		}
	}

	static String loadStream(InputStream in) throws IOException {
		int ptr = 0;
		// 把InputStream字节流 替换为BufferedReader字符流
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder buffer = new StringBuilder();
		while ((ptr = reader.read()) != -1) {
			buffer.append((char) ptr);
		}
		return buffer.toString();
	}

	/*
	 * 转换主方法
	 */
	public boolean conver() {
		if (swfFile.exists()) {
			System.out.println("****swf转换器开始工作，该文件已经转换为swf****");
			return true;
		}

		if (environment == 1) {
			System.out.println("****swf转换器开始工作，当前设置运行环境windows****");
		} else {
			System.out.println("****swf转换器开始工作，当前设置运行环境linux****");
		}

		try {

			if (fileType.equals("png")) {
				System.out.println("png2swf开始转换");
				png2swf();
			} else if (fileType.equals("jpeg") || fileType.equals("jpg")) {
				System.out.println("jpeg2swf开始转换");
				jpeg2swf();
			} else {
				System.out.println("doc2pdf开始转换");
				doc2pdf();
				System.out.println("pdf2swf开始转换");
				pdf2swf();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		if (fileType.equals("png")) {
			if (pngFile.exists()) {
				return true;
			} else {
				return false;
			}
		} else if (fileType.equals("jpg") || fileType.equals("jpeg")) {
			if (jpegFile.exists()) {
				return true;
			} else {
				return false;
			}
		} else {
			if (swfFile.exists()) {
				return true;
			} else {
				return false;
			}
		}

	}

	/*
	 * 返回文件路径@param s
	 */
	public String getswfPath() {
		if (swfFile.exists()) {
			String tempString = swfFile.getPath();
			tempString = tempString.replaceAll("\\\\", "/");
			return tempString;
		} else {
			return "";
		}
	}

	/*
	 * 设置输出路径
	 */
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
		if (!outputPath.equals("")) {
			String realName = fileName.substring(fileName.lastIndexOf("/"),
					fileName.lastIndexOf("."));
			if (outputPath.charAt(outputPath.length()) == '/') {
				swfFile = new File(outputPath + realName + ".swf");
			} else {
				swfFile = new File(outputPath + realName + ".swf");
			}
		}
	}

}