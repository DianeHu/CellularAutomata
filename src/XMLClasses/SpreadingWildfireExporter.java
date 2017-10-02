package XMLClasses;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import simulationDrivers.ErrorMessages;

/**
 * @author Tyler Yam This is a subclass of the XMLExporter superclass for the
 *         Spreading Wildfire simulation This class formats the XML to be export for
 *         the user's desires.
 */
public class SpreadingWildfireExporter extends XMLExporter {

	Element probCatch;
	Element probGrow;
	String pCatch;
	String pGrow;
	
	/**
	 * @param nR
	 * @param nC
	 * @param cC
	 * @param pC
	 * @param pG
	 * Constructor for SpreadingWildfireExporter
	 */
	public SpreadingWildfireExporter(String nR, String nC, String cC, String pC, String pG) {
		super(nR, nC, cC);
		pCatch = pC;
		pGrow = pG;
	}
	
	/**
	 * This methods builds the XML file tailored to the specifics of the SpreadingWildfire simulation
	 * to be saved with the user's files
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
