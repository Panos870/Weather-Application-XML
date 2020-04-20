package uk.ac.bangor.cs.pnd18qds.project2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
public class wParser
{
	String title = null ;
	String desc = null;
	 public String getWeather(String urlString) throws MalformedURLException,FileNotFoundException, SAXParseException, DOMException
	    {
	 
	    	
	    	URL url = new URL(urlString) ;
	    	Document doc;
	    	HttpURLConnection connect;
			try {
				connect = (HttpURLConnection)url.openConnection();
		    	connect.setRequestMethod("GET");
		    	connect.connect();
		    	
		    	InputStream in = connect.getInputStream();
		    	
		    	if((doc = getDocument(in)) != null) {
		    		NodeList nodes = doc.getElementsByTagName("title");
		    		title = nodes.item(1).getTextContent();
		    		NodeList description = doc.getElementsByTagName("description");
		    		desc = description.item(1).getTextContent();
		    		
		    		return title;
		    		
		    	}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

	    }
	    
    
    public static Document getDocument(InputStream is) throws SAXParseException {
    	
    	//This static method creates a new factory instance
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(is);
			return doc;
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}
		return null;
    	
    	
    }
    //retrieve the condition from the xml
   public String getCondition() {
	   //matches sequence of one or more whitespace characters
	   String regex = ":\\s+(.*?),";
	   Pattern r  = Pattern.compile(regex);
	   Matcher m = r.matcher(title);
	   if(m.find()) {
		   //group returns the input captured by the matcher
		   System.out.println(m.group(1));
		   String condition = m.group(1);
		   return condition;
	   }
	
	   return null;
	   
   }
   
   public String[][] getDescription() {
	   //rows 2D array
	   String[][] description = new String[1][7];
	   String pattern = "Temperature:(.*?), Wind Direction:(.*?), Wind Speed:(.*?), Humidity:(.*?), Pressure:(.*?, .*?), Visibility:(.*)";
	   	
	   	
	   	
	   //representation of an expression
	   Pattern descPattern = Pattern.compile(pattern);
	   //perform matching with the pattern
	   Matcher descMatcher = descPattern.matcher(desc);
	  
	   if(descMatcher.find()) {
		  int k = 0;
		  for(int i =1; i<=6;i++) {
			   
			   description[0][k++] = descMatcher.group(i);
		   }
		   return description;
	   }
	   return null;
   }
    
}
