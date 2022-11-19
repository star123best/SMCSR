package Controllers.students;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Font;


public class homePage {
    @FXML
    private Label mainLabel;
    static String username = null;
    public void main_name(String username_final){
        username = username_final;
        System.out.println("Username1: " + username);
    }
    public void initialize(){
        //Font font = Font.loadFont("file:resources/fonts//roboto/Roboto-Black.ttf", 70);
        //Font font = Font.loadFont("C:/Users/user/Desktop/Java/SMCSR_Files/Fonts/roboto/Roboto-Black.ttf", 70);
        //Font font = Font.loadFont(homePage.class.getResource("/Roboto-Black.ttf").toExternalForm(), 70);
        //System.out.println("File: " + homePage.class.getResource("/Roboto-Black.ttf"));
        //mainLabel.setFont(font);
        System.out.println("Username2: " + username);
    }
}
