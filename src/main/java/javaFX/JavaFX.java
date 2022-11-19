package javaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent FXML = FXMLLoader.load(JavaFX.class.getResource("/fxml/login.fxml"));
        Parent FXML = FXMLLoader.load(JavaFX.class.getResource("/fxml/students/homePage.fxml"));
        String css = JavaFX.class.getResource("/css/style.css").toExternalForm();

        Scene scene = new Scene(FXML, 600, 600);
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SMCSR");
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}
