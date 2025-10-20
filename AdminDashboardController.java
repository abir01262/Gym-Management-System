package com.gymapp;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.security.spec.ECField;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static javafx.application.Application.launch;

public class AdminDashboardController implements Initializable {



    @FXML
    private Button chatBtn;

    @FXML
    private Button close;

    @FXML
    private Button coachesBtn;

    @FXML
    private Button coaches_addBtn;

    @FXML
    private TextArea coaches_address;

    @FXML
    private Button coaches_deleteBtn;

    @FXML
    private ComboBox<?> coaches_gender;

    @FXML
    private TextField coaches_id;

    @FXML
    private TextField coaches_id1;

    @FXML
    private TextField coaches_name;

    @FXML
    private AnchorPane coaches_pane;


    @FXML
    private AnchorPane chat_pane;

    @FXML
    private TextField coaches_phoneNum;

    @FXML
    private Button coaches_resetBtn;

    @FXML
    private ComboBox<?> coaches_status;

    @FXML
    private TableColumn<Coach, String> coaches_t_address;

    @FXML
    private TableColumn<Coach, String> coaches_t_gender;

    @FXML
    private TableColumn<Coach, String> coaches_t_id;

    @FXML
    private TableColumn<Coach, String> coaches_t_name;

    @FXML
    private TableColumn<Coach, String> coaches_t_phoneNumber;

    @FXML
    private TableColumn<Coach, String> coaches_t_status;

    @FXML
    private TableView<Coach> coaches_tableView;

    @FXML
    private TableView<Member> members_tableView;


    @FXML private ComboBox<String> coach_id_comboBox;
    @FXML private ComboBox<String> member_id_comboBox;

    @FXML
    private Button coaches_updateBtn;

    @FXML
    private Button dashboardBtn;

    @FXML
    private Label dashboard_NumOfCoach;

    @FXML
    private Label dashboard_NumOfMem;

    @FXML
    private Label dashboard_income;

    @FXML
    private AnchorPane dashboard_pane;

    @FXML
    private Button log_outBtn;

    @FXML
    private AnchorPane main_pane;

    @FXML
    private Button memberBtn;

    @FXML
    private AnchorPane member_pane;

    @FXML
    private Button members_addBtn;

    @FXML
    private TextArea members_address;

    @FXML
    private Button members_deleteBtn;

    @FXML
    private DatePicker members_endDate;

    @FXML
    private TextField members_id;

    @FXML
    private TextField members_id1;

    @FXML
    private TextField members_name;

    @FXML
    private TextField members_phoneNum;

    @FXML
    private Button members_resetBtn;

    @FXML
    private DatePicker members_startDate;


    @FXML
    private ComboBox<?> members_gender;

    @FXML
    private ComboBox<?> members_schedule;

    @FXML
    private ComboBox<?> members_status;

    @FXML
    private TableColumn<Member , String> members_t_address;

    @FXML
    private TableColumn<Member , String> members_t_end;

    @FXML
    private TableColumn<Member , String> members_t_gender;

    @FXML
    private TableColumn<Member , String> members_t_id;

    @FXML
    private TableColumn <Member, String> members_t_coach;

    @FXML
    private TableColumn<Member , String> members_t_name;

    @FXML
    private TableColumn<Member , String> members_t_phoneNum;

    @FXML
    private TableColumn<Member , String> members_t_schedule;

    @FXML
    private TableColumn<Member , String> members_t_start;

    @FXML
    private TableColumn<Member , String> members_t_status;

    @FXML
    private Button members_updateBtn;

    @FXML
    private Button minimize;

    @FXML
    private Button paymentBtn;

    @FXML
    private Button chat_sendBtn;


    @FXML
    private TextField payment_amount;

    @FXML
    private ComboBox<String> payment_id;

    @FXML
    private Label payment_nameLabel;

    @FXML
    private AnchorPane payment_pane;

    @FXML
    private Button payment_payBtn;

    @FXML
    private TableColumn<Payment, String > payment_t_endDate;

    @FXML
    private TableColumn<Payment, String > payment_t_memberId;

    @FXML
    private TableColumn<Payment, String > payment_t_memberName;

    @FXML
    private TableColumn<Payment, String > payment_t_startDate;

    @FXML
    private TableColumn<Payment, String > payment_t_status;

    @FXML
    private TableColumn<Payment, String > payment_t_totalDue;

    @FXML
    private TableView<Payment> payment_tableView;

    @FXML
    private Label payment_totalDueLabel;


    @FXML
    private BarChart<String, Number> dashboard_graph;
    @FXML
    private CategoryAxis monthAxis;
    @FXML
    private NumberAxis amountAxis;

    @FXML private AnchorPane coach_assign_pane;
    @FXML private AnchorPane coach_add_update_delete_pane;
    @FXML private AnchorPane coach_mainBtns_pane;
    @FXML private AnchorPane coach_table_pane;
    @FXML private AnchorPane coach_delete_pane;

    @FXML
    private TableView<Assignment> assignment_tableView;
    @FXML
    private TableColumn<Assignment, String> assignment_id_col;
    @FXML
    private TableColumn<Assignment, String> assignment_member_col;
    @FXML
    private TableColumn<Assignment, String> assignment_coach_col;

    @FXML
    private Button assign_btn;
    @FXML
    private Button remove_btn;

    @FXML private AnchorPane members_table_pane;
    @FXML private AnchorPane members_add_update_pane;
    @FXML private AnchorPane members_delete_pane;
    @FXML private AnchorPane member_mainBtns_pane;

    @FXML private Button coaches_assignmentBtn;
    @FXML private Button coaches_view_list_paneBtn;
    @FXML private Button coaches_go_to_add_paneBtn;
    @FXML private Button coaches_go_to_update_paneBtn;
    @FXML private Button coaches_go_to_delete_paneBtn;


    @FXML private Button members_view_list_paneBtn;
    @FXML private Button members_go_to_add_paneBtn;
    @FXML private Button members_go_to_update_paneBtn;
    @FXML private Button members_go_to_delete_paneBtn;




    Double total_income=0.0;

