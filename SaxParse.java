package uk.ac.bangor.cs.pnd18qds.project2;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


//process XML data and do something useful with it.SAX loading specific events.Not the whole document
public class SaxParse  extends DefaultHandler{
	boolean bID = false;
	String id;
	StringBuilder builder;
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
		System.out.println("Start Document");
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
		System.out.println("End Document");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		if(qName.equalsIgnoreCase("geonameID")){
			System.out.println(qName);
			bID = true;
		}
	}
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		if(qName.equalsIgnoreCase("geonameID")){
			System.out.println(qName);
			bID = false;
		}
	}
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		if(bID) {
			
			id = new String(ch,start,length);
			bID = false;
		}
	}
	

	public String getID() {
		return id;
	}

}
