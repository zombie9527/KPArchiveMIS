package readWord;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

public class ReadWordText {
	
	//读取后缀为doc的文件，返回读取的字符串
	public static String readWordDoc(String docPath){
		WordExtractor ex = null;
		InputStream is = null;
		try{
			is = new FileInputStream(new File(docPath));
	        ex = new WordExtractor(is);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
        return ex.getText();
	}
	
	//读取后缀为docx的文件，返回读取的字符串
	public static String readWordDocx(String docxPath){
		OPCPackage opcPackage = null;
		POIXMLTextExtractor extractor = null;
		 try {
			 opcPackage = POIXMLDocument.openPackage(docxPath);
			 extractor = new XWPFWordExtractor(opcPackage);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 return extractor.getText();
	}
	
	//测试
    public static void main(String[] args) {
//    	 System.out.println(ReadWordText.readWordDoc("F:/uploadTestFile/网站安全事故报告制度.doc"));
//    	 System.out.println(ReadWordText.readWordDocx("F:/uploadTestFile/网站安全事故报告制度.docx"));
    }
}