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

public class AdminApply extends JFrame implements ActionListener {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 400;

    private static MongoDB db = ScholarlyFrame.db;

    private static JPanel descriptionPanel = new JPanel();

    private static ArrayList<JButton> tutorRButtons = new ArrayList<JButton>();
    private static ArrayList<Document> tutorRData = new ArrayList<Document>();

    protected static JButton approveButton, declineButton, currentButton;
    private JTextArea tutorRText = new JTextArea();

    private JPanel panel;
    private JFrame myFrame;
    private JScrollPane pane;

    private FindIterable<Document> docs;

    private String currentUser = "";

    public AdminApply() {
        approveButton = new JButton("Approve");
        //declineButton = new JButton("Decline");

        myFrame = this;
        this.setTitle("Approve new Admins");
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setIconImage(ScholarlyFrame.image);
        this.setFocusable(true);
        this.setLayout(null);

        approveButton.setBounds(WIDTH / 3 + 226, 310, 452, 50);
        //declineButton.setBounds(WIDTH / 3, 310, 226, 50);

        approveButton.addActionListener(e -> approve());
        //declineButton.addActionListener(e -> decline());

        this.getContentPane().add(approveButton);
        this.getContentPane().add(declineButton);

        docs = db.getTutorRequests(db.findUser(WelcomeFrame.username.getText()).getString("organization"));

        panel = new JPanel();
        panel.setLayout(new GridLayout(200, 1));

        pane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        listAdmins(docs);

        panel.setPreferredSize(new Dimension(WIDTH / 3, 12000));

        pane.setBounds(0, 0, WIDTH / 3, HEIGHT - 20);

        pane.setViewportView(panel);

        this.getContentPane().add(descriptionPanel);
        this.getContentPane().add(pane);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void updateDescription() {
        descriptionPanel.removeAll();

        descriptionPanel.setLayout(null);
        descriptionPanel.setBounds(WIDTH / 3, 0, 453, HEIGHT - 90);

        tutorRText.setText(ScholarlyFrame.studentInfo(db.findUser(currentUser)));
        tutorRText.setBounds(10, 10, 434, 300);
        tutorRText.setEditable(false);
        tutorRText.setOpaque(false);
        tutorRText.setLineWrap(true);
        tutorRText.setWrapStyleWord(true);

        descriptionPanel.add(tutorRText);
        descriptionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        descriptionPanel.revalidate();
        descriptionPanel.repaint();
    }

    private void approve() {
        Document doc = db.findUser(currentUser);
        //New method here
        db.recruit(doc.getString("username"), true);
        panel.remove(currentButton);
        panel.revalidate();
        panel.repaint();

        descriptionPanel.removeAll();
        descriptionPanel.revalidate();
        descriptionPanel.repaint();

        approveButton.setEnabled(false);
        declineButton.setEnabled(false);

        // here to
        db.recruit(currentUser, true);
        ScholarlyFrame.createNewTutorButton(db.findUser(currentUser));
        ScholarlyFrame.panel.revalidate();
        ScholarlyFrame.panel.repaint();

        Document adminDocument = db.findUser(WelcomeFrame.username.getText());
        String emailMessage = "Hello " + doc.getString("name") + ",\n\nYou have been approved by " + adminDocument.getString("name") + "! Here is their contact info: \n\nEmail: " + adminDocument.getString("email") + "\nPhone Number: " + adminDocument.getString("number") + "\n\nWelcome to being a tutor here on Scholarly. There will be a button on the bottom of your application where you can review different requests made by students!\n\nBest Regards,\nScholarly";
        ScholarlyFrame.eh.sendEmail(doc.getString("email"), "Congratulations!", emailMessage);

        JOptionPane.showMessageDialog(myFrame, "Approved!");
    }


    private void listAdmins(FindIterable<Document> tutorDocs) {
        for (Document tutorDoc : tutorDocs) {
            JButton studentButton = new JButton(tutorDoc.getString("name"));
            studentButton.setBounds(0, 0, pane.getWidth(), 100);
            studentButton.addActionListener(e -> {
                currentUser = tutorDoc.getString("username");
                currentButton = studentButton;

                System.out.println(currentUser);

                updateDescription();

                approveButton.setEnabled(true);
                
            });
            panel.add(studentButton);
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub

    }

}
