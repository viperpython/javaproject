import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WordCounter extends JFrame implements ActionListener {
    private JTextArea textArea;
    private JLabel wordCountLabel;
    private JButton countButton, findButton, replaceButton;

    public WordCounter() {
        super("Word Counter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new JTextArea(10, 30);
        wordCountLabel = new JLabel("Word count: 0");
        countButton = new JButton("Count");
        findButton = new JButton("Find");
        replaceButton = new JButton("Replace");

        JPanel panel = new JPanel();
        panel.add(countButton);
        panel.add(findButton);
        panel.add(replaceButton);
        panel.add(wordCountLabel);

        add(panel, BorderLayout.SOUTH);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        countButton.addActionListener(this);
        findButton.addActionListener(this);
        replaceButton.addActionListener(this);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new WordCounter();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == countButton) {
            String text = textArea.getText();
            int wordCount = countWords(text);
            wordCountLabel.setText("Word count: " + wordCount);
        } else if (e.getSource() == findButton) {
            String searchText = JOptionPane.showInputDialog(this, "Find:");
            if (searchText != null) {
                String text = textArea.getText();
                int index = text.indexOf(searchText);
                if (index != -1) {
                    textArea.select(index, index + searchText.length());
                } else {
                    JOptionPane.showMessageDialog(this, "Text not found.");
                }
            }
        } else if (e.getSource() == replaceButton) {
            String searchText = JOptionPane.showInputDialog(this, "Find:");
            if (searchText != null) {
                String replaceText = JOptionPane.showInputDialog(this, "Replace with:");
                if (replaceText != null) {
                    String text = textArea.getText();
                    text = text.replaceAll(searchText, replaceText);
                    textArea.setText(text);
                }
            }
        }
    }

    private int countWords(String text) {
        text = text.trim();
        if (text.isEmpty()) {
            return 0;
        }
        return text.split("\\s+").length;
    }
}
