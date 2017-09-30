package XMLClasses;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import simulationDrivers.ErrorMessages;

public class SpreadingWildfireExporter extends XMLExporter {

	public SpreadingWildfireExporter(String sT, String nR, String nC, String cC, String pC, String pG, String sT1,
			String fB, String sB, String sS) {
		super(sT, nR, nC, cC, pC, pG, sT1, fB, sB, sS);
		// TODO Auto-generated constructor stub
	}
	
	public void buildXML() {

	       try {
	          Document doc = XMLExporter.buildDocument();
	         
	          // root element
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
