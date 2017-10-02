package XMLClasses;

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

	/**
	 * The XML Elements specific to the Foraging Ants simulation
	 */
	Element maxAnts;
	Element homeLocX;
	Element homeLocY;
	Element foodLocX;
	Element foodLocY;

	/**
	 * Local variables to store variables passed in
	 */
	String maxAntsLocal;
	String homeLocXLocal;
	String homeLocYLocal;
	String foodLocXLocal;
	String foodLocYLocal;

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

			Element GridConfiguration = doc.createElement("GridConfiguration");
			doc.appendChild(GridConfiguration);

			addUniversalElements(doc, GridConfiguration);

			addChildrenToDataType(doc, GridConfiguration, maxAnts, "maxAnts", maxAntsLocal);
			addChildrenToDataType(doc, GridConfiguration, homeLocX, "homeLocX", homeLocXLocal);
			addChildrenToDataType(doc, GridConfiguration, homeLocY, "homeLocY", homeLocYLocal);
			addChildrenToDataType(doc, GridConfiguration, foodLocX, "foodLocX", foodLocXLocal);
			addChildrenToDataType(doc, GridConfiguration, foodLocY, "foodLocY", foodLocYLocal);

			writeXML(doc);

		} catch (ParserConfigurationException pce) {
			ErrorMessages.createErrors("Parsing Error!");
		} catch (TransformerException tfe) {
			ErrorMessages.createErrors("Transformer Error!");
		}
	}

}
