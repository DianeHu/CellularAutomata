package XMLClasses;

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

	Element fishBreedTurns;
	Element sharkBreedTurns;
	Element sharkStarveTurns;
	String fBreedTurns;
	String sBreedTurns;
	String sStarveTurns;

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

			Element GridConfiguration = doc.createElement("GridConfiguration");
			doc.appendChild(GridConfiguration);

			addUniversalElements(doc, GridConfiguration);

			addChildrenToDataType(doc, GridConfiguration, fishBreedTurns, "fishBreedTurns", fBreedTurns);
			addChildrenToDataType(doc, GridConfiguration, sharkBreedTurns, "sharkBreedTurns", sBreedTurns);
			addChildrenToDataType(doc, GridConfiguration, sharkStarveTurns, "sharkStarveTurns", sStarveTurns);

			writeXML(doc);

		} catch (ParserConfigurationException pce) {
			ErrorMessages.createErrors("Parsing Error!");
		} catch (TransformerException tfe) {
			ErrorMessages.createErrors("Transformer Error!");
		}
	}

}
