package VAC;

import org.bson.Document;

public class Scholarly {

    private static final MongoDB db = new MongoDB();
    public static boolean loggedIn = false;

    public static boolean login(String username, String password) {
        if (!db.checkIfUserExists(username)) {
            System.out.println("No Account");
            return false;
        }

        Document user = db.findUser(username);
        return user.get("username").equals(username) && user.get("password").equals(password);

    }
}
