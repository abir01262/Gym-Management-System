/*
 * NOTE:
 * 1. Make sure `MemberFileManager` and `Member` classes are correctly implemented.
 * 2. The member ID format "MM" + member_data_map.size() may create duplicate IDs if you delete members.
 *    For a more stable system, consider storing the last used ID in a separate file or JSON key.
 * 3. This controller assumes reg_pane1 and reg_pane2 toggle visibility for multi-step registration.
 * 4. Ensure FXML fx:id names exactly match (check Scene Builder carefully).
 */

package com.gymapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MemberRegistrationController implements Initializable {

    @FXML
    private Button backBtn;

    @FXML
    private Button nextBtn_1st;

    @FXML
    private TextArea reg_address;

    @FXML
    private Button reg_complete;

    @FXML
    private DatePicker reg_end;

    @FXML
    private ComboBox<String> reg_genderCombo;

    @FXML
    private TextField reg_name;

    @FXML
    private AnchorPane reg_pane1;

    @FXML
    private AnchorPane reg_pane2;

    @FXML
    private TextField reg_phonenum;

    @FXML
    private Label reg_prompt_label;

    @FXML
    private ComboBox<String> reg_scheduleCombo;

    @FXML
    private DatePicker reg_start;

    // Data storage
    private HashMap<String, Member> member_data_map = new HashMap<>();
    private HashMap<String, Payment> payment_data_map = new HashMap<>();

    // Member data
    private String member_id = "";
    private String member_name = "";
    private String member_address = "";
    private String member_phoneNum = "";
    private String member_gender = "";
    private String member_schedule = "";
    private String member_start = "";
    private String member_end = "";
    private String member_status = "unpaid";
    private String member_coach = "";

    // ComboBox lists
    private final String[] genderList = {"Male", "Female"};
    private final String[] scheduleList = {
            "7AM - 9AM", "8AM - 10AM", "9AM - 11AM",
            "10AM - 12PM", "4PM - 6PM", "5PM - 7PM", "6PM - 8PM"
    };

    // Called automatically when FXML is loaded
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reg_genderCombo.setItems(FXCollections.observableArrayList(genderList));
        reg_scheduleCombo.setItems(FXCollections.observableArrayList(scheduleList));

        // Initialize visible pane
        reg_pane1.setVisible(true);
        reg_pane2.setVisible(false);
    }

    @FXML
    void nextBtn_method(ActionEvent event) {
        member_data_map = MemberFileManager.loadMembers();
        payment_data_map = PaymentFileManager.loadPayments();

        // Auto-generate ID with leading zeros (e.g., MM01, MM02)
        int nextId = member_data_map.size() + 1;
        member_id = String.format("MM%02d", nextId);

        member_name = reg_name.getText().trim();
        member_address = reg_address.getText().trim();
        member_phoneNum = reg_phonenum.getText().trim();

        while (member_data_map.containsKey(member_id)){
            nextId ++;
            member_id = String.format("MM%02d", nextId);
        }
        if (!member_name.isEmpty() && !member_address.isEmpty() && !member_phoneNum.isEmpty()) {
            reg_pane1.setVisible(false);
            reg_pane2.setVisible(true);
        } else {
            showAlert("Failed", "Please fill all the required fields before proceeding!");
        }
    }


    private ObservableList<Payment> payment_list = FXCollections.observableArrayList();
    @FXML
    void reg_finishBtn_method(ActionEvent event) {
        member_gender = reg_genderCombo.getValue() == null ? "" : reg_genderCombo.getValue();
        member_schedule = reg_scheduleCombo.getValue() == null ? "" : reg_scheduleCombo.getValue();
        member_start = reg_start.getValue() == null ? "" : reg_start.getValue().toString();
        member_end = reg_end.getValue() == null ? "" : reg_end.getValue().toString();

        if (member_gender.isEmpty() || member_schedule.isEmpty() || member_start.isEmpty() || member_end.isEmpty()) {
            showAlert("Failed to Register", "Please fill all the information before completing registration.");
        } else {

            Member m = new Member(member_id, member_name, member_address, member_gender, member_phoneNum,
                    member_schedule,member_start, member_end, member_status);

            member_data_map.put(member_id, m);
            MemberFileManager.saveMembers(member_data_map);

            showAlert("Success", "Member registered successfully!\nMember ID: " + member_id);


            payment_data_map.put(m.getMember_id(), new Payment(m.getMember_id(),
                    m.getMember_name(),
                    m.getMember_start(),
                    m.getMember_end(),
                    m.getMember_status(),
                    calculateTotalDue(m.getMember_start(),m.getMember_end()))
            );
            PaymentFileManager.savePayments(payment_data_map);

            try {
                SocketBus.publish("app:data:changed","Members,Payment");
            }catch (Exception ignore){

            }

            clearFields();
            backToReg_pane1_method(null); // Reset UI to start pane
        }
    }


    @FXML
    void backToReg_pane1_method(ActionEvent event) {
        reg_pane2.setVisible(false);
        reg_pane1.setVisible(true);
    }

    @FXML
    private void backBtn_method(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    // Helper methods
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        reg_name.clear();
        reg_address.clear();
        reg_phonenum.clear();
        reg_genderCombo.getSelectionModel().clearSelection();
        reg_scheduleCombo.getSelectionModel().clearSelection();
        reg_start.setValue(null);
        reg_end.setValue(null);
    }
    private String calculateTotalDue(String startDateStr, String endDateStr) {
        try {
            // Validate input
            if (startDateStr == null || endDateStr == null ||
                    startDateStr.isBlank() || endDateStr.isBlank()) {
                return String.format("%.2f", 0.0);
            }

            LocalDate start = LocalDate.parse(startDateStr);
            LocalDate end = LocalDate.parse(endDateStr);

            // Calculate inclusive days between start and end
            long daysInclusive = ChronoUnit.DAYS.between(start, end) + 1;
            if (daysInclusive < 0) daysInclusive = 0;

            // Each day costs 5.0
            double total = daysInclusive * 5.0;

            return String.format("%.2f", total);
        } catch (Exception e) {
            // Catch parsing or null errors, return zero safely
            e.printStackTrace();
            return String.format("%.2f", 0.0);
        }
    }
}
