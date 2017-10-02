package XMLClasses;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import simulationDrivers.ErrorMessages;

/**
 * @author Tyler Yam This is a subclass of the XMLExporter superclass for the
 *         Segregation simulation This class formats the XML to be export for
 *         the user's desires.
 */
public class SegregationExporter extends XMLExporter {

	private Element segregationThreshold;
	private String segThreshold;

	/**
	 * @param nR
	 * @param nC
	 * @param cC
	 * @param sT
	 * Constructor for the SegregationExporter
	 */
	public SegregationExporter(String nR, String nC, String cC, String sT) {
		super(nR, nC, cC);
		segThreshold = sT;
	}

	/**
	 * This method builds the XML file tailored to the specifics of the Segregation simulation
	 * to be saved with the user's files
	 */
	public void buildXML() {

		try {
			Document doc = XMLExporter.buildDocument();

			Element GridConfiguration = doc.createElement("GridConfiguration");
			doc.appendChild(GridConfiguration);

			addUniversalElements(doc, GridConfiguration);

			addChildrenToDataType(doc, GridConfiguration, segregationThreshold, "segregationThreshold", segThreshold);

			writeXML(doc);

		} catch (ParserConfigurationException pce) {
			ErrorMessages.createErrors("Parsing Error!");
		} catch (TransformerException tfe) {
			ErrorMessages.createErrors("Transformer Error!");
		}
	}

}
