import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
// import java.util.Collections.*;
import java.util.List;
import java.util.ArrayList;
import java.io.*;


public class WordCounter extends JFrame implements ActionListener {
    public JTextArea textArea;
    public JLabel wordCountLabel;
    public JButton countButton, findButton, replaceButton,clearButton,importButton;
    static JPanel panel = new JPanel();
    public WordCounter() {
        super("Word Counter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 500));

        textArea = new JTextArea(10, 30);
        wordCountLabel = new JLabel("Word count: 0");
        countButton = new JButton("Count");
        findButton = new JButton("Find");
        replaceButton = new JButton("Replace");
        clearButton =new JButton("clear");
        importButton = new JButton("import");

       
        panel.add(countButton);
        panel.add(findButton);
        panel.add(replaceButton);
        panel.add(clearButton);
        panel.add(importButton);
        panel.add(wordCountLabel);
        

        add(panel, BorderLayout.SOUTH);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        countButton.addActionListener(this);
        findButton.addActionListener(this);
        replaceButton.addActionListener(this);
        clearButton.addActionListener(this);
        importButton.addActionListener(this);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new WordCounter();
    }
    public static List<Integer> findAllIndexes(String str, String substr) {
        List<Integer> indexes = new ArrayList<Integer>();
        int index = 0;
        while (index != -1) {
            index = str.indexOf(substr, index);
            if (index != -1) {
                indexes.add(index);
                index += substr.length();
            }
        }
        return indexes;
    }
    public void actionPerformed(ActionEvent e) {
        Highlighter highlighter = textArea.getHighlighter();
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
                    
                    HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.LIGHT_GRAY);
                    for (int y : findAllIndexes(text, searchText)) {
                        int p0 = y;
                        int p1 = p0 + searchText.length();
                    try {
                        highlighter.addHighlight(p0, p1, painter );
                    } catch (Exception x) {
                        System.out.println("invalid index");
                    }
                    }
                    
                    JOptionPane.showMessageDialog(this,"found this many instances of word\n"+searchText+" : "+findAllIndexes(text, searchText).size());
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
        else if(e.getSource()==clearButton){
            // highlighter.removeAllHighlights();
            textArea.setText("");
            

        }
        else if(e.getSource()==importButton){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                JOptionPane.showMessageDialog(this,"file selected "+selectedFile.getAbsolutePath());
                try {
                    byte[] buffer = new byte[1024];
                    StringBuilder sb = new StringBuilder();
                    FileInputStream in =new FileInputStream(selectedFile);
                    int len;
                    while((len =in.read(buffer))!=-1){
                        sb.append(new String(buffer, 0, len));
                    }
                   textArea.setText(sb.toString());
                   in.close();
                    // System.out.println("done");
                } catch (Exception f) {
                    JOptionPane.showMessageDialog(this,"file not found");
                }
                
                
                
            }
        }
    }

    public int countWords(String text) {
        text = text.trim();
        if (text.isEmpty()) {
            return 0;
        }
        return text.split("\\s+").length;
    }
}
