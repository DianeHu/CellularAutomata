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
	Element homeLoc;
	Element foodLoc;
	String mAnts;
	String eRate;
	String hLoc;
	String fLoc;
	

	public ForagingAntsExporter(String nR, String nC, String cC, String mA, String eR, String hL,String fL) {
		super(nR, nC, cC);
		mAnts=mA;
		eRate=eR;
		hLoc=hL;
		fLoc=fL;
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
	          addChildrenToDataType(doc, GridConfiguration, homeLoc, "homeLoc", hLoc);
	          addChildrenToDataType(doc, GridConfiguration, foodLoc, "foodLoc", fLoc);
	        
	          writeXML(doc);
	          
	       	} catch (ParserConfigurationException pce) {
	       		ErrorMessages.createErrors("Parsing Error!");
	      	  	} catch (TransformerException tfe) {
	      	  	ErrorMessages.createErrors("Transformer Error!");
	      	  	}
	}

}
