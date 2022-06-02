package GUIStuff;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;

import org.bson.Document;
import org.bson.conversions.Bson;

import VAC.MongoDB;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class TutorRequests extends JFrame implements ActionListener {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 400;

    private static MongoDB db = ScholarlyFrame.db;

    private static JPanel descriptionPanel = new JPanel();

    private static ArrayList<JButton> tutorRButtons = new ArrayList<JButton>();
    private static ArrayList<Document> tutorRData = new ArrayList<Document>();

    private static JButton approveButton, declineButton;

    public TutorRequests() {
        approveButton = new JButton("Approve");
        declineButton = new JButton("Decline");

        JFrame myFrame = this;
        this.setTitle("Tutor Requests");
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setIconImage(ScholarlyFrame.image);
        this.setFocusable(true);
        this.setLayout(null);

        FindIterable<Document> docs = db
                .getTutorRequests(db.findUser(WelcomeFrame.username.getText()).getString("organization"));
        Iterator<Document> it = docs.iterator();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(200, 1));

        JScrollPane pane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        while (it.hasNext()) {
            Document doc = it.next();
            JButton tutorRButton = new JButton(doc.getString("name"));
            tutorRButton.setBounds(0, 0, pane.getWidth(), 100);
            //tutorRButton.setPreferredSize(new Dimension(pane.getWidth(), 100));
            tutorRButton.addActionListener(e -> {
                // System.out.println(doc.getString("name"));

                descriptionPanel.setLayout(null);
                descriptionPanel.setBounds(WIDTH/3, 0, 453, HEIGHT - 37);

                JLabel tutorRLabel = new JLabel(doc.getString("description"));
                tutorRLabel.setBounds(10, 10, 453, HEIGHT - 37);

                descriptionPanel.add(tutorRLabel);
                descriptionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
                myFrame.getContentPane().add(descriptionPanel);
                descriptionPanel.revalidate();
                descriptionPanel.repaint();
            });
            panel.add(tutorRButton);
            tutorRData.add(db.findUser(doc.getString("username")));
            tutorRButtons.add(tutorRButton);
        }

        panel.setPreferredSize(new Dimension(WIDTH/3, 12000));

        pane.setBounds(0, 0, WIDTH/3, HEIGHT - 20);

        pane.setViewportView(panel);

        this.getContentPane().add(pane);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub

    }

}
