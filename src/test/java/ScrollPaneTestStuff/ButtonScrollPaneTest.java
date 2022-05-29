package ScrollPaneTestStuff;

import javax.swing.*;
import java.awt.*;

public class ButtonScrollPaneTest extends JFrame {
    public String[] people = new String[300];
    public ButtonScrollPaneTest() {
        int rows = 200;
        for (int i = 0; i < people.length; i++) {
            people[i] = "Jeff";
        }

        this.setTitle("ScrollPane Test");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setResizable(false);
        this.setPreferredSize(new Dimension(1000, 600));
        this.getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(rows, 1));
        //panel.setMaximumSize(new Dimension(100, 100 * people.length));

        JScrollPane pane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        for (int i = 0; i < people.length; i++) {
            JButton button = new JButton(people[i]);
            //button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setPreferredSize(new Dimension(pane.getWidth(), 100));
            panel.add(button);
        }

        panel.setPreferredSize(new Dimension(333, 12000));

        pane.setBounds(0, 0, 333, 400);

        pane.setViewportView(panel);

        this.getContentPane().add(pane);
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                new ButtonScrollPaneTest();
            }
        });

    }
}
