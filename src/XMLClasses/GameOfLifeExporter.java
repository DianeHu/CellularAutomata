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
 *         This is a subclass of the XMLExporter superclass for the GameOfLife
 *         simulation This class formats the XML to be export for the user's
 *         desires.
 */
public class GameOfLifeExporter extends XMLExporter {

	// initializes the resources used to get text Strings
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/XMLLabels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);

	/**
	 * @param numRowsParam
	 * @param numColsParam
	 * @param cellConfigParam
	 *            Constructor for GameOfLifeExporter
	 */
	public GameOfLifeExporter(String numRowsParam, String numColsParam, String cellConfigParam) {
		super(numRowsParam, numColsParam, cellConfigParam);
	}

	/**
	 * This method builds the XML file for the GameOfLife specific values and
	 * configuration to be exported
	 */
	public void buildXML() {

		try {
			Document doc = XMLExporter.buildDocument();

			Element GridConfiguration = doc.createElement(myResources.getString("GridConfiguration"));
			doc.appendChild(GridConfiguration);

			addUniversalElements(doc, GridConfiguration);

			writeXML(doc);

		} catch (ParserConfigurationException pce) {
			ErrorMessages.createErrors(myResources.getString("parseError"));
		} catch (TransformerException tfe) {
			ErrorMessages.createErrors(myResources.getString("transformerError"));
		}
	}

}
