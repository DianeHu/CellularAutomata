package XMLClasses;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import simulationDrivers.ErrorMessages;

public class WatorExporter extends XMLExporter{

	public WatorExporter(String sT, String nR, String nC, String cC, String pC, String pG, String sT1, String fB,
			String sB, String sS) {
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
