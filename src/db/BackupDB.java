package db;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class BackupDB {

	// 数据库备份
	public static boolean backupDatebase(String username, String password,
			String datebaseName, String filePath) {
		String strCommand;
		String path = System.getProperty("user.dir");
		if (password.equals("")) {
			strCommand = path
					+ "\\mysql\\bin\\mysqldump --hex-blob -h 127.0.0.1 -P3306 -u"
					+ username + " " + datebaseName + " > " + filePath;
		} else {
			strCommand = path
					+ "\\mysql\\bin\\mysqldump --hex-blob -h 127.0.0.1 -P3306 -u"
					+ username + " -p" + password + " --hex-blob "
					+ datebaseName + " > " + filePath;
		}
		StringBuffer sb = new StringBuffer("");
		StringBuffer str = new StringBuffer();
		str.append("cmd /c \"").append(strCommand).append("\"");
		System.out.println(str);
		Process ls_proc;
		try {
			ls_proc = Runtime.getRuntime().exec(str.toString());
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new DataInputStream(ls_proc.getInputStream())));
			String ss = "";
			while ((ss = in.readLine()) != null) {
				sb.append(ss).append("\n");
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static void main(String args[]) {

	}
}