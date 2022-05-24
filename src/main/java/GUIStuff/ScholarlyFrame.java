package GUIStuff;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ScholarlyFrame extends JFrame implements ActionListener {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;

    private URL iconURL = getClass().getResource("logo.png");
    private ImageIcon icon = new ImageIcon(iconURL);

    public ScholarlyFrame() {
        this.setIconImage(icon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setFocusable(true);
        new WelcomeFrame();
        // this.setVisible(true);

    }

    public class WelcomeFrame extends JFrame implements ActionListener {
        private final int WIDTH = ScholarlyFrame.WIDTH / 2;
        private final int HEIGHT = ScholarlyFrame.HEIGHT / 2;

        private JButton loginButton;
        private JButton registerButton;

        private static JLabel password1, label;
        public static JTextField username;
        private static JButton button;
        public static JPasswordField Password;

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
                frame.dispose();
                System.out.println("Username: " + username.getText() + "\nPassword: " + Password.getText() + "\n");
            });
            panel.add(button);

            frame.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton) {
                this.dispose();
                this.loginGUI();         
            } else if (e.getSource() == registerButton) {
                System.out.println("Register Requested");
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
