package XMLClasses;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import simulationDrivers.ErrorMessages;

public class SegregationExporter extends XMLExporter {

	private Element segregationThreshold;
	private String segThreshold;

	public SegregationExporter(String nR, String nC, String cC, String sT) {
		super(nR, nC, cC);
		segThreshold = sT;
	}

	public void buildXML() {

		try {
			Document doc = XMLExporter.buildDocument();

			// root element
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
