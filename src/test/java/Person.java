import java.util.UUID;

public class Person {
    private UUID id;
    private String username;
    private String password;
    private String organization;
    private boolean isAdmin;
    private boolean isTutor;
    private String description;

    public Person(String username, String password, String org, boolean isAdmin, boolean isTutor, String description) {
        this.username = username;
        this.password = password;
        this.organization = org;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isTutor() {
        return isTutor;
    }

    public void setTutor(boolean isTutor) {
        this.isTutor = isTutor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
