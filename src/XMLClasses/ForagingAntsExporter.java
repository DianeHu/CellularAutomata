package XMLClasses;

import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import simulationDrivers.ErrorMessages;

/**
 * @author Tyler Yam and inspiration from
 *         https://www.tutorialspoint.com/java_xml/java_dom_create_document.htm
 *         This is a subclass of the XMLExporter superclass for the Foraging
 *         Ants simulation This class formats the XML to be export for the
 *         user's desires.
 */
public class ForagingAntsExporter extends XMLExporter {

	// initializes the resources used to get text Strings
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/XMLLabels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);

	/**
	 * The XML Elements specific to the Foraging Ants simulation
	 */
	private Element maxAnts;
	private Element homeLocX;
	private Element homeLocY;
	private Element foodLocX;
	private Element foodLocY;

	/**
	 * Local variables to store variables passed in
	 */
	private String maxAntsLocal;
	private String homeLocXLocal;
	private String homeLocYLocal;
	private String foodLocXLocal;
	private String foodLocYLocal;

	/**
	 * @param numRowParam
	 * @param numColParam
	 * @param cellConfigParam
	 * @param maxAntsParam
	 * @param homeLocXParam
	 * @param homeLocYParam
	 * @param foodLocXParam
	 * @param foodLocYParam
	 *            Constructor for ForagingAntsExporter
	 */
	public ForagingAntsExporter(String numRowParam, String numColParam, String cellConfigParam, String maxAntsParam,
			String homeLocXParam, String homeLocYParam, String foodLocXParam, String foodLocYParam) {
		super(numRowParam, numColParam, cellConfigParam);
		maxAntsLocal = maxAntsParam;
		homeLocXLocal = homeLocXParam;
		homeLocYLocal = homeLocYParam;
		foodLocXLocal = foodLocXParam;
		foodLocYLocal = foodLocYParam;
	}

	/**
	 * Builds the XML documents with the Foraging Ant specific elements
	 */
	public void buildXML() {

		try {
			Document doc = XMLExporter.buildDocument();

			Element GridConfiguration = doc.createElement(myResources.getString("GridConfiguration"));
			doc.appendChild(GridConfiguration);

			addUniversalElements(doc, GridConfiguration);

			addChildrenToDataType(doc, GridConfiguration, maxAnts, myResources.getString("maxAnts"), maxAntsLocal);
			addChildrenToDataType(doc, GridConfiguration, homeLocX, myResources.getString("homeLocX"), homeLocXLocal);
			addChildrenToDataType(doc, GridConfiguration, homeLocY, myResources.getString("homeLocY"), homeLocYLocal);
			addChildrenToDataType(doc, GridConfiguration, foodLocX, myResources.getString("foodLocX"), foodLocXLocal);
			addChildrenToDataType(doc, GridConfiguration, foodLocY, myResources.getString("foodLocY"), foodLocYLocal);

			writeXML(doc);

		} catch (ParserConfigurationException pce) {
			ErrorMessages.createErrors(myResources.getString("parseError"));
		} catch (TransformerException tfe) {
			ErrorMessages.createErrors(myResources.getString("transformerError"));
		}
	}

}
