package db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Command {
	// 保存进程的输入流信息
	private List<String> stdoutList = new ArrayList<String>();
	// 保存进程的错误流信息
	private List<String> erroroutList = new ArrayList<String>();

	public void executeCommand(String command) {
		// 先清空
		stdoutList.clear();
		erroroutList.clear();

		Process p = null;
		try {
			p = Runtime.getRuntime().exec(command);

			// 创建2个线程，分别读取输入流缓冲区和错误流缓冲区
			ThreadUtil stdoutUtil = new ThreadUtil(p.getInputStream(),
					stdoutList);
			ThreadUtil erroroutUtil = new ThreadUtil(p.getErrorStream(),
					erroroutList);
			// 启动线程读取缓冲区数据
			stdoutUtil.start();
			erroroutUtil.start();

			// 让p等待10秒之后销毁，这样就能让命令继续执行下去
			Thread.sleep(10000);
			System.out.println("waitFor开始执行");
			// p.waitFor();
			p.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> getStdoutList() {
		return stdoutList;
	}

	public List<String> getErroroutList() {
		return erroroutList;
	}

}

class ThreadUtil implements Runnable {
	// 设置读取的字符编码
	private String character = "UTF-8";
	private List<String> list;
	private InputStream inputStream;

	public ThreadUtil(InputStream inputStream, List<String> list) {
		this.inputStream = inputStream;
		this.list = list;
	}

	public void start() {
		Thread thread = new Thread(this);
		thread.setDaemon(true);// 将其设置为守护线程
		thread.start();
	}

	public void run() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream,
					character));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line != null) {
					list.add(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				inputStream.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}