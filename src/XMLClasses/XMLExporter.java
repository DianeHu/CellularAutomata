package XMLClasses;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javafx.application.Application;
import javafx.stage.Stage;
import simulationDrivers.ErrorMessages;

public class XMLExporter {

	private static String simType;
	private static String nRows;
	private static String nCols;
	private static String cellConfig;
	private static String pCatch;
	private static String pGrow;
	private static String segThreshold;
	private static String fBreedTurns;
	private static String sBreedTurns;
	private static String sStarveTurns;
	
	public XMLExporter(String sT, String nR, String nC, String cC, String pC, String pG, String sT1, String fB, String sB, String sS)
	{
		//simType = sT1;
		//nRows = nR;
		//nCols = nC;
		//cellConfig = cC;
		//pCatch = pC;
		//pGrow = pG;
		//segThreshold = sT1;
		//fBreedTurns = fB;
		//sBreedTurns = sB;
		//sStarveTurns = sS;
	}
	
    public void buildXML() {

       try {
          DocumentBuilderFactory dbFactory =
          DocumentBuilderFactory.newInstance();
          DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
          Document doc = dBuilder.newDocument();
         
          // root element
          Element GridConfiguration = doc.createElement("GridConfiguration");
          doc.appendChild(GridConfiguration);
         

          Element simulationType = doc.createElement("simulationType");
          simulationType.appendChild(doc.createTextNode(simType));
          GridConfiguration.appendChild(simulationType);
          
          Element numRows = doc.createElement("numRows");
          numRows.appendChild(doc.createTextNode(nRows));
          GridConfiguration.appendChild(numRows);
          
          Element numCols = doc.createElement("numCols");
          numCols.appendChild(doc.createTextNode(nCols));
          GridConfiguration.appendChild(numCols);
          
          Element cellConfiguration = doc.createElement("cellConfiguration");
          cellConfiguration.appendChild(doc.createTextNode(cellConfig));
          GridConfiguration.appendChild(cellConfiguration);
          
          Element probCatch = doc.createElement("probCatch");
          probCatch.appendChild(doc.createTextNode(pCatch));
          GridConfiguration.appendChild(probCatch);
          
          Element probGrow = doc.createElement("probGrow");
          probGrow.appendChild(doc.createTextNode(pGrow));
          GridConfiguration.appendChild(probGrow);
          
          Element segregationThreshold = doc.createElement("segregationThreshold");
          segregationThreshold.appendChild(doc.createTextNode(segThreshold));
          GridConfiguration.appendChild(segregationThreshold);
          
          Element fishBreedTurns = doc.createElement("fishBreedTurns");
          fishBreedTurns.appendChild(doc.createTextNode(fBreedTurns));
          GridConfiguration.appendChild(fishBreedTurns);
          
          Element sharkBreedTurns = doc.createElement("sharkBreedTurns");
          sharkBreedTurns .appendChild(doc.createTextNode(sBreedTurns));
          GridConfiguration.appendChild(sharkBreedTurns);
          
          Element sharkStarveTurns = doc.createElement("sharkStarveTurns");
          sharkStarveTurns .appendChild(doc.createTextNode(sStarveTurns));
          GridConfiguration.appendChild(sharkStarveTurns);
          
          // write the content into xml file
          TransformerFactory transformerFactory = TransformerFactory.newInstance();
          Transformer transformer = transformerFactory.newTransformer();
          DOMSource source = new DOMSource(doc);
          //StreamResult result = new StreamResult();
          StreamResult result = new StreamResult(new File("GridConfigurationOutput.xml"));
          transformer.transform(source, result);
          System.out.println("File saved");
          // Output to console for testing
          

          
          //StreamResult consoleResult = new StreamResult(System.out);
          //transformer.transform(source, consoleResult);
          
       } catch (ParserConfigurationException pce) {
   		ErrorMessages.createErrors("Parsing Error!");
   	  } catch (TransformerException tfe) {
   		ErrorMessages.createErrors("Transformer Error!");
   	  }
    }

}