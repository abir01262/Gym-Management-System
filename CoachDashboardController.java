package com.gymapp;

import com.gymapp.net.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;


import java.util.*;
import java.io.*;
import java.net.Socket;

import javafx.scene.control.Label;


public class CoachDashboardController {

    @FXML
    private Button chat;

    @FXML
    private AnchorPane chatform;

    @FXML
    private Button pvtchat;

    @FXML
    private  AnchorPane pvtchatfrorm;

    @FXML
    private Button pubchat;

    @FXML
    private  AnchorPane pubchatform;

    @FXML
    private Button noticebutton;

    @FXML
    private AnchorPane notice;

    @FXML
    private Button assignment;

    @FXML
    private AnchorPane assignmentform;

    @FXML
    TextArea allTextArea;

    @FXML
    TextField msgTextField;

    @FXML
    TextArea noticeTextArea;

    @FXML
    TextArea assign1TextArea;
    @FXML
    TextField assign1TextField;

    @FXML
    TextArea assign2TextArea;
    @FXML
    TextField assign2TextField;

    @FXML
    TextArea assign3TextArea;
    @FXML
    TextField assign3TextField;

    @FXML
    TextArea chat1TextArea;
    @FXML
    TextField chat1TextField;

    @FXML
    TextArea chat2TextArea;
    @FXML
    TextField chat2TextField;

    @FXML
    TextArea chat3TextArea;
    @FXML
    TextField chat3TextField;

    @FXML
    Button coach_public_text_sendBtn;

    @FXML
    Button coach_member1_chat_sendBtn;

    @FXML
    Button coach_member2_chat_sendBtn;

    @FXML
    Button coach_member3_chat_sendBtn;
    
    @FXML
    Button coach_send_assignments_to_member1Btn;

    @FXML
    Button coach_send_assignments_to_member2Btn;

    @FXML
    Button coach_send_assignments_to_member3Btn;


    @FXML
    private Label coach_name;


    @FXML private TextField coach_notice_inputField;


    ///exit

    @FXML
    private void handleExit() {
        System.exit(0);
    }





    //scence switch

    public void switchform(javafx.event.ActionEvent event) {
        if (event.getSource() == chat){
            chatform.setVisible(true);
            notice.setVisible(false);
            assignmentform.setVisible(false);


        } else if (event.getSource()== noticebutton) {
            notice.setVisible(true);
            chatform.setVisible(false);
            assignmentform.setVisible(false);
        }
        else if (event.getSource()== assignment) {
            notice.setVisible(false);
            chatform.setVisible(false);
            assignmentform.setVisible(true);
        }
    }


    public void switchform2(javafx.event.ActionEvent event) {
        if (event.getSource() == pvtchat){

            pvtchatfrorm.setVisible(true);
            pubchatform.setVisible(false);
        }else if (event.getSource()== pubchat){
            pvtchatfrorm.setVisible(false);
            pubchatform.setVisible(true);
        }
    }

    @FXML private Button coach_notice_sendBtn;

    private Role   myRole;
    private String myId;
    private String myName;
    private SocketClient client;

    private final List<String> memberIds = new ArrayList<>(); // size 0..3


    public void initialize() {
        myRole = SessionContext.role;      // COACH
        myId   = SessionContext.id;
        myName = SessionContext.name;

        client = new SocketClient();
        client.connect(myRole, myId, myName, MessageBus::dispatch);

        coach_name.setText(myName);
        MessageBus.add(this::onMessage);

        memberIds.clear();
        memberIds.addAll(Router.get().membersForCoach(myId));
        gateSlots();

        for (Message m : PersistenceManager.readNotices())
            if (m.fromRole == Role.COACH && myId.equals(m.fromId))
                noticeTextArea.appendText("[Me] " + (m.text == null ? "" : m.text) + "\n");

        for (Message m : PersistenceManager.readPrivate())
            if (myId.equals(m.toId) && m.fromRole == Role.MEMBER)
                appendPrivateFromMember(m);

        for (Message m : PersistenceManager.readTasks())
            if (m.type == MessageType.TASK_UPDATE) appendTaskUpdate(m);
    }

    private void gateSlots() {
        TextArea[] areas = {chat1TextArea, chat2TextArea, chat3TextArea};
        TextField[] inputs = {chat1TextField, chat2TextField, chat3TextField};
        Button[] sends = {coach_member1_chat_sendBtn, coach_member2_chat_sendBtn, coach_member3_chat_sendBtn};

        TextField[] aInputs = {assign1TextField, assign2TextField, assign3TextField};
        Button[] aSends = {coach_send_assignments_to_member1Btn, coach_send_assignments_to_member2Btn, coach_send_assignments_to_member3Btn};

        for (int i = 0; i < 3; i++) {
            boolean enabled = i < memberIds.size();
            if (inputs[i] != null) inputs[i].setDisable(!enabled);
            if (sends[i] != null) sends[i].setDisable(!enabled);
            if (aInputs[i] != null) aInputs[i].setDisable(!enabled);
            if (aSends[i] != null) aSends[i].setDisable(!enabled);
            if (!enabled && areas[i] != null) areas[i].appendText("[system] no member assigned\n");
        }
    }

