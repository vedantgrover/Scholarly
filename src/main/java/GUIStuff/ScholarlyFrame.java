package GUIStuff;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import VAC.MongoDB;
import VAC.Scholarly;

public class ScholarlyFrame extends JFrame implements ActionListener {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;

    private URL iconURL = getClass().getResource("logo.png");
    private ImageIcon icon = new ImageIcon(iconURL);

    private static Object lock = new Object();

    public ScholarlyFrame() {
        this.setIconImage(icon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setFocusable(true);
        WelcomeFrame wFrame = new WelcomeFrame();
        while (!Scholarly.loggedIn) {
            System.out.println();
        }
        this.setVisible(true);

    }

    public class WelcomeFrame extends JFrame implements ActionListener {
        private final int WIDTH = ScholarlyFrame.WIDTH / 2;
        private final int HEIGHT = ScholarlyFrame.HEIGHT / 2;
        private static MongoDB db = new MongoDB();

        private JButton loginButton;
        private JButton registerButton;

        private static JLabel password1, label;
        public static JTextField username;
        private static JButton button;
        private static JButton backToButton;
        public static JPasswordField Password;

        private static JLabel firstNameLabel, lastNameLabel, orgLabel, usernameLabel, passwordLabel, emailLabel;
        public static JTextField firstName, lastName, org, registerUsername, password, email;
        private static JButton register, back;

        private static final String BASIC_INFO = "Basic Info";
        private static final String LOGIN_INFO = "Login Info";

        public WelcomeFrame() {
            this.setTitle("Scholarly Connect");
            this.setIconImage(icon.getImage());
            this.setResizable(false);
            this.setSize(WIDTH, HEIGHT);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel welcomeInfo = new JPanel();
            // welcomeInfo.setSize(new Dimension(100, 100)); // Check why this isn't
            // working.
            // welcomeInfo.setBackground(Color.BLACK);
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
            frame.setTitle("Login Page");
            frame.setLocationRelativeTo(null);
            frame.add(panel);
            frame.setSize(new Dimension(400, 200));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setIconImage(icon.getImage());

            label = new JLabel("Username");
            label.setBounds(100, 8, 70, 20);
            panel.add(label);

            username = new JTextField();
            username.setBounds(100, 27, 193, 28);
            panel.add(username);

            password1 = new JLabel("Password");
            password1.setBounds(100, 55, 70, 20);
            panel.add(password1);

            Password = new JPasswordField();
            Password.setBounds(100, 75, 193, 28);
            panel.add(Password);

            button = new JButton("Login");
            button.setBounds(100, 110, 90, 25);
            button.setForeground(Color.WHITE);
            button.setBackground(Color.BLACK);
            button.addActionListener(e -> {
                if (Scholarly.login(username.getText(), Password.getText())) {
                    System.out.println("Login Successful");
                    Scholarly.loggedIn = true;
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Incorrect username or password");
                }
            });
            panel.add(button);

            backToButton = new JButton("Back");
            backToButton.setBounds(25, 125, 45, 25);
            backToButton.setBackground(Color.white);
            backToButton.addActionListener(e -> {
                frame.dispose();
                new WelcomeFrame();
            });
            backToButton.setMargin(new Insets(0, 0, 0, 0));
            backToButton.setFocusable(false);
            panel.add(backToButton);

            frame.setVisible(true);
        }

        public void registerGUI() {
            JTabbedPane pane = new JTabbedPane();

            JPanel basicPanel = new JPanel();
            basicPanel.setLayout(null);

            JFrame frame = new JFrame();
            frame.setTitle("Login Page");
            frame.setLocationRelativeTo(null);
            frame.add(basicPanel);
            frame.setSize(new Dimension(400, 300));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setIconImage(icon.getImage());

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

            orgLabel = new JLabel("Organization");
            orgLabel.setBounds(100, 149, 100, 20);
            basicPanel.add(orgLabel);

            org = new JTextField();
            org.setBounds(100, 168, 193, 28);
            basicPanel.add(org);

            back = new JButton("Back");
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
                if (!db.createUser(firstName.getText(), lastName.getText(), email.getText(), org.getText(),
                        registerUsername.getText(), password.getText())) {
                    JOptionPane.showMessageDialog(frame, "An account with this username already exists");
                } else {
                    frame.dispose();
                    this.loginGUI();
                }
            });
            loginPanel.add(register);

            loginPanel.add(back);

            pane.add(BASIC_INFO, basicPanel);
            pane.add(LOGIN_INFO, loginPanel);

            frame.getContentPane().add(pane);

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

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}