    private String gender[] = {"Male", "Female"};
    public void coach_genderlist(){
        ArrayList<String > genderList = new ArrayList<>();
        for (String a : gender){
            genderList.add(a);
        }

        ObservableList listInfo = FXCollections.observableArrayList(genderList);
        coaches_gender.setItems(listInfo);
    }
    public void member_genderlist(){
        ArrayList<String > genderList = new ArrayList<>();
        for (String a : gender){
            genderList.add(a);
        }

        ObservableList listInfo = FXCollections.observableArrayList(genderList);
        members_gender.setItems(listInfo);
    }

    private String coachStatus[] = {"Free (0/3)","Free (1/3)","Free (2/3)","Full (3/3)"};
    public  void coaches_statusList(){
        ArrayList<String> statusList = new ArrayList<>();

        for (String a : coachStatus){
            statusList.add(a);
        }

        ObservableList listInfo = FXCollections.observableArrayList(statusList);
        coaches_status.setItems(listInfo);
    }

    private String memberStatus[] = {"Paid","Unpaid"};
    public  void members_statusList(){
        ArrayList<String> statusList = new ArrayList<>();

        for (String a : memberStatus){
            statusList.add(a);
        }

        ObservableList listInfo = FXCollections.observableArrayList(statusList);
        members_status.setItems(listInfo);

    }
    private String memberSchedule[] = {"7AM - 9AM", "8AM - 10AM","9AM - 11AM","10AM - 12PM","4PM - 6PM","5PM - 7PM","6PM - 8PM"};
    public void members_scheduleList(){
        ArrayList<String> scheduleList = new ArrayList<>();

        for (String a : memberSchedule){
            scheduleList.add(a);
        }

        ObservableList listInfo = FXCollections.observableArrayList(scheduleList);
        members_schedule.setItems(listInfo);
    }


    @FXML
    void dashboardBtn_method(ActionEvent event) {
        try {

            dashboard_pane.setVisible(true);
            member_pane.setVisible(false);
            payment_pane.setVisible(false);
            coaches_pane.setVisible(false);
            chat_pane.setVisible(false);

            mainBtnClicked(event);
        }catch (Exception e){


        }

    }


    @FXML
    void coachesBtn_method(ActionEvent event) {

        try {

            coaches_pane.setVisible(true);
            member_pane.setVisible(false);
            payment_pane.setVisible(false);
            dashboard_pane.setVisible(false);
            chat_pane.setVisible(false);

            mainBtnClicked(event);
        }catch (Exception e){
            System.out.println("coach btn method exception");
        }

    }


    private HashMap<String, Coach> coach_data_map = new HashMap<>();
    private ObservableList<Coach> coach_list=FXCollections.observableArrayList();

    private void set_coach_table(){
        coaches_t_id.setCellValueFactory(new PropertyValueFactory<>("coach_id"));
        coaches_t_name.setCellValueFactory(new PropertyValueFactory<>("coach_name"));
        coaches_t_address.setCellValueFactory(new PropertyValueFactory<>("coach_address"));
        coaches_t_gender.setCellValueFactory(new PropertyValueFactory<>("coach_gender"));
        coaches_t_phoneNumber.setCellValueFactory(new PropertyValueFactory<>("coach_phone_num"));
        coaches_t_status.setCellValueFactory(new PropertyValueFactory<>("coach_status"));
    }
    private void add_coach(Coach c){

        coach_data_map = CoachFileManager.loadCoaches();

        if (!coach_data_map.containsKey(c.getCoach_id())){
            coach_data_map.put(c.getCoach_id(),c);
            coach_list.add(c);
            CoachFileManager.saveCoaches(coach_data_map);
            dashboard_NumOfCoach.setText(String.valueOf(coach_data_map.size()));
            populateCoachAndMemberComboBoxes();
        }else {
            showAlert("Cannot add the user!","User with same ID already exists");

        }

        try {
            SocketBus.publish("app:data:changed","coaches");
        }catch (Exception ignore){

        }

    }
    private void delete_coach(String coachID) {
        // Reload latest data
        coach_data_map       = CoachFileManager.loadCoaches();
        assignment_data_map  = AssignmentFileManager.loadAssignments();
        member_data_map      = MemberFileManager.loadMembers();

        // 1) Remove the coach from the coach map/list
        Coach removed = coach_data_map.remove(coachID);
        if (removed == null) {
            showAlert("Failed to delete", "User with " + coachID + " doesn't exist");
            return;
        }
        CoachFileManager.saveCoaches(coach_data_map);
        coach_list.removeIf(c -> coachID.equals(c.getCoach_id()));
        coaches_tableView.refresh();

        // 2) Remove all assignments that reference this coach
        //    and collect affected member IDs
        List<String> affectedMembers = new ArrayList<>();
        Iterator<Map.Entry<String, Assignment>> it = assignment_data_map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Assignment> e = it.next();
            Assignment a = e.getValue();
            if (coachID.equals(a.getCoach_id())) {
                affectedMembers.add(a.getMember_id());
                it.remove();
            }
        }
        AssignmentFileManager.saveAssignments(assignment_data_map);

        // Refresh assignment observable/table from the updated map
        assignment_list.setAll(assignment_data_map.values());
        assignment_tableView.refresh();

        // 3) For every affected member, clear their member_coach and persist
        for (String mid : affectedMembers) {
            Member m = member_data_map.get(mid);
            if (m != null) m.setMember_coach("");
        }
        MemberFileManager.saveMembers(member_data_map);

        // Refresh members observable/table from the updated map
        member_list.setAll(member_data_map.values());
        members_tableView.refresh();

        // 4) Recompute coach statuses and refresh UI helpers
        updateCoachStatuses();
        populateCoachAndMemberComboBoxes();

        // 5) Update dashboard count (optional but consistent with add flow)
        dashboard_NumOfCoach.setText(String.valueOf(coach_data_map.size()));

        showAlert("Successful", "The coach and related assignments have been deleted!");

