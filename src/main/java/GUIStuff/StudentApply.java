package GUIStuff;

import VAC.MongoDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class StudentApply implements ActionListener {
    private static final MongoDB db = ScholarlyFrame.db;
    private static final JPanel descriptionPanel = new JPanel();


    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JLabel label = new JLabel(
            "Select the subjects from which tutors within your organization can help you with");
    JTextField textField = new JTextField();
    JButton buttonLol = new JButton("Send to Tutors");
    JTextArea textArea = new JTextArea(10, 20);
    JScrollPane scrollableText = new JScrollPane(textArea);

   

    public StudentApply() {

        label.setBounds(0, 0, 100, 50);
        label.setFont(new Font(null, Font.PLAIN, 14));

        //textArea.setPreferredSize();

        panel.setPreferredSize(new Dimension(250, 40));

        buttonLol.addActionListener(this);
        buttonLol.setPreferredSize(new Dimension(200, 40));


        scrollableText.add(panel);
        scrollableText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollableText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);


        frame.getContentPane().add(scrollableText);

        frame.setResizable(false);
        frame.add(label);
        frame.add(scrollableText);
        frame.add(buttonLol);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(700, 400));

        frame.setLayout(new FlowLayout());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonLol) {
            String tempVar = textArea.getText();
            if (tempVar.length() > 501) {
                JOptionPane.showMessageDialog(frame, "Limit to 500 charecters");
            }
            if (tempVar.length() < 2001) {
                db.studentSubjectRequest(WelcomeFrame.username.getText(), tempVar);
                frame.dispose();
            }
        }
    }

}
