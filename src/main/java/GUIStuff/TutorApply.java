package GUIStuff;

import VAC.MongoDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TutorApply implements ActionListener {
    private static final MongoDB db = ScholarlyFrame.db;

    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JLabel label = new JLabel(
            "Type your reasons and an Administrator at your organization can later connect and approve your approval.");
    JButton submitButton = new JButton("Send to Administrator");
    JTextArea textArea = new JTextArea(10, 20);
    JScrollPane scrollableText = new JScrollPane(textArea);

    public TutorApply() {

        label.setBounds(0, 0, 100, 50);
        label.setFont(new Font(null, Font.PLAIN, 14));

        panel.setPreferredSize(new Dimension(250, 40));

        submitButton.addActionListener(this);
        submitButton.setPreferredSize(new Dimension(200, 40));


        scrollableText.add(panel);
        scrollableText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollableText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        frame.getContentPane().add(scrollableText);

        frame.setResizable(false);
        frame.add(label);
        frame.add(scrollableText);
        frame.add(submitButton);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(700, 400));

        frame.setLayout(new FlowLayout());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String tempVar = textArea.getText();
            if (tempVar.length() > 2001) {
                JOptionPane.showMessageDialog(frame, "Limit to 2000 characters");
            }
            if (tempVar.length() < 2001) {
                db.makeTutorRequest(WelcomeFrame.username.getText(), tempVar);
                frame.dispose();
            }
        }
    }
}
