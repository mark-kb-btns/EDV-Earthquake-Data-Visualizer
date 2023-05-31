package earthquake.catalog.visualizer;

import java.io.BufferedReader;



import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.awt.geom.Point2D;
import javax.swing.event.MouseInputListener;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;
import org.jxmapviewer.viewer.WaypointRenderer;
import org.tc33.jheatchart.HeatChart;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;

import javax.swing.border.LineBorder;



public class Visualize extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel resultPanel = new JPanel();
	private List<Double> magnitudes;
	private double magnitude;
	private JLabel lblColorFive;
	private JLabel lblColorABOVE;
	private JLabel lblSummaryCoordinates;
	private JLabel lblSummaryEndTime;
	private JLabel lblSummaryMinMagnitude;
	private JLabel lblSummaryMaxMagnitude;
	private JLabel lblSummaryResultCoordinates;
	private JPanel panelSummary;
	private JLabel lblSummaryResultStartTime;
	private JLabel lblSummaryResultEndTime;
	private JLabel lblSummaryResultMinMagnitude;
	private JLabel lblSummaryResultMaxMagnitude;
	private JScrollPane scrlpaneEarthquakeResult;
	private HeatChart heatmap;
	private JPanel panelHeatMapChart;
	private List<String> dates;
	/**
	 * Launch the application.
	 * 
	 */
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Visualize frame = new Visualize();
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
	public Visualize() {
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setSize(1370, 770);
	    setTitle("Earthquake Data Visualizer");

	    // Create a panel to hold the content
	    contentPane = new JPanel();
	    contentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	    setContentPane(contentPane);
	    contentPane.setLayout(null);

	    // Add a scroll pane for the earthquake result table
	    scrlpaneEarthquakeResult = new JScrollPane();
	    scrlpaneEarthquakeResult.setBounds(0, 408, 1354, 323);
	    contentPane.add(scrlpaneEarthquakeResult);
	    getContentPane().add(scrlpaneEarthquakeResult);

	    // Create a panel for displaying the result with a border and background color
	    resultPanel = new JPanel();
	    resultPanel.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
	    resultPanel.setBackground(new Color(192, 192, 192));
	    resultPanel.setBounds(0, 0, 678, 384);
	    getContentPane().add(resultPanel);
	    resultPanel.setLayout(new BorderLayout(0, 0));

	    // Label to indicate earthquakes with magnitude above 6.0
	    lblColorABOVE = new JLabel("> 5.0");
	    lblColorABOVE.setForeground(new Color(29, 29, 31));
	    lblColorABOVE.setFont(new Font("Arial", Font.PLAIN, 11));
	    contentPane.add(lblColorABOVE);
	    lblColorABOVE.setBounds(651, 388, 27, 14);

	    // Label to indicate earthquakes with magnitude below 2.0
	    JLabel lblColorTwo = new JLabel("< 2.0");
	    lblColorTwo.setForeground(new Color(29, 29, 31));
	    lblColorTwo.setFont(new Font("Arial", Font.PLAIN, 11));
	    contentPane.add(lblColorTwo);
	    lblColorTwo.setBounds(414, 388, 27, 14);

	    // Label to indicate earthquakes with magnitude below 5.0
	    lblColorFive = new JLabel("< 5.0");
	    lblColorFive.setForeground(new Color(29, 29, 31));
	    lblColorFive.setFont(new Font("Arial", Font.PLAIN, 11));
	    contentPane.add(lblColorFive);
	    lblColorFive.setBounds(534, 388, 27, 14);

	    // Add an image label for visualization of earthquake intensity, from green spectrum to red
	    JLabel lblNewLabel = new JLabel("");
	    lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Melo\\Downloads\\th.jpg"));
	    lblNewLabel.setBounds(414, 382, 264, 7);
	    contentPane.add(lblNewLabel);

	    // Create a panel for displaying the summary information
	    panelSummary = new JPanel();
	    panelSummary.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	    panelSummary.setBounds(678, 0, 676, 45);
	    contentPane.add(panelSummary);
	    panelSummary.setLayout(null);

	    // Label for displaying focus point coordinates (summary panel)
	    lblSummaryCoordinates = new JLabel("Focus Point Coordinates: ");
	    lblSummaryCoordinates.setBounds(10, 0, 165, 14);
	    lblSummaryCoordinates.setFont(new Font("Arial", Font.BOLD, 11));
	    lblSummaryCoordinates.setForeground(new Color(29, 29, 31));
	    panelSummary.add(lblSummaryCoordinates);

	    // Label for displaying start time (summary panel)
	    JLabel lblSummaryStartTime = new JLabel("Start Time:");
	    lblSummaryStartTime.setBounds(196, 0, 82, 14);
	    lblSummaryStartTime.setFont(new Font("Arial", Font.BOLD, 11));
	    lblSummaryStartTime.setForeground(new Color(29, 29, 31));
	    panelSummary.add(lblSummaryStartTime);

	    // Label for displaying end time (summary panel)
	    lblSummaryEndTime = new JLabel("End Time: ");
	    lblSummaryEndTime.setFont(new Font("Arial", Font.BOLD, 11));
	    lblSummaryEndTime.setForeground(new Color(29, 29, 31));
	    lblSummaryEndTime.setBounds(325, 0, 74, 14);
	    panelSummary.add(lblSummaryEndTime);

	    // Label for displaying minimum magnitude (summary panel)
	    lblSummaryMinMagnitude = new JLabel("Min. Magnitude:");
	    lblSummaryMinMagnitude.setForeground(new Color(29, 29, 31));
	    lblSummaryMinMagnitude.setFont(new Font("Arial", Font.BOLD, 11));
	    lblSummaryMinMagnitude.setBounds(439, 0, 106, 14);
	    panelSummary.add(lblSummaryMinMagnitude);

	    // Label for displaying maximum magnitude 
	    lblSummaryMaxMagnitude = new JLabel("Max Magnitude:");
	    lblSummaryMaxMagnitude.setForeground(new Color(29, 29, 31));
	    lblSummaryMaxMagnitude.setFont(new Font("Arial", Font.BOLD, 11));
	    lblSummaryMaxMagnitude.setBounds(574, 0, 92, 14);
	    panelSummary.add(lblSummaryMaxMagnitude);

	    // Create a panel for displaying the heatmap chart
	    panelHeatMapChart = new JPanel();
	    panelHeatMapChart.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)))));
	    panelHeatMapChart.setBounds(678, 46, 676, 351);
	    contentPane.add(panelHeatMapChart);
	    panelHeatMapChart.setLayout(new BorderLayout(0, 0));
	    
	    JLabel lblMaxRadius = new JLabel("Max Radius km = 2000");
	    lblMaxRadius.setForeground(new Color(29, 29, 31));
	    lblMaxRadius.setFont(new Font("Arial", Font.PLAIN, 11));
	    lblMaxRadius.setBounds(10, 383, 164, 14);
	    contentPane.add(lblMaxRadius);
	}


	 void getData(String starttime, String endtime, double latitude, double longitude, double minmagnitude, double maxmagnitude, double maxradiuskm) throws ParseException  {

		 try {
	            // Create the API endpoint URL with the provided parameters
	    
	            String apiUrl = "https://earthquake.usgs.gov/fdsnws/event/1/query";
	            String encodedStarttime = URLEncoder.encode(starttime, "UTF-8");
	            String encodedEndtime = URLEncoder.encode(endtime, "UTF-8");
	            String urlParameters = String.format("format=geojson&starttime=%s&endtime=%s&latitude=%s&longitude=%s&minmagnitude=%.1f&maxmagnitude=%.1f&maxradiuskm=%s",
	                    encodedStarttime, encodedEndtime, latitude, longitude, minmagnitude, maxmagnitude, maxradiuskm);

	            URL url = new URL(apiUrl + "?" + urlParameters);

	            // Create an HttpURLConnection object
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("GET");

	            // Get the response code
	            int responseCode = connection.getResponseCode();

	            StringBuilder response = new StringBuilder(); 
	            JsonObject jsonResponse = new JsonObject();
	            // Check if the request was successful (200)
	            if (responseCode == HttpURLConnection.HTTP_OK) {
	                // Create a BufferedReader to read the response
	                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	                String line;

	                // Read the response line by line
	                while ((line = reader.readLine()) != null) {
	                    response.append(line);
	                }
	                reader.close();
	                
	             // Print the response
	                System.out.println(response.toString());

	                // Parse the JSON response and extract the earthquake data
	                Gson gson = new Gson();
	                 jsonResponse = gson.fromJson(response.toString(), JsonObject.class);

	            } else {
	                //System.out.println("Request failed. Response Code: " + responseCode);
	                JOptionPane.showMessageDialog(contentPane, "Request failed. Response Code: " + responseCode);
	            }

	            // Disconnect the connection
	            connection.disconnect();

	     
	            List<Double> latitudes = new ArrayList<>();  // Initialize a list to store latitude values
	            List<Double> longitudes = new ArrayList<>();  // Initialize a list to store longitude values
	            magnitudes = new ArrayList<>();  // Initialize a list to store magnitude values
	            dates = new ArrayList<>();  // Initialize a list to store date values
	            List<Earthquake> earthquakeData = new ArrayList<>();  // Initialize a list to store earthquake data

	            // Check if the "features" field exists in the JSON response
	            if (jsonResponse.has("features")) {
	                // Retrieve the "features" field as a JsonArray
	                JsonArray features = jsonResponse.getAsJsonArray("features");

	                // Extract earthquake data from the "features" array
	                for (int i = 0; i < features.size(); i++) {
	                    JsonObject feature = features.get(i).getAsJsonObject();
	                    JsonObject geometry = feature.getAsJsonObject("geometry");
	                    JsonArray coordinates = geometry.getAsJsonArray("coordinates");

	                    JsonObject properties = feature.getAsJsonObject("properties");

	                    double earthquakeLongitude = coordinates.get(0).getAsDouble();
	                    double earthquakeLatitude = coordinates.get(1).getAsDouble();
	                    magnitude = feature.getAsJsonObject("properties").get("mag").getAsDouble();

	                    // Check if the magnitude is within the specified range
	                    if (magnitude >= minmagnitude && magnitude <= maxmagnitude) {
	                        // Store the magnitude in the list
	                        magnitudes.add(magnitude);
	                    }

	                    // Extract the location from the JSON response
	                    JsonElement placeElement = properties.get("place");
	                    String location = (placeElement != null && !placeElement.isJsonNull()) ? placeElement.getAsString() : null;

	                    // Extract the time from the JSON response
	                    JsonElement timeElement = properties.get("time");
	                    long time = (timeElement != null && !timeElement.isJsonNull()) ? timeElement.getAsLong() : 0;

	                    // Convert the time to ISO 8601 format
	                    Instant instant = Instant.ofEpochMilli(time);
	                    LocalDateTime date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

	                    DateTimeFormatter formatterTime = DateTimeFormatter.ISO_LOCAL_TIME;
	                    DateTimeFormatter formatterDate = DateTimeFormatter.ISO_LOCAL_DATE;
	                    String isoTime = date.format(formatterTime);
	                    String isoDate = date.format(formatterDate);

	                    // Create an Earthquake object with extracted data and add it to the earthquakeData list
	                    Earthquake earthquake = new Earthquake(location, magnitude, isoDate, isoTime);
	                    earthquakeData.add(earthquake);

	                    // Store the latitude, longitude, magnitude, and date in separate lists
	                    latitudes.add(earthquakeLatitude);
	                    longitudes.add(earthquakeLongitude);
	                    magnitudes.add(magnitude);
	                    dates.add(isoDate);
	                }
	            } else {
	                // Handle the case where the "features" field is not present in the JSON response
	                //System.out.println("No 'features' found in the JSON response.");
	                JOptionPane.showMessageDialog(contentPane, "No 'features' found in the JSON response.");
	            }

	            DefaultTableModel model = new DefaultTableModel() {
	                @Override
	                public boolean isCellEditable(int row, int column) {
	                    // Make all cells non-editable
	                    return false;
	                }
	            };

	            // Add columns to the table model
	            model.addColumn("Location");
	            model.addColumn("Magnitude");
	            model.addColumn("Date");
	            model.addColumn("Time");

	            // Add earthquake data to the table model
	            for (Earthquake earthquake : earthquakeData) {
	                Object[] rowData = { earthquake.getLocation(), earthquake.getMagnitude(), earthquake.getDate(), earthquake.getTime() };
	                model.addRow(rowData);
	            }

	            // Create a JTable with the table model
	            JTable table = new JTable(model);

	            // Set the table as the viewport view for the JScrollPane
	            scrlpaneEarthquakeResult.setViewportView(table);


            
            

	         // Create a dataset to hold magnitude and date values
	            XYSeriesCollection dataset = new XYSeriesCollection();
	            XYSeries series = new XYSeries("mean magnitudes for the date");

	            // Create a map to store magnitudes grouped by date
	            Map<String, List<Double>> dateMagnitudeMap = new HashMap<>();

	            // Group magnitudes by date
	            for (int i = 0; i < dates.size(); i++) {
	                String date = dates.get(i);
	                double magnitude = magnitudes.get(i);

	                if (dateMagnitudeMap.containsKey(date)) {
	                    dateMagnitudeMap.get(date).add(magnitude);
	                } else {
	                    List<Double> magnitudesList = new ArrayList<>();
	                    magnitudesList.add(magnitude);
	                    dateMagnitudeMap.put(date, magnitudesList);
	                }
	            }

	            int index = 0;
	            // Calculate mean magnitude for each date and add it to the series
	            for (Map.Entry<String, List<Double>> entry : dateMagnitudeMap.entrySet()) {
	                String date = entry.getKey();
	                List<Double> magnitudesList = entry.getValue();

	                double sum = 0.0;
	                for (double magnitude : magnitudesList) {
	                    sum += magnitude;
	                }
	                double meanMagnitude = sum / magnitudesList.size();

	                series.add(index, meanMagnitude);

	                index++;
	            }

	            // Add the series to the dataset
	            dataset.addSeries(series);

	            // Create a scatter plot chart
	            JFreeChart chart = ChartFactory.createScatterPlot("Scatter Plot", "Date", "Magnitude", dataset,
	                    PlotOrientation.VERTICAL, true, false, false);

	            // Set the categories (dates) as the domain axis
	            String[] categories = dateMagnitudeMap.keySet().toArray(new String[0]);
	            SymbolAxis domainAxis = new SymbolAxis("Date", categories);
	            domainAxis.setTickLabelFont(domainAxis.getTickLabelFont().deriveFont(10f));
	            domainAxis.setLabelFont(domainAxis.getLabelFont().deriveFont(12f));
	            chart.getXYPlot().setDomainAxis(domainAxis);

	            // Create a chart panel and add it to the panelHeatMapChart
	            ChartPanel chartPanel = new ChartPanel(chart);
	            panelHeatMapChart.add(chartPanel);



           //panelHeatMapChart.add(new JLabel(new ImageIcon(image)));
           
           

            // Set the color scheme
            //heatmap.setColorScheme(ColorSchemes.Jet);

            
            //System.out.println(table.getModel().getRowCount());


	            
	         // Create a TileFactoryInfo object and a DefaultTileFactory using OSMTileFactoryInfo
	            TileFactoryInfo info1 = new OSMTileFactoryInfo();
	            DefaultTileFactory tileFactory1 = new DefaultTileFactory(info1);

	            // Create a JXMapViewer
	            JXMapViewer jxMapViewer = new JXMapViewer();
	            jxMapViewer.setTileFactory(tileFactory1);

	            // Set the address location and zoom level of the map viewer
	            GeoPosition geo = new GeoPosition(latitude, longitude);
	            jxMapViewer.setAddressLocation(geo);
	            jxMapViewer.setZoom(15);

	            // Set the bounds of the map viewer
	            jxMapViewer.setBounds(10, 75, 633, 359);

	            // Add mouse listeners and a mouse wheel listener to enable pan and zoom functionality
	            MouseInputListener pan = new PanMouseInputListener(jxMapViewer);
	            jxMapViewer.addMouseListener(pan);
	            jxMapViewer.addMouseMotionListener(pan);
	            jxMapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(jxMapViewer));

	            // Create a list of CustomWaypoint markers
	            List<CustomWaypoint> markers = new ArrayList<>();
	            for (int i = 0; i < latitudes.size(); i++) {
	                double latitude1 = latitudes.get(i);
	                double longitude1 = longitudes.get(i);
	                double magnitude = magnitudes.get(i);
	                GeoPosition position1 = new GeoPosition(latitude1, longitude1);
	                CustomWaypoint marker = new CustomWaypoint(position1, magnitude);
	                markers.add(marker);
	            }

	            // Create a set of CustomWaypoints
	            Set<CustomWaypoint> waypointSet = new HashSet<>(markers);

	            // Create a WaypointPainter and set the waypoints and renderer
	            WaypointPainter<CustomWaypoint> painter = new WaypointPainter<>();
	            painter.setWaypoints(waypointSet);
	            CustomWaypointRenderer waypointRenderer = new CustomWaypointRenderer();
	            painter.setRenderer(waypointRenderer);

	            // Set the painter as the overlay painter for the map viewer
	            jxMapViewer.setOverlayPainter(painter);

	            // Explicitly call paintWaypoint for each waypoint to render them
	            Graphics2D g = (Graphics2D) resultPanel.getGraphics();
	            for (CustomWaypoint waypoint : waypointSet) {
	                waypointRenderer.paintWaypoint(g, jxMapViewer, waypoint);
	            }

	            // Add the map viewer to the result panel
	            resultPanel.add(jxMapViewer);

				
				
	            // Create and configure JLabel for displaying the coordinates
	            lblSummaryResultCoordinates = new JLabel(latitude + ", " + longitude);
	            lblSummaryResultCoordinates.setFont(new Font("Arial", Font.PLAIN, 11));
	            lblSummaryResultCoordinates.setForeground(new Color(29, 29, 31));
	            lblSummaryResultCoordinates.setBounds(10, 20, 180, 14);
	            panelSummary.add(lblSummaryResultCoordinates);

	            // Create and configure JLabel for displaying the start time
	            lblSummaryResultStartTime = new JLabel(starttime);
	            lblSummaryResultStartTime.setFont(new Font("Arial", Font.PLAIN, 11));
	            lblSummaryResultStartTime.setForeground(new Color(29, 29, 31));
	            lblSummaryResultStartTime.setBounds(195, 20, 116, 14);
	            panelSummary.add(lblSummaryResultStartTime);

	            // Create and configure JLabel for displaying the end time
	            lblSummaryResultEndTime = new JLabel(endtime);
	            lblSummaryResultEndTime.setFont(new Font("Arial", Font.PLAIN, 11));
	            lblSummaryResultEndTime.setForeground(new Color(29, 29, 31));
	            lblSummaryResultEndTime.setBounds(325, 20, 95, 14);
	            panelSummary.add(lblSummaryResultEndTime);

	            // Create and configure JLabel for displaying the minimum magnitude
	            lblSummaryResultMinMagnitude = new JLabel("" + minmagnitude);
	            lblSummaryResultMinMagnitude.setFont(new Font("Arial", Font.PLAIN, 11));
	            lblSummaryResultMinMagnitude.setForeground(new Color(29, 29, 31));
	            lblSummaryResultMinMagnitude.setBounds(439, 20, 116, 14);
	            panelSummary.add(lblSummaryResultMinMagnitude);

	            // Create and configure JLabel for displaying the maximum magnitude
	            lblSummaryResultMaxMagnitude = new JLabel("" + maxmagnitude);
	            lblSummaryResultMaxMagnitude.setFont(new Font("Arial", Font.PLAIN, 11));
	            lblSummaryResultMaxMagnitude.setForeground(new Color(29, 29, 31));
	            lblSummaryResultMaxMagnitude.setBounds(574, 20, 92, 14);
	            panelSummary.add(lblSummaryResultMaxMagnitude);


	        } catch (IOException e) {
	        	JOptionPane.showMessageDialog(null, "An error occured during the I/O Exception: " + e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);}
	        }
	 

	 
	 /*The CustomWaypointRenderer class is responsible for rendering custom waypoints on the map, 
	  * while the CustomWaypoint class represents a single waypoint with a position and magnitude*/
	 public class CustomWaypointRenderer implements WaypointRenderer<CustomWaypoint> {
		    @Override
		    public void paintWaypoint(Graphics2D g, JXMapViewer mapViewer, CustomWaypoint waypoint) {
		        // Get the waypoint position and magnitude
		        GeoPosition position = waypoint.getPosition();
		        double magnitude = waypoint.getMagnitude();

		        // Convert the waypoint position to pixel coordinates
		        Point2D point = mapViewer.getTileFactory().geoToPixel(position, mapViewer.getZoom());
		        int x = (int) point.getX();
		        int y = (int) point.getY();
		        

		        // Assign a color based on the magnitude
		        Color color = waypoint.getColorAssignedWithMagnitude();

		        // Draw a circle at the waypoint location
		        g.setColor(color);
		        int circleSize = 10;
		        int circleX = x - circleSize / 2;
		        int circleY = y - circleSize / 2;
		        g.fillOval(circleX, circleY, circleSize, circleSize);
		    }
		}

	 
	    //clarifies how the color of the waypoint is assigned based on the magnitude range. 
		public class CustomWaypoint implements Waypoint {
		    private GeoPosition position;
		    private double magnitude;

		    public CustomWaypoint(GeoPosition position, double magnitude) {
		        this.position = position;
		        this.magnitude = magnitude;
		    }

		    @Override
		    public GeoPosition getPosition() {
		        return position;
		    }

		    public double getMagnitude() {
		        return magnitude;
		    }

		    public Color getColorAssignedWithMagnitude() {
		        double magnitude = getMagnitude();

		        // Assign a color based on the magnitude range
		        if (magnitude < 2.0) {
		            return Color.GREEN;
		        } else if (magnitude < 5.0) {
		            return Color.YELLOW;
		        } else {
		            return Color.RED;
		        }
		    }
		}
}


