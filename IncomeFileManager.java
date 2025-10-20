package com.gymapp;


import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

public class IncomeFileManager {
    private static final String FILE_PATH = "Income.json";
    private static final Gson gson = new Gson();
    private static double totalIncome = 0.0;

    // Load the income value from file
    public static void loadIncome() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            HashMap<String, Object> map = gson.fromJson(reader, HashMap.class);
            if (map != null && map.get("total_income") != null) {
                totalIncome = ((Number) map.get("total_income")).doubleValue();
            }
        } catch (IOException e) {
            // File not found or empty -> keep totalIncome = 0.0
        }
    }

    // Save current total income to file
    public static void saveIncome() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("total_income", totalIncome);
            gson.toJson(map, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add to income and save automatically
    public static void addIncome(double amount) {
        totalIncome += amount;
        saveIncome();
        System.out.println(amount+"added");
    }

    public static double getTotalIncome() {
        return totalIncome;
    }

    public static void setTotalIncome(double value) {
        totalIncome = value;
        saveIncome();
    }
}

