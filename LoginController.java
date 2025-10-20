package com.gymapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private void openAdminLogin(ActionEvent event) {
        openNewWindow("AdminLogin.fxml", "Admin Login");
    }

    @FXML
    private void openTrainerLogin(ActionEvent event) {
        openNewWindow("coach_login.fxml", "Coach Login");
    }

    @FXML
    private void openMemberLogin(ActionEvent event) {
        openNewWindow("MemberLogin.fxml", "Member Login");
    }

    private void openNewWindow(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = new Scene(loader.load());

            Stage newStage = new Stage();
            newStage.setTitle(title);
            newStage.setScene(scene);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
