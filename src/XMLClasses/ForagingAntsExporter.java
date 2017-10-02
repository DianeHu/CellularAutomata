package XMLClasses;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import simulationDrivers.ErrorMessages;

/**
 * @author Tyler Yam
 * This is a subclass of the XMLExporter superclass for the Foraging Ants simulation
 * This class formats the XML to be export for the user's desires.
 */
public class ForagingAntsExporter extends XMLExporter{
		
	/**
	 * The XML Elements specific to the Foraging Ants simulation
	 */
	Element maxAnts;
	Element homeLocX;
	Element homeLocY;
	Element foodLocX;
	Element foodLocY;
	
	/**
	 * Local variables to store variables passed in
	 */
	String mAnts;
	String hLocX;
	String hLocY;
	String fLocX;
	String fLocY;
	

	/**
	 * @param nR
	 * @param nC
	 * @param cC
	 * @param mA
	 * @param hLX
	 * @param hLY
	 * @param fLX
	 * @param fLY
	 * Constructor for ForagingAntsExporter
	 */
	public ForagingAntsExporter(String nR, String nC, String cC, String mA, String hLX,String hLY,String fLX,String fLY) {
		super(nR, nC, cC);
		mAnts=mA;
		hLocX=hLX;
		hLocY=hLY;
		fLocX=fLX;
		fLocY=fLY;
	}
	
	/**
	 * Builds the XML documents with the Foraging Ant specific elements
	 */
	protected void buildXML() {

	       try {
	          Document doc = XMLExporter.buildDocument();
	         
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
