package GUIStuff;

import VAC.EmailHandler;
import VAC.MongoDB;
import VAC.Scholarly;
import org.bson.Document;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class WelcomeFrame extends JFrame implements ActionListener {
    private static final MongoDB db = ScholarlyFrame.db;
    private static final EmailHandler eh = ScholarlyFrame.eh;

    private final JButton loginButton;
    private final JButton registerButton;

    public static JTextField username;
    public static JPasswordField Password;

    private static JLabel firstNameLabel, lastNameLabel, orgLabel, usernameLabel, passwordLabel, emailLabel, phoneLabel;
    protected static JTextField firstName, lastName, org, registerUsername, password, email, phone;
    private static JButton next;
    private static JButton register;

    private static final String BASIC_INFO = "Basic Info";
    private static final String LOGIN_INFO = "Login Info";

    private BufferedImage image;

    public WelcomeFrame() {
        try {
            image = ImageIO.read(new FileInputStream("C:\\Users\\vedan\\OneDrive\\Desktop\\Scholarly\\src\\main\\java\\GUIStuff\\logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setTitle("Scholarly Connect");
        this.setIconImage(image);
        this.setResizable(false);
        int WIDTH = ScholarlyFrame.WIDTH / 2;
        int HEIGHT = ScholarlyFrame.HEIGHT / 2;
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel welcomeInfo = new JPanel();
        JPanel blueBorder1 = new JPanel(), blueBorder2 = new JPanel();
        JPanel blackBorder1 = new JPanel(), blackBorder2 = new JPanel();

        blueBorder1.setBackground(Color.BLUE);
        blueBorder2.setBackground(Color.BLUE);
        blackBorder1.setBackground(Color.BLACK);
        blackBorder2.setBackground(Color.BLACK);

        String welcomeMessage = "Welcome to Scholarly!\n\nWhat would you like to do?";

        JTextPane pane = new JTextPane();
        pane.setEditable(false);
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setBold(set, true);

        StyledDocument doc = pane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        pane.setCharacterAttributes(set, true);
        pane.setText(welcomeMessage);

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        loginButton.setBounds(WIDTH / 3 - 50, 100, 100, 50);
        loginButton.addActionListener(this);
        registerButton.setBounds((WIDTH / 3) * 2 - 50, 100, 100, 50);
        registerButton.addActionListener(this);

        welcomeInfo.add(pane);

        this.getContentPane().add(loginButton);
        this.getContentPane().add(registerButton);
        this.getContentPane().add(welcomeInfo, BorderLayout.CENTER);
        this.getContentPane().add(blueBorder1, BorderLayout.PAGE_START);
        this.getContentPane().add(blackBorder1, BorderLayout.LINE_START);
        this.getContentPane().add(blueBorder2, BorderLayout.PAGE_END);
        this.getContentPane().add(blackBorder2, BorderLayout.LINE_END);

        this.setVisible(true);
    }

    public void loginGUI() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JFrame frame = new JFrame();
        frame.setTitle("Login");
        frame.add(panel);
        frame.setPreferredSize(new Dimension(400, 200));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setIconImage(image);

        JLabel label = new JLabel("Username");
        label.setBounds(100, 8, 70, 20);
        panel.add(label);

        username = new JTextField();
        username.setBounds(100, 27, 193, 28);
        panel.add(username);

        JLabel password1 = new JLabel("Password");
        password1.setBounds(100, 55, 70, 20);
        panel.add(password1);

        Password = new JPasswordField();
        Password.setBounds(100, 75, 193, 28);
        panel.add(Password);

        JButton button = new JButton("Login");
        button.setBounds(100, 110, 90, 25);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.addActionListener(e -> {
            if (Scholarly.login(username.getText(), new String(Password.getPassword()))) {
                System.out.println("Login Successful");
                Scholarly.loggedIn = true;
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Incorrect username or password");
            }
        });
        panel.add(button);

        JButton backToButton = new JButton("Back");
        backToButton.setBounds(25, 125, 45, 25);
        backToButton.setBackground(Color.white);
        backToButton.addActionListener(e -> {
            frame.dispose();
            new WelcomeFrame();
        });
        backToButton.setMargin(new Insets(0, 0, 0, 0));
        backToButton.setFocusable(false);
        panel.add(backToButton);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void registerGUI() {
        JTabbedPane pane = new JTabbedPane();

        JPanel basicPanel = new JPanel();
        basicPanel.setLayout(null);

        JFrame frame = new JFrame();
        frame.setTitle("Registration");
        frame.setLocationRelativeTo(null);
        frame.add(basicPanel);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setIconImage(image);

        firstNameLabel = new JLabel("First Name");
        firstNameLabel.setBounds(100, 8, 70, 20);
        basicPanel.add(firstNameLabel);

        firstName = new JTextField();
        firstName.setBounds(100, 27, 193, 28);
        basicPanel.add(firstName);

        lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setBounds(100, 55, 70, 20);
        basicPanel.add(lastNameLabel);

        lastName = new JTextField();
        lastName.setBounds(100, 75, 193, 28);
        basicPanel.add(lastName);

        emailLabel = new JLabel("Email");
        emailLabel.setBounds(100, 102, 70, 20);
        basicPanel.add(emailLabel);

        email = new JTextField();
        email.setBounds(100, 121, 193, 28);
        basicPanel.add(email);

        phoneLabel = new JLabel("Phone Number");
        phoneLabel.setBounds(100, 149, 100, 20);
        basicPanel.add(phoneLabel);

        phone = new JTextField();
        phone.setBounds(100, 168, 193, 28);
        basicPanel.add(phone);

        orgLabel = new JLabel("Organization");
        orgLabel.setBounds(100, 196, 100, 20);
        basicPanel.add(orgLabel);

        org = new JTextField();
        org.setBounds(100, 215, 193, 28);
        basicPanel.add(org);

        next = new JButton("Next");
        next.setBounds(100, 250, 90, 25);
        next.setForeground(Color.WHITE);
        next.setBackground(Color.BLACK);
        next.addActionListener(e -> pane.setSelectedIndex(1));
        basicPanel.add(next);

        JButton back = new JButton("Back");
        back.setBounds(25, 125, 45, 25);
        back.setBackground(Color.white);
        back.addActionListener(e -> {
            frame.dispose();
            new WelcomeFrame();
        });
        back.setMargin(new Insets(0, 0, 0, 0));
        back.setFocusable(false);
        basicPanel.add(back);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);

        usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(100, 8, 70, 20);
        loginPanel.add(usernameLabel);

        registerUsername = new JTextField();
        registerUsername.setBounds(100, 27, 193, 28);
        loginPanel.add(registerUsername);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(100, 55, 70, 20);
        loginPanel.add(passwordLabel);

        password = new JTextField();
        password.setBounds(100, 75, 193, 28);
        loginPanel.add(password);

        register = new JButton("Register");
        register.setBounds(100, 110, 90, 25);
        register.setForeground(Color.WHITE);
        register.setBackground(Color.BLACK);
        register.addActionListener(e -> {
            if (firstName.getText().trim().length() == 0 || lastName.getText().trim().length() == 0 || email.getText().trim().length() == 0 || phone.getText().trim().length() == 0 || org.getText().trim().length() == 0 || registerUsername.getText().trim().length() == 0 || password.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(frame, "Missing Fields");
            } else {
                int randomNumber = (int) (Math.random() * 99) + 1;
                eh.sendEmail(email.getText(), "Confirming Your Email", "Hello!\n\nThis is a bot who is just making sure your email works. Here's a gift! Your confirmation code!\n\nYour code: " + randomNumber + "\n\nThank you,\nScholarly");
                int userNumber = Integer.parseInt(JOptionPane.showInputDialog(frame, "Confirmation Code"));
                if (userNumber != randomNumber) {
                    JOptionPane.showMessageDialog(frame, "Your email was invalid. Please try again.");
                } else if (!db.createUser(firstName.getText().trim(), lastName.getText().trim(), email.getText().trim(), phone.getText().trim(), org.getText().trim(),
                        registerUsername.getText().trim(), password.getText().trim())) {
                    JOptionPane.showMessageDialog(frame, "An account with this username already exists");
                } else {
                    frame.dispose();
                    this.loginGUI();
                }
            }
        });
        loginPanel.add(register);

        loginPanel.add(back);

        pane.add(BASIC_INFO, basicPanel);
        pane.add(LOGIN_INFO, loginPanel);

        frame.getContentPane().add(pane);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public void editRegister() {
        Document currentData = db.findUser(username.getText());

        JTabbedPane pane = new JTabbedPane();

        JPanel basicPanel = new JPanel();
        basicPanel.setLayout(null);

        JFrame frame = new JFrame();
        frame.setTitle("Edit Info");
        frame.setLocationRelativeTo(null);
        frame.add(basicPanel);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setIconImage(image);

        firstNameLabel = new JLabel("First Name");
        firstNameLabel.setBounds(100, 8, 70, 20);
        basicPanel.add(firstNameLabel);

        String[] name = currentData.getString("name").split(" ");
        firstName = new JTextField();
        firstName.setBounds(100, 27, 193, 28);
        firstName.setText(name[0]);
        basicPanel.add(firstName);

        lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setBounds(100, 55, 70, 20);
        basicPanel.add(lastNameLabel);

        lastName = new JTextField();
        lastName.setBounds(100, 75, 193, 28);
        lastName.setText(name[1]);
        basicPanel.add(lastName);

        emailLabel = new JLabel("Email");
        emailLabel.setBounds(100, 102, 70, 20);
        basicPanel.add(emailLabel);

        email = new JTextField();
        email.setBounds(100, 121, 193, 28);
        email.setText(currentData.getString("email"));
        basicPanel.add(email);

        phoneLabel = new JLabel("Phone Number");
        phoneLabel.setBounds(100, 149, 100, 20);
        basicPanel.add(phoneLabel);

        phone = new JTextField();
        phone.setBounds(100, 168, 193, 28);
        phone.setText(currentData.getString("number"));
        basicPanel.add(phone);

        orgLabel = new JLabel("Organization");
        orgLabel.setBounds(100, 196, 100, 20);
        basicPanel.add(orgLabel);

        org = new JTextField();
        org.setBounds(100, 215, 193, 28);
        org.setText(currentData.getString("organization"));
        basicPanel.add(org);

        next = new JButton("Next");
        next.setBounds(100, 250, 90, 25);
        next.setForeground(Color.WHITE);
        next.setBackground(Color.BLACK);
        next.addActionListener(e -> pane.setSelectedIndex(1));
        basicPanel.add(next);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);

        usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(100, 8, 70, 20);
        loginPanel.add(usernameLabel);

        registerUsername = new JTextField();
        registerUsername.setBounds(100, 27, 193, 28);
        registerUsername.setText(currentData.getString("username"));
        loginPanel.add(registerUsername);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(100, 55, 70, 20);
        loginPanel.add(passwordLabel);

        password = new JTextField();
        password.setBounds(100, 75, 193, 28);
        password.setText(currentData.getString("password"));
        loginPanel.add(password);

        JLabel descriptionLabel = new JLabel("Description");
        descriptionLabel.setBounds(100, 102, 70, 20);
        loginPanel.add(descriptionLabel);

        JTextArea description = new JTextArea();
        description.setBounds(100, 121, 193, 124);
        description.setText(currentData.getString("description"));
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        loginPanel.add(description);

        if (currentData.getBoolean("isTutor") || currentData.getString("status").equals("aTutor") || currentData.getString("status").equals("aStudent")) {
            description.setEditable(true);
        }

        register = new JButton("Submit");
        register.setBounds(100, 250, 90, 25);
        register.setForeground(Color.WHITE);
        register.setBackground(Color.BLACK);
        register.addActionListener(e -> {
            if (firstName.getText().trim().length() == 0 || lastName.getText().trim().length() == 0 || email.getText().trim().length() == 0 || phone.getText().trim().length() == 0 || org.getText().trim().length() == 0 || registerUsername.getText().trim().length() == 0 || password.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(frame, "Missing Fields");
            } else {
                if (!email.getText().equals(currentData.getString("email"))) {
                    int randomNumber = (int) (Math.random() * 99) + 1;
                    eh.sendEmail(email.getText(), "Confirming Your Email", "Hello!\n\nLooks like you changed your email! Here's you're confirmation code!\n\nYour code: " + randomNumber + "\n\nThank you,\nScholarly");
                    int userNumber = Integer.parseInt(JOptionPane.showInputDialog(frame, "Confirmation Code"));
                    if (userNumber != randomNumber) {
                        JOptionPane.showMessageDialog(frame, "Your email was invalid. Please try again.");
                    }
                }
                db.editUser(firstName.getText(), lastName.getText(), email.getText(), phone.getText(), org.getText(), username.getText(), password.getText(), description.getText());
                ScholarlyFrame.docs = db.getTutors(db.findUser(username.getText()).getString("organization"));
                frame.dispose();
            }
        });
        loginPanel.add(register);

        pane.add(BASIC_INFO, basicPanel);
        pane.add(LOGIN_INFO, loginPanel);

        frame.getContentPane().add(pane);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            this.dispose();
            this.loginGUI();
        } else if (e.getSource() == registerButton) {
            this.dispose();
            this.registerGUI();
        }
    }

}