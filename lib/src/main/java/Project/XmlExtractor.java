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

    public static HashMap<String, String> extractXml() {
        try {
            HashMap<String, String> commandHashMap = new HashMap<>();
			File file = new File("E:/VS Code Projects/cs305_2022/queries.xml");
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			document.getDocumentElement().normalize();

			String searchString = "<" + document.getDocumentElement().getNodeName() + ">";
			String fileString = convertStringFromDocument(document);
			int index = fileString.indexOf(searchString) + searchString.length();
			fileString = fileString.substring(index, fileString.length());
			fileString = fileString.substring(0, fileString.length()-searchString.length());

			String cData = "<![CDATA[";
			String endCData = "]]>";
			int firstIndex = fileString.indexOf(cData);
			while (firstIndex != -1) {
                String idOfACommand = fileString.substring(fileString.indexOf("sql id=") + 8, 
                    fileString.indexOf("\" paramType"));
				fileString = fileString.substring(firstIndex + cData.length(), 
                    fileString.length());
				String command = fileString.substring(0, fileString.indexOf(endCData));
                commandHashMap.put(idOfACommand.trim(), command.trim());
				firstIndex = fileString.indexOf(cData);
			}

			/*for (int i=0; i<commands.size(); i++) {
				String crudOperator = commands.get(i).substring(0, commands.get(i).indexOf(' '));
				System.out.println(crudOperator);

				if (crudOperator.equals("INSERT")) {
					String tableName = commands.get(i).substring(
						commands.get(i).indexOf("INTO") + 5, 
						commands.get(i).indexOf("("));
					String columnNameString = commands.get(i).substring(
						commands.get(i).indexOf("(") + 1, 
						commands.get(i).indexOf(")"));
					String[] columnName = columnNameString.split(", ");

					String deleteCommands = commands.get(i).substring(
						commands.get(i).indexOf(")") + 1,
						commands.get(i).length());

					int checkBrackets = deleteCommands.indexOf("(");
					while (checkBrackets != -1) {
						HashMap<String, Object> columnDetails = new HashMap<>();

						String columnInfoString = deleteCommands.substring(
							deleteCommands.indexOf("(") + 1, 
							deleteCommands.indexOf(")"));
						String[] columnInfo = columnInfoString.split(", ");

						for (int j=0; j<columnName.length; j++) {
							columnDetails.put(columnName[j], columnInfo[j]);
						}
						System.out.println("Table Name:- " + tableName);
						System.out.println("Column Name and values:-");
						for (Entry<String, Object> set : columnDetails.entrySet()) {
							System.out.println(set.getKey() + " = " + set.getValue());
						}
						deleteCommands = deleteCommands.substring(
							deleteCommands.indexOf(")") + 1,
							deleteCommands.length());
						checkBrackets = deleteCommands.indexOf("(");
					}
				}
				else if (crudOperator.equals("UPDATE")) {
					String[] context = commands.get(i).split(" ");
					String setDataString = commands.get(i).substring(
						commands.get(i).indexOf("SET") + 4,
						commands.get(i).indexOf("WHERE") - 1);
					String[] setData = setDataString.split(", ");
					String condition = commands.get(i).substring(
						commands.get(i).indexOf("WHERE") + 6,
						commands.get(i).indexOf(";"));
					System.out.println("Table Name:- " + context[1]);
					for (int j=0; j<setData.length; j++) {
						String[] pair = setData[j].split("=");
						if (pair.length == 2) {
							System.out.println("Change " + pair[0].trim() + " to " + pair[1].trim());
						}
					}

					String[] pair = condition.split("=");
					if (pair.length == 2) {
						System.out.println("If " + pair[0].trim() + " is equal to " + pair[1].trim());
					}
				}
				else if (crudOperator.equals("DELETE")) {
					String[] context = commands.get(i).split(" ");
					String condition = commands.get(i).substring(
						commands.get(i).indexOf("WHERE") + 6,
						commands.get(i).indexOf(";"));
					System.out.println("Table Name:- " + context[2]);

					String[] pair = condition.split("=");
					if (pair.length == 2) {
						System.out.println("If " + pair[0].trim() + " is equal to " + pair[1].trim());
					}
				}
			}*/
            return commandHashMap;
		}
		catch (Exception e) {
			System.out.println(e.toString());
            return null;
		}
    }
}