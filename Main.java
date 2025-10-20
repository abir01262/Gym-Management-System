package com.gymapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.util.Objects;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        SocketBus.start();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        Scene scene = new Scene(loader.load());


        //doesn't work
//        Label test = new Label("Testing font");
//        test.setFont(Font.font("Mighty Sans", 24));
//        System.out.println(test.getFont());



        stage.setTitle("Glitched Muscles - Login");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(e -> {
            try { SocketBus.stop(); } catch (Exception ignore) {}
        });

    }

    public static void main(String[] args) {
        launch();
    }
}

