package com.gymapp;


import com.gymapp.net.*;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


import java.io.*;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;

public class MemberDashboardController {

    //chatbot
    @FXML
    private Button chat;
    @FXML
    private Button chatbot;
    @FXML
    private AnchorPane chatbotform;

    @FXML
    private AnchorPane chatform;
    @FXML
    private javafx.scene.control.TextArea area;

    @FXML
    private TextField userInput;
    private final String HISTORY_FILE = "chatbothisory.txt";

    //calory and generate
    @FXML
    private Button plan;
    @FXML
    private AnchorPane calory;

    //bmi
    @FXML
    private AnchorPane bmi;

    @FXML
    private Button bmibutton;

    @FXML
    private TextField weight;
    @FXML
    private TextField hight;
    @FXML
    private TextArea result;
    @FXML
    private AreaChart<Number, Number> bmiChart;


    private final String filename = "history.txt";
    private ArrayList<Double> bmiHistory = new ArrayList<>();
    private ArrayList<Double> weightHistory = new ArrayList<>();
    private XYChart.Series<Number, Number> series = new XYChart.Series<>();
    private XYChart.Series<Number, Number> weightSeries = new XYChart.Series<>();
    //pay

    @FXML
    private AnchorPane ass;
    @FXML
    private AnchorPane publicchat;
    @FXML
    private AnchorPane privatechat;
    @FXML
    private AnchorPane notice;

    @FXML
    private Button assbtn;
    @FXML
    private Button pubchatbtn;
    @FXML
    private Button pvtchatbtn;
    @FXML
    private Button noticebtn;
    @FXML
    private AnchorPane plang;
    @FXML
    private Button plangenerator;
    @FXML
    private AnchorPane carlo;
    @FXML
    private Button calorie;

    @FXML
    private Button member_Public_chat_sendBtn;

    @FXML
    private Button member_send_to_coachBtn;

    @FXML
    private Label member_name;

    @FXML
    private ComboBox<String> progressComboBox;


    @FXML private Label meber_name; // provided fx:id
    @FXML private TextArea noticeTextArea;
    @FXML private TextArea chat1TextArea;
    @FXML private TextField chat1TextField;


    @FXML private TextField assign1TextField;
    @FXML private TextArea assign1TextArea;



    private String coachId;

    private Role   myRole;
    private String myId;
    private String myName;
    private SocketClient client;


    private void onMessage(Message m) {
        if (m.type == MessageType.NOTICE && myId.equals(m.toId)) {
            appendNotice(m);
        }
        if (m.type == MessageType.PRIVATE_CHAT) {
            // Only append when a COACH messages this member.
            if (myId.equals(m.toId) && m.fromRole == Role.COACH) {
                appendPrivateFromCoach(m);
            }
            // DO NOT append self-sent messages here; they are already echoed in onSendToCoach().
        }
        if (m.type == MessageType.TASK_ASSIGN && myId.equals(m.toId)) {
            appendTaskAssign(m);
        }
    }


    private void appendNotice(Message m) {
        noticeTextArea.appendText("[Coach " + m.fromName + "] " + safe(m.text) + "\n");
    }

    private void appendPrivateFromCoach(Message m) {
        chat1TextArea.appendText("[Coach " + m.fromName + "] " + safe(m.text) + "\n");
    }

    private void appendPrivateFromSelf(Message m) {
        chat1TextArea.appendText("[Me] " + safe(m.text) + "\n");
    }

    private void appendTaskAssign(Message m) {
        assign1TextArea.appendText("Assigned: " + safe(m.taskName) + "\n");
    }

    @FXML public void onSendToCoach() {
        if (coachId == null || coachId.isBlank()) return;
        String t = chat1TextField.getText();
        if (t == null || t.isBlank()) return;

        Message m = new Message();
        m.type = MessageType.PRIVATE_CHAT;
        m.fromRole = myRole;        // MEMBER
        m.fromId = myId;
        m.fromName = myName;
        m.toId = coachId;
        m.ts = System.currentTimeMillis();
        m.text = t;

        client.send(m);             // send via this window's client
        appendPrivateFromSelf(m);   // local echo
        chat1TextField.clear();
    }

