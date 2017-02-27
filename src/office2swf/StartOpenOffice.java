package office2swf;

import java.io.IOException;

public class StartOpenOffice {
	// public static String OpenOfficePath =
	// "D:\\Program Files\\OpenOffice.org 3";
	static String path = System.getProperty("user.dir");
	public static String OpenOfficePath = path
			+ "\\OpenOffice\\App\\openoffice";// 便携版
	public static Process pro = null;

	/*
	 * OpenOffice的启动程序，使用命令启动 启动成功返回 1，失败返回 0
	 */
	public static int start() {
		// 如果从文件中读取的URL地址最后一个字符不是 '\'，则添加'\'
		if (OpenOfficePath.charAt(OpenOfficePath.length() - 1) != '\\') {
			OpenOfficePath += "\\";
		}
		// 启动OpenOffice的服务
		String command = OpenOfficePath
				+ "program\\soffice \"-accept=socket,host=localhost,port=8100;urp;StarOffice.ServiceManager\" -nologo -headless -nofirststartwizard";
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("OpenOffice启动成功");
		return 1;
	}

	public static int stop() {
		if (pro != null) {
			// 关闭openOffice服务进程
			pro.destroy();
			System.out.println("OpenOffice进程关闭成功");
			pro = null;
		} else {
			System.out.println("OpenOffice进程未启动");
		}
		return 0;
	}

	public static void main(String args[]) {
		StartOpenOffice.start();
	}
}
