package toPDF;

import java.awt.Insets;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.security.InvalidParameterException;

import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;


public class HTMLtoPDF {
	protected int topValue = 10;
	protected int leftValue = 20;
	protected int rightValue = 10;
	protected int bottomValue = 10;
	protected int userSpaceWidth = 1300;

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		try {
//			Test jt = new Test();
//			String html = readFile("E:\\Test\\pdf_sample.html", "UTF-8");
////			System.out.println(html);
//			jt.doConversion2(html, "E:\\Test\\pd4ml.pdf");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}

	public void doConversion2(String htmlDocument, String outputPath)
			throws InvalidParameterException, MalformedURLException,
			IOException {

		PD4ML pd4ml = new PD4ML();

		pd4ml.setHtmlWidth(userSpaceWidth); // set frame width of 设置框的宽度 
											// "virtual web browser"虚拟网络浏览器 

		// choose target paper format选择目标文件格式 
		pd4ml.setPageSize(pd4ml.changePageOrientation(PD4Constants.A4));

		// define PDF page margins定义PDF页边距 
		pd4ml.setPageInsetsMM(new Insets(topValue, leftValue, bottomValue,
				rightValue));

		// source HTML document also may have margins, could be suppressed this
		// way源HTML文档也可能有页边空白，可以抑制这种方式

		// (PD4ML *Pro* feature):特征
		pd4ml.addStyle("BODY {margin: 0; font-family:KaiTi_GB2312}", true);

		// If built-in basic PDF fonts are not sufficient or
		// if you need to output non-Latin texts, TTF embedding feature should
		// help
		// (PD4ML *Pro*)
//		pd4ml.useTTF("C:\\workspace\\HtmlToPDF", true);
		pd4ml.useTTF( "java:fonts", true );
		pd4ml.setDefaultTTFs("KaiTi_GB2312","MSJH", "KaiTi_GB2312");
		pd4ml.enableDebugInfo(); 

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// actual document conversion from HTML string to byte array
		pd4ml.render(new StringReader(htmlDocument), baos);
		// if the HTML has relative references to images etc,
		// use render() method with baseDirectory parameter instead
		baos.close();

		System.out.println("resulting PDF size: " + baos.size() + " bytes");
		// in Web scenarios it is a good idea to send the size with
		// "Content-length" HTTP header

		File output = new File(outputPath);
		java.io.FileOutputStream fos = new java.io.FileOutputStream(output);
		fos.write(baos.toByteArray());
		fos.close();

		System.out.println(outputPath + "\ndone.");
	}

	private final static String readFile(String path, String encoding)
			throws IOException {

		File f = new File(path);
		FileInputStream is = new FileInputStream(f);
		BufferedInputStream bis = new BufferedInputStream(is);

		ByteArrayOutputStream fos = new ByteArrayOutputStream();
		byte buffer[] = new byte[2048];

		int read;
		do {
			read = is.read(buffer, 0, buffer.length);
			if (read > 0) {
				fos.write(buffer, 0, read);
			}
		} while (read > -1);

		fos.close();
		bis.close();
		is.close();

		return fos.toString(encoding);
	}
}
