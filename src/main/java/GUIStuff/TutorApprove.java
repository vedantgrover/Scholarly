package GUIStuff;

import VAC.MongoDB;
import com.mongodb.client.FindIterable;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

public class TutorApprove extends JFrame implements ActionListener {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 400;

    private static MongoDB db = ScholarlyFrame.db;

    private static JPanel descriptionPanel = new JPanel();

    private static ArrayList<JButton> tutorRButtons = new ArrayList<JButton>();
    private static ArrayList<Document> tutorRData = new ArrayList<Document>();

    protected static JButton approveButton, declineButton;

    public TutorApprove() {
        approveButton = new JButton("Approve");
        declineButton = new JButton("Decline");

        JFrame myFrame = this;
        this.setTitle("Students that have requested help");
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setIconImage(ScholarlyFrame.image);
        this.setFocusable(true);
        this.setLayout(null);

        FindIterable<Document> docs = db
                .getStudentRequests(db.findUser(WelcomeFrame.username.getText()).getString("organization"));
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

                descriptionPanel.removeAll();

                descriptionPanel.setLayout(null);
                descriptionPanel.setBounds(WIDTH / 3, 0, 453, HEIGHT - 37);

                // JLabel tutorRLabel = new JLabel(doc.getString("description"));
                // tutorRLabel.setBounds(10, 10, 453, HEIGHT);

                JTextArea tutorRText = new JTextArea(ScholarlyFrame.studentInfo(doc));
                tutorRText.setBounds(10, 10, 434, 300);
                tutorRText.setEditable(false);
                tutorRText.setOpaque(false);
                tutorRText.setLineWrap(true);
                tutorRText.setWrapStyleWord(true);

                approveButton.setBounds(descriptionPanel.getWidth() / 2 - 5, 307, descriptionPanel.getWidth() / 2, 50);
                approveButton.addActionListener(event -> {
                    db.tutorStudentConnect(doc.getString("username"), true, doc.getString("description"));
                    //ScholarlyFrame.createNewTutorButton(doc);
                    //ScholarlyFrame.panel.revalidate();
                    //ScholarlyFrame.panel.repaint();
                    panel.remove(tutorRButton);
                    panel.revalidate();
                    panel.repaint();
                    descriptionPanel.removeAll();
                    descriptionPanel.revalidate();
                    descriptionPanel.repaint();

                    Document tutorDocument = db.findUser(WelcomeFrame.username.getText());
                    String emailMessage = "Hello " + doc.getString("name") + ",\n\nYou have been approved by " + tutorDocument.getString("name") + "! Here is there contact info: \n\nEmail: " + tutorDocument.getString("email") + "\nPhone Number: " + tutorDocument.getString("number");
                    ScholarlyFrame.eh.sendEmail(doc.getString("email"), "You now have a tutor!", emailMessage);

                    JOptionPane.showMessageDialog(myFrame, "Approved!");
                });
                descriptionPanel.add(approveButton);

                declineButton.setBounds(5, 307, descriptionPanel.getWidth() / 2, 50);
                declineButton.addActionListener(event -> {
                    //db.recruit(doc.getString("username"), true);
                    db.tutorStudentConnect(doc.getString("username"), false, doc.getString("description"));
                    panel.remove(tutorRButton);
                    panel.revalidate();
                    panel.repaint();
                    myFrame.remove(descriptionPanel);
                    myFrame.revalidate();
                    myFrame.repaint();

                    ScholarlyFrame.eh.sendEmail(doc.getString("email"), "We apologize", "Hello " + doc.getString("name") + ",\n\nWe regret to inform you that your application for a tutor has been denied.");
                    JOptionPane.showMessageDialog(myFrame, "Declined!");
                });
                descriptionPanel.add(declineButton);

                descriptionPanel.add(tutorRText);
                descriptionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
                myFrame.getContentPane().add(descriptionPanel);
                descriptionPanel.revalidate();
                descriptionPanel.repaint();
            });
            panel.add(tutorRButton);
            tutorRData.add(db.findUser(doc.getString("username")));
            tutorRButtons.add(tutorRButton);
        }

        panel.setPreferredSize(new Dimension(WIDTH / 3, 12000));

        pane.setBounds(0, 0, WIDTH / 3, HEIGHT - 20);

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
