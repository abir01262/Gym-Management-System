package com.gymapp;

import com.gymapp.net.MessageBus;
import com.gymapp.net.Role;
import com.gymapp.net.SessionContext;
import com.gymapp.net.SocketClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CoachLoginController {
    @FXML private TextField coachIdField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button backBtn;
    @FXML private  Button Coach_registerBtn;


    @FXML
    private void handleLogin() throws Exception{
        String id = coachIdField.getText();
        String pw = passwordField.getText();
        if (!"a".equals(pw)) return;

        String name = com.gymapp.CoachFileManager.getNameById(id);
        if (name == null) return;

        SessionContext.role = Role.COACH;
        SessionContext.id = id;
        SessionContext.name = name;
        SessionContext.client = new SocketClient();
        SessionContext.client.connect(Role.COACH, id, name, MessageBus::dispatch);

        UI.showCoachDashboard();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    private void backBtn_method(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
