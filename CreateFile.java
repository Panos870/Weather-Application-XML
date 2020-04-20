package uk.ac.bangor.cs.pnd18qds.project2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.XMLStreamReader;


//writing the XML
@SuppressWarnings("restriction")
public class CreateFile {
	
	XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
	XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
	XMLStreamWriter writer = null;
	XMLStreamReader reader = null;
	String searchTerm;
	File dir;
	String path;

	public CreateFile() throws XMLStreamException, IOException {
		
		dir = new File(System.getProperty("user.dir"));
		path = dir+"/searchData.xml";
		writer = xmlOutputFactory.createXMLStreamWriter(new FileWriter(dir+"/searchData.xml"));
		writer.writeStartDocument();
		writer.writeCharacters("\n");
		writer.writeStartElement("weatherSearch");
		writer.writeCharacters("\n\t");
			}


	public String getPath() {
		return path;
	}
	public void addLog(String term,Boolean found,String id) throws XMLStreamException {
		
		SimpleDateFormat Sdate= new SimpleDateFormat("dd/MM/yyyy");
		
		String found_str;
		if(found)
			found_str = "true";
		else
			found_str = "false";
		
		String date = Sdate.format(new Date());
		
		writer.writeStartElement("search");
		writer.writeAttribute("date", date);
		writer.writeCharacters("\n\t\t");
		writer.writeStartElement("term");
		writer.writeCharacters(term);
		writer.writeEndElement();
		writer.writeCharacters("\n\t\t");
		writer.writeStartElement("found");
		writer.writeCharacters(found_str);
		writer.writeEndElement();
		writer.writeCharacters("\n\t\t");
		writer.writeStartElement("geoNameID");
		writer.writeCharacters(id);
		writer.writeEndElement();
		writer.writeCharacters("\n\t");
		writer.writeEndElement();
		writer.writeCharacters("\n");
		writer.flush();
	}
	
	
	public void endFile() throws XMLStreamException {
		 
		writer.writeEndElement();
		writer.writeCharacters("\n");
		writer.writeEndDocument();
		writer.close();
	}

	public static void main(String[] args) throws XMLStreamException, IOException {
		
		CreateFile here = new CreateFile();
		here.addLog("London", true, "123");
		
		here.endFile();
	}
	
}