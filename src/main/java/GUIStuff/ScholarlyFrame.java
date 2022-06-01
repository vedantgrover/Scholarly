package GUIStuff;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.*;

import VAC.MongoDB;
import VAC.Scholarly;
import com.mongodb.client.FindIterable;
import org.bson.Document;

public class ScholarlyFrame extends JFrame implements ActionListener {

    protected static final int WIDTH = 1000;
    protected static final int HEIGHT = 600;
    protected static final MongoDB db = new MongoDB();

    private static JFrame myFrame;

    private static final int maxTutors = 200;

    private static final ArrayList<Document> tutors = new ArrayList<Document>();
    private static final ArrayList<JButton> tutorButtons = new ArrayList<JButton>();

    protected static BufferedImage image;

    public ScholarlyFrame() {
        db.switchToAdmin("CJobi");
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
        loginInfoPanel.setBounds(0, 0, 985, 45);

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
        Iterator<Document> it = docs.iterator();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(maxTutors, 1));
        //panel.setMaximumSize(new Dimension(100, 100 * people.length));

        JScrollPane pane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        while (it.hasNext()) {
            Document doc = it.next();
            JButton tutorButton = new JButton(doc.getString("name"));
            tutorButton.setPreferredSize(new Dimension(pane.getWidth(), 100));
            tutorButton.addActionListener(e -> {
                //System.out.println(doc.getString("name"));

                JPanel tutorPanel = new JPanel();
                tutorPanel.setLayout(null);
                tutorPanel.setBounds(333, 45, myFrame.getWidth() - 333, 370);

                JLabel label = new JLabel(doc.getString("username"));
                label.setBounds(333, 45, 150, 25);
                tutorPanel.add(label);

                tutorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
                myFrame.getContentPane().add(tutorPanel);
                tutorPanel.revalidate();
                tutorPanel.repaint();
            });
            panel.add(tutorButton);
            tutors.add(db.findUser(doc.getString("username")));
            tutorButtons.add(tutorButton);
        }

        panel.setPreferredSize(new Dimension(333, 12000));

        pane.setBounds(0, 45, 333, 370);

        pane.setViewportView(panel);

        this.getContentPane().add(pane);
        Document data = db.findUser(WelcomeFrame.username.getText());
        if (data.getBoolean("isAdmin")) {
            JButton button = new JButton("Tutor Requests ( " + data.getList("TutorRequests", List.class).size() + " )");
            button.setBounds(333, 415, 660, 150);
            button.addActionListener(e -> new TutorRequests());
            this.getContentPane().add(button);
        }

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}