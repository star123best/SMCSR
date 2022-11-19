package Controllers;
import Controllers.students.homePage;
import Controllers.loadingController;

import javafx.animation.*;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.apache.tools.ant.taskdefs.Java;

import javax.swing.*;
import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import java.util.Timer;
import java.util.TimerTask;

//MongoDB

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.apache.log4j.BasicConfigurator;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import java.util.Iterator;



public class loginController {
    @FXML
    private Label sign_up_button;
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    public loadingController loadingcontroller = new loadingController();
    public void loadingScene(String username, String password){
        Boolean complete = false;
        Parent FXML = null;
        try {
            //FXML = FXMLLoader.load(javaFX.JavaFX.class.getResource("/fxml/students/homePage.fxml"));
            FXML = FXMLLoader.load(javaFX.JavaFX.class.getResource("/fxml/loading.fxml"));
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(FXML, 600, 600);

            stage.sceneProperty().addListener((observable, oldScene, newScene) -> {
                System.out.println("New scene: " + newScene);
                System.out.println("Old scene: " + oldScene);
                System.out.println("Scene to change to: " + scene);
                System.out.println("Current scene: " + stage.getScene());
                System.out.println("Fired");
            });

            stage.setOnShown(e -> {
                if(stage.getScene().equals(scene)){
                    PauseTransition pause = new PauseTransition(Duration.millis(5));
                    pause.playFromStart();
                    pause.setOnFinished(action -> {
                        getUser(username, password);
                    });

                }
            });

            stage.setScene(scene);
            stage.close();
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void getUser(String username, String password){
        Thread thread = new Thread(() -> {

            /*Parent FXML = null;
        try {
            FXML = FXMLLoader.load(javaFX.JavaFX.class.getResource("/fxml/loading.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Scene scene = new Scene(FXML, 600, 600);
        stage.setScene(scene);
        stage.show();*/

            BasicConfigurator.configure();
            ConnectionString connectionString = new ConnectionString("mongodb+srv://star123best:R2NX0djzvjx3uvbZ@cluster0.lhvsfej.mongodb.net/?retryWrites=true&w=majority");
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .build();
            MongoClient mongoClient =  MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase("SMCSR");
        /*MongoCollection<Document> test = database.getCollection("Users");
        Document user = new Document("name", "test");
        test.insertOne(user);
        System.out.println("Collection created successfully");*/


            // Retrieving a collection
            MongoCollection<Document> studentsColl = database.getCollection("students");
            MongoCollection<Document> adminsColl = database.getCollection("admins");
            MongoCollection<Document> teachersColl = database.getCollection("teachers");
            MongoCollection<Document> secretarysColl = database.getCollection("secretarys");

            Iterator<Document> studentDoc = studentsColl.find().iterator();
            Iterator<Document> teachersDoc = teachersColl.find().iterator();
            Iterator<Document> adminsDoc = adminsColl.find().iterator();
            Iterator<Document> secretarysDoc = secretarysColl.find().iterator();
            //FindIterable<Document> studentDoc = students.find();
            //Iterator it = iterDoc.iterator();
            String username_acc = null;
            Boolean acc_found = false;
            int rank = 1;
            while(studentDoc.hasNext() || adminsDoc.hasNext() || teachersDoc.hasNext() || secretarysDoc.hasNext() && acc_found == false){

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String encodedPassword = password;
                for(int i = 0; i < 5; i++){
                    encodedPassword = Base64.getEncoder().encodeToString(encodedPassword.getBytes());
                }

                Document student_doc = (Document) studentDoc.next();
                Document teachers_doc = (Document) teachersDoc.next();
                Document admins_Doc = (Document) adminsDoc.next();
                Document secretarys_Doc = (Document) secretarysDoc.next();
                Boolean username_correct = false;
                Boolean password_correct = false;

                if(student_doc.containsKey(username)){
                    username_correct = true;
                    Document get_pass = (Document) student_doc.get(username);
                    if(get_pass.get("password").equals(encodedPassword)){
                        System.out.println("Password Correct");
                        username_acc = username;
                        password_correct = true;
                        acc_found = true;
                    } else {
                        username_correct = false;
                    }
                } else if(teachers_doc.containsKey(username)){
                    username_correct = true;
                    Document get_pass = (Document) teachers_doc.get(username);
                    if(get_pass.get("password").equals(encodedPassword)){
                        System.out.println("Password Correct");
                        username_acc = username;
                        password_correct = true;
                        acc_found = true;
                        rank = 2;
                    } else {
                        username_correct = false;
                    }
                } else if(secretarys_Doc.containsKey(username)){
                    username_correct = true;
                    Document get_pass = (Document) secretarys_Doc.get(username);
                    if(get_pass.get("password").equals(encodedPassword)){
                        System.out.println("Password Correct");
                        username_acc = username;
                        password_correct = true;
                        acc_found = true;
                        rank = 3;
                    } else {
                        username_correct = false;
                    }
                } else if(admins_Doc.containsKey(username)){
                    username_correct = true;
                    Document get_pass = (Document) admins_Doc.get(username);
                    if(get_pass.get("password").equals(encodedPassword)){
                        System.out.println("Password Correct");
                        username_acc = username;
                        password_correct = true;
                        acc_found = true;
                        rank = 4;
                    } else {
                        username_correct = false;
                    }
                }
                System.out.println(gson.toJson(student_doc));
            }
            if(acc_found == true){
                homePage main = new homePage();
                if(rank == 1){
                    loadingController.changeScene("homePage");
                    //loadingController.changeScene(FXMLLoader.load(javaFX.JavaFX.class.getResource("/fxml/students/homePage.fxml")));
                } else if(rank == 2){
                    //Teacher
                } else if(rank == 3){
                    //Secretary
                } else if(rank == 4){
                    //Admin
                }
            } else {
                //account not found
            }
        /*while (it.hasNext()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Document doc = (Document) it.next();
            Document students_doc = (Document) doc.get("students");
            Document teachers_doc = (Document) doc.get("teachers");
            Document admins_doc = (Document) doc.get("admins");
            Document secretarys_doc = (Document) doc.get("secretarys");

            String encodedPassword = password;
            for(int i = 0; i < 5; i++){
                encodedPassword = Base64.getEncoder().encodeToString(encodedPassword.getBytes());
            }

            Boolean username_correct = false;
            Boolean password_correct = false;
            if(students_doc.containsKey(username)){
                System.out.println("Username Correct");
                username_correct = true;
                Document get_pass = (Document) students_doc.get(username);
                if(get_pass.get("password").equals(encodedPassword)){
                    System.out.println("Password Correct");
                    password_correct = true;
                }
            } else if(teachers_doc.containsKey(username)){
                username_correct = true;
                Document get_pass = (Document) teachers_doc.get(username);
                if(get_pass.get("password").equals(encodedPassword)){
                    password_correct = true;
                }
            } else if(admins_doc.containsKey(username)){
                username_correct = true;
                Document get_pass = (Document) admins_doc.get(username);
                if(get_pass.get("password").equals(encodedPassword)){
                    password_correct = true;
                }
            } else if(secretarys_doc.containsKey(username)){
                username_correct = true;
                Document get_pass = (Document) secretarys_doc.get(username);
                if(get_pass.get("password").equals(encodedPassword)){
                    password_correct = true;
                }

                if(username_correct == true && password_correct == true){
                    System.out.println("Correct");
                } else if(username_correct == false){
                    System.out.println("Username icorrect");
                    usernameField.setStyle("-fx-border-color: #3a3e44 #3a3e44 red #3a3e44; -fx-background-color: #1f2329; -fx-text-fill: #b2b2b2;");
                } if(password_correct == false){
                    System.out.println("Password icorrect");
                    passwordField.setStyle("-fx-border-color: #3a3e44 #3a3e44 red #3a3e44; -fx-background-color: #1f2329; -fx-text-fill: #b2b2b2;");
                }
            }
            //System.out.println(gson.toJson(students_doc));
        }*/


        });

        thread.start();



    }
    public void initialize(){

        usernameField.focusedProperty().addListener(test -> {
            if(usernameField.isFocused() == true){
                usernameField.setStyle("-fx-border-color: white; -fx-border-width: 1px; -fx-background-color: #1f2329; -fx-text-fill: #b2b2b2;");
            } else {
                usernameField.setStyle("-fx-border-color: #3a3e44; -fx-background-color: #1f2329; -fx-text-fill: #b2b2b2;");
            }
        });

        passwordField.focusedProperty().addListener(test -> {
            if(passwordField.isFocused() == true){
                passwordField.setStyle("-fx-border-color: white; -fx-border-width: 1px; -fx-background-color: #1f2329; -fx-text-fill: #b2b2b2;");
            } else {
                passwordField.setStyle("-fx-border-color: #3a3e44; -fx-background-color: #1f2329; -fx-text-fill: #b2b2b2;");
            }
        });


        sign_up_button.setOnMouseEntered(entered -> {
            sign_up_button.setStyle("-fx-effect: dropshadow(three-pass-box, rgb(255,255,255), 5, 0, 0, 1);");
            TranslateTransition translate = new TranslateTransition(Duration.millis(400), sign_up_button);
            translate.setByY(-10);
            translate.setCycleCount(1);
            translate.setAutoReverse(false);
            translate.play();
        });

        sign_up_button.setOnMouseExited(entered -> {
            sign_up_button.setStyle("-fx-effect: dropshadow(three-pass-box, rgb(255,255,255), 0, 0, 0, 0);");
            TranslateTransition translate = new TranslateTransition(Duration.millis(400), sign_up_button);
            translate.setByY(10);
            translate.setCycleCount(1);
            translate.setAutoReverse(false);
            translate.play();
        });

        sign_up_button.setOnMouseClicked(clicked -> {
            try {
                Parent FXML = FXMLLoader.load(javaFX.JavaFX.class.getResource("/fxml/signup.fxml"));
                System.out.println("Fired");
                Stage stage = (Stage) sign_up_button.getScene().getWindow();
                Scene scene = new Scene(FXML, 600, 600);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        loginButton.setOnMouseEntered(entered -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(400), loginButton);
            scale.setToX(0.95);
            scale.setToY(0.95);
            scale.setCycleCount(1);
            scale.setAutoReverse(false);
            scale.play();
        });

        loginButton.setOnMouseExited(exited -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(400), loginButton);
            scale.setToX(1);
            scale.setToY(1);
            scale.setCycleCount(1);
            scale.setAutoReverse(false);
            scale.play();
        });

        loginButton.setOnMouseClicked(clicked -> {
            System.out.println(usernameField.getText());
            System.out.println(passwordField.getText());
            if(usernameField.getText().equals("")){
                usernameField.setStyle("-fx-border-color: #3a3e44 #3a3e44 red #3a3e44; -fx-background-color: #1f2329; -fx-text-fill: #b2b2b2;");
            } if(passwordField.getText().equals("")){
                passwordField.setStyle("-fx-border-color: #3a3e44 #3a3e44 red #3a3e44; -fx-background-color: #1f2329; -fx-text-fill: #b2b2b2;");
            } else if(!usernameField.getText().equals("") && !passwordField.getText().equals("")){
                loadingScene(usernameField.getText(), passwordField.getText());
            }
        });
    }
}
