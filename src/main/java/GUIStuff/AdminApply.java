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
        this.setTitle("Create Admin");
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setIconImage(ScholarlyFrame.image);
        this.setFocusable(true);
        this.setLayout(null);

        approveButton.setBounds(WIDTH / 3, 0, (WIDTH * 2)/3 - 15, HEIGHT - 40);
        approveButton.setEnabled(false);
        //declineButton.setBounds(WIDTH / 3, 310, 226, 50);

        approveButton.addActionListener(e -> approve());
        //declineButton.addActionListener(e -> decline());

        this.getContentPane().add(approveButton);

        docs = db.getAllStudents(db.findUser(WelcomeFrame.username.getText()).getString("organization"));

        panel = new JPanel();
        panel.setLayout(new GridLayout(200, 1));

        pane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        listAdmins(docs);

        panel.setPreferredSize(new Dimension(WIDTH / 3, 12000));

        pane.setBounds(0, 0, WIDTH / 3, HEIGHT - 20);

        pane.setViewportView(panel);

        this.getContentPane().add(pane);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void approve() {
        Document doc = db.findUser(currentUser);
        //New method here
        db.convertToAdmin(currentUser);
        panel.remove(currentButton);
        panel.revalidate();
        panel.repaint();

        approveButton.setEnabled(false);

        ScholarlyFrame.removeTutorButton(db.findUser(currentUser));
        ScholarlyFrame.panel.revalidate();
        ScholarlyFrame.panel.repaint();

        Document adminDocument = db.findUser(WelcomeFrame.username.getText());
        String emailMessage = "Hello " + doc.getString("name") + ",\n\nCongratulations! " + adminDocument.getString("name") + " has made you an admin! Remember to treat everyone with kindness and make someone smile today! You are very epic!\n\nBest Regards,\nScholarly";
        ScholarlyFrame.eh.sendEmail(doc.getString("email"), "Congratulations!", emailMessage);

        JOptionPane.showMessageDialog(myFrame, "Approved!");
    }


    private void listAdmins(FindIterable<Document> orgStudents) {
        for (Document orgDoc : orgStudents) {
            JButton studentButton = new JButton(orgDoc.getString("name"));
            studentButton.setBounds(0, 0, pane.getWidth(), 100);
            studentButton.addActionListener(e -> {
                currentUser = orgDoc.getString("username");
                currentButton = studentButton;

                System.out.println(currentUser);

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
