package GUIStuff;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TutorApply implements ActionListener {

    JFrame frame = new JFrame();
    JLabel label = new JLabel(
            "Type your reasons and an Administrator at your organization can later connect and approve your approval. ");
    JTextField textField = new JTextField();
    JButton buttonLol = new JButton("Send to Administrator");
    JTextArea textArea = new JTextArea(10,20);
    JScrollPane scrollableText = new JScrollPane(textArea);

    ArrayList<String> userInputs = new ArrayList<>();

    public TutorApply() {

        label.setBounds(0, 0, 100, 50);
        label.setFont(new Font(null, Font.PLAIN, 20));

        textArea.setPreferredSize(new Dimension(250, 40));

        buttonLol.addActionListener(this);
        buttonLol.setPreferredSize(new Dimension(150,40));

        scrollableText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollableText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        

        

        frame.getContentPane().add(scrollableText);

        frame.setResizable(false);
        frame.add(label);
        frame.add(scrollableText);
        frame.add(buttonLol);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 600));

        frame.setLayout(new FlowLayout());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonLol) {
            
        }
    }

}
