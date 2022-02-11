package Project;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;

public class XmlExtractor {

    public static String convertStringFromDocument(Document document) {
		try {
			DOMSource domSource = new DOMSource(document);
			StringWriter stringWriter = new StringWriter();
			StreamResult streamResult = new StreamResult(stringWriter);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(domSource, streamResult);
			return stringWriter.toString();
		}
		catch(TransformerException ex) {
			ex.printStackTrace();
			return null;
		}
	}

    public static HashMap<String, String> getCommandsFromXmlFile() {
        try {
            HashMap<String, String> hashMap = new HashMap<>();

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
			fileString = fileString.substring(0, 
				fileString.length() - nodeStringToSearch.length());

			String cData = "<![CDATA[";
			String endCData = "]]>";
			int index = fileString.indexOf(cData);
			while (index != -1) {
                String idOfACommand = fileString.substring(fileString.indexOf("sql id=") + 8, 
                    fileString.indexOf("\" paramType"));
				fileString = fileString.substring(index + cData.length(), 
                    fileString.length());
				String command = fileString.substring(0, fileString.indexOf(endCData));
                hashMap.put(idOfACommand.trim(), command.trim());
				index = fileString.indexOf(cData);
			}

			return hashMap;
		}
		catch (Exception e) {
            return null;
		}
    }
}