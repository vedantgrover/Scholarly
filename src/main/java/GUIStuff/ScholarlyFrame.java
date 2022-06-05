package GUIStuff;

import VAC.EmailHandler;
import VAC.MongoDB;
import VAC.Scholarly;
import com.mongodb.client.FindIterable;
import org.bson.Document;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class ScholarlyFrame extends JFrame implements ActionListener {

    protected static final int WIDTH = 1000;
    protected static final int HEIGHT = 600;
    protected static final MongoDB db = new MongoDB();
    protected static final EmailHandler eh = new EmailHandler();

    protected static JFrame myFrame;

    private static JPanel tutorPanel = new JPanel();

    private static final int maxTutors = 200;

    private static final ArrayList<Document> tutors = new ArrayList<Document>();
    private static final ArrayList<JButton> tutorButtons = new ArrayList<JButton>();

    private static JButton applyTutorButton;

    protected static BufferedImage image;

    protected static JScrollPane pane;
    protected static JPanel panel;

    public ScholarlyFrame() {
        // db.switchToAdmin("CJobi");
        applyTutorButton = new JButton();
        myFrame = this;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/GUIStuff/logo.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setIconImage(image);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(true);
        this.setResizable(false);
        this.setLayout(null);

        new WelcomeFrame();
        while (!Scholarly.loggedIn) {
            System.out.println();
        }

        String tier;

        JPanel loginInfoPanel = new JPanel();
        loginInfoPanel.setLayout(null);
        loginInfoPanel.setBounds(0, 0, 987, 45);

        JLabel userName = new JLabel("Username: " + WelcomeFrame.username.getText());
        userName.setBounds(WIDTH / 4 - 75, 10, 150, 25);
        loginInfoPanel.add(userName);

        JButton name = new JButton(db.findUser(WelcomeFrame.username.getText()).get("name").toString());
        name.setBounds(WIDTH / 2 - 75, 10, 150, 25);
        loginInfoPanel.add(name);

        if (db.findUser(WelcomeFrame.username.getText()).getBoolean("isAdmin")) {
            tier = "Administrator";
        } else if (db.findUser(WelcomeFrame.username.getText()).getBoolean("isTutor")) {
            tier = "Tutor";
        } else {
            tier = "Student";
        }

        JLabel custTier = new JLabel("Tier: " + tier);
        custTier.setBounds(750 - 75, 10, 150, 25);
        loginInfoPanel.add(custTier);

        loginInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        this.getContentPane().add(loginInfoPanel);

        FindIterable<Document> docs = db.getTutors(db.findUser(WelcomeFrame.username.getText()).getString("organization"));

        panel = new JPanel();
        panel.setLayout(new GridLayout(maxTutors, 1));

        pane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        for (Document tutorDoc : docs) {
            createNewTutorButton(tutorDoc);
        }

        panel.setPreferredSize(new Dimension(pane.getWidth(), 12000));

        pane.setBounds(0, 45, 333, 370);

        pane.setViewportView(panel);

        this.getContentPane().add(tutorPanel);
        this.getContentPane().add(pane);

        JButton applyButton = new JButton("Apply");
        applyButton.setBounds(0, 415, pane.getWidth(), 150);
        applyButton.addActionListener(e -> new TutorApply());
        this.getContentPane().add(applyButton);

        Document data = db.findUser(WelcomeFrame.username.getText());
        if (data.getBoolean("isAdmin")) {
            JButton button = new JButton("Tutor Requests");
            button.setBounds(333, 415, 660, 150);
            button.addActionListener(e -> new TutorRequests());
            this.getContentPane().add(button);
        }
        if (!data.getBoolean("isAdmin") && !data.getBoolean("isTutor")) {
            JButton button2 = new JButton("Request For a Tutor");
            button2.setBounds(333, 415, 660, 150);
            button2.addActionListener(e -> new StudentApply());
            this.getContentPane().add(button2);
        } else if (data.getBoolean("isTutor")) {
            JButton button3 = new JButton("Student Requests");
            button3.setBounds(333, 415, 660, 150);
            button3.addActionListener(e -> new StudentRequests());
            this.getContentPane().add(button3);
        }

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public static String studentInfo(Document doc) { 
        return "Name: " + doc.getString("name") + "\n\nPhone Number: " + doc.getString("number")
        + "\nEmail: " + doc.getString("email") + "\n\n" + doc.getString("description");
    }

    public static void createNewTutorButton(Document doc) {
        JButton tutorButton = new JButton(doc.getString("name"));
        tutorButton.setPreferredSize(new Dimension(pane.getWidth(), 100));
        tutorButton.addActionListener(e -> {
            // System.out.println(doc.getString("name"));

            tutorPanel.removeAll();

            tutorPanel.setLayout(null);
            tutorPanel.setBounds(333, 45, 652, 370);

            String tutorInfo = "Name: " + doc.getString("name") + "\n\nPhone Number: " + doc.getString("number")
                    + "\nEmail: " + doc.getString("email") + "\n\n" + doc.getString("description");
            JTextArea tutorIDisplay = new JTextArea(tutorInfo);
            tutorIDisplay.setBounds(10, 10, 642, 370);
            tutorIDisplay.setLineWrap(true);
            tutorIDisplay.setWrapStyleWord(true);
            tutorIDisplay.setEditable(false);
            tutorIDisplay.setOpaque(false);
            tutorPanel.add(tutorIDisplay);
            // Create Request Button for Tutors

            tutorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
            tutorPanel.revalidate();
            tutorPanel.repaint();
        });
        panel.add(tutorButton);
        tutors.add(db.findUser(doc.getString("username")));
        tutorButtons.add(tutorButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}
