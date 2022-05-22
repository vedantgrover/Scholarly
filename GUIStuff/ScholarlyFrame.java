package GUIStuff;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;

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
        // welcomePage();
        new WelcomeFrame();
        // this.setVisible(true);

    }

    public class WelcomeFrame extends JFrame implements ActionListener {
        private final int WIDTH = ScholarlyFrame.WIDTH / 2;
        private final int HEIGHT = ScholarlyFrame.HEIGHT / 2;

        private JButton loginButton;
        private JButton registerButton;

        public WelcomeFrame() {
            this.setTitle("Scholarly");
            this.setIconImage(icon.getImage());
            this.setResizable(false);
            this.setSize(WIDTH, HEIGHT);
            this.setLocationRelativeTo(null);
            this.setVisible(true);

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
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton) {
                System.out.println("Login Requested");
            } else if (e.getSource() == registerButton) {
                System.out.println("Register Requested");
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
