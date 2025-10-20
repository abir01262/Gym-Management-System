package com.gymapp;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class UI {
    private UI() {}

    public static void showCoachDashboard() { open("/com/gymapp/coach_dashboard.fxml", "Coach Dashboard"); }
    public static void showMemberDashboard() { open("/com/gymapp/member_dashboard.fxml", "Member Dashboard"); }

    private static void open(String fxml, String title) {
        Runnable r = () -> {
            try {
                FXMLLoader loader = new FXMLLoader(UI.class.getResource(fxml));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle(title);
                stage.setScene(new Scene(root));
                stage.setResizable(true);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to open " + fxml, e);
            }
        };
        if (Platform.isFxApplicationThread()) r.run(); else Platform.runLater(r);
    }
}
