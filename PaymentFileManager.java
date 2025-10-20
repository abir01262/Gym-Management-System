package com.gymapp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;

public class PaymentFileManager {


    private static final String FIle_PATH = "Payment.json";
    private static final Gson gson = new Gson();

    public static void savePayments(HashMap<String,Payment> paymentMap){
        try(Writer writer = new FileWriter(FIle_PATH)){

            gson.toJson(paymentMap,writer);

            try { SocketBus.publish("app:data:changed", "Payment"); } catch (Exception ignore) {}

            System.out.printf("payment data saved");
        }catch (IOException e){
            System.out.println("payment data failed");
            e.printStackTrace();
        }
    }

    public static HashMap<String , Payment> loadPayments(){
        File file = new File(FIle_PATH);
        if (!file.exists() || file.length() ==0){
            return new HashMap<>();
        }
        try (Reader reader = new FileReader(FIle_PATH)){
            Type type = new TypeToken<HashMap<String, Payment>>(){}.getType();
            HashMap<String, Payment> map =gson.fromJson(reader, type);
            return (map != null) ? map : new HashMap<>();
        }catch (FileNotFoundException e){
            System.out.println("no saved data, starting fresh");
            return new HashMap<>();
        }catch (IOException e){
            System.out.println("kisui pai nai");
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
