//Main imports

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.awt.Color;
import java.time.LocalDate;

//JSON imports
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class GUI{
    static int login_complete = 0;
    static Random objGenerator = new Random();
    static int student_pages = 0;
    static int teacher_pages = 0;
    static int admin_pages = 0;
    static int infractions_generator = 0;
    //objGenerator.nextInt();
    static int infractions_reasons = objGenerator.nextInt(3);
    static int appraisals = objGenerator.nextInt(2);
    static int appraisals_reasons = objGenerator.nextInt(3);
    static int confirm = 0;
    static String username_final = "";


    void file(String file) throws IOException{
        FileWriter fw = new FileWriter("C:/Users/user/Desktop/Coding stuff files/Java/SMCSR");
    }
    static void signup(){
        JFrame signup = new JFrame();
        signup.setSize(600,600);
        JLabel title = new JLabel ("Create your account");
        JLabel username_label = new JLabel ("Username:");
        JLabel email_label = new JLabel ("Email:");
        JLabel password_label = new JLabel ("Password:");
        JLabel confirm_password_label = new JLabel ("Confirm password:");
        JTextField username = new JTextField (1);
        JTextField email = new JTextField (1);
        JTextField password = new JTextField (1);
        JTextField confirm_password = new JTextField (1);
        JButton signup_ready = new JButton ("Signup");
        JLabel login = new JLabel ("Log in!");
        JLabel login2 = new JLabel("Already have an account?");

        //Error messages
        JLabel missing_fields = new JLabel("Please fill in all the required fields.", SwingConstants.CENTER);
        JLabel mismatching_passwords = new JLabel("Please ensure that both passwords match.", SwingConstants.CENTER);
        JLabel account_exists_username = new JLabel("An account with that username already exists.", SwingConstants.CENTER);


        Font font = new Font ("Calibri", Font.BOLD, 30);
        Font font2 = new Font ("Calibri", Font.BOLD, 20);
        title.setFont(font);
        login.setFont(font2);
        login2.setFont(font2);
        username_label.setFont(font2);
        email_label.setFont(font2);
        password_label.setFont(font2);
        confirm_password_label.setFont(font2);

        JPanel username_fill = new JPanel();
        username_fill.setLayout (null);

        login.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                signup.setVisible(false);
                try {
                    GUI.main(new String[0]);
                } catch (IOException ex) {
                    System.out.println("An error occoured: " + ex);
                    throw new RuntimeException(ex);
                }
            }
        });

        signup_ready.addActionListener(e -> {
            String passwordtext = password.getText();
            String confirmpasswordtext = confirm_password.getText();
            String usernametext = username.getText();
            String emailtext = email.getText();
            Boolean missing_fields_var = false;

            Boolean account_made = false;
            if(usernametext.equals("")){
                username.setBorder(new LineBorder(Color.RED, 1));
                username_fill.add(missing_fields);
                username_fill.remove(mismatching_passwords);
                missing_fields_var = true;
                SwingUtilities.updateComponentTreeUI(signup);
            }if(emailtext.equals("")){
                email.setBorder(new LineBorder(Color.RED, 1));
                missing_fields_var = true;
                username_fill.add(missing_fields);
                username_fill.remove(mismatching_passwords);
                SwingUtilities.updateComponentTreeUI(signup);
            }if(passwordtext.equals("")){
                password.setBorder(new LineBorder(Color.RED, 1));
                username_fill.add(missing_fields);
                username_fill.remove(mismatching_passwords);
                SwingUtilities.updateComponentTreeUI(signup);
                missing_fields_var = true;
            }if(confirmpasswordtext.equals("")){
                confirm_password.setBorder(new LineBorder(Color.RED, 1));
                username_fill.add(missing_fields);
                username_fill.remove(mismatching_passwords);
                missing_fields_var = true;
                SwingUtilities.updateComponentTreeUI(signup);
            } if(!passwordtext.equals(confirmpasswordtext) && missing_fields_var == false){
                password.setBorder(new LineBorder(Color.RED, 1));
                confirm_password.setBorder(new LineBorder(Color.RED, 1));
                username_fill.remove(missing_fields);
                username_fill.add(mismatching_passwords);
                SwingUtilities.updateComponentTreeUI(signup);
            } if(!usernametext.equals("") && !emailtext.equals("") && !passwordtext.equals("") && !confirmpasswordtext.equals("") && passwordtext.equals(confirmpasswordtext)){

                username.setBorder(new LineBorder(Color.BLACK, 2));
                email.setBorder(new LineBorder(Color.BLACK, 2));
                password.setBorder(new LineBorder(Color.BLACK, 10));
                confirm_password.setBorder(new LineBorder(Color.BLACK, 2));

                JsonObject json_elements = new JsonObject();
                    String fileName = "C:/Users/user/Desktop/Coding stuff files/Java/SMCSR/users.json";
                    Path path = Paths.get(fileName);

                    try (Reader reader = Files.newBufferedReader(path,
                            StandardCharsets.UTF_8)) {
                        System.out.println("Reader fired!");

                        JsonParser parser = new JsonParser();
                        JsonElement tree = parser.parse(reader);

                        Gson gson = new GsonBuilder().setPrettyPrinting().create();

                        JsonObject json_tree = tree.getAsJsonObject();

                        for(int i = 0; i < json_tree.size(); i++){
                            json_elements = json_tree;
                        }

                        try{
                            if(json_elements.get("students").getAsJsonObject().has(usernametext)){
                                username_fill.remove(missing_fields);
                                username_fill.remove(mismatching_passwords);
                                username_fill.add(account_exists_username);
                                SwingUtilities.updateComponentTreeUI(signup);
                                System.out.println("An account with that name has already been made.");
                                account_made = true;
                            }
                        }  catch(Exception ex) {
                            System.out.println("An error has occoured while signing up: " + ex);
                            System.out.println("This error could have occoured due to the account not being in our database.");
                        }

                    }  catch (Exception ex){
                        System.out.println("Error found while reading: " + ex);
                    }

                    if(account_made == false){
                        String encoded_password = passwordtext;
                        for(int counter = 0; counter < 5; counter++){
                            encoded_password = Base64.getEncoder().encodeToString(encoded_password.getBytes());
                        }
                        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                            System.out.println("Writer fired!");

                            Gson gson = new GsonBuilder().setPrettyPrinting().create();


                            JsonObject json_obj = new JsonObject();
                            json_obj.addProperty("username", usernametext);
                            json_obj.addProperty("password", encoded_password);
                            json_obj.addProperty("email", emailtext);

                            JsonObject apprasils_obj = new JsonObject();
                            JsonObject infractions_obj = new JsonObject();

                            json_obj.add("apprasils", apprasils_obj);
                            json_obj.add("infractions", infractions_obj);
                            json_elements.get("students").getAsJsonObject().add(usernametext, json_obj);

                            JsonElement tree = gson.toJsonTree(json_elements);
                            gson.toJson(tree, writer);

                            signup.setVisible(false);
                            student_main();
                        }  catch(Exception ex){
                            System.out.println("Error found while writing: " + ex);
                        }
                        username_final = usernametext;
                        System.out.println("Complete");
                    }
                }

        });

        missing_fields.setBackground(new Color(239, 79, 79, 255));
        mismatching_passwords.setBackground(new Color(239, 79, 79, 255));
        account_exists_username.setBackground(new Color(239, 79, 79, 255));

        missing_fields.setForeground(Color.black);
        mismatching_passwords.setForeground(Color.black);
        account_exists_username.setForeground(Color.black);

        missing_fields.setOpaque(true);
        mismatching_passwords.setOpaque(true);
        account_exists_username.setOpaque(true);

        login.setForeground(Color.BLUE.darker());
        login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        username.setBorder(new LineBorder(Color.BLACK, 1));
        email.setBorder(new LineBorder(Color.BLACK, 1));
        password.setBorder(new LineBorder(Color.BLACK, 1));
        confirm_password.setBorder(new LineBorder(Color.BLACK, 1));

        title.setBounds (160, 20, 295, 35);
        username_label.setBounds (90, 100, 100, 25);
        email_label.setBounds (125, 150, 90, 25);
        password_label.setBounds (90, 205, 105, 25);
        confirm_password_label.setBounds (15, 265, 170, 25);
        username.setBounds (185, 95, 190, 35);
        email.setBounds (185, 145, 190, 35);
        password.setBounds (185, 200, 190, 35);
        confirm_password.setBounds (185, 260, 190, 35);
        signup_ready.setBounds (220, 320, 115, 40);
        login2.setBounds(160, 400, 225, 30);
        login.setBounds (380, 400, 60, 30);
        missing_fields.setBounds(150, 450, 275, 40);
        mismatching_passwords.setBounds(150, 450, 275, 40);
        account_exists_username.setBounds(150, 450, 275, 40);


        username_fill.add (title);
        username_fill.add (username_label);
        username_fill.add (email_label);
        username_fill.add (password_label);
        username_fill.add (confirm_password_label);
        username_fill.add (username);
        username_fill.add (email);
        username_fill.add (password);
        username_fill.add (confirm_password);
        username_fill.add (signup_ready);
        username_fill.add(login2);
        username_fill.add(login);

        signup.setTitle("SMCSR Schoolhandle");
        signup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signup.add(username_fill);
        signup.setVisible(true);
    }
    static void infractions() throws IOException {
        JFrame frame = new JFrame();
        frame.setSize(800, 600);

        //Infractions
        int current_infraction = 1;
        JLabel infractions_label = new JLabel ("Infractions");
        JLabel infraction_1 = new JLabel("");
        JLabel infraction_2 = new JLabel("");
        JLabel infraction_3 = new JLabel("");
        JLabel infraction_4 = new JLabel("");
        JLabel infraction_5 = new JLabel("");
        JLabel infraction_6 = new JLabel("");
        JLabel infraction_7 = new JLabel("");
        JLabel infraction_8 = new JLabel("");

        //Apprasils
        int current_apprasil = 1;
        JLabel apprasils_label = new JLabel ("Apprasils");
        JLabel apprasil_1 = new JLabel("");
        JLabel apprasil_2 = new JLabel("");
        JLabel apprasil_3 = new JLabel("");
        JLabel apprasil_4 = new JLabel("");
        JLabel apprasil_5 = new JLabel("");
        JLabel apprasil_6 = new JLabel("");
        JLabel apprasil_7 = new JLabel("");
        JLabel apprasil_8 = new JLabel("");

        //Other
        JSeparator seperator = new JSeparator(JSeparator.VERTICAL);

        Font font = new Font("Calibri", Font.BOLD, 30);
        infractions_label.setFont(font);
        apprasils_label.setFont(font);

        Font font2 = new Font("Didot", Font.BOLD, 15);
        infraction_1.setFont(font2);
        infraction_2.setFont(font2);
        infraction_3.setFont(font2);
        infraction_4.setFont(font2);
        infraction_5.setFont(font2);
        infraction_6.setFont(font2);
        infraction_7.setFont(font2);
        infraction_8.setFont(font2);
        apprasil_1.setFont(font2);
        apprasil_2.setFont(font2);
        apprasil_3.setFont(font2);
        apprasil_4.setFont(font2);
        apprasil_5.setFont(font2);
        apprasil_6.setFont(font2);
        apprasil_7.setFont(font2);
        apprasil_8.setFont(font2);

        seperator.setForeground(Color.black);

        infractions_label.setBounds (100, 65, 170, 30);
        infraction_1.setBounds(30, 100, 400, 30);
        infraction_2.setBounds(30, 150, 400, 30);
        infraction_3.setBounds(30, 200, 400, 30);
        infraction_4.setBounds(30, 250, 400, 30);
        infraction_5.setBounds(30, 300, 400, 30);
        infraction_6.setBounds(30, 350, 400, 30);
        infraction_7.setBounds(30, 400, 400, 30);
        infraction_8.setBounds(30, 400, 450, 30);

        apprasils_label.setBounds (525, 65, 170, 30);
        apprasil_1.setBounds(425, 100, 400, 30);
        apprasil_2.setBounds(425, 150, 400, 30);
        apprasil_3.setBounds(425, 200, 400, 30);
        apprasil_4.setBounds(425, 250, 400, 30);
        apprasil_5.setBounds(425, 300, 400, 30);
        apprasil_6.setBounds(425, 350, 400, 30);
        apprasil_7.setBounds(425, 400, 400, 30);
        apprasil_8.setBounds(425, 400, 450, 30);

        seperator.setBounds(400, 0, 10, 600);

        JPanel panel = new JPanel(null);

        panel.add(infractions_label);
        panel.add(apprasils_label);
        panel.add(infraction_1);
        panel.add(infraction_2);
        panel.add(infraction_3);
        panel.add(infraction_4);
        panel.add(infraction_5);
        panel.add(infraction_6);
        panel.add(infraction_7);
        panel.add(infraction_8);

        panel.add(apprasil_1);
        panel.add(apprasil_2);
        panel.add(apprasil_3);
        panel.add(apprasil_4);
        panel.add(apprasil_5);
        panel.add(apprasil_6);
        panel.add(apprasil_7);
        panel.add(apprasil_8);
        panel.add(seperator);

        frame.add(panel);
        frame.setTitle("SMCSR Schoolhandle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JsonObject infractions_json_elements = new JsonObject();
        String fileName = "C:/Users/user/Desktop/Coding stuff files/Java/SMCSR/users.json";
        Path path = Paths.get(fileName);

        try (Reader reader = Files.newBufferedReader(path,
                StandardCharsets.UTF_8)) {

            JsonParser parser = new JsonParser();
            JsonElement tree = parser.parse(reader);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            //JsonArray array = tree.getAsJsonArray();
            JsonObject json_tree = tree.getAsJsonObject();

            for (int i = 0; i < json_tree.size(); i++) {
                infractions_json_elements = json_tree;
            }
            if(infractions_json_elements.get("students").getAsJsonObject().get(username_final).getAsJsonObject().get("infractions").getAsJsonObject().size() > 0){
                Set<Map.Entry<String, JsonElement>> entries_infractions = infractions_json_elements.get("students").getAsJsonObject().get(username_final).getAsJsonObject().get("infractions").getAsJsonObject().entrySet();
                Set<Map.Entry<String, JsonElement>> entries_apprasils = infractions_json_elements.get("students").getAsJsonObject().get(username_final).getAsJsonObject().get("apprasils").getAsJsonObject().entrySet();
                for(Map.Entry<String, JsonElement> entry: entries_infractions){
                    String key = entry.getKey();
                    JsonObject current_object = infractions_json_elements.get("students").getAsJsonObject().get(username_final).getAsJsonObject().get("infractions").getAsJsonObject().get(key).getAsJsonObject();
                    System.out.println("Key: " + key);
                    System.out.println("By: " + current_object.get("by").getAsString());
                    System.out.println("Subject: " + current_object.get("subject").getAsString());
                    System.out.println("Reason: " + current_object.get("reason").getAsString());
                    System.out.println("Date: " + current_object.get("date").getAsString());

                    String infraction_reason = current_object.get("reason").getAsString();
                    String infraction_subject = current_object.get("subject").getAsString();
                    String date = current_object.get("date").getAsString();
                    if(current_infraction == 1){
                        infraction_1.setText(infraction_reason + " (" + infraction_subject + ")" + "  " + date);
                        current_infraction++;
                    } else if(current_infraction == 2){
                        infraction_2.setText(infraction_reason + " (" + infraction_subject + ")" + "  " + date);
                        current_infraction++;
                    } else if(current_infraction == 3){
                        infraction_3.setText(infraction_reason + " (" + infraction_subject + ")" + "  " + date);
                        current_infraction++;
                    } else if(current_infraction == 4){
                        infraction_4.setText(infraction_reason + " (" + infraction_subject + ")" + "  " + date);
                        current_infraction++;
                    } else if(current_infraction == 5){
                        infraction_5.setText(infraction_reason + " (" + infraction_subject + ")" + "  " + date);
                        current_infraction++;
                    } else if(current_infraction == 6){
                        infraction_6.setText(infraction_reason + " (" + infraction_subject + ")" + "  " + date);
                        current_infraction++;
                    } else if(current_infraction == 7){
                        infraction_7.setText(infraction_reason + " (" + infraction_subject + ")" + "  " + date);
                        current_infraction++;
                    } else if(current_infraction == 8){
                        infraction_8.setText(infraction_reason + " (" + infraction_subject + ")" + "  " + date);
                        current_infraction++;
                    }

                }
                for(Map.Entry<String, JsonElement> entry: entries_apprasils){
                    String key = entry.getKey();
                    JsonObject current_object = infractions_json_elements.get("students").getAsJsonObject().get(username_final).getAsJsonObject().get("apprasils").getAsJsonObject().get(key).getAsJsonObject();
                    System.out.println("Key: " + key);
                    System.out.println("By: " + current_object.get("by").getAsString());
                    System.out.println("Subject: " + current_object.get("subject").getAsString());
                    System.out.println("Reason: " + current_object.get("reason").getAsString());

                    String apprasil_reason = current_object.get("reason").getAsString();
                    String apprasil_subject = current_object.get("subject").getAsString();
                    String date = current_object.get("date").getAsString();
                    if(current_apprasil == 1){
                        apprasil_1.setText(apprasil_reason + " (" + apprasil_subject + ")" + "  " + date);
                        current_apprasil++;
                    } else if(current_apprasil == 2){
                        apprasil_2.setText(apprasil_reason + " (" + apprasil_subject + ")" + "  " + date);
                        current_apprasil++;
                    } else if(current_apprasil == 3){
                        apprasil_3.setText(apprasil_reason + " (" + apprasil_subject + ")" + "  " + date);
                        current_apprasil++;
                    } else if(current_apprasil == 4){
                        apprasil_4.setText(apprasil_reason + " (" + apprasil_subject + ")" + "  " + date);
                        current_apprasil++;
                    } else if(current_apprasil == 5){
                        apprasil_5.setText(apprasil_reason + " (" + apprasil_subject + ")" + "  " + date);
                        current_apprasil++;
                    } else if(current_apprasil == 6){
                        apprasil_6.setText(apprasil_reason + " (" + apprasil_subject + ")" + "  " + date);
                        current_apprasil++;
                    } else if(current_apprasil == 7){
                        apprasil_7.setText(apprasil_reason + " (" + apprasil_subject + ")" + "  " + date);
                        current_apprasil++;
                    } else if(current_apprasil == 8){
                        apprasil_8.setText(apprasil_reason + " (" + apprasil_subject + ")" + "  " + date);
                        current_apprasil++;
                    }
                }
            }
        } catch(Exception e){
            System.out.println("An error has occoured while trying to get your infractions");
        }
    }
    static void student_assesment(){
        JFrame student_assesment = new JFrame();
        student_assesment.setSize(600,600);

        JButton behaviour = new JButton("Behaviour");
        JButton appraisal = new JButton("Appraisal");
        JButton assesment = new JButton("Assesment");
        JButton homework = new JButton("Homework");
        JButton conduct = new JButton("Conduct");

        Font font = new Font ("Calibri", Font.BOLD, 20);
        behaviour.setMaximumSize( new Dimension( 100, 10));
        appraisal.setMaximumSize( new Dimension( 100, 10));
        assesment.setMaximumSize( new Dimension( 100, 10));
        homework.setMaximumSize( new Dimension( 100, 10));
        conduct.setMaximumSize( new Dimension( 100, 10));


        JPanel headers = new JPanel();
        headers.setLayout(new GridLayout(1, 4));
        headers.add(behaviour);
        headers.add(appraisal);
        headers.add(homework);
        headers.add(conduct);

        behaviour.addActionListener(e -> {
            if(student_pages != 2){
                student_assesment.setVisible(false);
                try {
                    infractions();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                student_pages = 2;
            }
        });
        appraisal.addActionListener(e -> {
            if(student_pages != 3){
                student_assesment.setVisible(false);
                try {
                    infractions();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                student_pages = 3;
            }
        });
        homework.addActionListener(e -> {
            if(student_pages != 4){
                student_assesment.setVisible(false);
                student_homework();
                student_pages = 4;
            }
        });
        conduct.addActionListener(e -> {
            if(student_pages != 5){
                student_assesment.setVisible(false);
                student_conduct();
                student_pages = 5;
            }
        });

        student_assesment.add(headers, BorderLayout.NORTH);
        student_assesment.setTitle("SMCSR Schoolhandle");
        student_assesment.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        student_assesment.setVisible(true);
    }
    static void student_homework(){
        JFrame student_homework = new JFrame();
        student_homework.setSize(600,600);
        JLabel homework_text = new JLabel ("Homework");

        JButton behaviour = new JButton("Behaviour");
        JButton appraisal = new JButton("Appraisal");
        JButton assesment = new JButton("Assesment");
        JButton homework = new JButton("Homework");
        JButton conduct = new JButton("Conduct");

        Font font = new Font ("Calibri", Font.BOLD, 30);
        behaviour.setMaximumSize( new Dimension( 100, 10));
        appraisal.setMaximumSize( new Dimension( 100, 10));
        assesment.setMaximumSize( new Dimension( 100, 10));
        homework.setMaximumSize( new Dimension( 100, 10));
        conduct.setMaximumSize( new Dimension( 100, 10));
        homework_text.setFont(font);

        JPanel panel = new JPanel();
        panel.add(homework_text, BorderLayout.PAGE_START);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 200, 10, 0));

        JPanel headers = new JPanel();
        headers.setLayout(new GridLayout(1, 4));
        headers.add(behaviour);
        headers.add(appraisal);
        headers.add(homework);
        headers.add(conduct);

        behaviour.addActionListener(e -> {
            if(student_pages != 2){
                student_homework.setVisible(false);
                try {
                    infractions();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                student_pages = 2;
            }
        });
        appraisal.addActionListener(e -> {
            if(student_pages != 3){
                student_homework.setVisible(false);
                try {
                    infractions();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                student_pages = 3;
            }
        });
        homework.addActionListener(e -> {
            if(student_pages != 4){
                student_homework.setVisible(false);
                student_homework();
                student_pages = 4;
            }
        });
        conduct.addActionListener(e -> {
            if(student_pages != 5){
                student_homework.setVisible(false);
                student_conduct();
                student_pages = 5;
            }
        });

        student_homework.add(panel, BorderLayout.CENTER);
        student_homework.setTitle("SMCSR Schoolhandle");
        student_homework.add(headers, BorderLayout.NORTH);
        student_homework.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        student_homework.setVisible(true);
    }
    static void student_conduct(){
        JFrame student_conduct = new JFrame();
        student_conduct.setSize(600,600);

        JButton behaviour = new JButton("Behaviour");
        JButton appraisal = new JButton("Appraisal");
        JButton assesment = new JButton("Assesment");
        JButton homework = new JButton("Homework");
        JButton conduct = new JButton("Conduct");

        Font font = new Font ("Calibri", Font.BOLD, 20);
        behaviour.setMaximumSize( new Dimension( 100, 10));
        appraisal.setMaximumSize( new Dimension( 100, 10));
        assesment.setMaximumSize( new Dimension( 100, 10));
        homework.setMaximumSize( new Dimension( 100, 10));
        conduct.setMaximumSize( new Dimension( 100, 10));


        JPanel headers = new JPanel();
        headers.setLayout(new GridLayout(1, 4));
        headers.add(behaviour);
        headers.add(appraisal);
        headers.add(homework);
        headers.add(conduct);

        behaviour.addActionListener(e -> {
            if(student_pages != 2){
                student_conduct.setVisible(false);
                try {
                    infractions();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                student_pages = 2;
            }
        });
        appraisal.addActionListener(e -> {
            if(student_pages != 3){
                student_conduct.setVisible(false);
                try {
                    infractions();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                student_pages = 3;
            }
        });
        homework.addActionListener(e -> {
            if(student_pages != 4){
                student_conduct.setVisible(false);
                student_homework();
                student_pages = 4;
            }
        });
        conduct.addActionListener(e -> {
            if(student_pages != 5){
                student_conduct.setVisible(false);
                student_conduct();
                student_pages = 5;
            }
        });

        student_conduct.add(headers, BorderLayout.NORTH);
        student_conduct.setTitle("SMCSR Schoolhandle");
        student_conduct.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        student_conduct.setVisible(true);
    }


    static void admin_main(){
        JFrame admin_main = new JFrame();
        admin_main.setSize(600,600);
        JLabel welcometext =  new JLabel("Welcome " + username_final + "!" + " Please choose what you wish to do.");
        JButton btn1 = new JButton ("Behaviour");
        JButton btn2 = new JButton ("Conduct");
        JButton btn3 = new JButton ("Circulars");
        JButton btn4 = new JButton ("Assesment");


        Font font = new Font ("Calibri", Font.BOLD, 20);
        welcometext.setFont(font);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.add(welcometext, BorderLayout.PAGE_START);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        btn1.setMaximumSize( new Dimension( 100, 10));
        btn2.setMaximumSize( new Dimension( 100, 10));
        btn3.setMaximumSize( new Dimension( 100, 10));
        btn4.setMaximumSize( new Dimension( 100, 10));

        JPanel headers = new JPanel();
        headers.setLayout(new GridLayout(1, 4));
        headers.add(btn1);
        headers.add(btn2);
        headers.add(btn3);
        headers.add(btn4);

        btn1.addActionListener(e ->{
            if(admin_pages != 2){
                admin_main.setVisible(false);
                try {
                    punishments();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                admin_pages = 2;
            }
        });
        btn2.addActionListener(e ->{
            if(admin_pages != 3){
                admin_main.setVisible(false);
                admin_conduct();
                admin_pages = 3;
            }
        });
        btn3.addActionListener(e -> {
            if(admin_pages != 4){
                admin_main.setVisible(false);
                admin_circulars();
                admin_pages = 4;
            }
        });
        btn4.addActionListener(e -> {
            if(admin_pages != 5){
                admin_main.setVisible(false);
                assesment();
                admin_pages = 5;
            }
        });

        admin_main.add(panel, BorderLayout.CENTER);
        admin_main.add(headers, BorderLayout.NORTH);
        admin_main.setTitle("SMCSR Schoolhandle");
        admin_main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        admin_main.setVisible(true);
    }

    static void admin_conduct(){
        JFrame admin_conduct = new JFrame();
        admin_conduct.setSize(600,600);


    }
    static void admin_circulars(){
        JFrame admin_circulars = new JFrame();
        admin_circulars.setSize(600,600);

        JTextField title_box = new JTextField("Title");
        JTextArea message_box = new JTextArea("Message");
        JButton btn1 = new JButton ("Behaviour");
        JButton btn2 = new JButton ("Conduct");
        JButton btn3 = new JButton ("Circulars");
        JButton btn4 = new JButton ("Assesment");
        JButton send = new JButton("Send");
        JLabel sent_text = new JLabel("Sent!");

        Font font = new Font ("Calibri", Font.BOLD, 20);
        sent_text.setFont(font);

        title_box.setMaximumSize( new Dimension(200, 30));
        title_box.setPreferredSize( new Dimension(200, 30));
        title_box.setMinimumSize( new Dimension(200, 30));
        message_box.setMinimumSize( new Dimension(300, 75));
        message_box.setMaximumSize( new Dimension(300, 75));
        btn1.setMaximumSize( new Dimension( 100, 10));
        btn2.setMaximumSize( new Dimension( 100, 10));
        btn3.setMaximumSize( new Dimension( 100, 10));
        btn4.setMaximumSize( new Dimension( 100, 10));
        send.setPreferredSize( new Dimension(100, 30));

        title_box.setHorizontalAlignment((int) JTextArea.CENTER_ALIGNMENT);

        JScrollPane scrollpane = new JScrollPane(message_box);
        scrollpane.setMaximumSize( new Dimension(350, 100));

        JCheckBox box1 = new JCheckBox("Students", false);
        box1.setBounds(100,150, 50,50);
        JCheckBox box2 = new JCheckBox("Parents", false);
        box2.setBounds(100, 150, 50, 50);
        JCheckBox box3 = new JCheckBox("Staff", false);
        box3.setBounds(100, 150, 50, 50);


        JPanel title_panel = new JPanel();
        title_panel.setLayout(new BoxLayout(title_panel, BoxLayout.Y_AXIS));
        title_panel.add(title_box);
        title_panel.add(Box.createRigidArea(new Dimension(0, 5)));
        title_panel.add(scrollpane);


        title_panel.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));

        JPanel box_panel = new JPanel();
        box_panel.setLayout(new BoxLayout(box_panel, BoxLayout.Y_AXIS));
        box_panel.add(box1);
        box_panel.add(box2);
        box_panel.add(box3);
        box_panel.add(send);
        box_panel.setBorder(BorderFactory.createEmptyBorder(0,240,270,0));



        send.addActionListener(e ->{
            box_panel.add(sent_text);
            title_box.setText("Title");
            message_box.setText("Message");
            admin_circulars.revalidate();
        });



        JPanel headers = new JPanel();
        headers.setLayout(new GridLayout(1, 4));
        headers.add(btn1);
        headers.add(btn2);
        headers.add(btn3);
        headers.add(btn4);

        btn1.addActionListener(e ->{
            if(admin_pages != 2){
                admin_circulars.setVisible(false);
                try {
                    punishments();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                admin_pages = 2;
            }
        });
        btn2.addActionListener(e ->{
            if(admin_pages != 3){
                admin_circulars.setVisible(false);
                admin_conduct();
                admin_pages = 3;
            }
        });
        btn3.addActionListener(e -> {
            if(admin_pages != 4){
                admin_circulars.setVisible(false);
                admin_circulars();
                admin_pages = 4;
            }
        });
        btn4.addActionListener(e -> {
            if(admin_pages != 5){
                admin_circulars.setVisible(false);
                assesment();
                admin_pages = 5;
            }
        });

        admin_circulars.add(headers, BorderLayout.NORTH);
        admin_circulars.add(title_panel, BorderLayout.CENTER);
        admin_circulars.add(box_panel, BorderLayout.SOUTH);
        admin_circulars.setTitle("SMCSR Schoolhandle");
        admin_circulars.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        admin_circulars.setVisible(true);
    }


    static void teacher_main(){
        JFrame teacher_main = new JFrame();
        teacher_main.setSize(600,600);
        JLabel welcometext = new JLabel("Welcome " + username_final + "!" + " Please choose what you wish to do.");
        JButton btn1 = new JButton("Assesment");
        JButton btn2 = new JButton("Attendance");
        JButton btn3 = new JButton ("Behaviour");
        JButton btn4 = new JButton("Homework");

        Font font = new Font ("Calibri", Font.BOLD, 20);
        welcometext.setFont(font);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.add(welcometext, BorderLayout.PAGE_START);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JPanel headers = new JPanel();
        headers.setLayout(new GridLayout(1, 4));
        headers.add(btn1);
        headers.add(btn2);
        headers.add(btn3);
        headers.add(btn4);

        btn1.addActionListener(e ->{
            if(teacher_pages != 2){
                teacher_main.setVisible(false);
                try {
                    punishments();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                teacher_pages = 2;
            }
        });
        btn2.addActionListener(e ->{
            if(teacher_pages != 3){
                teacher_main.setVisible(false);
                teacher_homework();
                teacher_pages = 3;
            }
        });
        btn3.addActionListener(e -> {
            if(teacher_pages != 4){
                teacher_main.setVisible(false);
                assesment();
                teacher_pages = 5;
            }
        });
        btn4.addActionListener(e -> {
            if(teacher_pages != 5){
                teacher_main.setVisible(false);
                teacher_attendance();
                teacher_pages = 5;
            }
        });

        teacher_main.add(panel, BorderLayout.CENTER);
        teacher_main.add(headers, BorderLayout.NORTH);
        teacher_main.setTitle("SMCSR Schoolhandle");
        teacher_main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        teacher_main.setVisible(true);
    }

    static void student_main(){
        JFrame student_main = new JFrame();
        student_main.setSize(600,600);
        JLabel welcometext = new JLabel("Welcome " + username_final + "!" + " Please choose what you wish to do.");
        JButton behaviour = new JButton("Behaviour");
        JButton appraisal = new JButton("Appraisal");
        JButton assesment = new JButton("Assesment");
        JButton homework = new JButton("Homework");
        JButton conduct = new JButton("Conduct");

        Font font = new Font ("Calibri", Font.BOLD, 20);
        welcometext.setFont(font);
        behaviour.setMaximumSize( new Dimension( 100, 10));
        appraisal.setMaximumSize( new Dimension( 100, 10));
        assesment.setMaximumSize( new Dimension( 100, 10));
        homework.setMaximumSize( new Dimension( 100, 10));
        conduct.setMaximumSize( new Dimension( 100, 10));

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.add(welcometext, BorderLayout.PAGE_START);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JPanel headers = new JPanel();
        headers.setLayout(new GridLayout(1, 5));
        headers.add(behaviour);
        headers.add(appraisal);
        headers.add(homework);
        headers.add(conduct);
        headers.add(assesment);

        behaviour.addActionListener(e -> {
            if(student_pages != 2){
                student_main.setVisible(false);
                try {
                    infractions();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                student_pages = 2;
            }
        });
        appraisal.addActionListener(e -> {
            if(student_pages != 3){
                student_main.setVisible(false);
                try {
                    infractions();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                student_pages = 3;
            }
        });
        homework.addActionListener(e -> {
            if(student_pages != 4){
                student_main.setVisible(false);
                student_homework();
                student_pages = 4;
            }
        });
        conduct.addActionListener(e -> {
            if(student_pages != 5){
                student_main.setVisible(false);
                student_conduct();
                student_pages = 5;
            }
        });
        assesment.addActionListener(e -> {
            if(student_pages != 6){
                student_main.setVisible(false);
                student_assesment();
                student_pages = 6;
            }
        });

        student_main.add(panel, BorderLayout.CENTER);
        student_main.add(headers, BorderLayout.NORTH);
        student_main.setTitle("SMCSR Schoolhandle");
        student_main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        student_main.setVisible(true);
    }
    static void punishments() throws IOException{
        JFrame frame = new JFrame();
        frame.setSize(600,600);

        JLabel title = new JLabel ("Behaviour");
        JLabel choices_label = new JLabel("Type of action");
        JLabel subject_label = new JLabel("Subject you teach");
        JLabel sucsess_label = new JLabel ("Sucsess!", SwingConstants.CENTER);
        JLabel error_label = new JLabel("An error has occoured");

        String [] action_choices = {"Apprasil", "Infraction"};
        String [] subject_choices = {"Accounting", "Biology", "Chemistry", "Computing", "Design and Technoligy", "Engineering", "English", "English Literature", "Enviormental Studies", "Maltese", "Mathematics", "Physical Education", "Physics", "PSCD", "Religion", "Italian", "German", "Spanish"};
        JComboBox<String> actions_box = new JComboBox<>(action_choices);
        JComboBox<String> subjects_box = new JComboBox<>(subject_choices);
        JButton send = new JButton("Send");
        JButton btn1 = new JButton ("Behaviour");
        JButton btn2 = new JButton ("Conduct");
        JButton btn3 = new JButton ("Circulars");
        JButton btn4 = new JButton ("Assesment");

        JTextField username_field = new JTextField("Username");
        JTextField reason_field = new JTextField("Reason");

        Font title_font = new Font("Calibri", Font.BOLD, 30);
        title.setFont(title_font);

        //Main font
        Font font = new Font ("Calibri", Font.BOLD, 20);
        choices_label.setFont(font);
        subject_label.setFont(font);
        sucsess_label.setFont(font);

        send.setMaximumSize( new Dimension(80, 30));
        //Headers dimensions
        btn1.setMaximumSize( new Dimension( 100, 10));
        btn2.setMaximumSize( new Dimension( 100, 10));
        btn3.setMaximumSize( new Dimension( 100, 10));
        btn4.setMaximumSize( new Dimension( 100, 10));

        sucsess_label.setForeground(new Color(255, 255, 255));
        sucsess_label.setBackground(new Color(139,195,74));
        sucsess_label.setOpaque(true);

        error_label.setForeground(new Color(255, 255, 255));
        error_label.setBackground(new Color(206, 0, 0));
        error_label.setOpaque(true);

        JPanel main_panel = new JPanel();
        main_panel.setLayout (null);

        main_panel.add(title);
        main_panel.add(choices_label);
        main_panel.add(actions_box);
        main_panel.add(subject_label);
        main_panel.add(subjects_box);
        main_panel.add(send);
        main_panel.add(username_field);
        main_panel.add(reason_field);

        title.setBounds (205, 65, 200, 40);
        choices_label.setBounds (15, 140, 125, 50);
        actions_box.setBounds (10, 185, 125, 35);
        subject_label.setBounds (370, 140, 200, 50);
        subjects_box.setBounds (380, 185, 125, 35);
        send.setBounds (210, 270, 115, 40);
        sucsess_label.setBounds (170, 335, 180, 45);
        username_field.setBounds(160, 185, 100, 40);
        reason_field.setBounds(270, 185, 100, 40);

        //Adding headers to the headers JPanel
        JPanel headers = new JPanel();
        headers.setLayout(new GridLayout(1, 4));
        headers.add(btn1);
        headers.add(btn2);
        headers.add(btn3);
        headers.add(btn4);

        btn1.addActionListener(e ->{
            if(admin_pages != 2){
                frame.setVisible(false);
                try {
                    punishments();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                admin_pages = 2;
            }
        });
        btn2.addActionListener(e ->{
            if(admin_pages != 3){
                frame.setVisible(false);
                admin_conduct();
                admin_pages = 3;
            }
        });
        btn3.addActionListener(e -> {
            if(admin_pages != 4){
                frame.setVisible(false);
                admin_circulars();
                admin_pages = 4;
            }
        });
        btn4.addActionListener(e -> {
            if(admin_pages != 5){
                frame.setVisible(false);
                assesment();
                admin_pages = 5;
            }
        });


        send.addActionListener(e -> {
            System.out.println(actions_box.getSelectedItem());
            if(subjects_box.getSelectedItem() != null && actions_box.getSelectedItem() != null){
                JsonObject json_elements = new JsonObject();
                String fileName = "C:/Users/user/Desktop/Coding stuff files/Java/SMCSR/users.json";
                Path path = Paths.get(fileName);

                try (Reader reader = Files.newBufferedReader(path,
                        StandardCharsets.UTF_8)) {

                    JsonParser parser = new JsonParser();
                    JsonElement tree = parser.parse(reader);

                    Gson gson = new GsonBuilder().setPrettyPrinting().create();

                    //JsonArray array = tree.getAsJsonArray();
                    JsonObject json_tree = tree.getAsJsonObject();

                    for(int i = 0; i < json_tree.size(); i++){
                        json_elements = json_tree;
                    }
                }  catch (Exception ex){
                    System.out.println("Error found while reading: " + ex);
                }

                try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {

                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String actions_choice;
                    if(actions_box.getSelectedItem().equals("Apprasil")){
                        actions_choice = "apprasils";
                    } else{
                        actions_choice = "infractions";
                    }
                    String username = username_field.getText();
                    String subject = (String) subjects_box.getSelectedItem();
                    String reason = reason_field.getText();
                    String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    UUID id = UUID.randomUUID();

                    JsonObject json_obj = new JsonObject();
                    json_obj.addProperty("by", username_final);
                    json_obj.addProperty("Subject", subject);
                    json_obj.addProperty("Reason", reason);
                    json_obj.addProperty("Date", date);

                    json_elements.get("students").getAsJsonObject().get(username).getAsJsonObject().get(actions_choice).getAsJsonObject().add(String.valueOf(id), json_obj);

                    JsonElement tree = gson.toJsonTree(json_elements);
                    gson.toJson(tree, writer);
                }  catch(Exception ex){
                    System.out.println("Error found while writing: " + ex);
                }
                System.out.println("Complete");
                main_panel.remove(error_label);
                main_panel.add(sucsess_label);
                main_panel.updateUI();
                ActionListener taskPerformer = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        main_panel.remove(sucsess_label);
                        main_panel.updateUI();
                    }
                };

                int delay = 3000;
                new Timer(delay, taskPerformer).start();
            }
            else{
                main_panel.remove(sucsess_label);
                main_panel.add(error_label);
                main_panel.updateUI();
                ActionListener taskPerformer = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        main_panel.remove(error_label);
                        main_panel.updateUI();
                    }
                };

                int delay = 3000;
                new Timer(delay, taskPerformer).start();
            }
        });

        frame.add(headers, BorderLayout.NORTH);
        frame.add(main_panel, BorderLayout.CENTER);
        frame.setTitle("SMCSR Schoolhandle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    static void teacher_homework(){

    }
    static void assesment(){

    }
    static void teacher_attendance(){

    }

    static void secretary_main(){

    }
    public static void main(String [] args) throws IOException {

        JFrame frame = new JFrame();
        frame.setSize(600,600);

        JLabel welcometext = new JLabel ("Please login to your account.");
        JLabel usernametext = new JLabel ("Username:");
        JLabel passwordtext = new JLabel ("Password:");
        JTextField username = new JTextField (1);
        JPasswordField password = new JPasswordField (0);
        JButton btn = new JButton ("Login");
        JLabel sign = new JLabel ("Sign up!");
        JLabel sign2 = new JLabel("Don't have an account?");

        sign.setForeground(Color.BLUE.darker());
        sign.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Font font1 = new Font("Calibri", Font.BOLD, 20);
        Font font2 = new Font ("Calibri", Font.BOLD, 30);
        welcometext.setFont(font2);
        usernametext.setFont(font1);
        passwordtext.setFont(font1);
        sign.setFont(font1);
        sign2.setFont(font1);
        welcometext.setBounds (100, 25, 400, 30);
        usernametext.setBounds (95, 100, 110, 30);
        passwordtext.setBounds (95, 150, 110, 30);
        username.setBounds (190, 100, 145, 30);
        password.setBounds (190, 150, 145, 35);
        btn.setBounds (205, 215, 115, 40);
        sign2.setBounds(130, 275, 225, 30);
        sign.setBounds (335, 275, 75, 30);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(welcometext);
        panel.add(usernametext);
        panel.add(username);
        panel.add(passwordtext);
        panel.add(password);
        panel.add(btn);
        panel.add(sign);
        panel.add(sign2);



        sign.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setVisible(false);
                signup();
            }

        });
        btn.addActionListener(e -> {
            String check_user = username.getText();
            String check_pass = new String(password.getPassword());

            JsonObject json_elements = new JsonObject();
            String fileName_login = "C:/Users/user/Desktop/Coding stuff files/Java/SMCSR/users.json";
            Path path_login = Paths.get(fileName_login);

            try (Reader reader_login = Files.newBufferedReader(path_login,
                    StandardCharsets.UTF_8)) {

                JsonParser parser_login = new JsonParser();
                JsonElement tree_login = parser_login.parse(reader_login);

                Gson gson_login = new GsonBuilder().setPrettyPrinting().create();

                //JsonArray array = tree.getAsJsonArray();
                JsonObject json_tree = tree_login.getAsJsonObject();

                for (int i = 0; i < json_tree.size(); i++) {
                    json_elements = json_tree;
                }
                String encoded_password = check_pass;
                for(int counter = 0; counter < 5; counter++){
                    encoded_password = Base64.getEncoder().encodeToString(encoded_password.getBytes());
                }

                try{
                    if(json_elements.get("admins").getAsJsonObject().has(check_user) == true && json_elements.get("admins").getAsJsonObject().get(check_user).getAsJsonObject().get("password").getAsString().equals(encoded_password)){
                        login_complete = 1;
                        System.out.println("Admin detected.");
                        username_final = json_elements.get("admins").getAsJsonObject().get(check_user).getAsJsonObject().get("username").getAsString();
                        frame.setVisible(false);
                        admin_main();
                    }
                    else if(json_elements.get("teachers").getAsJsonObject().has(check_user) == true && json_elements.get("teachers").getAsJsonObject().get(check_user).getAsJsonObject().get("password").getAsString().equals(encoded_password)){
                        login_complete = 1;
                        System.out.println("Teacher detected.");
                        username_final = json_elements.get("teachers").getAsJsonObject().get(check_user).getAsJsonObject().get("username").getAsString();
                        frame.setVisible(false);
                        teacher_main();
                    }
                    else if(json_elements.get("secretarys").getAsJsonObject().has(check_user) == true && json_elements.get("secretarys").getAsJsonObject().get(check_user).getAsJsonObject().get("password").getAsString().equals(encoded_password)){
                        login_complete = 1;
                        System.out.println("Secretary detected.");
                        username_final = json_elements.get("secretarys").getAsJsonObject().get(check_user).getAsJsonObject().get("username").getAsString();
                        frame.setVisible(false);
                        secretary_main();
                    }
                    else if(json_elements.get("students").getAsJsonObject().has(check_user) == true && json_elements.get("students").getAsJsonObject().get(check_user).getAsJsonObject().get("password").getAsString().equals(encoded_password)){
                        login_complete = 1;
                        System.out.println("Student detected.");
                        username_final = json_elements.get("students").getAsJsonObject().get(check_user).getAsJsonObject().get("username").getAsString();
                        frame.setVisible(false);
                        student_main();
                    }
                    else{
                        System.out.println("Account not found.");
                    }
                }  catch(Exception ex){
                    System.out.println("An error has occoured while logging in: " + ex);
                    System.out.println("This error could have occoured due to the account not being in our database.");
                }
            } catch (IOException ex) {
                System.out.println("An error has occoured while trying to login: " + ex);

            }

            if(login_complete == 0){
                /*panel.add(incorrect);
                SwingUtilities.updateComponentTreeUI(frame);*/
            }
        });

        frame.add(panel);
        frame.setTitle("SMCSR Schoolhandle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}