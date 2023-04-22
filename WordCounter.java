// importing necessary packages
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

// defining the class WordCounter and implementing ActionListener interface
class WordCounter extends JFrame implements ActionListener {// declaring necessary components
public JTextArea textArea;
public JLabel wordCountLabel;
public JButton countButton, findButton, replaceButton, clearButton, openButton,saveButton;
static JPanel panel = new JPanel();

// constructor to initialize GUI components
public WordCounter() {
    super("Word Counter"); // setting title of the frame
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // setting default close operation
    setPreferredSize(new Dimension(800, 500)); // setting preferred size of the frame

    // initializing text area, label and buttons
    textArea = new JTextArea(10, 30);
    wordCountLabel = new JLabel("Word count: 0");
    countButton = new JButton("Count");
    findButton = new JButton("Find");
    replaceButton = new JButton("Replace");
    clearButton =new JButton("clear");
    openButton = new JButton("Open");
    saveButton =new JButton("Save");

    // adding buttons and label to the panel
    panel.add(countButton);
    panel.add(findButton);
    panel.add(replaceButton);
    panel.add(clearButton);
    panel.add(openButton);
    panel.add(saveButton);
    panel.add(wordCountLabel);
    
    // adding panel and text area to the frame
    add(panel, BorderLayout.SOUTH);
    add(new JScrollPane(textArea), BorderLayout.CENTER);

    // adding action listeners to buttons
    countButton.addActionListener(this);
    findButton.addActionListener(this);
    replaceButton.addActionListener(this);
    clearButton.addActionListener(this);
    openButton.addActionListener(this);
    saveButton.addActionListener(this);

    // packing the frame and making it visible
    pack();
    setVisible(true);
}

// main method to execute the program
public static void main(String[] args) {
    new WordCounter();
}

// method to find all indexes of a substring in a string
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

// method to perform action on button clicks
public void actionPerformed(ActionEvent e) {
    Highlighter highlighter = textArea.getHighlighter(); // initializing highlighter object
    if (e.getSource() == countButton) { // when count button is clicked
        String text = textArea.getText(); // get the text from text area
        int wordCount = countWords(text); // count the words in the text
        wordCountLabel.setText("Word count: " + wordCount); // set the label text to the word count
    } else if (e.getSource() == findButton) { // when find button is clicked
        String searchText = JOptionPane.showInputDialog(this, "Find:"); // show input dialog to get search text
        if (searchText != null) {
            String text = textArea.getText(); // get the text from text area
            int index = text.indexOf(searchText); // find the first occurrence of the search text
            if (index != -1) { // if search text is found
                HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.LIGHT_GRAY); // create a highlight painter object
                for (int y : findAllIndexes(text, searchText)){//  finding all the indexes of matching words //
                        int p0 = y;
                        int p1 = p0 + searchText.length();
                    try {
                        highlighter.addHighlight(p0, p1, painter );//hilighting every index
                    } catch (Exception x) {
                        System.out.println("invalid index");
                    }
                    }
                    
                    JOptionPane.showMessageDialog(this,"found this many instances of word\n"+searchText+" : "+findAllIndexes(text, searchText).size());//message box for showing no of words found
                    // textArea.select(index, index + searchText.length());
                    
                } else {
                    JOptionPane.showMessageDialog(this, "Text not found.");//show this if no matching text was found 
                }
            }
        } else if (e.getSource() == replaceButton) {//when replace button is clicked
            String searchText = JOptionPane.showInputDialog(this, "Find:");//creating a input dialog box
            if (searchText != null) {
                String replaceText = JOptionPane.showInputDialog(this, "Replace with:");//imput dialog box for replacing text to something given
                if (replaceText != null) {
                    String text = textArea.getText();//fetching text from text area
                    text = text.replaceAll(searchText, replaceText);//replacing text 
                    textArea.setText(text);//setting modified text into text area
                }
            }
        }
        else if(e.getSource()==clearButton){//when clear button is pressed
            // highlighter.removeAllHighlights();
            textArea.setText("");//clearing the canvas
            

        }
        else if(e.getSource()==openButton){//when open is pressed
            JFileChooser fileChooser = new JFileChooser();//creating a filechooser object
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));//setting default directory 
            int result = fileChooser.showOpenDialog(this);//opening file chooser and storing the name and status of selection in result
            if (result == JFileChooser.APPROVE_OPTION) {//checking if file is selected
                File selectedFile = fileChooser.getSelectedFile();//fetching file location
                JOptionPane.showMessageDialog(this,"file selected "+selectedFile.getAbsolutePath());//show a message dialog with the selected file's absolute path
                try {
                    byte[] buffer = new byte[1024]; // create a byte array to hold the file data
                    StringBuilder sb = new StringBuilder(); // create a string builder to hold the text data
                    FileInputStream in = new FileInputStream(selectedFile); // create a file input stream to read the file
                    int len; // create an integer to hold the length of the data read
                    while((len =in.read(buffer))!=-1){ // read data from the input stream into the buffer
                        sb.append(new String(buffer, 0, len)); // convert the byte data to a string and append it to the string builder
                    }
                    textArea.setText(sb.toString()); // set the text area's text to the string builder's contents
                    in.close(); // close the input stream

                    // System.out.println("done");
                } catch (Exception f) {
                    JOptionPane.showMessageDialog(this,"file not found");//is file is not found
                }
                
                
                
            }
        }
        else if (e.getSource()==saveButton){
            Object[] options = {"Existing", "New"};
                            Icon icon = UIManager.getIcon("OptionPane.questionIcon");
                            
                            // Display the prompt and get the user's choice
                            int choice = JOptionPane.showOptionDialog(null, "Choose any one of the following", "Choose", 
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
                            
                            // Handle the user's choice
                            if (choice == JOptionPane.YES_OPTION) {
                                
                                JFileChooser filechoose = new JFileChooser();
                                filechoose.setCurrentDirectory(new File(System.getProperty("user.home")));
                                int select =filechoose.showSaveDialog(this);
                                if(select==JFileChooser.APPROVE_OPTION){
                                    File fileTosave = filechoose.getSelectedFile();
                                    try {
                                        FileOutputStream out=new FileOutputStream(fileTosave);
                                        String saving=textArea.getText();
                                        byte[] strToBytes = saving.getBytes();
                                        out.write(strToBytes);
                                        out.close();
                                        
                                    } catch (Exception x) {
                                        // TODO: handle exception
                                    }
                                }

                                
        
                            } 
                            else if (choice == JOptionPane.NO_OPTION) {
                                JOptionPane.showMessageDialog(this,"choose in which folder file should be created.");
                                JFileChooser filechoose = new JFileChooser();
                                filechoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                                filechoose.setCurrentDirectory(new File(System.getProperty("user.home")));
                                int select =filechoose.showSaveDialog(this);
                                if(select==JFileChooser.APPROVE_OPTION){
                                    File filetocreate = filechoose.getSelectedFile();
                                    if(filetocreate.isFile()){
                                        JOptionPane.showMessageDialog(this,"please choose a folder !!!");
                                    }
                                    else{
                                        String filename =JOptionPane.showInputDialog(this,"file name");
                                        // System.out.println(filetocreate.getAbsolutePath());
                                        filename=filetocreate.getAbsolutePath()+"\\"+filename;
                                        // System.out.println(filename);
                                        File crfile =new File(filename);
                                        if(crfile.exists()){
                                            JOptionPane.showMessageDialog(this,"file already exist !!! cannot create a new file");
                                        }
                                        else{
                                        try{
                                        
                                        crfile.createNewFile();
                                        FileOutputStream out=new FileOutputStream(crfile);
                                        String saving=textArea.getText();
                                        byte[] strToBytes = saving.getBytes();
                                        out.write(strToBytes);
                                        out.close();
                                        JOptionPane.showInternalMessageDialog(this, "file succesfully saved");

                                        }
                                    
                                        catch(Exception i){
                                            JOptionPane.showMessageDialog(this,"file cannot be created");
                                            System.out.println("file cannot be created");
                                        }
                                    }

                                    }
                            }
            
        }}
    }

    public int countWords(String text) {
        text = text.trim();
        if (text.isEmpty()) {
            return 0;
        }
        return text.split("\\s+").length;
    }
}
