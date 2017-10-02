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
 *         This is a subclass of the XMLExporter superclass for the Wator
 *         simulation This class formats the XML to be export for the user's
 *         desires.
 */
public class WatorExporter extends XMLExporter {

	// initializes the resources used to get text Strings
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/XMLLabels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);

	private Element fishBreedTurns;
	private Element sharkBreedTurns;
	private Element sharkStarveTurns;
	private String fBreedTurns;
	private String sBreedTurns;
	private String sStarveTurns;

	/**
	 * @param numRowsParam
	 * @param numColsParam
	 * @param cellConfigsParam
	 * @param fishBreedParam
	 * @param sharkBreedParam
	 * @param sharkStarveParam
	 *            Constructor for WatorExporter
	 */
	public WatorExporter(String numRowsParam, String numColsParam, String cellConfigsParam, String fishBreedParam,
			String sharkBreedParam, String sharkStarveParam) {
		super(numRowsParam, numColsParam, cellConfigsParam);
		fBreedTurns = fishBreedParam;
		sBreedTurns = sharkBreedParam;
		sStarveTurns = sharkStarveParam;
	}

	/**
	 * This method builds the XML file tailored to the specifics to the Wator
	 * simulation to be save to with the user's files
	 */
	public void buildXML() {

		try {
			Document doc = XMLExporter.buildDocument();

			Element GridConfiguration = doc.createElement(myResources.getString("GridConfiguration"));
			doc.appendChild(GridConfiguration);

			addUniversalElements(doc, GridConfiguration);

			addChildrenToDataType(doc, GridConfiguration, fishBreedTurns, myResources.getString("fishBreedTurns"),
					fBreedTurns);
			addChildrenToDataType(doc, GridConfiguration, sharkBreedTurns, myResources.getString("sharkBreedTurns"),
					sBreedTurns);
			addChildrenToDataType(doc, GridConfiguration, sharkStarveTurns, myResources.getString("sharkStarveTurns"),
					sStarveTurns);

			writeXML(doc);

		} catch (ParserConfigurationException pce) {
			ErrorMessages.createErrors(myResources.getString("parseError"));
		} catch (TransformerException tfe) {
			ErrorMessages.createErrors(myResources.getString("transformerError"));
		}
	}

}
