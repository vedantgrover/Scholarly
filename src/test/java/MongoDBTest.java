import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * The Code that I wrote within this class is a bit of an example on what we COULD do when we register users into the DB. 
 * Obvisously we will get all the data with user input. But this will be how it gets registered into the database. We'll make it work. 
 * This what I have learnt so far. I will be learning more later.
 */

public class MongoDBTest {
    public static MongoClient mongoClient; // The overall server. Connection is handled through here.
    public static MongoDatabase database; // The thingiemabobber that stores the Collections of Documents.
    public static MongoCollection<Document> testDocs; // The thing that stores the documents. (Collection)
    public static void main(String[] args) {
        mongoClient = new MongoClient(new MongoClientURI("mongodb+srv://vac:LfXrbCO7JrH9PgLC@scholarly.l7vvy.mongodb.net/?retryWrites=true&w=majority")); // Connecting to the server. THE STRING SHOULD NOT LEAVE THE CODE PLS
        database = mongoClient.getDatabase("VAC"); // Grabbing all the data from the database and making it interactable with the code.
        testDocs = database.getCollection("userData"); // Grabbing all the documents from the data and putting it into a MongoCollection within the code.

        Person jeff = new Person("jefferyb", "jeffbisrich", "amazonhs", false, false, "Knows computer science and coding. I am Rich."); // A person with all of these. All of this will be stored in the Database
        Person mark = new Person("markz", "markzisrich", "metahs", false, false, "Knows computer science and coding. I am Rich."); // Another rich person. 
        testDocs.insertOne(convert(jeff)); // Putting rich person into the Collection named Test
        Document query = new Document("isTutor", false); // Just an example of a query. This will set the query to search for all of the documents with the "isTutor" value set to true.
        FindIterable<Document> cursor = testDocs.find(); // Grabs all the documents with the query filter up above.
        System.out.println("\n" + cursor.first()); // Prints out the first document with the isAdmin set to jeff object isAdmin.

        //testDocs.findOneAndReplace(jeffisAdmin, convert(mark)); // Finding the first document with the filter of isAdmin == jeff object isAdmin and replacing it with the mark object data.
        cursor = testDocs.find(query); // Grabs all the documents with the query filter up above.
        Iterator<Document> it = cursor.iterator(); // Gets all the data into a object that we can go through.
        System.out.println();
        System.out.println(cursor.first()); // Prints out the first document with the isAdmin set to jeff object isAdmin.

        Document all = new Document(); // Sets the filter to no filter
        testDocs.deleteMany(all); // Deletes all current data within the collection
        

        for (int i = 0; i < 10; i++) { // Creating 10 new people as examples in the DB
            Person newPerson = new Person("person" + i, "jhs" + (i * 54354), "jhs", false, (Math.random() <= 0.5) ? true:false, "I am person" + i + " and password do be " + (i * 54354));
            testDocs.insertOne(convert(newPerson));
        }

        
        System.out.println();
        while (it.hasNext()) { // Going through the Iterator to find all the documents based off of the query.
            // Document next = cursor.cursor().next();
            System.out.println(it.next());
        }

        query = new Document("username", "person1");

        Bson updates = Updates.combine(
            Updates.set("description", "I have updated my description"),
            Updates.addToSet("ego", 999),
            Updates.currentTimestamp("lastUpdated")
        );

        UpdateOptions options = new UpdateOptions().upsert(true);

        try {
            UpdateResult result = testDocs.updateOne(query, updates, options);
            System.out.println("Modified document count: " + result.getModifiedCount());
            System.out.println("Upserted id: " + result.getUpsertedId()); // only contains a value when an upsert is performed
        } catch (MongoException me) {
            System.err.println("Unable to update due to an error: " + me);
        }

        List<Document> tutors = new ArrayList<Document>();
        //tutors.add(new Document("tutorName", "Example Name").append("TutorDescription", "I am the Tutor Description"));

        Document doc = new Document("TutorRequests", tutors);
        testDocs.insertOne(doc);

        //System.out.println(doc);

        query = new Document();
        testDocs.deleteMany(query);

        
    }

    /**
     * Converts a Person Object into a storeable document.
     * @param p The Person object you want to convert
     * @return The storeable document
     */
    public static Document convert(Person p) {
        return new Document("ID", p.getId()).append("username", p.getUsername()).append("password", p.getPassword()).append("description", p.getDescription()).append("isAdmin", p.isAdmin()).append("isTutor", p.isTutor());
    }
}
