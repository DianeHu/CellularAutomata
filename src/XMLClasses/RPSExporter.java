package XMLClasses;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import simulationDrivers.ErrorMessages;

public class RPSExporter extends XMLExporter {
	
	
	public RPSExporter(String nR, String nC, String cC) {
		super(nR, nC, cC);
	}

	public void buildXML() {

	       try {
	          Document doc = XMLExporter.buildDocument();
	         
	          // root element
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