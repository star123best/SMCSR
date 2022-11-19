import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.apache.log4j.BasicConfigurator;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;

import java.util.Iterator;

public class mongo {
    //46.11.6.83
    public static void main(String [] args){
        //MongoClient mongoClient = new MongoClient();
        //DB database = mongoClient.getDB("TheDatabaseName");

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
        //MongoCollection<Document> collection = database.getCollection("Users");
        MongoCollection<Document> students = database.getCollection("students");
        MongoCollection<Document> admins = database.getCollection("admins");
        MongoCollection<Document> teachers = database.getCollection("teachers");
        MongoCollection<Document> secretarys = database.getCollection("secretarys");
        Document doc1 = new Document();
        Document doc2 = new Document();
        Document doc3 = new Document();
        Document doc4 = new Document();

        doc2.append("Test4", new Document("username", "Test4").append("password", "VmtaYVUxTnRWbkpPVlZaWFZrUkJPUT09"));
        doc3.append("Test1", new Document("username", "Test1").append("password", "VmtaYVUxTnRWbkpPVlZaWFZrUkJPUT09"));
        doc4.append("Test5", new Document("username", "Test5").append("password", "VmtaYVUxTnRWbkpPVlZaWFZrUkJPUT09"));

        doc1.append("Test3", new Document("username", "Test3").append("password", "VmtaYVUxTnRWbkpPVlZaWFZrUkJPUT09").append("email", "").append("apprasils", new Document("Helping Peers", new Document("by", "Test1").append("subject", "accounting").append("date", "12/09/2022"))).append("infractions", new Document()));
        doc1.append("Test6", new Document("username", "Test6").append("password", "VmtaYVUxTnRWbkpPVlZaWFZrUkJPUT09").append("email", "").append("apprasils", new Document()).append("infractions", new Document()));
        students.insertOne(doc1);
        teachers.insertOne(doc2);
        admins.insertOne(doc3);
        secretarys.insertOne(doc4);

        /*
        FindIterable<Document> iterDoc = collection.find();
        Iterator it = iterDoc.iterator();

        while (it.hasNext()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Document before = (Document) it.next();
            Document after = (Document) before.get("students");
            System.out.println(gson.toJson(after));

            if(after.containsKey("Test3")){
                System.out.println("Found");
            } else {
                System.out.println("Not found");
            }
        }

        /*
        Document students = new Document();
        Document teachers = new Document();
        Document admins = new Document();
        Document secretarys = new Document();

        students.append("Test3", new Document("username", "Test3").append("password", "VmtaYVUxTnRWbkpPVlZaWFZrUkJPUT09").append("email", "").append("apprasils", new Document("Helping Peers", new Document("by", "Test1").append("subject", "accounting").append("date", "12/09/2022"))).append("infractions", new Document()));
        students.append("Test6", new Document("username", "Test6").append("password", "VmtaYVUxTnRWbkpPVlZaWFZrUkJPUT09").append("email", "").append("apprasils", new Document()).append("infractions", new Document()));
        teachers.append("Test4", new Document("username", "Test4").append("password", "VmtaYVUxTnRWbkpPVlZaWFZrUkJPUT09"));
        admins.append("Test1", new Document("username", "Test1").append("password", "VmtaYVUxTnRWbkpPVlZaWFZrUkJPUT09"));
        secretarys.append("Test5", new Document("username", "Test5").append("password", "VmtaYVUxTnRWbkpPVlZaWFZrUkJPUT09"));

        Document document = new Document().append("students",students).append("teachers", teachers).append("admins", secretarys).append("secretarys", secretarys);


        //Inserting document into the collection
        collection.insertOne(document);

         */
    }
}
