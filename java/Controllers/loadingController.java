package Controllers;
import Controllers.loginController;

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
import javafx.util.Duration;
import org.apache.tools.ant.taskdefs.Java;

import javax.swing.*;
import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.Base64;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.Timer;
import java.util.TimerTask;

public class loadingController {
    @FXML
    private Circle cirlce1;
    @FXML
    private Circle cirlce2;
    @FXML
    private Circle cirlce3;
    @FXML
    private Circle cirlce4;
    @FXML
    public Circle circle5;

    public static String test = "";


    public static void changeScene(String file){
        test = file;
        /*System.out.println("Fired5: " + file);

        Stage stage = (Stage) circle5.getScene().getWindow();
        Scene scene = new Scene(file, 600, 600);
        stage.setScene(scene);
        stage.show();*/
    }
    public void initialize() {


        TranslateTransition tl1 = new TranslateTransition(Duration.millis(300), cirlce1);
        TranslateTransition tl2 = new TranslateTransition(Duration.millis(600), cirlce2);
        TranslateTransition tl3 = new TranslateTransition(Duration.millis(900), cirlce3);
        TranslateTransition tl4 = new TranslateTransition(Duration.millis(1200), cirlce4);
        PauseTransition pt = new PauseTransition(Duration.millis(500));
        tl1.setByY(-30);
        tl2.setByY(-30);
        tl3.setByY(-30);
        tl4.setByY(-30);

            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(0), action -> {
                tl1.setByY(-30);
                tl2.setByY(-30);
                tl3.setByY(-30);
                tl4.setByY(-30);
                tl1.play();
                tl2.play();
                tl3.play();
                tl4.play();

            }), new KeyFrame(Duration.millis(1500), action -> {
                tl1.setByY(30);
                tl2.setByY(30);
                tl3.setByY(30);
                tl4.setByY(30);
                tl1.play();
                tl2.play();
                tl3.play();
                tl4.play();
            }), new KeyFrame(Duration.seconds(3), action -> {
                tl1.setByY(-30);
                tl2.setByY(-30);
                tl3.setByY(-30);
                tl4.setByY(-30);
                tl1.play();
                tl2.play();
                tl3.play();
                tl4.play();
            }));
            pt.playFromStart();
            pt.setOnFinished(e -> {
                timeline.playFromStart();
            });
            timeline.setOnFinished(e -> {
                if(test.equals("homePage")){
                    System.out.println("Fired");
                    System.out.println(circle5.getScene());

                    Parent FXML = null;
                    try {
                        FXML = FXMLLoader.load(javaFX.JavaFX.class.getResource("/fxml/students/homePage.fxml"));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Stage stage = (Stage) circle5.getScene().getWindow();
                    Scene scene = new Scene(FXML, 600, 600);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    timeline.playFromStart();
                }

            });
    }
}
