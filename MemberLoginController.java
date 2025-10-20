package com.gymapp;

import com.gymapp.net.MessageBus;
import com.gymapp.net.Role;
import com.gymapp.net.SessionContext;
import com.gymapp.net.SocketClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MemberLoginController {
    @FXML
    private TextField memberIdField;
    @FXML private PasswordField passwordField;

    @FXML private Button loginButton;
    @FXML private Button member_registerBtn;

    @FXML
    private Button backBtn;

    @FXML
    private void member_registerBtn_method(ActionEvent event){

        try {
            Parent root = FXMLLoader.load(getClass().getResource("member_register.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setTitle("Member Registration");
            stage.setScene(scene);
            stage.show();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleLogin() throws Exception{
        String id = memberIdField.getText();
        String pw = passwordField.getText();
        if (!"a".equals(pw)) return;

        String name = com.gymapp.MemberFileManager.getNameById(id);
        if (name == null) return;

        SessionContext.role = Role.MEMBER;
        SessionContext.id = id;
        SessionContext.name = name;
        SessionContext.client = new SocketClient();
        SessionContext.client.connect(Role.MEMBER, id, name, MessageBus::dispatch);

        UI.showMemberDashboard();
    }

    @FXML
    private void backBtn_method(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