        try {
            SocketBus.publish("app:data:changed", "coaches,assignments,Members");
        } catch (Exception ignore) { }
    }

    private void update_coach(String coachID ,String NEWcoachName, String NEWcoachAddress, String NewcoachGender, String NEWcoachPhoneNum, String NEWcoachStatus){

        coach_data_map = CoachFileManager.loadCoaches();

        Coach c= coach_data_map.get(coachID);
        if (c != null){
            if (NEWcoachName != null && !NEWcoachName.isEmpty()) {
                c.setCoach_name(NEWcoachName);
            }
            if (NEWcoachAddress !=null && !NEWcoachAddress.isEmpty()) {
                c.setCoach_address(NEWcoachAddress);
            }
            if (NewcoachGender !=null && !NewcoachGender.isEmpty()){
                c.setCoach_gender(NewcoachGender);
            }
            if (NEWcoachPhoneNum !=null && !NEWcoachPhoneNum.isEmpty()) {
                c.setCoach_phone_num(NEWcoachPhoneNum);
            }
            if (NEWcoachStatus != null && !NEWcoachStatus.isEmpty()) {
                c.setCoach_status(NEWcoachStatus);
            }
            CoachFileManager.saveCoaches(coach_data_map);
            coaches_tableView.refresh();


            try {
                SocketBus.publish("app:data:changed","coaches");
            }catch (Exception ignore){

            }
        }
        else {
            showAlert("Failed to update!","please type and ID and data to update.");
        }
    }

    @FXML
    void coaches_addBtn_method(ActionEvent event){

        String coach_id=coaches_id.getText();
        String coach_name = coaches_name.getText();
        String coach_Address = coaches_address.getText();
        String coach_gender = (String) coaches_gender.getValue();
        String coach_phoneNum = coaches_phoneNum.getText();
        String coach_status = (String) coaches_status.getValue();

        if (coach_id ==null || coach_name ==null || coach_Address ==null || coach_gender ==null || coach_phoneNum ==null || coach_status ==null  ){
            showAlert("Failed to add the user!","Please fill all the information.");
        }else {
            add_coach(new Coach(coach_id, coach_name, coach_Address, coach_gender, coach_phoneNum, coach_status));


            showAlert("Successful!","New Coach has been added!");
        }
        clear_coach_fields();
    }

    @FXML
    void coaches_updateBtn_method(ActionEvent event){
        String id = coaches_id.getText();
        String name = coaches_name.getText();
        String address = coaches_address.getText();
        String gender = (String) coaches_gender.getValue();
        String phoneNum = coaches_phoneNum.getText();
        String status = (String) coaches_status.getValue();

        if (coach_data_map.containsKey(id)) {
            update_coach(id, name, address, gender, phoneNum, status);
            showAlert("Successful!", "The Coach has been updated!");
        }else {
            showAlert("Unsuccessful!","The "+id+" don't exist in the system!");
        }
        clear_coach_fields();
    }

    @FXML
    void coaches_deleteBtn_method(ActionEvent event){
        String id = coaches_id1.getText();
        if (coach_data_map.containsKey(id)) {
            delete_coach(id);
            showAlert("Successful","The coach has been deleted!");
        }else {
            showAlert("Failed to delete","User with "+id+" doesn't exist");
        }
    }

    @FXML
    void coaches_resetBtn_method(ActionEvent event){
        clear_coach_fields();
    }

    private void clear_coach_fields(){
        coaches_id.clear();
        coaches_name.clear();
        coaches_address.clear();
        coaches_gender.setValue(null);
        coaches_phoneNum.clear();
        coaches_status.setValue(null);
    }


    @FXML
    void coaches_view_listBtn_method(ActionEvent event){

        try{
            coach_table_pane.setVisible(true);
            coach_add_update_delete_pane.setVisible(false);
            coach_assign_pane.setVisible(false);
            coach_delete_pane.setVisible(false);

            coachBtnClicked(event);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void coaches_go_to_add_paneBtn_method(ActionEvent event){

        try{
            coach_table_pane.setVisible(false);
            coach_add_update_delete_pane.setVisible(true);
            coach_assign_pane.setVisible(false);
            coach_delete_pane.setVisible(false);

            coaches_addBtn.setVisible(true);
            coaches_updateBtn.setVisible(false);


            coachBtnClicked(event);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @FXML
    void coaches_go_to_update_paneBtn_method(ActionEvent event){

        try{
            coach_table_pane.setVisible(false);
            coach_add_update_delete_pane.setVisible(true);
            coach_assign_pane.setVisible(false);
            coach_delete_pane.setVisible(false);

            coaches_addBtn.setVisible(false);
            coaches_updateBtn.setVisible(true);


            coachBtnClicked(event);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @FXML
    void coaches_go_to_delete_paneBtn_method(ActionEvent event){

        try{
            coach_table_pane.setVisible(false);
            coach_add_update_delete_pane.setVisible(false);
            coach_assign_pane.setVisible(false);
            coach_delete_pane.setVisible(true);

            coachBtnClicked(event);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @FXML
    void coaches_assignmentBtn_method(ActionEvent event){

        try {
            coach_table_pane.setVisible(false);
            coach_add_update_delete_pane.setVisible(false);
            coach_assign_pane.setVisible(true);
            coach_delete_pane.setVisible(false);

            coachBtnClicked(event);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private HashMap<String, Assignment> assignment_data_map;
    private ObservableList<Assignment> assignment_list;




    private void set_assignment_table() {
        assignment_id_col.setCellValueFactory(new PropertyValueFactory<>("assignment_id"));
        assignment_member_col.setCellValueFactory(new PropertyValueFactory<>("member_id"));
        assignment_coach_col.setCellValueFactory(new PropertyValueFactory<>("coach_id"));
    }
    private void updateCoachStatuses() {

        assignment_data_map = AssignmentFileManager.loadAssignments();

        HashMap<String, Integer> coachMemberCount = new HashMap<>();

        for (Assignment assignment : assignment_data_map.values()) {
            String coachId = assignment.getCoach_id();
            coachMemberCount.put(coachId, coachMemberCount.getOrDefault(coachId, 0) + 1);
        }

        for (Coach coach : coach_data_map.values()) {
            int count = coachMemberCount.getOrDefault(coach.getCoach_id(), 0);
            String status = (count >= 3) ? "Full (3/3)" : "Free (" + count + "/3)";
            coach.setCoach_status(status);
        }

        CoachFileManager.saveCoaches(coach_data_map);
        coach_list.setAll(coach_data_map.values());
        coaches_tableView.refresh();

        try {
            SocketBus.publish("app:data:changed","coaches");
        }catch (Exception ignore){

        }
    }

    @FXML
    private void assignMemberToCoach() {

        coach_data_map = CoachFileManager.loadCoaches();
        member_data_map = MemberFileManager.loadMembers();
        assignment_data_map = AssignmentFileManager.loadAssignments();

        String selectedCoachID = coach_id_comboBox.getValue();
        String selectedMemberID = member_id_comboBox.getValue();

        if (selectedCoachID == null || selectedMemberID == null) {
            showAlert("Selection Error", "Please select both a Coach ID and a Member ID before assigning.");
            return;
        }

        Coach selectedCoach = coach_data_map.get(selectedCoachID);
        Member selectedMember = member_data_map.get(selectedMemberID);

        if (selectedMember == null || selectedCoach == null) {
            System.out.println("Select both a member and a coach to assign.");
            return;
        }

        // Prevent assigning if coach is full
        if (selectedCoach.getCoach_status().startsWith("Full")) {
            showAlert("Failed to add!","This coach is already full.");
            return;
        }

        // Prevent duplicate assignment for same member
        for (Assignment a : assignment_data_map.values()) {
            if (a.getMember_id().equals(selectedMember.getMember_id())) {
                showAlert("Failed to assign!","This member is already assigned.");
                return;
            }
        }

        String newId = "A" + (assignment_data_map.size() + 1);
        Assignment newAssignment = new Assignment(newId, selectedMember.getMember_id(), selectedCoach.getCoach_id());
        assignment_data_map.put(newId, newAssignment);

        Member m = member_data_map.get(selectedMemberID);
        m.setMember_coach(selectedCoachID);

        MemberFileManager.saveMembers(member_data_map);

        AssignmentFileManager.saveAssignments(assignment_data_map);
        assignment_list.setAll(assignment_data_map.values());
        assignment_tableView.refresh();

        updateCoachStatuses();
        showAlert("Successful!","Assigned successfully!");

        coach_id_comboBox.setValue(null);
        member_id_comboBox.setValue(null);


        try {
            SocketBus.publish("app:data:changed","Members");
        }catch (Exception ignore){

        }

    }

    @FXML
    private void removeAssignment() {

        assignment_data_map = AssignmentFileManager.loadAssignments();
        member_data_map = MemberFileManager.loadMembers();

        String coachID = coach_id_comboBox.getValue();
        String memberID = member_id_comboBox.getValue();

        if (coachID == null || memberID == null) {
            showAlert("Missing Information", "Please select both Coach ID and Member ID to remove assignment.");
            return;
        }

// Find the assignment to remove
        Assignment selected = null;
        for (Assignment a : assignment_data_map.values()) {
            if (a.getCoach_id().equals(coachID) && a.getMember_id().equals(memberID)) {
                selected = a;
                break;
            }
        }

        if (selected == null) {
            showAlert("Not Found", "No such assignment found for the selected coach and member.");
            return;
        }

        Member m = member_data_map.get(memberID);
        m.setMember_coach("");

        assignment_data_map.remove(selected.getAssignment_id());
        AssignmentFileManager.saveAssignments(assignment_data_map);

        MemberFileManager.saveMembers(member_data_map);

        assignment_list.setAll(assignment_data_map.values());
        assignment_tableView.refresh();

        updateCoachStatuses();
        showAlert("Successful!","Assignment removed successfully!");
        coach_id_comboBox.setValue(null);
        member_id_comboBox.setValue(null);

        try {
            SocketBus.publish("app:data:changed","assignments,Members");
        }catch (Exception ignore){

        }

    }

    private void populateCoachAndMemberComboBoxes() {
        // Clear old items first
        coach_id_comboBox.getItems().clear();
        member_id_comboBox.getItems().clear();

        // Add all coach IDs
        if (coach_data_map != null && !coach_data_map.isEmpty()) {
            coach_id_comboBox.getItems().addAll(coach_data_map.keySet());
        }

        // Add all member IDs
        if (member_data_map != null && !member_data_map.isEmpty()) {
            member_id_comboBox.getItems().addAll(member_data_map.keySet());
        }
    }









    @FXML
    void memberBtn_method(ActionEvent event) {

        try {

            member_pane.setVisible(true);
            dashboard_pane.setVisible(false);
            payment_pane.setVisible(false);
            coaches_pane.setVisible(false);
            chat_pane.setVisible(false);
            mainBtnClicked(event);
        }catch (Exception e){

        }
    }

    private HashMap <String, Member> member_data_map = new HashMap<>();
    private ObservableList<Member> member_list = FXCollections.observableArrayList();

    private void set_member_table(){
        members_t_id.setCellValueFactory(new PropertyValueFactory<>("member_id"));
        members_t_name.setCellValueFactory(new PropertyValueFactory<>("member_name"));
        members_t_address.setCellValueFactory(new PropertyValueFactory<>("member_address"));
        members_t_gender.setCellValueFactory(new PropertyValueFactory<>("member_gender"));
        members_t_phoneNum.setCellValueFactory(new PropertyValueFactory<>("member_phone_num"));
        members_t_schedule.setCellValueFactory(new PropertyValueFactory<>("member_schedule"));
        members_t_start.setCellValueFactory(new PropertyValueFactory<>("member_start"));
        members_t_end.setCellValueFactory(new PropertyValueFactory<>("member_end"));
        members_t_status.setCellValueFactory(new PropertyValueFactory<>("member_status"));
        members_t_coach.setCellValueFactory(new PropertyValueFactory<>("member_coach"));
        members_tableView.setItems(member_list);
    }
    public void add_member(Member m){

        member_data_map = MemberFileManager.loadMembers();

        if (!member_data_map.containsKey(m.getMember_id())){


            member_data_map.put(m.getMember_id(),m);
            member_list.add(m);
            MemberFileManager.saveMembers(member_data_map);

            try {
                SocketBus.publish("app:data:changed","Members");
            }catch (Exception ignore){

            }

            showAlert("Successful!","New Member has been added!");
            dashboard_NumOfMem.setText(String.valueOf(member_data_map.size()));
            populateCoachAndMemberComboBoxes();

            //add er logei payment data

            payment_data_map = PaymentFileManager.loadPayments();

            payment_data_map.put(m.getMember_id(), new Payment(m.getMember_id(),
                    m.getMember_name(),
                    m.getMember_start(),
                    m.getMember_end(),
                    m.getMember_status(),
                    calculateTotalDue(m.getMember_start(),m.getMember_end(),m.getMember_status()))
            );
            if ("paid".equalsIgnoreCase(m.getMember_status())) {
                double amount = Double.parseDouble(
                        payment_data_map.get(m.getMember_id()).getPayment_totalDue()
                );

                PaymentFileManager.savePayments(payment_data_map);

                try {
                    SocketBus.publish("app:data:changed","Payment");
                }catch (Exception ignore){

                }

            }

            payment_list.add(payment_data_map.get(m.getMember_id()));
            PaymentFileManager.savePayments(payment_data_map);
            updateUnpaidComboBox();

            try {
                SocketBus.publish("app:data:changed","Payment");
            }catch (Exception ignore){

            }

        }else {
            showAlert("Cannot add the user!","User with the same ID already exists");
        }
    }
    private void delete_member(String memberID){

        member_data_map = MemberFileManager.loadMembers();
        payment_data_map = PaymentFileManager.loadPayments();
        assignment_data_map = AssignmentFileManager.loadAssignments();

        Member remove = member_data_map.remove(memberID);
        if (remove!=null){
            assignment_data_map.values().removeIf(a -> memberID.equals(a.getMember_id()));
            payment_data_map.remove(memberID);

            MemberFileManager.saveMembers(member_data_map);
            PaymentFileManager.savePayments(payment_data_map);
            AssignmentFileManager.saveAssignments(assignment_data_map);

            member_list.removeIf(member -> member.getMember_id().equals(memberID));
            assignment_list.removeIf(a -> memberID.equals(a.getMember_id()));
            payment_list.removeIf(payment -> payment.getPayment_id().equals(memberID));


            updateCoachStatuses();
            populateCoachAndMemberComboBoxes();
            updateUnpaidComboBox();

            try {
                SocketBus.publish("app:data:changed","Members,Payment,assignments");
            }catch (Exception ignore){

            }

        }

    }
    private void update_member(String memberID, String NewMemberName, String NewMemberAddress, String NewMemberGender, String NewMemberPhoneNum, String NewMemberSchedule, String NewMemberEnd, String NewMemberStatus) {

        member_data_map = MemberFileManager.loadMembers();
        payment_data_map = PaymentFileManager.loadPayments();

        Member m = member_data_map.get(memberID);


        Payment p = payment_data_map.get(memberID);
        if (m != null) {
            if (NewMemberName != null && !NewMemberName.isEmpty()) {
                m.setMember_name(NewMemberName);

                if (p != null) {
                    p.setPayment_name(NewMemberName);
                }
            }
            if (NewMemberAddress != null && !NewMemberAddress.isEmpty()) {
                m.setMember_address(NewMemberAddress);
            }
            if (NewMemberGender != null && !NewMemberGender.isEmpty()) {
                m.setMember_gender(NewMemberGender);
            }
            if (NewMemberPhoneNum != null && !NewMemberPhoneNum.isEmpty()) {
                m.setMember_phone_num(NewMemberPhoneNum);
            }
            if (NewMemberSchedule != null && !NewMemberSchedule.isEmpty()) {
                m.setMember_schedule(NewMemberSchedule);
            }
            if (NewMemberEnd != null && !NewMemberEnd.isEmpty()) {


                if (p != null) {
                    p.setPayment_end(NewMemberEnd);

                    String newDue = calculateUpdatedTotalDue(m.getMember_id(), m.getMember_end(), NewMemberEnd, NewMemberStatus);
                    System.out.println(newDue);

                    // If unpaid, add to existing due
                    if (NewMemberStatus != null && NewMemberStatus.equalsIgnoreCase("unpaid")) {
                        double oldDue = 0.0;
                        try {
                            oldDue = Double.parseDouble(p.getPayment_totalDue());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        double total = oldDue + Double.parseDouble(newDue);
                        p.setPayment_totalDue(String.format("%.2f", total));
                    }
                    else if (NewMemberStatus != null && NewMemberStatus.equalsIgnoreCase("paid")) {

                        try {
                            double paidAmount = Double.parseDouble(p.getPayment_totalDue());
                            System.out.println(paidAmount);
                            double newPaid = paidAmount + Double.parseDouble(newDue);
                            System.out.println(newPaid);
                            IncomeFileManager.addIncome(newPaid);


                            GraphFileManager.updateGraph(newPaid);
                            GraphFileManager.loadGraphData(dashboard_graph);
                            dashboard_graph.setAnimated(true);
                        } catch (Exception ignored) {}

                        p.setPayment_totalDue("0.00");
                    } else {
                        if (m.getMember_status().equalsIgnoreCase("paid")) {
                            p.setPayment_totalDue("0.00");
                        } else {
                            double oldDue = 0.0;
                            try {
                                oldDue = Double.parseDouble(p.getPayment_totalDue());
                            } catch (Exception ignored) {
                            }

                            double total = oldDue + Double.parseDouble(newDue);
                            p.setPayment_totalDue(String.format("%.2f", total));
                        }
                    }

                }

                m.setMember_end(NewMemberEnd);
            }
            if (NewMemberStatus != null && !NewMemberStatus.isEmpty()) {
                m.setMember_status(NewMemberStatus);


                if (p != null) {
                    if (NewMemberStatus.equalsIgnoreCase("paid")) {

                        double paidAmount = 0.0;
                        try {
                            paidAmount = Double.parseDouble(p.getPayment_totalDue());
                        } catch (Exception ignored) {}

                        IncomeFileManager.addIncome(paidAmount);
                        GraphFileManager.updateGraph(paidAmount);
                        GraphFileManager.loadGraphData(dashboard_graph);
                        dashboard_graph.setAnimated(true);
                        p.setPayment_totalDue("0.00");
                    }
                    p.setPayment_status(NewMemberStatus);

                }
            }
            MemberFileManager.saveMembers(member_data_map);
            members_tableView.refresh();


            PaymentFileManager.savePayments(payment_data_map);
            updateUnpaidComboBox();
            payment_tableView.refresh();

            try {
                SocketBus.publish("app:data:changed","Members,Payment");
            }catch (Exception ignore){

            }
        }
    }

    @FXML
    void members_addBtn_method(ActionEvent event) {

        String member_id = members_id.getText().trim();
        String member_name = members_name.getText().trim();
        String member_address = members_address.getText().trim();
        String member_gender = members_gender.getValue() == null ? "" : (String) members_gender.getValue();
        String member_phoneNum = members_phoneNum.getText().trim();
        String member_schedule = members_schedule.getValue() == null ? "" : (String) members_schedule.getValue();
        String member_startDate = members_startDate.getValue() == null ? "" : members_startDate.getValue().toString();
        String member_endDate = members_endDate.getValue() == null ? "" : members_endDate.getValue().toString();
        String member_status = members_status.getValue() == null ? "" : (String) members_status.getValue();

        if (
                member_id.isEmpty() || member_name.isEmpty() || member_address.isEmpty() ||
                        member_gender.isEmpty() || member_phoneNum.isEmpty() || member_schedule.isEmpty() ||
                        member_startDate.isEmpty() || member_endDate.isEmpty() || member_status.isEmpty()
        ) {
            showAlert("Failed to add the user!", "Please fill all the information.");
        } else {
            add_member(new Member(
                    member_id, member_name, member_address, member_gender,
                    member_phoneNum, member_schedule, member_startDate,
                    member_endDate, member_status
            ));

        }
        clear_member_fields();
    }


    @FXML
    void members_updateBtn_method(ActionEvent event){
        String id = members_id.getText();
        String name = members_name.getText();
        String address = members_address.getText();
        String gender = (String) members_gender.getValue();
        String phoneNum = members_phoneNum.getText();
        String schedule = (String) members_schedule.getValue();
        String endDate = (members_endDate.getValue() != null) ? members_endDate.getValue().toString() : "";
        String status = (String) members_status.getValue();

        member_data_map = MemberFileManager.loadMembers();

        if (member_data_map.containsKey(id)){
            update_member(id, name, address, gender, phoneNum, schedule, endDate, status);
            showAlert("Successful!", "The member has been updated!");
        }else {
            showAlert("Unsuccessful!","The "+id+" don't exist in the system!");
        }
        clear_member_fields();
    }
    @FXML
    void members_deleteBtn_method(ActionEvent event){

        member_data_map = MemberFileManager.loadMembers();

        String id = members_id1.getText();
        if (member_data_map.containsKey(id)) {
            delete_member(id);
            showAlert("Successful","The member has been deleted!");
        }else {
            showAlert("Failed to delete","User with "+id+" doesn't exist");
        }

    }
    @FXML
    void members_resetBtn_method(ActionEvent event){
        clear_member_fields();
    }

    private void clear_member_fields(){
        members_id.clear();
        members_name.clear();
        members_address.clear();
        members_gender.setValue(null);
        members_phoneNum.clear();
        members_schedule.setValue(null);
        members_startDate.setValue(null);
        members_endDate.setValue(null);
        members_status.setValue(null);
    }

    @FXML
    void members_view_listBtn_method(ActionEvent event){

        try{
            members_table_pane.setVisible(true);
            members_add_update_pane.setVisible(false);
            members_delete_pane.setVisible(false);
            memberBtnClicked(event);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void members_go_to_add_paneBtn_method(ActionEvent event){

        try {
            members_table_pane.setVisible(false);
            members_add_update_pane.setVisible(true);
            members_delete_pane.setVisible(false);

            members_addBtn.setVisible(true);
            members_updateBtn.setVisible(false);

            memberBtnClicked(event);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void members_go_to_update_panBtn_method(ActionEvent event){

        try{
            members_table_pane.setVisible(false);
            members_add_update_pane.setVisible(true);
            members_delete_pane.setVisible(false);

            members_addBtn.setVisible(false);
            members_updateBtn.setVisible(true);

            memberBtnClicked(event);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void members_go_to_delete_paneBtn_method(ActionEvent event){

        try {
            members_table_pane.setVisible(false);
            members_add_update_pane.setVisible(false);
            members_delete_pane.setVisible(true);

            memberBtnClicked(event);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void paymentBtn_method(ActionEvent event) {
        try {

            payment_pane.setVisible(true);
            member_pane.setVisible(false);
            dashboard_pane.setVisible(false);
            coaches_pane.setVisible(false);
            chat_pane.setVisible(false);
            mainBtnClicked(event);
        }catch (Exception e){


        }

    }


    private HashMap<String, Payment> payment_data_map = new HashMap<>();
    private ObservableList<Payment> payment_list = FXCollections.observableArrayList();

    private void set_payment_table(){
        payment_t_memberId.setCellValueFactory(new PropertyValueFactory<>("payment_id"));
        payment_t_memberName.setCellValueFactory(new PropertyValueFactory<>("payment_name"));
        payment_t_startDate.setCellValueFactory(new PropertyValueFactory<>("payment_start"));
        payment_t_endDate.setCellValueFactory(new PropertyValueFactory<>("payment_end"));
        payment_t_status.setCellValueFactory(new PropertyValueFactory<>("payment_status"));
        payment_t_totalDue.setCellValueFactory(new PropertyValueFactory<>("payment_totalDue"));

    }
    private String calculateTotalDue(String startDateStr, String endDateStr, String status) {
        try {
            if (status != null && status.equalsIgnoreCase("paid")) {
                LocalDate start = LocalDate.parse(startDateStr);
                LocalDate end = LocalDate.parse(endDateStr);
                long daysInclusive = ChronoUnit.DAYS.between(start, end) + 1;
                double paidAmount = daysInclusive * 5.0;

                IncomeFileManager.addIncome(paidAmount);
                dashboard_income.setText(String.format("%.2f", IncomeFileManager.getTotalIncome()));

                GraphFileManager.updateGraph(paidAmount);
                GraphFileManager.loadGraphData(dashboard_graph);
                dashboard_graph.setAnimated(true);

                return String.format("%.2f", 0.0);
            }

            LocalDate start = LocalDate.parse(startDateStr);
            LocalDate end = LocalDate.parse(endDateStr);

            long daysInclusive = ChronoUnit.DAYS.between(start, end) + 1; // inclusive
            if (daysInclusive < 0) daysInclusive = 0;

            double total = daysInclusive * 5.0;
            return String.format("%.2f", total);

        } catch (Exception ex) {
            // parse errors or other problems -> treat as zero due
            ex.printStackTrace();
            return String.format("%.2f", 0.0);
        }
    }
    private String calculateUpdatedTotalDue(String memberID, String oldEndStr, String newEndStr, String newStatus) {
        try {
            // If status is paid, no new due
            if (newStatus != null && newStatus.equalsIgnoreCase("paid")) {

                LocalDate oldEnd = LocalDate.parse(oldEndStr);
                LocalDate newEnd = LocalDate.parse(newEndStr);
                long days = ChronoUnit.DAYS.between(oldEnd, newEnd);
                double paidAmount = days * 5.0;

                Payment p = payment_data_map.get(memberID);
                double oldDue = Double.parseDouble(p.getPayment_totalDue());
                IncomeFileManager.addIncome(paidAmount +oldDue);
                dashboard_income.setText(String.format("%.2f", IncomeFileManager.getTotalIncome()));

                GraphFileManager.updateGraph(paidAmount);
                GraphFileManager.loadGraphData(dashboard_graph);
                dashboard_graph.setAnimated(true);

                return String.format("%.2f", 0.0);
            }

            // Validate inputs
            if (oldEndStr == null || newEndStr == null ||
                    oldEndStr.isBlank() || newEndStr.isBlank()) {
                return String.format("%.2f", 0.0);
            }

            LocalDate oldEnd = LocalDate.parse(oldEndStr);
            LocalDate newEnd = LocalDate.parse(newEndStr);

            // If new end date is before or equal to old, no extra charge
            if (!newEnd.isAfter(oldEnd)) {
                return String.format("%.2f", 0.0);
            }

            // Calculate difference in days (exclusive of oldEnd)
            long extraDays = ChronoUnit.DAYS.between(oldEnd, newEnd);
            double extraCost = extraDays * 5.0;

            return String.format("%.2f", extraCost);

        } catch (Exception e) {
            e.printStackTrace();
            return String.format("%.2f", 0.0);
        }
    }
    private void updateUnpaidComboBox() {

        payment_data_map = PaymentFileManager.loadPayments();

        payment_id.getItems().clear();


        for (Payment p : payment_data_map.values()) {
            if ("unpaid".equalsIgnoreCase(p.getPayment_status())) {
                payment_id.getItems().add(p.getPayment_id());
            }
        }
    }
    @FXML
    private void handlePaymentSelection() {

        payment_data_map = PaymentFileManager.loadPayments();

        String selectedId = payment_id.getValue();
        if (selectedId == null || selectedId.isEmpty()) return;

        Payment p = payment_data_map.get(selectedId);
        if (p != null) {
            payment_nameLabel.setText(p.getPayment_name());
            payment_totalDueLabel.setText(p.getPayment_totalDue());
        }
    }

    @FXML
    private void handlePayButton() {
        String selectedId = payment_id.getValue();
        if (selectedId == null || selectedId.isEmpty()) {
            showAlert("No Selection", "Please select a member ID first.");
            return;
        }

        String inputAmount = payment_amount.getText().trim();
        if (inputAmount.isEmpty()) {
            showAlert("Invalid Input", "Please enter a payment amount.");
            return;
        }

        double payAmount;
        try {
            payAmount = Double.parseDouble(inputAmount);
            if (payAmount <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert("Invalid Amount", "Please enter a valid positive number.");
            return;
        }

        payment_data_map = PaymentFileManager.loadPayments();
        member_data_map = MemberFileManager.loadMembers();

        Payment p = payment_data_map.get(selectedId);
        Member m = member_data_map.get(selectedId);
        if (p == null || m == null) return;

        double currentDue = Double.parseDouble(p.getPayment_totalDue());
        double newDue = currentDue - payAmount;

        if (newDue <= 0) {
            // fully paid
            p.setPayment_totalDue("0.00");
            p.setPayment_status("Paid");
            m.setMember_status("Paid");

            IncomeFileManager.addIncome(currentDue);
            dashboard_income.setText(String.format("%.2f", IncomeFileManager.getTotalIncome()));

            GraphFileManager.updateGraph(currentDue);
            GraphFileManager.loadGraphData(dashboard_graph);
            dashboard_graph.setAnimated(true);

            showAlert("Payment Successful", "Member has fully paid the due amount.");
        } else {
            // partially paid
            p.setPayment_totalDue(String.format("%.2f", newDue));
            p.setPayment_status("Unpaid");
            m.setMember_status("Unpaid");
            IncomeFileManager.addIncome(payAmount);
            dashboard_income.setText(String.format("%.2f", IncomeFileManager.getTotalIncome()));

            GraphFileManager.updateGraph(payAmount);
            GraphFileManager.loadGraphData(dashboard_graph);


            showAlert("Partial Payment", "Remaining due: $" + String.format("%.2f", newDue));
        }

        PaymentFileManager.savePayments(payment_data_map);
        MemberFileManager.saveMembers(member_data_map);

        payment_totalDueLabel.setText(p.getPayment_totalDue());
        payment_nameLabel.setText(p.getPayment_name());
        payment_amount.clear();
        updateUnpaidComboBox();
        payment_tableView.refresh();
        members_tableView.refresh();

        try {
            SocketBus.publish("app:data:changed","Members,Payment");
        }catch (Exception ignore){

        }

    }








    @FXML
    void chatBtn_method(ActionEvent event) {

        try {

            chat_pane.setVisible(true);
            member_pane.setVisible(false);
            payment_pane.setVisible(false);
            coaches_pane.setVisible(false);
            dashboard_pane.setVisible(false);

            mainBtnClicked(event);
        }catch (Exception e){


        }

    }
    @FXML
    void chat_sendBtn_method(){

    }

    @FXML
    void log_outBtn_method() {

        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Notification");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)){

                log_outBtn.getScene().getWindow().hide();

                // login controller change korle ata open hobe

                Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                // in case border bar jodi remove korte hoy
//                root.setOnMousePressed((MouseEvent event) -> {
//                    x =event.getScreenX();
//                    y=event.getScreenY();
//                });
//
//                root.setOnMouseDragged((MouseEvent event) -> {
//                    stage.setX(event.getScreenX() - x);
//                    stage.setY(event.getScreenY() - y);
//
//                    stage.setOpacity(.8);
//                });
//
//                root.setOnMouseReleased((MouseEvent event) ->{
//                    stage.setOpacity(1);
//                });
//                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        coach_genderlist();
        coaches_statusList();
        coach_data_map =CoachFileManager.loadCoaches();
        coach_list = FXCollections.observableArrayList(coach_data_map.values());
        set_coach_table();
        coaches_tableView.setItems(coach_list);


        member_genderlist();
        members_statusList();
        members_scheduleList();
        member_data_map = MemberFileManager.loadMembers();
        member_list =FXCollections.observableArrayList(member_data_map.values());
        set_member_table();
        members_tableView.setItems(member_list);


        // Assignments
        assignment_data_map = AssignmentFileManager.loadAssignments();
        assignment_list = FXCollections.observableArrayList(assignment_data_map.values());
        set_assignment_table();
        assignment_tableView.setItems(assignment_list);
        updateCoachStatuses();
        populateCoachAndMemberComboBoxes();

        payment_data_map = PaymentFileManager.loadPayments();
        payment_list = FXCollections.observableArrayList(payment_data_map.values());
        set_payment_table();
        payment_tableView.setItems(payment_list);

        dashboard_NumOfMem.setText(String.valueOf(member_data_map.size()));
        dashboard_NumOfCoach.setText(String.valueOf(coach_data_map.size()));
        IncomeFileManager.loadIncome();
        dashboard_income.setText(String.format("%.2f", IncomeFileManager.getTotalIncome()));

        updateUnpaidComboBox();

        GraphFileManager.loadGraphData(dashboard_graph);
        dashboard_graph.setAnimated(true);


        SocketBus.addListener((ch, payload) -> {
            if ("app:data:changed".equals(ch)) {
                // Ensure UI updates run on FX thread
                Platform.runLater(this::refreshDataSafely);
            }
        });



    }


    private void refreshDataSafely() {
        try {

            HashMap<String, Coach> c = CoachFileManager.loadCoaches();
            coach_data_map = c;
            coaches_tableView.setItems(FXCollections.observableArrayList(c.values()));
            coaches_tableView.refresh();

            HashMap <String, Member> m = MemberFileManager.loadMembers();
            member_data_map = m;
            members_tableView.setItems(FXCollections.observableArrayList(m.values()));
            members_tableView.refresh();

            HashMap <String, Assignment > a = AssignmentFileManager.loadAssignments();
            assignment_data_map = a;
            assignment_tableView.setItems(FXCollections.observableArrayList(a.values()));
            assignment_tableView.refresh();

            populateCoachAndMemberComboBoxes();
//            updateCoachStatuses();

            HashMap<String, Payment> p = PaymentFileManager.loadPayments();
            payment_data_map = p;
            payment_tableView.setItems(FXCollections.observableArrayList(p.values()));
            payment_tableView.refresh();
            updateUnpaidComboBox();

            dashboard_NumOfMem.setText(String.valueOf(member_data_map.size()));
            dashboard_NumOfCoach.setText(String.valueOf(coach_data_map.size()));
            IncomeFileManager.loadIncome();
            dashboard_income.setText(String.format("%.2f", IncomeFileManager.getTotalIncome()));

            GraphFileManager.loadGraphData(dashboard_graph);
            dashboard_graph.setAnimated(true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void minimize(){

        Stage stage = (Stage) main_pane.getScene().getWindow();
        stage.setIconified(true);
    }

    public void close(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();    }

    private void resetButtonColor(Button button) {
        button.setStyle(
                button.getStyle()
                        .replaceAll("-fx-background-color:[^;]*;", "")
                        .replaceAll("-fx-text-fill:[^;]*;", "")
                        + "; -fx-background-color: black; -fx-text-fill: white;"
        );
    }

    private void mainBtnClicked(ActionEvent event){
        Button clickedButton = (Button) event.getSource();
        resetButtonColor(dashboardBtn);
        resetButtonColor(coachesBtn);
        resetButtonColor(memberBtn);
        resetButtonColor(paymentBtn);
        resetButtonColor(chatBtn);

        clickedButton.setStyle(clickedButton.getStyle() +
                "; -fx-background-color: white;" +
                " -fx-text-fill: black;"
        );    }


    private void resetButtonColorForCoach(Button button) {
        button.setStyle(
                button.getStyle()
                        .replaceAll("-fx-background-color:[^;]*;", "")
                        .replaceAll("-fx-text-fill:[^;]*;", "")
                        + "; -fx-background-color: white; -fx-text-fill: black;"
        );
    }

    private void coachBtnClicked(ActionEvent event){
        Button clickedButton = (Button) event.getSource();
        resetButtonColorForCoach(coaches_view_list_paneBtn);
        resetButtonColorForCoach(coaches_go_to_add_paneBtn);
        resetButtonColorForCoach(coaches_go_to_update_paneBtn);
        resetButtonColorForCoach(coaches_go_to_delete_paneBtn);
        resetButtonColorForCoach(coaches_assignmentBtn);

        clickedButton.setStyle(clickedButton.getStyle() +
                "; -fx-background-color:  #F68E46;" +
                " -fx-text-fill: black;"
        );    }




    private void resetButtonColorForMember(Button button) {
        button.setStyle(
                button.getStyle()
                        .replaceAll("-fx-background-color:[^;]*;", "")
                        .replaceAll("-fx-text-fill:[^;]*;", "")
                        + "; -fx-background-color: transparent; -fx-text-fill: white;"
        );
    }

    private void memberBtnClicked(ActionEvent event){
        Button clickedButton = (Button) event.getSource();
        resetButtonColorForMember(members_view_list_paneBtn);
        resetButtonColorForMember(members_go_to_add_paneBtn);
        resetButtonColorForMember(members_go_to_update_paneBtn);
        resetButtonColorForMember(members_go_to_delete_paneBtn);

        clickedButton.setStyle(clickedButton.getStyle() +
                "; -fx-background-color:  #F68E46;" +
                " -fx-text-fill: black;"
        );    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}