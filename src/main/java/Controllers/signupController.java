package Controllers;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Base64;

//MongoDB

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.apache.log4j.BasicConfigurator;
import org.bson.Document;

import java.util.Iterator;

public class signupController {
    @FXML
    private Label loginButton;
    @FXML
    private Button createAccountButton;
    @FXML
    private TextField emailField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    public void createAccount(String username, String password, String email){
        BasicConfigurator.configure();
        ConnectionString connectionString = new ConnectionString("mongodb+srv://star123best:R2NX0djzvjx3uvbZ@cluster0.lhvsfej.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient =  MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("SMCSR");
        MongoCollection<Document> collection = database.getCollection("students");

        String encodedPassword = password;
        for(int i = 0; i < 5; i++){
            encodedPassword = Base64.getEncoder().encodeToString(encodedPassword.getBytes());
        }

        Document account_settings = new Document();
        account_settings.append("username", username).append("password", encodedPassword).append("email", email).append("apprasils", new Document()).append("infractions", new Document());
        Document mainAccount = new Document();
        mainAccount.append(username, account_settings);

        collection.insertOne(mainAccount);
        System.out.println("Account added");
    }
    public void getUser(String username, String password){
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
        MongoCollection<Document> collection = database.getCollection("Users");

        FindIterable<Document> iterDoc = collection.find();
        Iterator it = iterDoc.iterator();

        while (it.hasNext()) {
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
        }
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

        emailField.focusedProperty().addListener(test -> {
            if(emailField.isFocused() == true){
                emailField.setStyle("-fx-border-color: white; -fx-border-width: 1px; -fx-background-color: #1f2329; -fx-text-fill: #b2b2b2;");
            } else {
                emailField.setStyle("-fx-border-color: #3a3e44; -fx-background-color: #1f2329; -fx-text-fill: #b2b2b2;");
            }
        });


        loginButton.setOnMouseEntered(entered -> {
            loginButton.setStyle("-fx-effect: dropshadow(three-pass-box, rgb(255,255,255), 5, 0, 0, 1);");
            TranslateTransition translate = new TranslateTransition(Duration.millis(400), loginButton);
            translate.setByY(-10);
            translate.setCycleCount(1);
            translate.setAutoReverse(false);
            translate.play();
        });

        loginButton.setOnMouseExited(entered -> {
            loginButton.setStyle("-fx-effect: dropshadow(three-pass-box, rgb(255,255,255), 0, 0, 0, 0);");
            TranslateTransition translate = new TranslateTransition(Duration.millis(400), loginButton);
            translate.setByY(10);
            translate.setCycleCount(1);
            translate.setAutoReverse(false);
            translate.play();
        });

        loginButton.setOnMouseClicked(clicked -> {
            try {
                Parent FXML = FXMLLoader.load(javaFX.JavaFX.class.getResource("/fxml/login.fxml"));
                System.out.println("Fired");
                Stage stage = (Stage) loginButton.getScene().getWindow();
                Scene scene = new Scene(FXML, 600, 600);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        createAccountButton.setOnMouseEntered(entered -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(400), createAccountButton);
            scale.setToX(0.95);
            scale.setToY(0.95);
            scale.setCycleCount(1);
            scale.setAutoReverse(false);
            scale.play();
        });

        createAccountButton.setOnMouseExited(exited -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(400), createAccountButton);
            scale.setToX(1);
            scale.setToY(1);
            scale.setCycleCount(1);
            scale.setAutoReverse(false);
            scale.play();
        });

        createAccountButton.setOnMouseClicked(clicked -> {
            if(usernameField.getText().equals("")){
                usernameField.setStyle("-fx-border-color: #3a3e44 #3a3e44 red #3a3e44; -fx-background-color: #1f2329; -fx-text-fill: #b2b2b2;");
            } if(passwordField.getText().equals("")){
                passwordField.setStyle("-fx-border-color: #3a3e44 #3a3e44 red #3a3e44; -fx-background-color: #1f2329; -fx-text-fill: #b2b2b2;");
            } if(emailField.getText().equals("")){
                emailField.setStyle("-fx-border-color: #3a3e44 #3a3e44 red #3a3e44; -fx-background-color: #1f2329; -fx-text-fill: #b2b2b2;");
            } else if(!usernameField.getText().equals("") && !passwordField.getText().equals("")){
                createAccount(usernameField.getText(), passwordField.getText(), emailField.getText());
            }
        });
    }
}
