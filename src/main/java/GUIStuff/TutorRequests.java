package GUIStuff;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.mongodb.MongoException;
import com.mongodb.bulk.UpdateRequest;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;
import org.bson.conversions.Bson;

import VAC.MongoDB;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TutorRequests extends JFrame implements ActionListener {

    private static MongoDB db = new MongoDB();

    private static ArrayList<JButton> tutorRButtons = new ArrayList<JButton>();
    private static ArrayList<Document> tutorRData = new ArrayList<Document>();

    private static JButton approveButton, declineButton;

    public TutorRequests() {
        approveButton = new JButton("Approve");
        declineButton = new JButton("Decline");

        JFrame myFrame = this;
        this.setTitle("Tutor Requests");
        this.setIconImage(ScholarlyFrame.image);
        this.setLayout(null);
        this.setPreferredSize(new Dimension(500, 300));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        List<Document> tutorR = db.findUser(WelcomeFrame.username.getText()).getList("TutorRequests", Document.class);
        JPanel tutorRPanel = new JPanel();
        tutorRPanel.setLayout(new GridLayout(10, 1));

        JScrollPane tutorRScroll = new JScrollPane(tutorRPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        for (Document doc : tutorR) {
            JButton tutorRButton = new JButton(doc.getString("name"));
            tutorRButton.setPreferredSize(new Dimension(tutorRScroll.getWidth(), 100));
            tutorRButton.addActionListener(e -> {

                JPanel tutorRDescription = new JPanel();
                tutorRDescription.setLayout(null);
                tutorRDescription.setBounds(myFrame.getWidth() * 1/3, 0, myFrame.getWidth() * 2/3, myFrame.getHeight());

                JLabel description = new JLabel(doc.getString("description"));
                description.setBounds(myFrame.getWidth() * 1/3, 0, myFrame.getWidth() * 2/3, myFrame.getHeight());
                tutorRDescription.add(description);

                approveButton.setBounds((tutorRDescription.getWidth() * 1/3) - approveButton.getWidth()/2, 75, 100, 50);
                approveButton.addActionListener(event -> recruit(doc.getString("username"), true));

                declineButton.setBounds((tutorRDescription.getWidth() * 2/3) - declineButton.getWidth()/2, 75, 100, 50);
                declineButton.addActionListener(event -> recruit(doc.getString("username"), false));

                myFrame.getContentPane().add(tutorRDescription);
                revalidate();
                repaint();
            });
            tutorRPanel.add(tutorRButton);
            tutorRButtons.add(tutorRButton);
            tutorRData.add(doc);
        }

        tutorRPanel.setPreferredSize(new Dimension(tutorRScroll.getWidth(), 12000));

        tutorRScroll.setBounds(0, 0, this.getWidth() * 1/3, this.getHeight());
        tutorRScroll.setViewportView(tutorRPanel);

        this.getContentPane().add(tutorRScroll);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static boolean recruit(String username, boolean approved) {
        if (approved) {
            db.makeTutor(username);
            removeTutorRequest(username);
            return true;
        } else {
            removeTutorRequest(username);
            return false;
        }
    }

    public static void removeTutorRequest(String username) {
        Document query = new Document("TutorRequests", new Document("username", username));

        Bson updates = Updates.pull("TutorRequests", new Document("username", username));

        UpdateOptions options = new UpdateOptions().upsert(true);

        try {
            db.data.updateOne(query, updates, options);
        } catch (MongoException me) {
            System.err.println("Unable to update due to an error: " + me);
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    
}