    // FXML: onAction="#onUpdateProgress"
    @FXML public void onUpdateProgress() {
        if (coachId == null || coachId.isBlank()) return;
        String task = assign1TextField.getText();
        String status = progressComboBox.getSelectionModel().getSelectedItem();
        if (task == null || task.isBlank() || status == null) return;

        Message m = new Message();
        m.type = MessageType.TASK_UPDATE;
        m.fromRole = myRole;
        m.fromId = myId;
        m.fromName = myName;
        m.toId = coachId;
        m.ts = System.currentTimeMillis();
        m.taskName = task;
        m.taskStatus = status;

        client.send(m);
        assign1TextArea.appendText("Updated: " + task + " -> " + status + "\n");
    }

    private String safe(String s) { return s == null ? "" : s; }


    //chatbot


    @FXML
    private void handleUserInput() {
        String input = userInput.getText().toLowerCase().trim();

        if (input.isEmpty()) return;


        if (input.contains("hi") || input.contains("hello")) {
            respond(input, "Hi!I am bot.Official chatbot of Glitched Gym.How can i help you?");
        } else if (input.contains("location")) {
            respond(input, "We are located at Gulshan, Dhaka — easily accessible by posh cars 😒");
        } else if (input.contains("facilities")) {
            respond(input, "Our gym includes a steam room, locker facilities, shower rooms, a protein shake bar,massage center and more.WoW!");
        } else if (input.contains("payment")) {
            respond(input, "We accept cash, card, bKash, and online transfers.Sadly, we don’t accept 'good vibes' as payment yet");
        } else if (input.contains("programs")) {
            respond(input, "Yes!Whether you want abs,biceps, or just to look good in selfies—we’ve got a program for that");
        } else if (input.contains("membership")) {
            respond(input, "We have monthly 20000 BDT, quarterly 50000 BDT, and yearly 18,0000 BDT plans.");
        } else if (input.contains("timing") || input.contains("time")) {
            respond(input, "Our gym is open from 6 AM to 10 PM, 7 days a week.");
        } else if (input.contains("trainer")) {
            respond(input, "Yes! We have certified trainers available for personal training sessions.Abu Sayed is the main trainer.");
        } else if (input.contains("workout")) {
            respond(input, "We offer strength training, cardio, yoga, and HIIT classes and many more.just what you need tell us.");
        } else if (input.contains("diet") || input.contains("nutrition")) {
            respond(input, "Yes, our trainers provide personalized diet plans.But sadly pizza,biryani are not considered");
        } else if (input.contains("bye")) {
            respond(input, "Thank you for visiting Glitched Gym. Stay healthy! Come back again to make us rich");
        } else {
            respond(input, "Sorry, I didn’t understand. Please ask about gym related questions.");
        }
        userInput.clear();
    }


    private void respond(String usermsg, String botreply) {
        String conversation = "You: " + usermsg + "\nBot: " + botreply + "\n\n";
        area.appendText(conversation);/////////////////////////////////////////////////////////////

        // save  file
        try (FileWriter writer = new FileWriter(HISTORY_FILE, true)) {
            writer.write(conversation);
        } catch (IOException e) {
            System.out.println("Error saving history: " + e.getMessage());
        }
    }


