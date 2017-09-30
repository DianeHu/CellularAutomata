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
	Element homeLocX;
	Element homeLocY;
	Element foodLocX;
	Element foodLocY;
	String mAnts;
	String hLocX;
	String hLocY;
	String fLocX;
	String fLocY;
	

	public ForagingAntsExporter(String nR, String nC, String cC, String mA, String hLX,String hLY,String fLX,String fLY) {
		super(nR, nC, cC);
		mAnts=mA;
		hLocX=hLX;
		hLocY=hLY;
		fLocX=fLX;
		fLocY=fLY;
	}
	
	public void buildXML() {

	       try {
	          Document doc = XMLExporter.buildDocument();
	         
	          // root element
	          Element GridConfiguration = doc.createElement("GridConfiguration");
	          doc.appendChild(GridConfiguration);
	    
	          addUniversalElements(doc, GridConfiguration);
	          
	          addChildrenToDataType(doc, GridConfiguration, maxAnts, "maxAnts", mAnts);
	          addChildrenToDataType(doc, GridConfiguration, homeLocX, "homeLocX", hLocX);
	          addChildrenToDataType(doc, GridConfiguration, homeLocY, "homeLocY", hLocY);
	          addChildrenToDataType(doc, GridConfiguration, foodLocX, "foodLocX", fLocX);
	          addChildrenToDataType(doc, GridConfiguration, foodLocY, "foodLocY", fLocY);
	        
	          writeXML(doc);
	          
	       	} catch (ParserConfigurationException pce) {
	       		ErrorMessages.createErrors("Parsing Error!");
	      	  	} catch (TransformerException tfe) {
	      	  	ErrorMessages.createErrors("Transformer Error!");
	      	  	}
	}

}
