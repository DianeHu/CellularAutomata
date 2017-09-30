package XMLClasses;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import XMLClasses.GridConfiguration;


/**
 * @author Tyler Yam
 * Source Code from Rhondu Smithwick and Robert C. Duvall
 */


/**
 * @author Tyler Yam
 * Source Code from Rhondu Smithwick and Robert C. Duvall
 * This class will read in XMLs
 */
public abstract class XMLReader {

    private final DocumentBuilder DOCUMENT_BUILDER;

    
    /**
     * @author Tyler Yam
     * Source Code from Rhondu Smithwick and Robert C. Duvall
     * Create a parser for XML files
     */
    public XMLReader () {
        DOCUMENT_BUILDER = getDocumentBuilder();
        }


    /**
     * @author Tyler Yam
     * Source Code from Rhondu Smithwick and Robert C. Duvall
     * Stores XML values a GridConfiguration
     */
    /*public GridConfiguration getGridConfiguration (File dataFile) {
        Element root = getRootElement(dataFile);
        if (! isValidFile(root, GridConfiguration.DATA_TYPE)) {
            throw new XMLException("XML file does not represent %s", GridConfiguration.DATA_TYPE);
        }
        // Stores the data with its field for gridConfiguration
        Map<String, String> results = new HashMap<>();
        for (String field : GridConfiguration.myDataFields) {
            results.put(field, getTextValue(root, field));
        }
       return new GridConfiguration(results);
    }*/


    // Get root element of an XML file
    protected Element getRootElement (File xmlFile) {
        try {
            DOCUMENT_BUILDER.reset();
            Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
            return xmlDocument.getDocumentElement();
        }
        catch (SAXException | IOException e) {
            throw new XMLException(e);
        }
    }

    // Returns if this is a valid XML file for the specified object type
    protected boolean isValidFile (Element root, String type) {
        return root.getTagName().equals(type);
    }

    // Get value of Element's text
    protected String getTextValue (Element e, String tagName) {
        NodeList nodeList = e.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        else {

            return "Sorry, Do not pass go";
        }
    }

    // Builds documentBuilder
    private DocumentBuilder getDocumentBuilder () {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new XMLException(e);
        }
    }
}