    @FXML
    private void handleBotHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE))) {
            area.clear(); // clear before showing history
            String line;
            while ((line = reader.readLine()) != null) {
                area.appendText(line + "\n");
            }
        } catch (IOException e) {
            area.appendText("No history found yet.\n");
        }
    }


    //scence switch

    public void switchform(javafx.event.ActionEvent event) {
        if (event.getSource() == chat) {
            chatform.setVisible(true);
            chatbotform.setVisible(false);
            bmi.setVisible(false);
            calory.setVisible(false);
            notice.setVisible(false);
            privatechat.setVisible(false);
            publicchat.setVisible(false);
            ass.setVisible(false);
            plang.setVisible(false);
            carlo.setVisible(false);

        } else if (event.getSource() == chatbot) {
            chatbotform.setVisible(true);
            chatform.setVisible(false);
            bmi.setVisible(false);
            calory.setVisible(false);
            notice.setVisible(false);
            privatechat.setVisible(false);
            publicchat.setVisible(false);
            ass.setVisible(false);
            plang.setVisible(false);
            carlo.setVisible(false);
        } else if (event.getSource() == bmibutton) {
            bmi.setVisible(true);
            chatform.setVisible(false);
            chatbotform.setVisible(false);
            calory.setVisible(false);
            notice.setVisible(false);
            privatechat.setVisible(false);
            publicchat.setVisible(false);
            ass.setVisible(false);
            plang.setVisible(false);
            carlo.setVisible(false);

        } else if (event.getSource() == plan) {
            calory.setVisible(true);
            chatform.setVisible(false);
            chatbotform.setVisible(false);
            bmi.setVisible(false);
            notice.setVisible(false);
            privatechat.setVisible(false);
            publicchat.setVisible(false);
            ass.setVisible(false);
            plang.setVisible(false);
            carlo.setVisible(false);
        } else if (event.getSource() == plangenerator) {

            calory.setVisible(false);
            chatform.setVisible(false);
            chatbotform.setVisible(false);
            bmi.setVisible(false);
            notice.setVisible(false);
            privatechat.setVisible(false);
            publicchat.setVisible(false);
            ass.setVisible(false);
            plang.setVisible(true);
            carlo.setVisible(false);
        } else if (event.getSource() == calorie) {

            calory.setVisible(false);
            chatform.setVisible(false);
            chatbotform.setVisible(false);
            bmi.setVisible(false);
            notice.setVisible(false);
            privatechat.setVisible(false);
            publicchat.setVisible(false);
            ass.setVisible(false);
            plang.setVisible(false);
            carlo.setVisible(true);
        }
    }

    //secean switch2

    //scence switch

    public void switchform2(javafx.event.ActionEvent event) {
        if (event.getSource() == noticebtn) {
            notice.setVisible(true);
            privatechat.setVisible(false);
            ass.setVisible(false);
            chatform.setVisible(false);
            plang.setVisible(false);
            carlo.setVisible(false);


        } else if (event.getSource() == assbtn) {
            notice.setVisible(false);
            privatechat.setVisible(false);

            ass.setVisible(true);
            chatform.setVisible(false);
            plang.setVisible(false);
            carlo.setVisible(false);

        } else if (event.getSource() == pvtchatbtn) {
            notice.setVisible(false);
            privatechat.setVisible(true);
//            publicchat.setVisible(false);
            ass.setVisible(false);
            chatform.setVisible(false);
            plang.setVisible(false);
            carlo.setVisible(false);


        }
//        } else if (event.getSource() == pubchatbtn) {
//            notice.setVisible(false);
//            privatechat.setVisible(false);
////            publicchat.setVisible(true);
//            ass.setVisible(false);
//            chatform.setVisible(false);
//            plang.setVisible(false);
//            carlo.setVisible(false);
//
//        }


    }


    @FXML
    private void handleBack() {
        notice.setVisible(false);
        privatechat.setVisible(false);
        publicchat.setVisible(false);
        ass.setVisible(false);
        chatform.setVisible(true);

    }


    //bmi calculation and more


    /// initial function
    @FXML
    public void initialize() {

        // cache identity at open time
        myRole = SessionContext.role;          // MEMBER
        myId   = SessionContext.id;
        myName = SessionContext.name;

        // per-window client
        client = new SocketClient();
        client.connect(myRole, myId, myName, MessageBus::dispatch);

        meber_name.setText(myName);

        MessageBus.add(this::onMessage);

        coachId = Router.get().coachForMember(myId);
        boolean hasCoach = coachId != null && !coachId.isBlank();
        chat1TextField.setDisable(!hasCoach);
        member_send_to_coachBtn.setDisable(!hasCoach);

        progressComboBox.getItems().setAll("Not Started", "In Progress", "Blocked", "Done");

        // history
        for (Message m : PersistenceManager.readNotices())
            if (myId.equals(m.toId)) appendNotice(m);

        for (Message m : PersistenceManager.readPrivate()) {
            if (myId.equals(m.toId) && m.fromRole == Role.COACH) appendPrivateFromCoach(m);
            if (m.fromRole == Role.MEMBER && myId.equals(m.fromId)) appendPrivateFromSelf(m);
        }

        for (Message m : PersistenceManager.readTasks()) {
            if (m.type == MessageType.TASK_ASSIGN && myId.equals(m.toId)) appendTaskAssign(m);
            if (m.type == MessageType.TASK_UPDATE && m.fromRole == Role.MEMBER && myId.equals(m.fromId))
                assign1TextArea.appendText("Updated: " + safe(m.taskName) + " -> " + safe(m.taskStatus) + "\n");
        }

        //abirs

        loadDataFromFile();
        // Setup chart
        series.setName("BMI Values");
        weightSeries.setName("Weight");
        bmiChart.getData().addAll(series,weightSeries);
        refreshChart();
        //
        initWorkoutPlan();
        //
        initProgressTracker();
        //
        initCalorieChecker();
        //
        initCalorieTracker();
        //setupDashboardFeatures();


    }



    @FXML
    private void CalculateBmi() {
        try {
            double w = Double.parseDouble(weight.getText());
            double h = Double.parseDouble(hight.getText());
            double bmi = w / (h * h);


            String res = "Weight: " + w + " kg, Height: " + h + " m -> BMI: " + bmi;

            result.setText(res + "\n");

            bmiHistory.add(bmi);
            weightHistory.add(w);
            //Saves data to a file (so it’s not lost) and updates the chart to show the new point.
            saveDataToFile();
            refreshChart();
            //Clears input fields after calculation for a clean UI.
            weight.clear();
            hight.clear();
        } catch (Exception e) {
            result.setText("Valid number plz!!\n");
        }
    }

    //Clears the result area and loops through all saved entries.
    @FXML
    private void showHistory() {
        result.clear();
        for (int i = 0; i < bmiHistory.size(); i++) {
            double weight = weightHistory.get(i);
            double bmi = bmiHistory.get(i);
            result.appendText("Entry " + (i + 1) +
                    ": Weight = " + Math.round(weight * 100.0) / 100.0 +
                    " kg, BMI = " + Math.round(bmi * 100.0) / 100.0 + "\n");
        }
    }

    // Opens a file  and writes weight,BMI pairs on each line.
    private void saveDataToFile() {
        try (BufferedWriter writter = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < bmiHistory.size(); i++) {
                writter.write(weightHistory.get(i) + "," + bmiHistory.get(i));
                writter.newLine();
            }

        } catch (Exception e) {
            result.appendText("error saving file.\n");
        }
    }

    private void loadDataFromFile() {
        //Clears any old data before loading new
        bmiHistory.clear();
        weightHistory.clear();
        File file = new File(filename);
        if (!file.exists()) return;
        //Reads file line by line, splits each line by comma, and adds each weight and BMI back to the lists.
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    double ojon = Double.parseDouble(parts[0]);
                    double vmi = Double.parseDouble(parts[1]);
                    weightHistory.add(ojon);
                    bmiHistory.add(vmi);
                }
