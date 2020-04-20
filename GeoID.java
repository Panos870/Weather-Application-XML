package uk.ac.bangor.cs.pnd18qds.project2;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class GeoID {
	
	
	public String getGeoID(String location) throws MalformedURLException {
		
		String urlString = "http://api.geonames.org/search?q="+ URLEncoder.encode(location) +"&maxRows=1&lang=en&username=leasner";
		URL url = new URL(urlString);
		HttpURLConnection connection;
		String id = null;
		
		try {
			connection = (HttpURLConnection)url.openConnection();
	    	connection.setRequestMethod("GET");
	    	connection.connect();
			InputStream is = connection.getInputStream();
			id = processDocument(is);
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("CONNECTION ERROR");
		}
		
		if(id != null)
			return id.trim(); 
		else
			return id;
		
	}
	
	private String processDocument(InputStream is) {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		
		
		SaxParse handler = new SaxParse();
		try {
			SAXParser parser = spf.newSAXParser();
			parser.parse(is,handler);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return handler.getID();
		
		
	}
	

}
