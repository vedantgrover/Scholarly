package VAC;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;

import io.github.cdimascio.dotenv.Dotenv;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.UUID;

public class MongoDB {
    public static MongoCollection<Document> data;

    public MongoDB() {
        MongoClient mongoClient = new MongoClient(new MongoClientURI(Dotenv.load().get("MONGODB_SRV")));
        MongoDatabase database = mongoClient.getDatabase("VAC");
        data = database.getCollection("userData");
    }

    public boolean checkIfUserExists(String username) {
        FindIterable<Document> iterable = data.find(new Document("username", username));

        return iterable.first() != null;
    }

    public Document findUser(String username) {
        return data.find(new Document("username", username)).first();
    }

    public boolean createUser(String firstName, String lastName, String email, String phoneNumber, String org,
                              String username, String password) {
        if (checkIfUserExists(username)) {
            return false;
        }

        if (data.countDocuments(new Document("organization", org)) == 0) {

            data.insertOne(new Document("ID", UUID.randomUUID()).append("name", firstName + " " + lastName)
                    .append("email", email).append("number", phoneNumber).append("organization", org)
                    .append("username", username).append("password", password).append("isAdmin", true)
                    .append("isTutor", false).append("status", "none").append("description", ""));
            return true;
        }

        data.insertOne(
                new Document("ID", UUID.randomUUID()).append("name", firstName + " " + lastName).append("email", email)
                        .append("number", phoneNumber).append("organization", org).append("username", username)
                        .append("password", password).append("isAdmin", false).append("isTutor", false)
                        .append("status", "none").append("description", ""));
        return true;
    }

    public void editUser(String firstName, String lastName, String email, String phoneNumber, String org,
                         String username, String password, String description) {
        if (!checkIfUserExists(username)) {
            return;
        }

        Document query = new Document("username", username);

        Bson updates = Updates.combine(
                Updates.set("name", firstName + " " + lastName),
                Updates.set("email", email),
                Updates.set("number", phoneNumber),
                Updates.set("organization", org),
                Updates.set("username", username),
                Updates.set("password", password),
                Updates.set("description", description)
        );

        UpdateOptions options = new UpdateOptions().upsert(true);

        try {
            data.updateOne(query, updates, options);
        } catch (MongoException me) {
            me.printStackTrace();
        }
    }

    public void convertToAdmin(String username) {
        Document query = new Document("username", username);

        Bson updates = Updates.combine(
                Updates.set("isAdmin", true),
                Updates.set("isTutor", false),
                Updates.set("status", "none"),
                Updates.set("description", "")
        );

        UpdateOptions options = new UpdateOptions().upsert(true);

        try {
            data.updateOne(query, updates, options);
        } catch (MongoException me) {
            me.printStackTrace();
        }
    }

    public void removeTutor(String username) {
        Document query = new Document("username", username);

        Bson updates = Updates.set("isTutor", false);

        UpdateOptions options = new UpdateOptions().upsert(true);

        try {
            data.updateOne(query, updates, options);
        } catch (MongoException me) {
            me.printStackTrace();
        }
    }

    public FindIterable<Document> getAllStudents(String org) {
        Document query = new Document("organization", org).append("isAdmin", false);

        return data.find(query);
    }

    public FindIterable<Document> getTutors(String org) {
        Document query = new Document("organization", org).append("isTutor", true);

        return data.find(query);
    }

    public FindIterable<Document> getTutorRequests(String org) {
        Document query = new Document("organization", org).append("status", "aTutor").append("isAdmin", false).append("isTutor", false);

        return data.find(query);
    }

    public FindIterable<Document> getStudentRequests(String org) {
        Document query = new Document("organization", org).append("status", "aStudent").append("isAdmin", false);

        return data.find(query);
    }

    public void makeTutorRequest(String username, String description) {
        if (!checkIfUserExists(username)) {
            return;
        }

        Document query = new Document("username", username);

        Bson updates = Updates.combine(
                Updates.set("status", "aTutor"),
                Updates.set("description", description)
        );

        UpdateOptions options = new UpdateOptions().upsert(true);

        try {
            data.updateOne(query, updates, options);
        } catch (MongoException me) {
            System.err.println("Unable to update due to an error: " + me);
        }
    }

    public void studentSubjectRequest(String username, String description) {
        //works
        if (!checkIfUserExists(username)) {
            return;
        }

        Document query = new Document("username", username);

        Bson updates = Updates.combine(
                Updates.set("status", "aStudent"),
                Updates.set("description", description)
        );
        UpdateOptions options = new UpdateOptions().upsert(true);

        try {
            data.updateOne(query, updates, options);
        } catch (MongoException me) {
            System.err.println("Unable to update due to an error: " + me);
        }

    }


    public void recruit(String username, boolean approve) {
        Document query = new Document("username", username);
        if (approve) {
            Bson updates = Updates.combine(
                    Updates.set("status", "none"),
                    Updates.set("isTutor", true)
            );

            UpdateOptions options = new UpdateOptions().upsert(true);

            try {
                data.updateOne(query, updates, options);
            } catch (MongoException me) {
                me.printStackTrace();
            }
        } else {
            Bson updates = Updates.set("status", "none");

            UpdateOptions options = new UpdateOptions().upsert(true);

            try {
                data.updateOne(query, updates, options);
            } catch (MongoException me) {
                me.printStackTrace();
            }
        }
    }

    public void tutorStudentConnect(String username, boolean approve) {
        Document query = new Document("username", username);
        if (approve) {
            Bson updates = Updates.combine(
                    Updates.set("status", "none"),
                    Updates.set("description", "")
            );
            UpdateOptions options = new UpdateOptions().upsert(false);

            try {
                data.updateOne(query, updates, options);
            } catch (MongoException me) {
                me.printStackTrace();
            }
        } else {
            Bson updates = Updates.combine(
                    Updates.set("status", "none"),
                    Updates.set("description", "")
            );
            UpdateOptions options = new UpdateOptions().upsert(true);

            try {
                data.updateOne(query, updates, options);
            } catch (MongoException me) {
                me.printStackTrace();
            }
        }
    }
}
