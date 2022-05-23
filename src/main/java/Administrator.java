import java.util.Scanner;

public class Administrator {

    private final Scanner sc;
    
    private String username;
    private String password;

    private boolean unlocked;

    public Administrator(String username, String password) {
        this.username = username;
        this.password = password;

        this.unlocked = false;

        this.sc = new Scanner(System.in);

        login();
    }

    public void login() {
        while (!unlocked) {
            System.out.print("Username >> ");
            String userInput = sc.nextLine();
            
            if (!userInput.equals(username)) {
                System.out.println("Incorrect Username. Try again");
                continue;
            }

            System.out.print("Password >> ");
            userInput = sc.nextLine();

            if (!userInput.equals(password)) {
                System.out.println("Incorrect Password. Try again");
                continue;
            }

            this.unlocked = true;
        }
    }

    private void lock() {
        this.unlocked = true;
    }

    public void getCommand(String command) {
        login();
        switch (command.toLowerCase()) {
            case "review":
                break;
            case "quit":
                this.lock();
                break;
            default:
                System.out.println("Invalid Command");
            
        }
    }

    
}
