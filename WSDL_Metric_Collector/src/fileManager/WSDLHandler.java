package fileManager;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WSDLHandler extends DefaultHandler{
	
	private boolean portTypeFound;
	
	private StringVector simpleTypes;
	private StringVector complexTypes;
	private StringVector elements;
	private StringVector messages;
	private StringVector portTypes;
	private StringVector operations;
	
	private int deepnessLevel;
	
	public WSDLHandler(){ 
		portTypeFound = false;
		
		simpleTypes = new StringVector();
		complexTypes = new StringVector();
		elements = new StringVector();
		messages = new StringVector();
		portTypes = new StringVector();
		operations = new StringVector();
		
		deepnessLevel = -1;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		deepnessLevel++;
		/*
		if (qName.equalsIgnoreCase("xs:simpleType")){
			if(attributes.getValue("name") != null){
				simpleTypes.addString(attributes.getValue("name"));
				System.out.println("xs:simpleType name: " + attributes.getValue("name") + " Counter: " + simpleTypes.size() + " Depth: " + deepnessLevel);
			}
		}
		else if (qName.equalsIgnoreCase("xs:complexType")){
			if(attributes.getValue("name") != null){
				complexTypes.addString(attributes.getValue("name"));
				System.out.println("xs:complexType name: " + attributes.getValue("name") + " Counter: " + complexTypes.size() + " Depth: " + deepnessLevel);
			}
		}
		else if (qName.equalsIgnoreCase("xs:element")){
			if(attributes.getValue("name") != null){
				elements.addString(attributes.getValue("name"));
				System.out.println("xs:element name: " + attributes.getValue("name") + " Counter: " + elements.size() + " Depth: " + deepnessLevel);
			}
		}
		else if (qName.equalsIgnoreCase("message")){
			if(attributes.getValue("name") != null){
				messages.addString(attributes.getValue("name"));
				System.out.println("Messges name: " + attributes.getValue("name") + " Counter: " + messages.size() + " Depth: " + deepnessLevel);
			}
		}
		else if (qName.equalsIgnoreCase("portType")){
			if(attributes.getValue("name") != null){
				portTypeFound = true;
				portTypes.addString(attributes.getValue("name"));
				System.out.println("PortType name: " + attributes.getValue("name") + " Counter: " + portTypes.size() + " Depth: " + deepnessLevel);
			}
		}
		else if (qName.equalsIgnoreCase("operation")){
			if(attributes.getValue("name") != null && portTypeFound){
				operations.addString(attributes.getValue("name"));
				System.out.println("operation name: " + attributes.getValue("name") + " Counter: " + operations.size() + " Depth: " + deepnessLevel);
			}
		}
		*/
		if (qName.toLowerCase().contains("message")){
			if(attributes.getValue("name") != null){
				messages.addString(attributes.getValue("name"));
//				System.out.println("Messges name: " + attributes.getValue("name") + " Counter: " + messages.size() + " Depth: " + deepnessLevel);
			}
		}
		else if (qName.toLowerCase().contains("porttype")){
			if(attributes.getValue("name") != null){
				portTypeFound = true;
				portTypes.addString(attributes.getValue("name"));
//				System.out.println("PortType name: " + attributes.getValue("name") + " Counter: " + portTypes.size() + " Depth: " + deepnessLevel);
			}
		}
		else if (qName.toLowerCase().contains("operation")){
			if(attributes.getValue("name") != null && portTypeFound){
				operations.addString(attributes.getValue("name"));
//				System.out.println("operation name: " + attributes.getValue("name") + " Counter: " + operations.size() + " Depth: " + deepnessLevel);
			}
		}
		
	}

	public void endElement(String uri, String localName, String qName) throws SAXException { 
		
		deepnessLevel--;
		
		if (qName.toLowerCase().contains("porttype")){
			portTypeFound = false;
		}
	}

	public void characters(char ch[], int start, int length) throws SAXException { /* Nothing to do */ }

	/**/
	public StringVector getSimpleTypes() {
		return simpleTypes;
	}
	
	public StringVector getComplexTypes() {
		return complexTypes;
	}

	public StringVector getElements() {
		return elements;
	}
	
	public StringVector getMessages() {
		return messages;
	}
	
	public StringVector getPortTypes() {
		return portTypes;
	}

	public StringVector getOperations() {
		return operations;
	}
}