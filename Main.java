import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Read CSV file and create dataset
        CategoryDataset dataset = createDataset("sportsData.csv");

        // Create a bar chart
        JFreeChart barChart = ChartFactory.createBarChart(
                "Injuries by Year and Scenario",
                "Year",
                "Injuries",
                dataset
        );

        // Display the chart in a JFrame
        JFrame frame = new JFrame("Injuries Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ChartPanel(barChart));
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static CategoryDataset createDataset(String csvFilePath) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean headerSkipped = false;

            while ((line = reader.readLine()) != null) {
                // Skip the header line
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                // Split the CSV line
                String[] parts = line.split(",");

                // Extract data from CSV
                String year = parts[1];
                String scenario = parts[3];
                String injurytype = parts[2];
                int injuries = Integer.parseInt(parts[5]);

                // Add data to the dataset
                dataset.addValue(injuries, injurytype, year);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return dataset;
    }
}
