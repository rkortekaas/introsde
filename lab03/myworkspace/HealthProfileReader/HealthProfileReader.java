// import java.util.HashMap;
// import java.util.Map;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import pojos.HealthProfile;
import pojos.Person;

public class HealthProfileReader {

	Document doc;
    XPath xpath;

	public void loadXML() throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        doc = builder.parse("people.xml");

        //creating xpath object
        getXPathObj();
    }

    public XPath getXPathObj() {

        XPathFactory factory = XPathFactory.newInstance();
        xpath = factory.newXPath();
        return xpath;
    }

    public Node getPersonByName(String firstname, String lastname) throws XPathExpressionException {

        XPathExpression expr = xpath.compile("/people/person[firstname='" + firstname + "'] | /people/person[lastname='" + lastname + "']");
        Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
        return node;
    }

    public NodeList getPersonsByWeight(String weight, String condition) throws XPathExpressionException {

    	XPathExpression expr = xpath.compile("//healthprofile[weight " + condition + "'" + weight + "']");
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        return nodes;	
    }


	/**
	 * The health profile reader gets information from the command line about
	 * weight and height and calculates the BMI of the person based on this
	 * parameters
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws ParserConfigurationException, SAXException,
            IOException, XPathExpressionException {
		
		HealthProfileReader test = new HealthProfileReader();

		test.loadXML();

		Node node = test.getPersonByName("Paul", "Pogba");
		System.out.println("Node name: " + node.getNodeName());

		NodeList nodes = test.getPersonsByWeight("80", ">");
		System.out.println("Persons having weight > 80");
        for (int i = 0; i < nodes.getLength(); i++) {
	        System.out.println(nodes.item(i).getNodeValue());
	    }
	}

}