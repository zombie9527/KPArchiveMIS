package db;

import hibernate.Fileinfo;

import java.io.*;
import java.lang.*;
import java.sql.Blob;
import java.util.ArrayList;

import org.apache.commons.fileupload.util.Streams;

import office2swf.Office2Swf;
import office2swf.StartOpenOffice;

import file.*;

public class RecoverDB {
	public static boolean recoverDatebase(String username, String password,
			String datebaseName, String filePath, String userFilePath) {
		String stmt1;
		String stmt2;
		String path = System.getProperty("user.dir");
		if (password.equals("")) {
			stmt1 = path + "\\mysql\\bin\\mysqladmin -u" + username
					+ " create " + datebaseName;
			stmt2 = path + "\\mysql\\bin\\mysql -u" + username + " "
					+ datebaseName + " < " + filePath;
		} else {
			stmt1 = path + "\\mysql\\bin\\mysqladmin -u" + username + " -p"
					+ password + " create " + datebaseName;
			stmt2 = path + "\\mysql\\bin\\mysql -u" + username + " -p"
					+ password + datebaseName + " < " + filePath;
		}

		String stmt22 = "cmd /c " + stmt2;

		// 下面这些会导致程序挂起
		System.out.println("创建archives库：" + stmt1);
		Command com1 = new Command();
		com1.executeCommand(stmt1);
		System.out.println("还原archives库：" + stmt2);
		Command com = new Command();
		com.executeCommand(stmt22);

		System.out.println("数据库还原完成");

		// 数据库还原之后进行文件所在位置的还原，也就是将原文件从数据库取出来进行格式转换，存到指定的位置
		int count = OperationFileInfo.GetTableInfoCount();
		int start = OperationFileInfo.GetFirstInfoId();
		System.out.println(count + "  " + start);
		System.out.println(userFilePath);
		// 启动转换程序
		if (StartOpenOffice.start() == 1) {
			System.out.println("OpenOffice服务启动成功");
		}
		// 循环取出每一条记录进行转换
		for (int i = start; i < start + count; i++) {
			Fileinfo f = OperationFileInfo.ReadFileinfo(i);
			if (f.getFileDelete() != 1) {// 文件信息没有被删除
				Blob file = f.getFile();
				InputStream input;
				File ff;
				File folder;
				try {
					input = file.getBinaryStream();
					ff = new File(userFilePath + f.getRealPath());// 原文件的真实名称的存放地址
					folder = new File(userFilePath
							+ f.getRealPath().substring(0,
									f.getRealPath().lastIndexOf("/")));
					System.out.println(userFilePath
							+ f.getRealPath().substring(0,
									f.getRealPath().lastIndexOf("/")));
					if (!folder.isDirectory()) {
						folder.mkdirs();
					}
					System.out.println(userFilePath + f.getRealPath());
					OutputStream output = new FileOutputStream(ff);
					byte[] buff = new byte[input.available()];
					input.read(buff);
					output.write(buff);
					input.close();
					output.close();
					// 取得文件的后缀
					String s = f.getRealPath().substring(
							f.getRealPath().lastIndexOf("."));

					System.out.println(userFilePath + f.getTimePath() + s);
					// 转存时间戳名的文件
					BufferedInputStream in = new BufferedInputStream(
							new FileInputStream(new File(userFilePath
									+ f.getRealPath())));
					BufferedOutputStream outStream = new BufferedOutputStream(
							new FileOutputStream(new File(userFilePath
									+ f.getTimePath() + s)));
					Streams.copy(in, outStream, true);
					// 对时间戳名文件进行格式转换
					Office2Swf d = new Office2Swf(userFilePath
							+ f.getTimePath() + s);
					d.conver();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return true;
	}

	public static void main(String args[]) {

	}
}