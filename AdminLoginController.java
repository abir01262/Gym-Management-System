//package com.gymapp;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.PasswordField;
//
//import java.util.Objects;
//
//public class AdminLoginController {
//    @FXML
//    private PasswordField passwordField;
//
//    @FXML
//    private Button ad_loginBtn;
//
//    private String ad_ps = "t#B7&q!Z@x$M9w"; //fixed pass
//
//    @FXML
//    private void openDash(ActionEvent event) throws Exception {
//         String givenPass = passwordField.getText().trim();
//        if ( givenPass.isEmpty() ){
//            showAlert("Error", "Please type the password!");
//            return;
//        } else if (!Objects.equals(ad_ps, givenPass)) {
//            showAlert("Wrong Password!","Try Again!");
//            return;
//        }
//        System.out.printf("right pass");
//        //load the dashboard
//
//    }
//
//
//    private void showAlert(String title, String content) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(content);
//        alert.showAndWait();
//    }
//}


package com.gymapp;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AdminLoginController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button ad_loginBtn;

    private final String serverSidePassword = "a"; //t#B7&q!Z@x$M9w

    private MockAuthServer authServer;
    private int serverPort;

    @FXML
    private void initialize() {
        try {
            authServer = new MockAuthServer(0, serverSidePassword);
            authServer.start();
            serverPort = authServer.getPort();
            System.out.println("MockAuthServer started on port " + serverPort);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (authServer != null) authServer.shutdown();
            }));
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Server Error", "Could not start mock auth server: " + e.getMessage());
        }
    }

    @FXML
    private void openDash(ActionEvent event) {
        String givenPass = passwordField.getText().trim();

        if (givenPass.isEmpty()) {
            showAlert("Error", "Please type the password!");
            return;
        }

        ad_loginBtn.setDisable(true);

        new Thread(() -> {
            boolean isValid;
            try (Socket socket = new Socket("127.0.0.1", serverPort);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                out.println("VALIDATE:" + givenPass);

                String serverResponse = in.readLine();
                isValid = "OK".equals(serverResponse);

            } catch (IOException e) {
                String err = e.getMessage();
                Platform.runLater(() -> {
                    showAlert("Network Error", "Could not reach auth server: " + err);
                    ad_loginBtn.setDisable(false);
                });
                return;
            }

            Platform.runLater(() -> {
                if (!isValid) {
                    showAlert("Wrong Password!", "Try Again!");
                    ad_loginBtn.setDisable(false);
                } else {
                    System.out.println("Correct password! Opening dashboard...");
                    try {
                        openDashboardWindow();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        showAlert("Error", "Could not open dashboard: " + ex.getMessage());
                        ad_loginBtn.setDisable(false);
                    }
                }
            });
        }).start();
    }

    private void openDashboardWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_dashboard.fxml"));
        Scene scene = new Scene(loader.load());

        Stage dashboardStage = new Stage();
        dashboardStage.setTitle("Admin Dashboard");
        dashboardStage.setScene(scene);
        dashboardStage.show();

        Stage currentStage = (Stage) ad_loginBtn.getScene().getWindow();
        currentStage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private static class MockAuthServer extends Thread {
        private final ServerSocket serverSocket;
        private final ExecutorService pool = Executors.newCachedThreadPool();
        private volatile boolean running = true;
        private final String realPassword;

        MockAuthServer(int port, String realPassword) throws IOException {
            this.serverSocket = new ServerSocket(port);
            this.realPassword = realPassword;
        }

        int getPort() {
            return serverSocket.getLocalPort();
        }

        @Override
        public void run() {
            try {
                while (running) {
                    Socket client = serverSocket.accept();
                    pool.execute(() -> handleClient(client));
                }
            } catch (IOException e) {
                if (running) e.printStackTrace();
            }
        }

        private void handleClient(Socket clientSocket) {
            try (Socket sock = clientSocket;
                 BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                 PrintWriter out = new PrintWriter(sock.getOutputStream(), true)) {

                String line = in.readLine();
                if (line != null && line.startsWith("VALIDATE:")) {
                    String pass = line.substring("VALIDATE:".length());
                    if (realPassword.equals(pass)) {
                        out.println("OK");
                    } else {
                        out.println("FAIL");
                    }
                } else {
                    out.println("FAIL");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("couldn't verify");
            }
        }

        void shutdown() {
            running = false;
            try {
                serverSocket.close();
            } catch (IOException ignored) {}
            pool.shutdownNow();
        }
    }
    @FXML
    private void backBtn_method(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}

