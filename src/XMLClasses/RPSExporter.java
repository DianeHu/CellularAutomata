package XMLClasses;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import simulationDrivers.ErrorMessages;

/**
 * @author Tyler Yam This is a subclass of the XMLExporter superclass for the
 *         RPS simulation This class formats the XML to be export for
 *         the user's desires.
 */
public class RPSExporter extends XMLExporter {
	
	
	/**
	 * @param nR
	 * @param nC
	 * @param cC
	 * Constructor for RPSExporter
	 */
	public RPSExporter(String nR, String nC, String cC) {
		super(nR, nC, cC);
	}

	/**
	 * This method builds the XML file tailored to the specifics of the RPS simulation
	 * and saves it to the user's files
	 */
	public void buildXML() {

	       try {
	          Document doc = XMLExporter.buildDocument();
	         
	          Element GridConfiguration = doc.createElement("GridConfiguration");
	          doc.appendChild(GridConfiguration);

	          addUniversalElements(doc, GridConfiguration);
    
	          writeXML(doc);
	          
	       	} catch (ParserConfigurationException pce) {
	       		ErrorMessages.createErrors("Parsing Error!");
	      	  	} catch (TransformerException tfe) {
	      	  	ErrorMessages.createErrors("Transformer Error!");
	      	  	}
	}

}