    private void onMessage(Message m) {
        if (m.type == MessageType.PRIVATE_CHAT && myId.equals(m.toId) && m.fromRole == Role.MEMBER)
            appendPrivateFromMember(m);
        if (m.type == MessageType.TASK_UPDATE) appendTaskUpdate(m);
        if (m.type == MessageType.TASK_ASSIGN && m.fromRole == Role.COACH && myId.equals(m.fromId)) {
            int idx = indexOfMember(m.toId);
            if (idx == 0) assign1TextArea.appendText("Assigned: " + safe(m.taskName) + "\n");
            else if (idx == 1) assign2TextArea.appendText("Assigned: " + safe(m.taskName) + "\n");
            else if (idx == 2) assign3TextArea.appendText("Assigned: " + safe(m.taskName) + "\n");
        }
    }

    private void appendPrivateFromMember(Message m) {
        int idx = indexOfMember(m.fromId);
        if (idx == 0) chat1TextArea.appendText("[" + m.fromName + "] " + safe(m.text) + "\n");
        else if (idx == 1) chat2TextArea.appendText("[" + m.fromName + "] " + safe(m.text) + "\n");
        else if (idx == 2) chat3TextArea.appendText("[" + m.fromName + "] " + safe(m.text) + "\n");
    }

    private void appendTaskUpdate(Message m) {
        int idx = indexOfMember(m.fromId);
        String line = m.fromName + ": " + safe(m.taskName) + " -> " + safe(m.taskStatus) + "\n";
        if (idx == 0) assign1TextArea.appendText(line);
        else if (idx == 1) assign2TextArea.appendText(line);
        else if (idx == 2) assign3TextArea.appendText(line);
    }

    @FXML public void onSendNotice() {
        // Prefer the TextField as input; if it's not present, fall back to the big TextArea
        String t = null;
        if (coach_notice_inputField != null) t = coach_notice_inputField.getText();
        if (t == null || t.isBlank()) {
            if (noticeTextArea != null) t = noticeTextArea.getText();
        }
        if (t == null || t.isBlank()) return;

        Message m = new Message();
        m.type = MessageType.NOTICE;
        m.fromRole = myRole;   // COACH
        m.fromId   = myId;
        m.fromName = myName;
        m.ts       = System.currentTimeMillis();
        m.text     = t;

        client.send(m);                         // send to members via hub
        if (noticeTextArea != null) {
            noticeTextArea.appendText("[Me] " + t + "\n");  // echo locally for coach
        }
        if (coach_notice_inputField != null) coach_notice_inputField.clear();
    }

    @FXML public void onSendChat1() { sendPrivate(0, chat1TextField, chat1TextArea); }
    @FXML public void onSendChat2() { sendPrivate(1, chat2TextField, chat2TextArea); }
    @FXML public void onSendChat3() { sendPrivate(2, chat3TextField, chat3TextArea); }

    private void sendPrivate(int idx, TextField input, TextArea area) {
        if (idx >= memberIds.size()) return;
        String t = input.getText();
        if (t == null || t.isBlank()) return;

        Message m = new Message();
        m.type = MessageType.PRIVATE_CHAT;
        m.fromRole = myRole;
        m.fromId = myId;
        m.fromName = myName;
        m.toId = memberIds.get(idx);
        m.ts = System.currentTimeMillis();
        m.text = t;

        client.send(m);
        area.appendText("[Me] " + t + "\n");
        input.clear();
    }

    @FXML public void onAssign1() { sendAssign(0, assign1TextField); }
    @FXML public void onAssign2() { sendAssign(1, assign2TextField); }
    @FXML public void onAssign3() { sendAssign(2, assign3TextField); }

    private void sendAssign(int idx, TextField input) {
        if (idx >= memberIds.size()) return;
        String task = input.getText();
        if (task == null || task.isBlank()) return;

        Message m = new Message();
        m.type = MessageType.TASK_ASSIGN;
        m.fromRole = myRole;
        m.fromId = myId;
        m.fromName = myName;
        m.toId = memberIds.get(idx);
        m.ts = System.currentTimeMillis();
        m.taskName = task;
        m.slotIndex = idx;

        client.send(m);
        if (idx == 0) assign1TextArea.appendText("Assigned: " + task + "\n");
        else if (idx == 1) assign2TextArea.appendText("Assigned: " + task + "\n");
        else if (idx == 2) assign3TextArea.appendText("Assigned: " + task + "\n");
    }

    private int indexOfMember(String memberId) { return memberIds.indexOf(memberId); }
    private String safe(String s) { return s == null ? "" : s; }


        @FXML
    private void coach_public_text_sendBtn_method(){

        }
}