package XMLClasses;

import java.io.File;
import java.util.ResourceBundle;

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

/**
 * @author Tyler Yam and inspiration from
 *         https://www.tutorialspoint.com/java_xml/java_dom_create_document.htm
 *         This is a superclass that formats the XML to be exported for the
 *         user's desires.
 */
public abstract class XMLExporter {

	// initializes the resources used to get text Strings
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/XMLLabels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);

	private static String nRows;
	private static String nCols;
	private static String cellConfig;

	private static Element numRows;
	private static Element numCols;
	private static Element cellConfiguration;

	/**
	 * @param numRowsParam
	 * @param numColsParam
	 * @param cellConfiguParam
	 *            Constructor is for the XMLExporter
	 */
	public XMLExporter(String numRowsParam, String numColsParam, String cellConfiguParam) {
		nRows = numRowsParam;
		nCols = numColsParam;
		cellConfig = cellConfiguParam;
	}

	/**
	 * @param doc
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 *             This method utilizes the transformer class to convert a source,
	 *             specifically a DOM source into a result to write the XML and save
	 *             it to the user's files
	 */
	protected void writeXML(Document doc)
			throws TransformerFactoryConfigurationError, TransformerConfigurationException, TransformerException {

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("GridConfigurationOutput.xml"));
		transformer.transform(source, result);
	}

	/**
	 * @return
	 * @throws ParserConfigurationException
	 *             This method builds a DOM document for the elements of the XML to
	 *             be added to
	 */
	public static Document buildDocument() throws ParserConfigurationException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.newDocument();
		return doc;
	}

	/**
	 * @param doc
	 * @param GridConfiguration
	 *            This method adds elements that need to be included in the XML for
	 *            all types of simulation
	 */
	public static void addUniversalElements(Document doc, Element GridConfiguration) {
		addChildrenToDataType(doc, GridConfiguration, numRows, myResources.getString("numRows"), nRows);
		addChildrenToDataType(doc, GridConfiguration, numCols, myResources.getString("numCols"), nCols);
		addChildrenToDataType(doc, GridConfiguration, cellConfiguration, myResources.getString("cellConfiguration"),
				cellConfig);
	}

	/**
	 * @param doc
	 * @param GridConfiguration
	 * @param element
	 * @param name
	 * @param input
	 *            This method will add elements that are specific to individual
	 *            simulations
	 */
	public static void addChildrenToDataType(Document doc, Element GridConfiguration, Element element, String name,
			String input) {
		element = doc.createElement(name);
		element.appendChild(doc.createTextNode(input));
		GridConfiguration.appendChild(element);
	}

}