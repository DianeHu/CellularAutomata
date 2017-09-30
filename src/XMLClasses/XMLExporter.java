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

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javafx.application.Application;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import simulationDrivers.ErrorMessages;

public abstract class XMLExporter {

	private static String simType;
	private static String nRows;
	private static String nCols;
	private static String cellConfig;
	protected static String pCatch;
	protected static String pGrow;
	protected static String segThreshold;
	protected static String fBreedTurns;
	protected static String sBreedTurns;
	protected static String sStarveTurns;
	static Element simulationType;
	static Element numRows;
	static Element numCols;
	static Element cellConfiguration;
	Element probCatch;
	Element probGrow;
	Element segregationThreshold;
	Element fishBreedTurns; 
	Element sharkBreedTurns; 
	Element sharkStarveTurns;
	
	public XMLExporter(String sT, String nR, String nC, String cC, String pC, String pG, String sT1, String fB, String sB, String sS)
	{
		simType = sT1;
		nRows = nR;
		nCols = nC;
		cellConfig = cC;
		pCatch = pC;
		pGrow = pG;
		segThreshold = sT1;
		fBreedTurns = fB;
		sBreedTurns = sB;
		sStarveTurns = sS;
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
		addChildrenToDataType(doc, GridConfiguration, simulationType, "simulationType", simType);
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