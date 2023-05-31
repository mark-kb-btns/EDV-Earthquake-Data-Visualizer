package earthquake.catalog.visualizer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GetCoordinates {
    public JScrollPane createTable() {
        JScrollPane scrollPane = null;

        //Read the CSV file and extract the data
        String csvFile = "C:\\Users\\Melo\\Downloads\\world_country_and_usa_states_latitude_and_longitude_values.csv";
        String line;
        String csvSeparator = ",";

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Country");
        tableModel.addColumn("Latitude");
        tableModel.addColumn("Longitude");
       
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            //Create a DefaultTableModel
            while ((line = br.readLine()) != null) {
                //Populate the DefaultTableModel with the CSV data
                String[] rowData = line.split(csvSeparator);
                tableModel.addRow(rowData);
            }

            //Create a JTable using the DefaultTableModel
            JTable table = new JTable(tableModel);

            //Add the JTable to a JScrollPane
            scrollPane = new JScrollPane(table);

            return scrollPane;

            //Display the JTable in user interface
            //JFrame frame = new JFrame("CSV to JTable Example");
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //frame.add(scrollPane);
            //frame.pack();
            //frame.setVisible(true);
        } catch (IOException e) {
           // e.printStackTrace();
        	JOptionPane.showMessageDialog(null, "Error!", "Exception", JOptionPane.ERROR_MESSAGE);
        }

        return scrollPane;
    }
}
