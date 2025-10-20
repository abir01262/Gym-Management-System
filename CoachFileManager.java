package com.gymapp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;

public class CoachFileManager {


    private static final String FILE_PATH = "coaches.json";
    private static final Gson gson = new Gson();

    public static void saveCoaches(HashMap<String, Coach> coachMap) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(coachMap, writer);

            try { SocketBus.publish("app:data:changed", "coaches"); } catch (Exception ignore) {}


            System.out.println("Coach data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, Coach> loadCoaches() {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() ==0) {
            return new HashMap<>();
        }
            try (Reader reader = new FileReader(FILE_PATH)) {
                Type type = new TypeToken<HashMap<String, Coach>>() {
                }.getType();
                HashMap<String, Coach> map = gson.fromJson(reader, type);
                return (map != null) ? map : new HashMap<>();
            } catch (FileNotFoundException e) {
                System.out.println("No saved data found, starting fresh.");
                return new HashMap<>();
            } catch (IOException e) {
                e.printStackTrace();
                return new HashMap<>();
            }

    }
    public static String getNameById(String id){
        HashMap <String, Coach> coach_data_map =loadCoaches();
        Coach coach = coach_data_map.get(id);
        return (coach != null && coach.getCoach_name() != null) ? coach.getCoach_name() : null;

    }

}
