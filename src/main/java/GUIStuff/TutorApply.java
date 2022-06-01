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
import javax.swing.JTextField;

public class TutorApply implements ActionListener {

    JFrame frame = new JFrame();
    JLabel label = new JLabel("Type your reasons and an Administrator at your organization can latter connect and approve your approval. ");
    JTextField textField = new JTextField();
    JButton button = new JButton();

    ArrayList<String> userInputs = new ArrayList<>();


    TutorApply() {

        label.setBounds(0,0,100,50);
        label.setFont(new Font(null, Font.PLAIN,20));

        textField.setPreferredSize(new Dimension(250,40));

        button.addActionListener(this);

        frame.setResizable(false);
        frame.add(label);
        frame.add(textField);
        frame.add(button);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000,600));

        frame.setLayout(new FlowLayout());
        frame.pack();
        frame.setVisible(true);

    }

    


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==button) {
            //Github stuff -> Will work later tonight 
            }
        }
        
    }
    

