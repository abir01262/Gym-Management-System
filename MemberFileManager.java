package com.gymapp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;

public class MemberFileManager {


    private static final String FILE_PATH = "Members.json";
    private static final Gson gson = new Gson();

    public static void saveMembers(HashMap<String, Member> memberMap) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(memberMap, writer);

            try { SocketBus.publish("app:data:changed", "Members"); } catch (Exception ignore) {}

            System.out.println("Member data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, Member> loadMembers() {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() ==0) {
            return new HashMap<>();
        }
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type type = new TypeToken<HashMap<String, Member>>() {
            }.getType();
            HashMap<String, Member> map = gson.fromJson(reader, type);
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
        HashMap<String, Member> member_data_map = loadMembers();
        Member member = member_data_map.get(id);
        return (member != null) ? member.getMember_name() : null;
    }

}
