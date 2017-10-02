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
 *         This is a subclass of the XMLExporter superclass for the Spreading
 *         Wildfire simulation This class formats the XML to be export for the
 *         user's desires.
 */
public class SpreadingWildfireExporter extends XMLExporter {

	// initializes the resources used to get text Strings
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/XMLLabels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);

	private Element probCatch;
	private Element probGrow;
	private String pCatch;
	private String pGrow;

	/**
	 * @param numRowsParam
	 * @param numColsParam
	 * @param cellConfigParam
	 * @param probCatchParam
	 * @param probGrowParam
	 *            Constructor for SpreadingWildfireExporter
	 */
	public SpreadingWildfireExporter(String numRowsParam, String numColsParam, String cellConfigParam,
			String probCatchParam, String probGrowParam) {
		super(numRowsParam, numColsParam, cellConfigParam);
		pCatch = probCatchParam;
		pGrow = probGrowParam;
	}

	/**
	 * This methods builds the XML file tailored to the specifics of the
	 * SpreadingWildfire simulation to be saved with the user's files
	 */
	public void buildXML() {

		try {
			Document doc = XMLExporter.buildDocument();

			Element GridConfiguration = doc.createElement(myResources.getString("GridConfiguration"));
			doc.appendChild(GridConfiguration);

			addUniversalElements(doc, GridConfiguration);

			addChildrenToDataType(doc, GridConfiguration, probCatch, myResources.getString("probCatch"), pCatch);
			addChildrenToDataType(doc, GridConfiguration, probGrow, myResources.getString("probGrow"), pGrow);

			writeXML(doc);

		} catch (ParserConfigurationException pce) {
			ErrorMessages.createErrors(myResources.getString("parseError"));
		} catch (TransformerException tfe) {
			ErrorMessages.createErrors(myResources.getString("transformerError"));
		}
	}

}
