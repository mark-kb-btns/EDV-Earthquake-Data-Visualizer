package earthquake.catalog.visualizer;
import java.awt.EventQueue;



import javax.swing.JFrame;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;
import javax.swing.JButton;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Cursor;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.SoftBevelBorder;

public class Main extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8820570083377936104L;
	JFrame frmEarthquakeDataVisualizer = new JFrame();
	private JTextField startTimeField;
	private JTextField endTimeField;
	private JTextField latitudeField;
	private JTextField minMagnitudeField;
	private JTextField maxMagnitudeField;
	private JTextField longitudeField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmEarthquakeDataVisualizer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Creating an instance of JFrame as the parent container
		frmEarthquakeDataVisualizer.getContentPane().setFont(new Font("Arial", Font.PLAIN, 11));
		frmEarthquakeDataVisualizer.getContentPane().setBackground(new Color(245, 245, 247));
		frmEarthquakeDataVisualizer.setForeground(SystemColor.activeCaptionText);
		frmEarthquakeDataVisualizer.setSize(669, 730);
		//frmEarthquakeDataVisualizer.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frmEarthquakeDataVisualizer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmEarthquakeDataVisualizer.getContentPane().setLayout(null);
		frmEarthquakeDataVisualizer.setResizable(false);
		
		//Creating an instance of JPanel as header panel container
		JPanel headerPanel = new JPanel();
		headerPanel.setBounds(0, 0, 661, 81);
		headerPanel.setBackground(new Color(0, 120, 212));
		frmEarthquakeDataVisualizer.getContentPane().add(headerPanel);
		headerPanel.setLayout(null);
		
		//Initialize how to use label (header panel)
		JLabel lblHowToUse = new JLabel("How To Use");
		lblHowToUse.setForeground(new Color(29, 29, 31));
		lblHowToUse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblHowToUse.setFont(new Font("Arial", Font.PLAIN, 12));
		lblHowToUse.setBounds(563, 61, 76, 14);
		headerPanel.add(lblHowToUse);
		
		//display message on how to use when clicked
		lblHowToUse.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(frmEarthquakeDataVisualizer, "Input parameters.\r\n"
						+ "\r\n"
						+ "Start Time Format: YYYY-MM-DD\r\n"
						+ "End Time Format: YYYY-MM-DD\r\n"
						+ "Min. Magnitude Data Type: Decimal\r\n"
						+ "Max. Magnitude Data Type: Decimal\r\n"
						+ "Latitude and Longitude Data Type: Long", "How To Use", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		//Initialize about label (header panel)
		JLabel lblAbout = new JLabel("About");
		lblAbout.setForeground(new Color(29, 29, 31));
		lblAbout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblAbout.setBounds(507, 61, 46, 14);
		headerPanel.add(lblAbout);
		lblAbout.setFont(new Font("Arial", Font.PLAIN, 12));
		
		//display message about the application when clicked
		lblAbout.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
                // Show the message when the label is clicked
                JOptionPane.showMessageDialog(frmEarthquakeDataVisualizer, "Earthquake Data Visualization fetches data from USGS.\r\n"
                		+ "\r\n"
                		+ "Features:\r\n"
                		+ "Customized earthquake parameter input\r\n"
                		+ "Table for Recent Earthquakes\r\n"
                		+ "Earthquake Data Mapping\r\n"
                		+ "Scatter Chart\r\n"
                		+ "\r\n"
                		+ "Limited controls.", "About", JOptionPane.INFORMATION_MESSAGE);
            }
		});
		
		//Initialize title label (header panel)
		JLabel lblTitle = new JLabel("Earthquake \r\nData \r\nVisualizer");
		lblTitle.setBounds(97, 0, 564, 62);
		headerPanel.add(lblTitle);
		lblTitle.setFont(new Font("Arial Black", Font.BOLD, 35));
		lblTitle.setForeground(new Color(29, 29, 31));
		
		//Initialize logo label with icon (header panel)
		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(-24, -27, 131, 130);
		headerPanel.add(lblLogo);
		lblLogo.setIcon(new ImageIcon("C:\\Users\\Melo\\OneDrive\\Pictures\\download (1).png"));
		
		//Initialize background label with background photo (header panel)
		JLabel lblBackground = new JLabel("");
		lblBackground.setBounds(0, 0, 651, 110);
		headerPanel.add(lblBackground);
		lblBackground.setIcon(new ImageIcon("C:\\Users\\Melo\\Downloads\\jenna-duffy-eG8EXvfTBU8-unsplash.jpg"));
		
		//Creating an instance of GetRecentEarthquake() to fetch data for recent earthquakes table
		//Setting up the table inside a scrollpane for easy navigation
		GetRecentEarthquake recentEarthquake = new GetRecentEarthquake();
		JScrollPane scrollPane = recentEarthquake.createTable();
		

		//Creating a JScrollPane() instance to make a container for the recent earthquakes table
		JScrollPane scrlpaneRecentEarthquakes = new JScrollPane();
		scrlpaneRecentEarthquakes.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrlpaneRecentEarthquakes.setForeground(new Color(29, 29, 31));
		scrlpaneRecentEarthquakes.setBackground(SystemColor.menu);
		scrlpaneRecentEarthquakes.setFont(new Font("Arial", Font.PLAIN, 11));
		scrlpaneRecentEarthquakes.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrlpaneRecentEarthquakes.setViewportBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		scrlpaneRecentEarthquakes.setBounds(0, 110, 643, 170);
		frmEarthquakeDataVisualizer.getContentPane().add(scrlpaneRecentEarthquakes);
		// Sets the view of the scroll pane to the "scrollPane" component
		scrlpaneRecentEarthquakes.setViewportView(scrollPane);
		//scrlpaneRecentEarthquakes.pack();

		//Initializing label for recent earthquakes
		JLabel scrlpaneTitle = new JLabel("Recent Earthquakes");
		scrlpaneTitle.setBounds(10, 86, 169, 14);
		frmEarthquakeDataVisualizer.getContentPane().add(scrlpaneTitle);
		scrlpaneTitle.setForeground(new Color(29, 29, 31));
		scrlpaneTitle.setFont(new Font("Arial", Font.BOLD, 14));
		
		//Creating an instance of JPanel() visualizer component
		JPanel visualizerPanel = new JPanel();
		visualizerPanel.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(255, 255, 255), new Color(160, 160, 160)), "VISUALIZE", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(29, 29, 31))));
		visualizerPanel.setBackground(new Color(0, 120, 212));
		visualizerPanel.setBounds(0, 291, 195, 401);
		frmEarthquakeDataVisualizer.getContentPane().add(visualizerPanel);
		visualizerPanel.setLayout(null);
		
		//Initialize starttiefield to receive input from user (visualizer panel)
		startTimeField = new JTextField();
		startTimeField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		startTimeField.setBackground(new Color(245, 245, 247));
		startTimeField.setBounds(30, 36, 135, 23);
		visualizerPanel.add(startTimeField);
		startTimeField.setColumns(10);
		
		//Set the label for starttimefield (visualizer panel)
		JLabel lblStartTime = new JLabel("Start Time");
		lblStartTime.setForeground(new Color(29, 29, 31));
		lblStartTime.setFont(new Font("Arial", Font.PLAIN, 11));
		lblStartTime.setBounds(30, 23, 60, 14);
		visualizerPanel.add(lblStartTime);
		
		//Using a ToolTipText to display information when cursor lingers over the component (visualizer panel)
		JLabel lblStartTimeInfo = new JLabel("");
		lblStartTimeInfo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblStartTimeInfo.setIcon(new ImageIcon("C:\\Users\\Melo\\Downloads\\icons8-device-information-16.png"));
		lblStartTimeInfo.setFont(new Font("Arial Black", Font.BOLD, 11));
		lblStartTimeInfo.setBounds(151, 23, 14, 14);
		visualizerPanel.add(lblStartTimeInfo);
		lblStartTimeInfo.setToolTipText("<html>Limit to events on or after the specified start time <br>\r\nDATA TYPE: String<html>");
	
		//Initialize endtimefield to receive input from user (visualizer panel)
		endTimeField = new JTextField();
		endTimeField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		endTimeField.setBackground(new Color(245, 245, 247));
		endTimeField.setBounds(30, 83, 135, 23);
		visualizerPanel.add(endTimeField);
		endTimeField.setColumns(10);
		
		//Set the label for endtimefield (visualizer panel)
		JLabel lblEndTime = new JLabel("End Time");
		lblEndTime.setForeground(new Color(29, 29, 31));
		lblEndTime.setFont(new Font("Arial", Font.PLAIN, 11));
		lblEndTime.setBounds(30, 70, 60, 14);
		visualizerPanel.add(lblEndTime);
		
		//Using a ToolTipText to display information when cursor lingers over the component (visualizer panel)
		JLabel lblEndTimeInfo = new JLabel("");
		lblEndTimeInfo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblEndTimeInfo.setIcon(new ImageIcon("C:\\Users\\Melo\\Downloads\\icons8-device-information-16.png"));
		lblEndTimeInfo.setFont(new Font("Arial Black", Font.BOLD, 11));
		lblEndTimeInfo.setBounds(151, 70, 14, 14);
		visualizerPanel.add(lblEndTimeInfo);
		lblEndTimeInfo.setToolTipText("<html>Limit to events on or before the specified end time <br>\r\nDATA TYPE: String<html>");
		
		//Initialize latitudefield to receive input from user (visualizer panel)
		latitudeField = new JTextField();
		latitudeField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		latitudeField.setBounds(30, 260, 135, 23);
		visualizerPanel.add(latitudeField);
		latitudeField.setColumns(10);
		
		//Set the label for latitudefield (visualizer panel)
		JLabel lblLatitude = new JLabel("Latitude");
		lblLatitude.setForeground(new Color(29, 29, 31));
		lblLatitude.setFont(new Font("Arial", Font.PLAIN, 11));
		lblLatitude.setBounds(30, 247, 46, 14);
		visualizerPanel.add(lblLatitude);
		
		//Initialize longitudefield to receive input from user (visualizer panel)
		longitudeField = new JTextField();
		longitudeField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		longitudeField.setBounds(30, 306, 135, 23);
		visualizerPanel.add(longitudeField);
		longitudeField.setColumns(10);
		
		//Set the label for longitudefield (visualizer panel)
		JLabel lblLongitude = new JLabel("Longitude");
		lblLongitude.setFont(new Font("Arial", Font.PLAIN, 11));
		lblLongitude.setForeground(new Color(29, 29, 31));
		lblLongitude.setBounds(30, 294, 53, 14);
		visualizerPanel.add(lblLongitude);
		
		//Set the label for both latitude and longitude field (visualizer panel)
		JLabel lblCoordinate = new JLabel("Coordinate");
		lblCoordinate.setForeground(new Color(29, 29, 31));
		lblCoordinate.setFont(new Font("Arial", Font.PLAIN, 11));
		lblCoordinate.setBounds(92, 232, 60, 14);
		visualizerPanel.add(lblCoordinate);
		
		//Using a ToolTipText to display information when cursor lingers over the component (visualizer panel)
		JLabel lblCoordinateInfo = new JLabel("");
		lblCoordinateInfo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblCoordinateInfo.setIcon(new ImageIcon("C:\\Users\\Melo\\Downloads\\icons8-device-information-16.png"));
		lblCoordinateInfo.setBounds(151, 232, 14, 14);
		visualizerPanel.add(lblCoordinateInfo);
		lblCoordinateInfo.setToolTipText("<html>Indicate the latitude and longitude of place. <br>\r\nNull field will result in error. <br>\r\nNOTE: Refer to the coordinates chart<br>\r\nDATA TYPE: LONG, LONG<html>");
		
		//Initialize minmagnitudefield to receive input from user (visualizer panel)
		minMagnitudeField = new JTextField();
		minMagnitudeField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		minMagnitudeField.setBounds(30, 138, 135, 23);
		visualizerPanel.add(minMagnitudeField);
		minMagnitudeField.setColumns(10);
		
		//Set the label for minmagnitudefield (visualizer panel)
		JLabel lblMinMagnitude = new JLabel("Min. Magnitude");
		lblMinMagnitude.setForeground(new Color(29, 29, 31));
		lblMinMagnitude.setFont(new Font("Arial", Font.PLAIN, 11));
		lblMinMagnitude.setBounds(30, 126, 74, 14);
		visualizerPanel.add(lblMinMagnitude);
		
		//Using a ToolTipText to display information when cursor lingers over the component (visualizer panel)
		JLabel lblMinMagnitudeInfo = new JLabel("");
		lblMinMagnitudeInfo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblMinMagnitudeInfo.setIcon(new ImageIcon("C:\\Users\\Melo\\Downloads\\icons8-device-information-16.png"));
		lblMinMagnitudeInfo.setBounds(151, 126, 14, 14);
		visualizerPanel.add(lblMinMagnitudeInfo);
		lblMinMagnitudeInfo.setToolTipText("<html>Limit to events with a magnitude larger than the specified minimum<br>\r\n DATA TYPE: Decimal<html>");
		
		//Initialize maxmagnitudefield to receive input from user (visualizer panel)
		maxMagnitudeField = new JTextField();
		maxMagnitudeField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		maxMagnitudeField.setBounds(30, 184, 135, 23);
		visualizerPanel.add(maxMagnitudeField);
		maxMagnitudeField.setColumns(10);
		
		//Set the label for maxmagnitudefield (visualizer panel)
		JLabel lblMaxMagnitude = new JLabel("Max Magnitude");
		lblMaxMagnitude.setForeground(new Color(29, 29, 31));
		lblMaxMagnitude.setFont(new Font("Arial", Font.PLAIN, 11));
		lblMaxMagnitude.setBounds(30, 172, 74, 14);
		visualizerPanel.add(lblMaxMagnitude);
		
		//Using a ToolTipText to display information when cursor lingers over the component (visualizer panel)
		JLabel lblMaxMagnitudeInfo = new JLabel("");
		lblMaxMagnitudeInfo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblMaxMagnitudeInfo.setIcon(new ImageIcon("C:\\Users\\Melo\\Downloads\\icons8-device-information-16.png"));
		lblMaxMagnitudeInfo.setBounds(151, 172, 14, 14);
		visualizerPanel.add(lblMaxMagnitudeInfo);
		lblMaxMagnitudeInfo.setToolTipText("<html>Limit to events with a magnitude smaller than the specified maximum<br> \r\nDATA TYPE: Decimal<html>");
		
		//Creating an instance of GetCoordinates() to fetch data on coordinates
		GetCoordinates coordinates = new GetCoordinates();
		//call the createTable() method, which returns a JScrollPane object.
		JScrollPane scrollPane1 = coordinates.createTable();
		
		//Creating a JScrollPane() instance to make a container for the coordinates table
		JScrollPane scrlpaneGetCoordinates = new JScrollPane();
		scrlpaneGetCoordinates.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrlpaneGetCoordinates.setFont(new Font("Arial", Font.PLAIN, 11));
		scrlpaneGetCoordinates.setForeground(new Color(29, 29, 31));
		scrlpaneGetCoordinates.setViewportBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		scrlpaneGetCoordinates.setBackground(SystemColor.menu);
		scrlpaneGetCoordinates.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrlpaneGetCoordinates.setBounds(202, 291, 441, 401);
		frmEarthquakeDataVisualizer.getContentPane().add(scrlpaneGetCoordinates);
		scrlpaneGetCoordinates.setViewportView(scrollPane1);
		
		//Creating a submit button
		JButton btnSubmit = new JButton("SUBMIT");
		btnSubmit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSubmit.setForeground(new Color(29, 29, 31));
		btnSubmit.setBackground(new Color(255, 255, 255));
		btnSubmit.setFont(new Font("Arial", Font.BOLD, 11));
		btnSubmit.setBounds(77, 360, 89, 23);
		visualizerPanel.add(btnSubmit);
		
		//register action listener for the btnsubmit button
		btnSubmit.addActionListener(this);
		
		
		
	}

	//
	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			// Retrieve input values from text fields
		    String starttime = startTimeField.getText();
		    String endtime = endTimeField.getText();
		    Double latitude = Double.parseDouble(latitudeField.getText());
		    Double longitude = Double.parseDouble(longitudeField.getText());
		    Double minmag = Double.parseDouble(minMagnitudeField.getText());
		    Double maxmag = Double.parseDouble(maxMagnitudeField.getText());
		    double maxradiuskm = 2000;

		    // Create an instance of the Visualize class and display the results
		    Visualize results = new Visualize();
		    results.setVisible(true);
		    results.getData(starttime, endtime, latitude, longitude, minmag, maxmag, maxradiuskm);
		    
		 // Handle NullPointerException and NumberFormatException
		} catch (NullPointerException | NumberFormatException e1) {
		    JOptionPane.showMessageDialog(frmEarthquakeDataVisualizer, "Check inputs and try again.", "ERROR", 0);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(frmEarthquakeDataVisualizer, "Error parsing! Try again.", "ERROR", 0 );
		}
	}
}




