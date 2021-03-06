package XMLClasses;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Tyler Yam
 * Source Code from Rhondu Smithwick and Robert C. Duvall
 * This class will read in XMLs for both Gridconfigurations
 * and StyleConfigurations
 */
/**
 * @author tyler
 *
 */
public abstract class XMLReader {

	// initializes the resources used to get text Strings
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/XMLLabels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);

	private final DocumentBuilder DOCUMENT_BUILDER;

	/**
	 * Constructor for XMLReader
	 */
	public XMLReader() {
		DOCUMENT_BUILDER = getDocumentBuilder();
	}

	/**
	 * @param xmlFile
	 * @return This is a getter for the root element of an XML file
	 */
	protected Element getRootElement(File xmlFile) {
		try {
			DOCUMENT_BUILDER.reset();
			Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
			return xmlDocument.getDocumentElement();
		} catch (SAXException | IOException e) {
			throw new XMLException(e);
		}
	}

	/**
	 * @param root
	 * @param type
	 * @return This method accesses if the file is valid if the tag name of a root
	 *         matches the type
	 */
	protected boolean isValidFile(Element root, String type) {
		return root.getTagName().equals(type);
	}

	/**
	 * @param e
	 * @param tagName
	 * @return This method gets the text value of an element
	 */
	protected String getTextValue(Element e, String tagName) {
		NodeList nodeList = e.getElementsByTagName(tagName);
		if (nodeList != null && nodeList.getLength() > 0) {
			return nodeList.item(0).getTextContent();
		} else {
			throw new XMLException(myResources.getString("XMLInvalid"));
		}
	}

	/**
	 * @return This method gets the document Builder
	 */
	private DocumentBuilder getDocumentBuilder() {
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new XMLException(e);
		}
	}
}
