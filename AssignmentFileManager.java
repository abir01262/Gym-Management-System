package com.gymapp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AssignmentFileManager {

    private static final String FILE_PATH = "assignments.json";
    private static final Gson gson = new Gson();

    public static void saveAssignments(HashMap<String, Assignment> assignmentMap) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(assignmentMap, writer);

            try { SocketBus.publish("app:data:changed", "assignments"); } catch (Exception ignore) {}

            System.out.println("Assignment data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, Assignment> loadAssignments() {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            return new HashMap<>();
        }
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type type = new TypeToken<HashMap<String, Assignment>>() {}.getType();
            HashMap<String, Assignment> map = gson.fromJson(reader, type);
            return (map != null) ? map : new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static boolean isCoachOfMember(String coachId, String memberId){
        HashMap<String, Assignment> assignments = loadAssignments();
        for (Assignment a : assignments.values()) {
            if (a.getCoach_id().equals(coachId) && a.getMember_id().equals(memberId)) {
                return true;
            }
        }
        return false;
    }
    public static String getCoachForMember(String memberId){
        HashMap<String, Assignment> assignments = loadAssignments();
        for (Assignment a : assignments.values()) {
            if (a.getMember_id().equals(memberId)) {
                return a.getCoach_id();
            }
        }
        return null;
    }
    public static java.util.List<String> getMembersForCoach(String coachId){
        HashMap<String, Assignment> assignments = loadAssignments();
        List<String> members = new ArrayList<>();
        for (Assignment a : assignments.values()) {
            if (a.getCoach_id().equals(coachId)) {
                members.add(a.getMember_id());
            }
        }
        return members;
    }
}
