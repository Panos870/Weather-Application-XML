package uk.ac.bangor.cs.pnd18qds.project2;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.xml.stream.XMLStreamException;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXParseException;
import javax.swing.SwingConstants;

@SuppressWarnings({ "serial", "restriction" })
public class WeatherFrame extends JFrame {

	private JPanel contentPane;
	JTextArea textArea =  new JTextArea();
	private String condition;
	private ImageIcon icon ;
	private wParser weather = new wParser();
		
	String locationID;
	Boolean foundGeo = false;
	Boolean foundBbc = false;
	GeoID geoID = new GeoID();
	CreateFile searchLogger;
	String[] columns =  {"Temperature", "Wind Direction","Wind Speed","Humidity","Pressure","Visibility"};
	String[][] data  = {{"N/a","N/a","N/a","N/a","N/a","N/a"}};
	
	private String urlString = "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/";
	WeatherFrame page = this;
	private JTable table;
	private JTable attributes;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WeatherFrame frame = new WeatherFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WeatherFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 572, 362);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		JLabel lblLocation = new JLabel("Location:");
		lblLocation.setBounds(106, 28, 79, 21);
		new DefaultTableModel(data, columns);
		contentPane.setLayout(null);
		lblLocation.setHorizontalAlignment(SwingConstants.CENTER);
		lblLocation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblLocation);
		
		final TextField locationfield = new TextField();
		locationfield.setBounds(210, 28, 208, 21);
		locationfield.setBackground(Color.WHITE);
		contentPane.add(locationfield);
		
		JButton btnForecast = new JButton("Forecast");
		btnForecast.setBounds(210, 77, 85, 21);
		btnForecast.setForeground(Color.BLACK);
		btnForecast.setBackground(Color.YELLOW);
		btnForecast.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(btnForecast);
		
		final TextField textField1 = new TextField();
		textField1.setBounds(106, 132, 312, 21);
		textField1.setBackground(Color.WHITE);
		textField1.setForeground(Color.BLACK);
		contentPane.add(textField1);
		
		table = new JTable(data,columns);
		table.setBounds(10, 283, 531, 21);
		table.setSurrendersFocusOnKeystroke(true);
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table.setFillsViewportHeight(true);
		table.setForeground(Color.BLACK);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setBackground(Color.WHITE);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"", "", "", "", "", ""},
			},
			new String[] {
				"", "", "", "", "", ""
			}
		));
		contentPane.add(table);
		
		final JLabel lblIcon = new JLabel();
		lblIcon.setBounds(234, 132, 130, 143);
		lblIcon.setIcon(new ImageIcon("C:\\Users\\PaNoS\\eclipse-workspace\\project2\\src\\main\\resources\\iconfinder_Sunny_3741356.png"));
		contentPane.add(lblIcon);
		
		
		
		try {
			searchLogger = new CreateFile();
		} catch (XMLStreamException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {	
			e2.printStackTrace();
		}
		
		
		
		attributes = new JTable();
		attributes.setBounds(10, 268, 531, 21);
		attributes.setFont(new Font("Dialog", Font.BOLD, 12));
		attributes.setModel(new DefaultTableModel(
			new Object[][] {
				{"Temperature", "Wind Direction", "Wind Speed", "Humidity", "Pressure", "Visibility"},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		attributes.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(attributes);
		
		
		btnForecast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								
				String location = locationfield.getText().trim().toLowerCase();  //trim input and set to lower case ready for search
			try {
					locationID = geoID.getGeoID(location);
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					foundGeo = false;
					System.out.println("LOCATION ID NOT FOUND");
				}
				
				
				if(locationID != null)
					foundGeo = true;
				else
					foundGeo = false;
				
				try {
					searchLogger.addLog(location,foundGeo,locationID);
					
				} catch (XMLStreamException e1) {
					e1.printStackTrace();
				}
				
				
				
				if(foundGeo) {
					try {
						
						textField1.setText(weather.getWeather(urlString+locationID));
						foundBbc=true;
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						foundBbc = false;
						System.out.println("MALFORMED URL EXCEPTION");
					} catch (SAXParseException e1) {
						System.out.println("BBC WEATHER DOES NOT HAVE RECORDS FOR GIVEN LOCATION");
						foundBbc = false;
					} catch (DOMException e1) {
						foundBbc = false;
						System.out.println("BBC WEATHER DOES NOT HAVE RECORDS FOR GIVEN LOCATION");
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						System.out.println("BBC WEATHER DOES NOT HAVE RECORDS FOR GIVEN LOCATION");
					}
				}
				
				
				//retrieve the condition
				
				if(foundBbc) {
					condition = weather.getCondition();
					condition.trim();
					data = weather.getDescription();
					foundBbc = false;
				}
				else
					data = null;
					TableModel tmodel = new DefaultTableModel(data, columns);
				    table.setModel(tmodel);
				    System.err.println(condition);
					
				//Based on condition select an icon
				
				if(condition.equals("Not Available"))
					icon = new ImageIcon("C:\\\\Users\\\\PaNoS\\\\eclipse-workspace\\\\project2\\\\src\\\\main\\\\resources\\icons8-unavailable-64.png");
				else if(condition.equals("Light Cloud"))
					icon = new ImageIcon("C:\\\\Users\\\\PaNoS\\\\eclipse-workspace\\\\project2\\\\src\\\\main\\\\resources\\iconfinder_Partly_Cloudy_3741357.png");
				else if(condition.equals("Thick Cloud"))
					icon = new ImageIcon("C:\\\\Users\\\\PaNoS\\\\eclipse-workspace\\\\project2\\\\src\\\\main\\\\resources\\iconfinder_Overcast_3741359.png");
				else if(condition.equals("Sunny"))
					icon = new ImageIcon("C:\\\\Users\\\\PaNoS\\\\eclipse-workspace\\\\project2\\\\src\\\\main\\\\resources\\iconfinder_Sunny_3741356.png");
				else if(condition.equals("windy"))
					icon = new ImageIcon("C:\\\\Users\\\\PaNoS\\\\eclipse-workspace\\\\project2\\\\src\\\\main\\\\resources\\iconfinder_Windy_3741354.png");
				else if(condition.equals("Rain"))
					icon = new ImageIcon("C:\\\\Users\\\\PaNoS\\\\eclipse-workspace\\\\project2\\\\src\\\\main\\\\resources\\iconfinder_Moderate_Rain_3741351.png");
				else if(condition.equals("Heavy Rain"))
					icon = new ImageIcon("C:\\\\Users\\\\PaNoS\\\\eclipse-workspace\\\\project2\\\\src\\\\main\\\\resources\\iconfinder_Moderate_Rain_3741351.png");
				else if(condition.equals("Light Rain"))
					icon = new ImageIcon("C:\\\\Users\\\\PaNoS\\\\eclipse-workspace\\\\project2\\\\src\\\\main\\\\resources\\iconfinder_Light_Rain_3741355.png");
				else if(condition.equals("Light Snow"))
					icon = new ImageIcon("C:\\Users\\PaNoS\\eclipse-workspace\\project2\\src\\main\\resources\\iconfinder_Foggy_3741362.png");
				else if(condition.equals("Hazy"))
					icon = new ImageIcon("C:\\\\Users\\\\PaNoS\\\\eclipse-workspace\\\\project2\\\\src\\\\main\\\\resources\\iconfinder_Moderate_Rain_3741351.png");
				else if(condition.equals("Sunny Intervals"))
					icon = new ImageIcon("C:\\Users\\PaNoS\\eclipse-workspace\\project2\\src\\main\\\\resources\\iconfinder_Partly_Cloudy_3741357.png");
				else if(condition.equals("Partly Cloudy"))
					icon = new ImageIcon("C:\\\\Users\\\\PaNoS\\\\eclipse-workspace\\\\project2\\\\src\\\\main\\\\resources\\\\iconfinder_Partly_Cloudy_3741357.png");
				else if(condition.equals("Drizzle"))
					icon = new ImageIcon(getClass().getResource("/iconfinder_Light_Rain_3741355.png"));
				else
					icon = new ImageIcon("C:\\\\Users\\\\PaNoS\\\\eclipse-workspace\\\\project2\\\\src\\\\main\\\\resources\\\\icons8-unavailable-64.png");
				
				
				lblIcon.setIcon(icon);
				page.repaint();
				page.revalidate();
				
				//client
				try {
					System.out.println("----------------------------------------------");
					System.out.println("" + geoID);
					System.out.println("----------------------------------------------");
					System.out.println(locationID);
					System.out.println(location);
					System.out.println(weather.getWeather(urlString+locationID));
					System.out.println("----------------------------------------------");
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SAXParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (DOMException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
					}
			});
	}
}