//                bmiHistory.add(Double.parseDouble(line));
            }
        } catch (IOException e) {
            result.appendText("error loading file");
        }
    }


    //Loops through all entries and plots BMI and weight on the chart using their index as the X-axis value.
    private void refreshChart() {
        series.getData().clear();
        weightSeries.getData().clear();
        for (int i = 0; i < bmiHistory.size(); i++) {
            series.getData().add(new XYChart.Data<>(i + 1, bmiHistory.get(i)));
            weightSeries.getData().add(new XYChart.Data<>(i + 1, weightHistory.get(i)));
        }
    }

    /// exit

    @FXML
    private void handleExit() {
        System.exit(0);
    }


    /// plan generator


    @FXML
    private ComboBox<String> goalSelector;
    @FXML
    private TextArea workoutPlanArea;

    private final String[] goals = {"Weight Loss", "Muscle Gain", "Strength", "Endurance"};
    private final java.util.Random random = new java.util.Random();

    //Map is like a dictionary
    //It stores key → value pairs.
    //You can later ask for the value using the key.
    //This creates a map where:
    //Each key is a goal name (like "Weight Loss")
    //Each value is an array of String workouts related to that goal

    private final java.util.Map<String, String[]> workoutOptions = Map.of(
            "Weight Loss", new String[]{
                    "- 30 min Cardio daily",
                    "- HIIT 3x/week",
                    "- Light Strength Training",
                    "- 10,000 steps daily",
                    "- Low-carb diet with lean protein"
            },
            "Muscle Gain", new String[]{
                    "- Bench Press and Deadlifts",
                    "- Protein shake after workouts",
                    "- Train each muscle twice/week",
                    "- Eat 5 small high-protein meals",
                    "- Focus on progressive overload"
            },
            "Strength", new String[]{
                    "- Deadlifts 3x/week",
                    "- Squats and Bench Press",
                    "- Rest properly between sets",
                    "- Focus on core strength",
                    "- Track your max lifts"
            },
            "Endurance", new String[]{
                    "- 5km run or cycling daily",
                    "- Swimming 3x/week",
                    "- Full-body stretching",
                    "- Circuit training for stamina",
                    "- Hydration and balanced meals"
            }
    );

    private final String FILE_PATH = "workout_plan.txt";


    //Fills dropdown, loads last saved plan,sets up the event so selecting a goal creates a plan
    public void initWorkoutPlan() {
        // Add goals to dropdown
        goalSelector.getItems().addAll(goals);

        // Load previous plan
        loadSavedPlan();

        // When user selects a goal, generate a new random plan auto with oput button click
        goalSelector.setOnAction(e -> generateRandomPlan());
    }

    @FXML
    private void generateRandomPlan() {
        String selectedGoal = goalSelector.getValue(); // get chosen goal
        if (selectedGoal == null) return;

        //Safety check: make sure the goal has workouts.
        String[] options = workoutOptions.get(selectedGoal);
        if (options == null || options.length == 0) return;

        //Creates an empty list
        java.util.List<String> selectedWorkouts = new java.util.ArrayList<>();
        //it will keep picking workouts until you have 3 unique ones.
        while (selectedWorkouts.size() < 3) {
            //options = list of workouts for the selected goal
            //random.nextInt(options.length) gives a random index number
            String workout = options[random.nextInt(options.length)];
            //Checks if the chosen workout is not already in the list.
            //avoids duplicates so don’t get the same workout twice.
            if (!selectedWorkouts.contains(workout)) {
                //Adds that workout to the list.
                selectedWorkouts.add(workout);
            }
        }

        //Builds a nicely formatted workout plan text.
        StringBuilder plan = new StringBuilder("Workout Plan for " + selectedGoal + ":\n\n");
        for (String line : selectedWorkouts) {
            plan.append(line).append("\n");
        }

        //Shows the plan in the text area and saves it to file
        workoutPlanArea.setText(plan.toString());
        savePlanToFile(plan.toString());
    }

    //Saves the plan text to workout_plan.txt
    private void savePlanToFile(String planText) {
        try (java.io.FileWriter writer = new java.io.FileWriter(FILE_PATH)) {
            writer.write(planText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSavedPlan() {
        java.io.File file = new java.io.File(FILE_PATH);
        if (file.exists()) {
            try (java.util.Scanner scanner = new java.util.Scanner(file)) {
                StringBuilder content = new StringBuilder();
                while (scanner.hasNextLine()) {
                    content.append(scanner.nextLine()).append("\n");
                }
                //Converts the collected text into a string and shows it in the TextArea
                workoutPlanArea.setText(content.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private ProgressBar progressBar;


    private final String PROGRESS_FILE = "progress.txt";
    private double progress = 0.0;

    public void initProgressTracker() {
        // Load saved progress instantly
        progress = loadProgress();//Loads saved progress and sets it visually.
        progressBar.setProgress(progress);
    }


    @FXML
    private void handleDoneButton() {

        workoutPlanArea.clear();// clear old plan

        clearWorkoutFile(); // clear file


        progress += 0.02;

        if (progress >= 1.0) { // if 100% reached

            javafx.application.Platform.runLater(() -> {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setTitle("Motivation!");
                alert.setHeaderText("🎉 Milestone Completed! Keep Going! 🎉\nHere is your coupon code," + generateCoupon() + "\nPlease go to the counter to redeem it.");
                alert.showAndWait();
            });

            // reset progress after full completion

            progress = 0.0;
        }

        // visual updatee
        progressBar.setProgress(progress);

        // sve progress to file
        saveProgress(progress);
    }

    // Save current progress to file
    private void saveProgress(double value) {
        try (java.io.FileWriter writer = new java.io.FileWriter(PROGRESS_FILE)) {
            writer.write(Double.toString(value));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load progress from file
    private double loadProgress() {
        java.io.File file = new java.io.File(PROGRESS_FILE);
        if (file.exists()) {
            try (java.util.Scanner scanner = new java.util.Scanner(file)) {
                if (scanner.hasNextDouble()) {
                    return scanner.nextDouble();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0.0;// if no file found
    }


    // clear the workout plan file
    //Empties the workout plan file when a new session starts.
    private void clearWorkoutFile() {
        try (java.io.FileWriter writer = new java.io.FileWriter("workout_plan.txt")) {
            writer.write("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /// calory checker


    @FXML
    private TextField foodInput;
    @FXML
    private TextArea calorieResult;
    //Creates a map with key .foodnmae and valuecalories

    private Map<String, Integer> foodCalories = new HashMap<>();

    public void initCalorieChecker() {
        initFoodCalories();//First, it loads all food items and their calories into the map.

        // Auto update calories while typing(gpt use)
        foodInput.textProperty().addListener((obs, oldText, newText) -> showCalories(newText));

        // Or press Enter to check
        // foodInput.setOnAction(e -> showCalories(foodInput.getText()));
    }

    private void showCalories(String foodName) {
        if (foodName == null || foodName.isEmpty()) {
            //If the input is empty, it clears the result and stops.
            calorieResult.clear();
            return;
        }

        // Looks for the food name in the map
        Integer calories = foodCalories.get(foodName.toLowerCase().trim());
        if (calories != null) {
            calorieResult.setText("✅ " + capitalize(foodName) + ": " + calories + " kcal");
        } else {
            calorieResult.setText("⚠️ Food not found in database.");
        }
    }

    private void initFoodCalories() {
        foodCalories.put("apple", 95);
        foodCalories.put("banana", 105);
        foodCalories.put("rice", 200);
        foodCalories.put("roti", 246);
        foodCalories.put("egg", 75);
        foodCalories.put("chicken", 190);
        foodCalories.put("hilsa", 450);
        foodCalories.put("tilapia", 150);
        foodCalories.put("milk", 120);
        foodCalories.put("lentils", 226);
        foodCalories.put("vegetable curry", 110);
        foodCalories.put("aloo bhorta", 150);
        foodCalories.put("begun bhaja", 114);
        foodCalories.put("fish curry", 323);
        foodCalories.put("biriyani", 418);
        foodCalories.put("fried rice", 258);

    }

    //gpt
    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }


    /// calory tracker


    @FXML
    private TextField foodInput1;
    @FXML
    private TextField calorieInput;
    @FXML
    private TextField goalInput;
    @FXML
    private ComboBox<String> mealSelector;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ProgressBar progressBar1;
    @FXML
    private TextArea resultAreap;

    private double totalCalories = 0;
    private double calorieGoal = 0;
    private final String FILE_PATH1 = "calories_data.txt";
    private final String File_Path2 = "caloryhistory.txt";
    private final String TODAY_FILE = "today_data.txt";


    // Custom init (since initialize() is used elsewhere)
    //Initializes meal list, sets today’s date, and loads old data + history.
    public void initCalorieTracker() {
        mealSelector.getItems().addAll("Breakfast", "Lunch", "Snack", "Dinner");
        datePicker.setValue(LocalDate.now());

        // Load saved data and progress
        loadSavedData();
        loadMealHistory();
        loadTodayData();
    }

    private void loadTodayData() {
        File file = new File(TODAY_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            resultAreap.setText(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadMealHistory() {
        File historyFile = new File(File_Path2);
        if (!historyFile.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(historyFile))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            resultAreap.setText(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleSetGoal() {
        try {
            calorieGoal = Double.parseDouble(goalInput.getText());
            updateProgress();

            showMessage("Goal set to " + calorieGoal + " kcal for today!");
        } catch (Exception e) {
            showMessage("⚠️ Please enter a valid number for the goal.");
        }
    }

    //Takes input from goalInput, sets calorieGoal, updates progress bar, and shows confirmation.

    @FXML
    private void handleAddMeal() {
        String foods = foodInput1.getText().trim();
        String cals = calorieInput.getText().trim();
        String meal = mealSelector.getValue();
        LocalDate date = datePicker.getValue();

        // Save today's meals
        try (FileWriter writer = new FileWriter(TODAY_FILE)) { // overwrite each time
            writer.write(resultAreap.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (foods.isEmpty() || cals.isEmpty() || meal == null || date == null) {
            showMessage("⚠️ Please fill all fields.");
            return;
        }
        //Splits multiple foods and calories

        String[] foodItems = foods.split(",");
        String[] calorieItems = cals.split(",");

        if (foodItems.length != calorieItems.length) {
            showMessage("⚠️ Number of foods and calories must match.");
            return;
        }

        // meal entry
        StringBuilder entry = new StringBuilder("📅 " + date + " | " + meal + ":\n");
        double sum = 0;

        for (int i = 0; i < foodItems.length; i++) {
            String food = foodItems[i].trim();
            double cal = Double.parseDouble(calorieItems[i].trim());
            entry.append("   • ").append(food).append(": ").append(cal).append(" kcal\n");
            sum += cal;
        }

        entry.append("Total for this meal: ").append(sum).append(" kcal\n\n");

        // Show in today TextArea
        resultAreap.appendText(entry.toString());

        // Update progress
        totalCalories += sum;
        updateProgress();

        //Saves data to files (today, daily, history).
        saveDailyData();


        appendToHistory(entry.toString());
        saveTodayData();

        // Clear input fields
        foodInput1.clear();
        calorieInput.clear();
        mealSelector.setValue(null);

        //If goal reached → shows message + resets for next day
        if (totalCalories >= calorieGoal && calorieGoal > 0) {
            showMessage("⚠️ You reached your calorie goal! Stop eating 😊");
            clearForNextDay();
        }

    }

    private void saveTodayData() {
        try (FileWriter writer = new FileWriter(TODAY_FILE)) {
            writer.write(resultAreap.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //saveDailyData() → saves total + goal
    private void saveDailyData() {
        try (FileWriter writer = new FileWriter(FILE_PATH1)) { // overwrite each day
            writer.write("Goal:" + calorieGoal + "\n");
            writer.write("Total:" + totalCalories + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //saveDailyData() → saves total + goal
    private void appendToHistory(String entry) {
        try (FileWriter writer = new FileWriter(File_Path2, true)) {
            writer.write(entry); // ONLY meal entries
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleShowHistory() {
        File historyFile = new File(File_Path2); // meal history

        if (!historyFile.exists()) {
            showMessage("⚠️ No history found yet.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(historyFile))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            resultAreap.setText(content.toString()); // only meals
            showMessage("📖 Showing full history.");
        } catch (IOException e) {
            showMessage("❌ Error loading history file.");
            e.printStackTrace();
        }
    }


    //Clears today’s data, resets calories & progress bar, saves it, and confirms.
    @FXML
    private void handleFinishDay() {
        clearForNextDay();
        showMessage("✅ Day finished. Data saved to history.");
    }

    // Updates progress bar according to (totalCalories / calorieGoal)
    private void updateProgress() {
        if (calorieGoal > 0) {
            double progress = Math.min(totalCalories / calorieGoal, 1.0);
            progressBar1.setProgress(progress);
        }
    }

//load for initial show

    private void loadSavedData() {
        File file = new File(FILE_PATH1);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Goal:")) {
                    calorieGoal = Double.parseDouble(line.substring(5).trim());
                } else if (line.startsWith("Total:")) {
                    totalCalories = Double.parseDouble(line.substring(6).trim());
                }
            }
            updateProgress();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Clears the text area, resets total calories and progress.
    private void clearForNextDay() {
        resultAreap.clear();
        totalCalories = 0;
        progressBar1.setProgress(0);
        saveDailyData();
    }

    private void showMessage(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Calorie Tracker");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }

    @FXML
    private void handleClearTextArea() {
        resultAreap.clear(); // Clears only the TextArea temporarily
        showMessage("🧹 Text area cleared.");
    }

    private static String generateCoupon() {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final SecureRandom random = new SecureRandom();

        StringBuilder coupon = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(CHARACTERS.length());
            coupon.append(CHARACTERS.charAt(index));
        }
        return coupon.toString();


    }
}





