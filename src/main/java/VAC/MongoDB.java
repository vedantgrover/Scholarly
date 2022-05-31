package VAC;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.mongodb.client.model.Filters;
import org.bson.Document;

import javax.print.Doc;

/**
 * The Code that I wrote within this class is a bit of an example on what we COULD do when we register users into the DB. 
 * Obvisously we will get all the data with user input. But this will be how it gets registered into the database. We'll make it work. 
 * This what I have learnt so far. I will be learning more later.
 */

public class MongoDB {
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    public static MongoCollection<Document> data;

    public MongoDB() {
        mongoClient = new MongoClient(new MongoClientURI("mongodb+srv://vac:LfXrbCO7JrH9PgLC@scholarly.l7vvy.mongodb.net/?retryWrites=true&w=majority"));
        database = mongoClient.getDatabase("VAC");
        data = database.getCollection("userData");

        //data.deleteMany(new Document());
    }

    public boolean checkIfUserExists(String username) {
        FindIterable<Document> iterable = data.find(new Document("username", username));

        return iterable.first() != null;
    }

    public Document findUser(String username) {
        return data.find(new Document("username", username)).first();
    }

    public Document findUserByName(String name) {
        return data.find(new Document("name", name)).first();
    }

    public boolean createUser(String firstName, String lastName, String email, String phoneNumber, String org, String username, String password) {
        boolean isAdmin = false;
        if (checkIfUserExists(username)) {
            return false;
        }

        if (data.countDocuments(new Document("organization", org)) == 0) {
            List<Document> tutorRequests = new ArrayList<Document>();
            data.insertOne(new Document("ID", UUID.randomUUID()).append("name", firstName + " " + lastName).append("email", email).append("number", phoneNumber).append("organization", org).append("username", username).append("password", password).append("isAdmin", true).append("isTutor", false).append("TutorRequests", tutorRequests));
            return true;
        }

        data.insertOne(new Document("ID", UUID.randomUUID()).append("name", firstName + " " + lastName).append("email", email).append("number", phoneNumber).append("organization", org).append("username", username).append("password", password).append("isAdmin", false).append("isTutor", false));
        return true;
    }

    public FindIterable<Document> getTutors(String org) {
        Document query = new Document("organization", org).append("isTutor", true);
        FindIterable<Document> docs = data.find(query);

        return docs;
    }

    public Document createTutorRequest(String tutorUsername, String description) {
        return new Document("tutorUsername", tutorUsername).append("description", description);
    }

    public boolean switchToAdmin(String username) {
        if (!checkIfUserExists(username)) {
            return false;
        }

        List<Document> tutorData = new ArrayList<Document>();
        Document currentData = findUser(username).append("TutorRequests", tutorData);
        data.updateOne(new Document("username", username), currentData);

        return true;
    }

    
    
}
