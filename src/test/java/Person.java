import java.util.UUID;

public class Person {
    private final UUID id;
    private final String username;
    private final String password;
    private final boolean isAdmin;
    private final boolean isTutor;
    private final String description;

    public Person(String username, String password, boolean isAdmin, boolean isTutor, String description) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isTutor = isTutor;
        this.description = description;

        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isTutor() {
        return isTutor;
    }

    public String getDescription() {
        return description;
    }

}
