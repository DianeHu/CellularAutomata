package XMLClasses;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import simulationDrivers.ErrorMessages;

public class ForagingAntsExporter extends XMLExporter{
	
	public ForagingAntsExporter(String nR, String nC, String cC) {
		super(nR, nC, cC);
	}

	Element maxAnts;
	Element evaporationRate;
	Element foragingLandX;
	Element foragingLandY;
	String mAnts;
	String eRate;
	String fLandX;
	String fLandY;
	

	public ForagingAntsExporter(String nR, String nC, String cC, String mA, String eR, String fLX,String fLY) {
		super(nR, nC, cC);
		mAnts=mA;
		eRate=eR;
		fLandX=fLX;
		fLandY=fLY;
	}
	
	public void buildXML() {

	       try {
	          Document doc = XMLExporter.buildDocument();
	         
	          // root element
	          Element GridConfiguration = doc.createElement("GridConfiguration");
	          doc.appendChild(GridConfiguration);
	    
	          addUniversalElements(doc, GridConfiguration);
	          
	          addChildrenToDataType(doc, GridConfiguration, maxAnts, "maxAnts", mAnts);
	          addChildrenToDataType(doc, GridConfiguration, evaporationRate, "evaporationRate", eRate);
	          addChildrenToDataType(doc, GridConfiguration, foragingLandX, "foragingLandX", fLandX);
	          addChildrenToDataType(doc, GridConfiguration, foragingLandY, "foragingLandY", fLandY);
	        
	          writeXML(doc);
	          
	       	} catch (ParserConfigurationException pce) {
	       		ErrorMessages.createErrors("Parsing Error!");
	      	  	} catch (TransformerException tfe) {
	      	  	ErrorMessages.createErrors("Transformer Error!");
	      	  	}
	}

}
