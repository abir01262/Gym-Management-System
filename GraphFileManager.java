package com.gymapp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class GraphFileManager {

    private static final String GRAPH_FILE = "graph_data.json";
    private static Map<String, Double> monthData = new HashMap<>();

    // Load existing data from file
    static {
        try (FileReader reader = new FileReader(GRAPH_FILE)) {
            Type type = new TypeToken<Map<String, Double>>() {}.getType();
            monthData = new Gson().fromJson(reader, type);
            if (monthData == null) monthData = new HashMap<>();
        } catch (Exception e) {
            monthData = new HashMap<>();
        }
    }

    // Save map to JSON
    private static void saveGraphData() {
        try (FileWriter writer = new FileWriter(GRAPH_FILE)) {
            new Gson().toJson(monthData, writer);
            System.out.println("Graph data saved: " + monthData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Update income data for current month
    public static void updateGraph(double amount) {
        int month = LocalDate.now().getMonthValue();
        String monthKey = String.valueOf(month);

        double currentAmount = monthData.getOrDefault(monthKey, 0.0);
        monthData.put(monthKey, currentAmount + amount);
        saveGraphData();
    }

    // Load chart visually
    public static void loadGraphData(BarChart<String, Number> barChart) {
        barChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total Paid Amounts");

        for (int i = 1; i <= 12; i++) {
            String key = String.valueOf(i);
            double amount = monthData.getOrDefault(key, 0.0);
            series.getData().add(new XYChart.Data<>(getMonthName(i), amount));
        }

        barChart.getData().add(series);
    }

    private static String getMonthName(int monthNumber) {
        return switch (monthNumber) {
            case 1 -> "Jan";
            case 2 -> "Feb";
            case 3 -> "Mar";
            case 4 -> "Apr";
            case 5 -> "May";
            case 6 -> "Jun";
            case 7 -> "Jul";
            case 8 -> "Aug";
            case 9 -> "Sep";
            case 10 -> "Oct";
            case 11 -> "Nov";
            case 12 -> "Dec";
            default -> "";
        };
    }
}


