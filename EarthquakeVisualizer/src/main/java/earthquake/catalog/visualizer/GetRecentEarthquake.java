package earthquake.catalog.visualizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GetRecentEarthquake extends JPanel {
    public JScrollPane createTable() {
        JScrollPane scrollPane = null;

        try {
            // Create a URL object for the earthquake data source
            URL url = new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.geojson");

            // Open a connection to the URL and set the request method
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read the response from the connection
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            // Read line by line and append to the response StringBuilder
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            

            // Close the reader and disconnect the connection
            reader.close();
            connection.disconnect();

            // Parse the JSON response and extract the earthquake data
            Gson gson = new Gson();
            JsonObject jsonResponse = gson.fromJson(response.toString(), JsonObject.class);
            JsonArray features = jsonResponse.getAsJsonArray("features");

            // Create a list to store earthquake data
            List<Earthquake> earthquakeData = new ArrayList<>();

            // Iterate over the features array and extract earthquake properties
            for (int i = 0; i < features.size(); i++) {
                JsonObject feature = features.get(i).getAsJsonObject();
                JsonObject properties = feature.getAsJsonObject("properties");

                // Extract relevant earthquake information from the properties
                String location = properties.get("place").getAsString();
                JsonElement magnitudeElement = properties.get("mag");
                double magnitude = magnitudeElement.isJsonNull() ? 0.0 : magnitudeElement.getAsDouble();
                long time = properties.get("time").getAsLong();

                // Convert the time to ISO 8601 format
                Instant instant = Instant.ofEpochMilli(time);
                LocalDateTime date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

                DateTimeFormatter formatterTime = DateTimeFormatter.ISO_LOCAL_TIME;
                DateTimeFormatter formatterDate = DateTimeFormatter.ISO_LOCAL_DATE;
                String isoTime = date.format(formatterTime);
                String isoDate = date.format(formatterDate);

                // Create an Earthquake object and add it to the earthquakeData list
                Earthquake earthquake = new Earthquake(location, magnitude, isoDate, isoTime);
                earthquakeData.add(earthquake);
            }

            // Create a DefaultTableModel to store earthquake data for the JTable
            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Make all cells non-editable
                    return false;
                }
            };

            // Add column headers to the table model
            model.addColumn("Location");
            model.addColumn("Magnitude");
            model.addColumn("Date");
            model.addColumn("Time");

            // Add earthquake data to the table model
            for (Earthquake earthquake : earthquakeData) {
                Object[] rowData = { earthquake.getLocation(), earthquake.getMagnitude(), earthquake.getDate(), earthquake.getTime() };
                model.addRow(rowData);
            }

            // Create a JTable with the table model and wrap it in a JScrollPane
            JTable table = new JTable(model);
            scrollPane = new JScrollPane(table);

        }catch (IOException e) {
        	JOptionPane.showMessageDialog(null, "Unable to retrieve data, check your ineternet connection: " + e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);}
    	
            // Print the stack trace if an exception occurs
            //e.printStackTrace();
        	
        

        return scrollPane;
    }
}
