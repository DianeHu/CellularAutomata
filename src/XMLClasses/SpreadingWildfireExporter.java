package XMLClasses;

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

	Element probCatch;
	Element probGrow;
	String pCatch;
	String pGrow;

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

			Element GridConfiguration = doc.createElement("GridConfiguration");
			doc.appendChild(GridConfiguration);

			addUniversalElements(doc, GridConfiguration);

			addChildrenToDataType(doc, GridConfiguration, probCatch, "probCatch", pCatch);
			addChildrenToDataType(doc, GridConfiguration, probGrow, "probGrow", pGrow);

			writeXML(doc);

		} catch (ParserConfigurationException pce) {
			ErrorMessages.createErrors("Parsing Error!");
		} catch (TransformerException tfe) {
			ErrorMessages.createErrors("Transformer Error!");
		}
	}

}
