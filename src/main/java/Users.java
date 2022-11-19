import com.google.gson.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Users {
    static void fx_test(){

    }
    static void writer_infractions(){
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

            String username = "Test3";
            String by = "Test1";
            String subject = "Accounting";
            String reason = "Missing homework";
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            UUID id = UUID.randomUUID();

            JsonObject json_obj = new JsonObject();
            json_obj.addProperty("by", by);
            json_obj.addProperty("Subject", subject);
            json_obj.addProperty("Reason", reason);
            json_obj.addProperty("Date", date);

            json_elements.get("students").getAsJsonObject().get(username).getAsJsonObject().get("infractions").getAsJsonObject().add(String.valueOf(id), json_obj);

            JsonElement tree = gson.toJsonTree(json_elements);
            gson.toJson(tree, writer);
        }  catch(Exception ex){
            System.out.println("Error found while writing: " + ex);
        }
        System.out.println("Complete");
    }
    static void date(){
        System.out.println("Date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }
    static void token(){
        for(int i = 0; i < 5; i++){
            UUID random = UUID.randomUUID();
            System.out.println( random );
        }
    }
    static void reader_writer(){

        JsonObject json_elements = new JsonObject();
        String fileName = "C:/Users/user/Desktop/Coding stuff files/Java/SMCSR/test_json.json";
        Path path = Paths.get(fileName);


        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();


            JsonObject json_obj = new JsonObject();

            json_obj.addProperty("Encoded: ", "Password");

            json_elements.add("test", json_obj);

            JsonElement tree = gson.toJsonTree(json_elements);
            gson.toJson(tree, writer);
        }  catch(Exception ex){
            System.out.println("Error found while writing: " + ex);
        }
        System.out.println("Complete");
    }

    static void encode_test(){
        Scanner s = new Scanner(System.in);
        System.out.println("Enter string you want encrypted");
        String string = s.next();
        String encodedString = string;
        for(int i = 0; i < 5; i++){
            encodedString = Base64.getEncoder().encodeToString(encodedString.getBytes());
        }
        System.out.println("Encoded: " + encodedString);
        /*byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);*/
    }

    public static void main(String[] args) throws IOException {
        encode_test();
    }
}