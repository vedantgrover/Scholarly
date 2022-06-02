package VAC;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;
import org.bson.conversions.Bson;

import javax.print.Doc;
import javax.xml.transform.sax.TemplatesHandler;

/**
 * The Code that I wrote within this class is a bit of an example on what we
 * COULD do when we register users into the DB.
 * Obvisously we will get all the data with user input. But this will be how it
 * gets registered into the database. We'll make it work.
 * This what I have learnt so far. I will be learning more later.
 */

public class MongoDB {
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    public static MongoCollection<Document> data;

    public MongoDB() {
        mongoClient = new MongoClient(new MongoClientURI(
                "mongodb+srv://vac:LfXrbCO7JrH9PgLC@scholarly.l7vvy.mongodb.net/?retryWrites=true&w=majority"));
        database = mongoClient.getDatabase("VAC");
        data = database.getCollection("userData");

        // data.deleteMany(new Document());
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

    public boolean createUser(String firstName, String lastName, String email, String phoneNumber, String org,
            String username, String password) {
        boolean isAdmin = false;
        if (checkIfUserExists(username)) {
            return false;
        }

        if (data.countDocuments(new Document("organization", org)) == 0) {

            data.insertOne(new Document("ID", UUID.randomUUID()).append("name", firstName + " " + lastName)
                    .append("email", email).append("number", phoneNumber).append("organization", org)
                    .append("username", username).append("password", password).append("isAdmin", true)
                    .append("isTutor", false).append("pending", false).append("description", ""));
            return true;
        }

        data.insertOne(
                new Document("ID", UUID.randomUUID()).append("name", firstName + " " + lastName).append("email", email)
                        .append("number", phoneNumber).append("organization", org).append("username", username)
                        .append("password", password).append("isAdmin", false).append("isTutor", false)
                        .append("pending", false).append("description", ""));
        return true;
    }

    public FindIterable<Document> getTutors(String org) {
        Document query = new Document("organization", org).append("isTutor", true);
        FindIterable<Document> docs = data.find(query);

        return docs;
    }

    public FindIterable<Document> getTutorRequests(String org) {
        Document query = new Document("organization", org).append("pending", true);
        FindIterable<Document> docs = data.find(query);

        return docs;
    }

    public void makeTutorRequest(String username, String description) {
        if (!checkIfUserExists(username)) {
            return;
        }

        Document query = new Document("username", username);

        Bson updates = Updates.combine(
            Updates.set("pending", true),
            Updates.set("description", description)
        );

        UpdateOptions options = new UpdateOptions().upsert(true);

        try {
            UpdateResult result = data.updateOne(query, updates, options);
        } catch (MongoException me) {
            System.err.println("Unable to update due to an error: " + me);
        }
    }

    public void recruit(String username, boolean approve) {
        Document query = new Document("username", username);
        if (approve) {
            Bson updates = Updates.combine(
                Updates.set("pending", false),
                Updates.set("isTutor", true)
            );

            UpdateOptions options = new UpdateOptions().upsert(true);

            try {
                UpdateResult result = data.updateOne(query, updates, options);
            } catch (MongoException me) {
                System.err.println(me);
            }
        } else {
            Bson updates = Updates.set("pending", false);

            UpdateOptions options = new UpdateOptions().upsert(true);

            try {
                UpdateResult result = data.updateOne(query, updates, options);
            } catch (MongoException me) {
                System.err.println(me);
            }
        }
    }
}
