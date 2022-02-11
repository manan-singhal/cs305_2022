package Project;

import java.io.StringWriter;
import javax.xml.transform.Transformer;
import java.io.File;
import javax.xml.transform.dom.DOMSource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.TransformerException;
import java.util.HashMap;
import javax.xml.transform.TransformerFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;

public class XmlExtractor {

    public static HashMap<String, String> getCommandsFromXmlFile() {
        try {
			File file = new File("E:/VS Code Projects/cs305_2022/queries.xml");
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			document.getDocumentElement().normalize();

			String nodeStringToSearch = "<" + document.getDocumentElement().getNodeName() + ">";
			String fileString = convertStringFromDocument(document);
			fileString = fileString.substring(
				fileString.indexOf(nodeStringToSearch) + nodeStringToSearch.length(), 
				fileString.length());
			fileString = fileString.substring(0, fileString.length() - nodeStringToSearch.length());

			HashMap<String, String> hashMap = new HashMap<>();
			int index = fileString.indexOf("<![CDATA[");
			while (index != -1) {
                String idOfACommand = fileString.substring(fileString.indexOf("sql id=") + 8, 
                    fileString.indexOf("\" paramType"));
				fileString = fileString.substring(index + 9, 
                    fileString.length());
				String command = fileString.substring(0, fileString.indexOf("]]>"));
                hashMap.put(idOfACommand.trim(), command.trim());
				index = fileString.indexOf("<![CDATA[");
			}

			return hashMap;
		}
		catch (Exception e) {
            return null;
		}
    }

	public static String convertStringFromDocument(Document document) {
		StringWriter stringWriter;
		StreamResult streamResult;
		TransformerFactory transformerFactory;
		Transformer transformer;
		try {
			stringWriter = new StringWriter();
			streamResult = new StreamResult(stringWriter);
			transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();
			transformer.transform(new DOMSource(document), streamResult);
			return stringWriter.toString();
		}
		catch(TransformerException ex) {}
		return null;
	}
}