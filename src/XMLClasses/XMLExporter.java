package XMLClasses;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class XMLExporter {

	private static String nRows;
	private static String nCols;
	private static String cellConfig;

	static Element numRows;
	static Element numCols;
	static Element cellConfiguration;

	
	public XMLExporter(String nR, String nC, String cC)
	{
		nRows = nR;
		nCols = nC;
		cellConfig = cC;
	}

	protected void writeXML(Document doc)
			throws TransformerFactoryConfigurationError, TransformerConfigurationException, TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
          Transformer transformer = transformerFactory.newTransformer();
          DOMSource source = new DOMSource(doc);
          StreamResult result = new StreamResult(new File("GridConfigurationOutput.xml"));
          transformer.transform(source, result);
          System.out.println("File saved");
	}

	public static Document buildDocument() throws ParserConfigurationException {
		DocumentBuilderFactory dbFactory =
          DocumentBuilderFactory.newInstance();
          DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
          Document doc = dBuilder.newDocument();
		return doc;
	}

	public static void addUniversalElements(Document doc, Element GridConfiguration) {
          addChildrenToDataType(doc, GridConfiguration, numRows, "numRows", nRows);
          addChildrenToDataType(doc, GridConfiguration, numCols, "numCols", nCols);
          addChildrenToDataType(doc, GridConfiguration, cellConfiguration, "cellConfiguration", cellConfig);
	}

	public static void addChildrenToDataType(Document doc, Element GridConfiguration, Element element, String name, String input) {
		element = doc.createElement(name);
          element.appendChild(doc.createTextNode(input));
          GridConfiguration.appendChild(element);
	}

}