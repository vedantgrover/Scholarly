package GUIStuff;

import VAC.MongoDB;
import com.mongodb.client.FindIterable;
import org.bson.Document;

import javax.print.Doc;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

public class StudentRequests extends JFrame implements ActionListener {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 400;

    private static MongoDB db = ScholarlyFrame.db;

    private static JPanel descriptionPanel = new JPanel();

    private static ArrayList<JButton> studentRButtons = new ArrayList<JButton>();
    private static ArrayList<Document> studentRData = new ArrayList<Document>();

    protected static JButton approveButton, declineButton, currentButton;
    private JTextArea studentRText = new JTextArea();

    private JPanel panel;
    private JFrame myFrame;
    private JScrollPane pane;

    private FindIterable<Document> docs;

    private String currentUser = "";

    public StudentRequests() {
        approveButton = new JButton("Approve");
        declineButton = new JButton("Decline");

        myFrame = this;
        this.setTitle("Student Requests");
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setIconImage(ScholarlyFrame.image);
        this.setFocusable(true);
        this.setLayout(null);

        approveButton.setBounds(WIDTH / 3 + 226, 310, 226, 50);
        declineButton.setBounds(WIDTH / 3, 310, 226, 50);

        approveButton.addActionListener(e -> approve());

        declineButton.addActionListener(e -> decline());

        this.getContentPane().add(approveButton);
        this.getContentPane().add(declineButton);

        docs = db.getStudentRequests(db.findUser(WelcomeFrame.username.getText()).getString("organization"));

        panel = new JPanel();
        panel.setLayout(new GridLayout(200, 1));

        pane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        listStudents(docs);

        approveButton.setEnabled(false);
        declineButton.setEnabled(false);

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

        studentRText.setText(ScholarlyFrame.studentInfo(db.findUser(currentUser)));
        studentRText.setBounds(10, 10, 434, 300);
        studentRText.setEditable(false);
        studentRText.setOpaque(false);
        studentRText.setLineWrap(true);
        studentRText.setWrapStyleWord(true);

        descriptionPanel.add(studentRText);
        descriptionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        descriptionPanel.revalidate();
        descriptionPanel.repaint();
    }

    private void approve() {
        Document doc = db.findUser(currentUser);
        db.tutorStudentConnect(doc.getString("username"), true, doc.getString("description"));
        panel.remove(currentButton);
        panel.revalidate();
        panel.repaint();

        descriptionPanel.removeAll();
        descriptionPanel.revalidate();
        descriptionPanel.repaint();

        approveButton.setEnabled(false);
        declineButton.setEnabled(false);

        Document tutorDocument = db.findUser(WelcomeFrame.username.getText());
        String emailMessage = "Hello " + doc.getString("name") + ",\n\nYou have been approved by " + tutorDocument.getString("name") + "! Here is their contact info: \n\nEmail: " + tutorDocument.getString("email") + "\nPhone Number: " + tutorDocument.getString("number") + "\n\nBest Regards,\nScholarly";
        ScholarlyFrame.eh.sendEmail(doc.getString("email"), "You now have a tutor!", emailMessage);

        JOptionPane.showMessageDialog(myFrame, "Approved!");
    }

    private void decline() {
        Document doc = db.findUser(currentUser);
        db.tutorStudentConnect(doc.getString("username"), false, doc.getString("description"));
        panel.remove(currentButton);
        panel.revalidate();
        panel.repaint();

        descriptionPanel.removeAll();
        descriptionPanel.revalidate();
        descriptionPanel.repaint();

        approveButton.setEnabled(false);
        declineButton.setEnabled(false);

        ScholarlyFrame.eh.sendEmail(doc.getString("email"), "We Apologize", "Hello " + doc.getString("name") + ",\n\nWe regret to inform you that your application for a tutor has been denied.\n\nBest Regards,\nScholarly");
        JOptionPane.showMessageDialog(myFrame, "Declined!");
    }

    private void listStudents(FindIterable<Document> studentDocs) {
        for (Document studentDoc : studentDocs) {
            JButton studentButton = new JButton(studentDoc.getString("name"));
            studentButton.setBounds(0, 0, pane.getWidth(), 100);
            studentButton.addActionListener(e -> {
                currentUser = studentDoc.getString("username");
                currentButton = studentButton;

                System.out.println(currentUser);

                updateDescription();

                approveButton.setEnabled(true);
                declineButton.setEnabled(true);
            });
            panel.add(studentButton);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}
