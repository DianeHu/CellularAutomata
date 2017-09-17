package cellsociety_team08;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLReaderDOM {

	private static File fXMLFile;
	private Document doc;

	public XMLReaderDOM(File xml) throws SAXException, IOException, ParserConfigurationException {
	}

	public void printXML(File xml) throws ParserConfigurationException, SAXException, IOException {
		fXMLFile = xml;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(fXMLFile);
		doc.getDocumentElement().normalize();
		System.out.println("Simulation type: " + doc.getDocumentElement().getNodeName());
		if (doc.hasChildNodes()) {
			printNode(doc.getChildNodes());
		}
	}
	
	public int printNumRowsCols(File xml) {
		//NodeList nList = doc.getElementsByTagName("numRows");
		String temp = doc.getElementById("numRows").getAttribute("numRows");
		int numRows = Integer.parseInt(temp);
		return numRows;
	}

	private void printNode(NodeList nodeLS) {
		for (int i = 0; i < nodeLS.getLength(); i++) {
			Node currNode = nodeLS.item(i);
			if (currNode.getNodeType() == Node.ELEMENT_NODE) {
				System.out.println(currNode.getNodeName());
				System.out.println(currNode.getTextContent());

				if (currNode.hasAttributes()) {
					NamedNodeMap nodeMap = currNode.getAttributes();
					for (int j = 0; j < nodeMap.getLength(); j++) {
						Node node = nodeMap.item(j);
						System.out.println(node.getNodeName());
						System.out.println(node.getNodeValue());
					}
				}
			}
			if (currNode.hasChildNodes()) {
				printNode(currNode.getChildNodes());
			}
			System.out.println(currNode.getNodeName());
		}
	}
}
