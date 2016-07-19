/**
 * @Date 2016年7月19日
 *
 * @author Administrator
 */
package utils;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * 提供实现对db.cfg.xml文件的读和查找工作
 * 
 * @author 郭瑞彪
 *
 */
public class XMLUtils {

	// all the utils methods are static
	public static Document getDocument() throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new File("src/db.cfg.xml"));
		return document;
	}

	public static void write2Xml(Document document) throws Exception {

		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();

		// wrapper the two arguments
		DOMSource xmlSource = new DOMSource(document);

		StreamResult targetResult = new StreamResult(new File("src/db.cfg.xml"));

		transformer.transform(xmlSource, targetResult);

	}

}
