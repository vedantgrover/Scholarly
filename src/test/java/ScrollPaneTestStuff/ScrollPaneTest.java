package ScrollPaneTestStuff;

import javax.swing.*;
import java.awt.*;

public class ScrollPaneTest {

    private static void createAndShowGUI() {

        // Create and set up the window.  
        final JFrame frame = new JFrame("Scroll Pane Example");

        // Display the window.  
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set flow layout for the frame  
        frame.getContentPane().setLayout(new FlowLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

        JScrollPane scrollableTextArea = new JScrollPane(buttonPanel);

        for (int i = 0; i < 50; i++) {
            addAButton("Test", buttonPanel);
        }

        buttonPanel.setPreferredSize(new Dimension(buttonPanel.getWidth(), 1500));
        scrollableTextArea.setPreferredSize(new Dimension(500, 500));

        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollableTextArea.setViewportView(buttonPanel);

        frame.getContentPane().add(scrollableTextArea);
        frame.pack();
    }

    public static void addAButton(String text, Container container) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(container.getWidth(), 10));
        container.add(button);
    }

    public static void main(String[] args) {


        javax.swing.SwingUtilities.invokeLater(ScrollPaneTest::createAndShowGUI);
    }
}  