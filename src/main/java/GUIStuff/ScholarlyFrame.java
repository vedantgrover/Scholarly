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

    private static JButton tutorRemoveButton;

    protected static BufferedImage image;

    protected static JScrollPane pane;
    protected static JPanel panel;

    private static JPanel descriptionPanel = new JPanel();

    private JTextArea tutorDescription = new JTextArea();

    protected static FindIterable<Document> docs;

    private String currentUser = "";

    public ScholarlyFrame() {
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

        WelcomeFrame wf = new WelcomeFrame();
        while (!Scholarly.loggedIn) {
            System.out.println();
        }

        String tier;

        JPanel loginInfoPanel = new JPanel();
        loginInfoPanel.setLayout(null);
        loginInfoPanel.setBounds(0, 0, WIDTH - 15, 45);

        JLabel userName = new JLabel("Username: " + WelcomeFrame.username.getText());
        userName.setBounds(WIDTH / 4 - 75, 10, 150, 25);
        loginInfoPanel.add(userName);

        JButton name = new JButton(db.findUser(WelcomeFrame.username.getText()).get("name").toString());
        name.setBounds(WIDTH / 2 - 75, 10, 150, 25);
        name.addActionListener(e -> {
            wf.editRegister();
        });
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

        docs = db.getTutors(db.findUser(WelcomeFrame.username.getText()).getString("organization"));

        panel = new JPanel();
        panel.setLayout(new GridLayout(maxTutors, 1));

        pane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        listTutors(docs);

        panel.setPreferredSize(new Dimension(pane.getWidth(), 12000));

        pane.setBounds(0, 45, 333, 370);

        pane.setViewportView(panel);

        this.getContentPane().add(descriptionPanel);
        this.getContentPane().add(pane);

        Document data = db.findUser(WelcomeFrame.username.getText());
        tutorRemoveButton = new JButton("Remove Tutor");
        if (data.getBoolean("isAdmin")) {
            tutorRemoveButton.setBounds(0, 415, pane.getWidth(), 75);
            tutorRemoveButton.setEnabled(false);
            tutorRemoveButton.addActionListener(e -> {
                String userPassword = JOptionPane.showInputDialog(myFrame, "Please Enter Your Password");
                if (userPassword != null && !userPassword.equals(data.getString("password"))) {
                    JOptionPane.showMessageDialog(myFrame, "Incorrect Password");
                }

                db.removeTutor(currentUser);
                removeTutorButton(db.findUser(currentUser));
                panel.revalidate();
                panel.repaint();

                descriptionPanel.removeAll();
                descriptionPanel.revalidate();
                descriptionPanel.repaint();

                Document currentTutorData = db.findUser(currentUser);
                String message = "Hello " + currentTutorData.getString("name") + ",\n\nWe just got a notification saying that you are no longer a tutor! We hope that this was for the better. We are just informing you that you are no longer a tutor.\n\nBest Regards,\nScholarly";
                eh.sendEmail(currentTutorData.getString("email"), "So...what happened?", message);
            });
            this.getContentPane().add(tutorRemoveButton);

            JButton button = new JButton("Tutor Requests");
            button.setBounds(333, 415, 660, 150);
            button.addActionListener(e -> new TutorRequests());
            this.getContentPane().add(button);

            JButton applyButton1 = new JButton("Create Admins");
            applyButton1.setBounds(0, 490, pane.getWidth(), 75);
            applyButton1.addActionListener(e -> new AdminApply());
            this.getContentPane().add(applyButton1);
        } else {
            JButton applyButton = new JButton("Apply");
            applyButton.setBounds(0, 415, pane.getWidth(), 150);
            applyButton.addActionListener(e -> new TutorApply());
            this.getContentPane().add(applyButton);
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

    private void listTutors(FindIterable<Document> tutorDocs) {
        for (Document tutorDoc : tutorDocs) {
            JButton tutorButton = new JButton(tutorDoc.getString("name"));
            tutorButton.setBounds(0, 0, pane.getWidth(), 100);
            tutorButton.addActionListener(e -> {
                currentUser = tutorDoc.getString("username");
                tutorRemoveButton.setEnabled(true);

                System.out.println(currentUser);

                updateDescription();
            });
            panel.add(tutorButton);
            tutorButtons.add(tutorButton);
        }
    }

    private void updateDescription() {
        descriptionPanel.removeAll();

        descriptionPanel.setLayout(null);
        descriptionPanel.setBounds(WIDTH / 3, 45, (WIDTH * 2/3) - 15, pane.getHeight());

        tutorDescription.setText(ScholarlyFrame.studentInfo(db.findUser(currentUser)));
        tutorDescription.setBounds(10, 10, 434, 300);
        tutorDescription.setEditable(false);
        tutorDescription.setOpaque(false);
        tutorDescription.setLineWrap(true);
        tutorDescription.setWrapStyleWord(true);

        descriptionPanel.add(tutorDescription);
        descriptionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        descriptionPanel.revalidate();
        descriptionPanel.repaint();
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
    
    public static void removeTutorButton(Document doc) {
        for (JButton cb : tutorButtons) {
            if (cb.getText().equals(doc.getString("name"))) {
                panel.remove(cb);
                tutorButtons.remove(cb);
                break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}
