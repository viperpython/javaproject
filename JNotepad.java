// importing necessary packages
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import java.util.List;
// import java.util.Random;
import java.util.ArrayList;
import java.io.*;

// defining the class JNotepad and implementing ActionListener interface
class JNotepad extends JFrame implements ActionListener {// declaring necessary components
public JTextArea textArea;
public JLabel wordCountLabel;
public JButton countButton, findButton, replaceButton, clearButton, openButton,saveButton,fontSize;
static JPanel panel = new JPanel();

// constructor to initialize GUI components
public JNotepad() {
    super("JNotepad"); // setting title of the frame
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // setting default close operation
    setPreferredSize(new Dimension(800, 500)); // setting preferred size of the frame

    // initializing text area, label and buttons
    textArea = new JTextArea(10, 30);
    textArea.setFont(new Font("Segoe UI", Font.PLAIN, 16) );
    wordCountLabel = new JLabel("Word count: 0");
    countButton = new JButton("Count");
    findButton = new JButton("Find");
    replaceButton = new JButton("Replace");
    clearButton =new JButton("clear");
    openButton = new JButton("Open");
    saveButton =new JButton("Save");
    fontSize =new JButton("Font Size");

    // adding buttons and label to the panel
    panel.add(countButton);
    panel.add(findButton);
    panel.add(replaceButton);
    panel.add(clearButton);
    panel.add(openButton);
    panel.add(saveButton);
    panel.add(fontSize);
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
    fontSize.addActionListener(this);

    // packing the frame and making it visible
    pack();
    setVisible(true);
}

// main method to execute the program
public static void main(String[] args) {
    new JNotepad();
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
                    try{
                        // String video[] = {"test1.mp4","test2.mp4","test3.mp4","test4.mp4"};
                        // ProcessBuilder pb = new ProcessBuilder("C:\\Program Files\\VideoLAN\\VLC\\vlc.exe", video[new Random().nextInt(video.length)]);
                        ProcessBuilder pb = new ProcessBuilder("C:\\Program Files\\VideoLAN\\VLC\\vlc.exe", "zz\\test3.mp4");
                        pb.start();
                    }
                    catch(IOException a){
                        a.getStackTrace();
                    }
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
        else if (e.getSource()==saveButton){//detect save button click
            Object[] options = {"Existing", "New(choose folder)"};//array for display options to select existing file or new file
                            Icon icon = UIManager.getIcon("OptionPane.questionIcon");//get question icon
                            
                            // Display the prompt and get the user's choice
                            int choice = JOptionPane.showOptionDialog(null, "Choose any one of the following", "Choose", 
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
                            
                            // Handle the user's choice
                            if (choice == JOptionPane.YES_OPTION) {//existing file
                                
                                JFileChooser filechoose = new JFileChooser();//create a filechooser object
                                filechoose.setCurrentDirectory(new File(System.getProperty("user.home")));//set default directory to user home
                                int select =filechoose.showSaveDialog(this);//open show dialog and store result in select variable
                                if(select==JFileChooser.APPROVE_OPTION){//if file is selected
                                    File fileTosave = filechoose.getSelectedFile();//file object to save savefile
                                    try {
                                        FileOutputStream out=new FileOutputStream(fileTosave);//new outputstream for writing to filetosave variable 
                                        String saving=textArea.getText();//get textArea text
                                        byte[] strToBytes = saving.getBytes();//new byte array and store all the bytes in saving string
                                        out.write(strToBytes);//writing array to file
                                        out.close();//close stream
                                        
                                    } catch (Exception x) {//catch any error
                                        System.out.println("error writing to file");
                                        // TODO: handle exception
                                    }
                                }

                                
        
                            } 
                            else if (choice == JOptionPane.NO_OPTION) {//new option is choosen
                                // JOptionPane.showMessageDialog(this,"choose in which folder file should be created.");
                                JFileChooser filechoose = new JFileChooser();//create a filechoose object
                                filechoose.setDialogTitle("choose in which folder file should be created.");//set chooser title
                                filechoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//set property such that only folder can be selected
                                filechoose.setCurrentDirectory(new File(System.getProperty("user.home")));//set default opening directory
                                int select =filechoose.showSaveDialog(this);//open file chooser and store result in select
                                if(select==JFileChooser.APPROVE_OPTION){//check if file is selected
                                    File filetocreate = filechoose.getSelectedFile();//create a file object and assign selected folder
                                    if(filetocreate.isFile()){//check is a folder or not
                                        JOptionPane.showMessageDialog(this,"please choose a folder !!!");
                                    }
                                    else{
                                        String filename =JOptionPane.showInputDialog(this,"file name");//input new file name
                                        // System.out.println(filetocreate.getAbsolutePath());
                                        filename=filetocreate.getAbsolutePath()+"\\"+filename;//concat new file name to file object
                                        // System.out.println(filename);
                                        File crfile =new File(filename);//create a file object based on new filename
                                        if(crfile.exists()){//if file already exists
                                            JOptionPane.showMessageDialog(this,"file already exist !!! cannot create a new file");//show error message
                                        }
                                        else{//if not present
                                        try{
                                        
                                        crfile.createNewFile();//create new file from file object
                                        FileOutputStream out=new FileOutputStream(crfile);//create a file output stream
                                        String saving=textArea.getText();//get text from textarea
                                        byte[] strToBytes = saving.getBytes();//byte array to store bystream of saving string 
                                        out.write(strToBytes);//writing to file
                                        out.close();//close file stream
                                        JOptionPane.showInternalMessageDialog(this, "file succesfully saved");//show file saved

                                        }
                                    
                                        catch(Exception i){
                                            JOptionPane.showMessageDialog(this,"file cannot be created");//catch any error
                                            System.out.println(i.getStackTrace());//get error stack trace
                                        }
                                    }

                                    }
                            }
            
        }}
        else if(e.getSource()==fontSize){//detect font size button press
            try{
            int FontSizeInput=Integer.parseInt(JOptionPane.showInputDialog(this, "Enter new font size"));//imput font size
            textArea.setFont(new Font("Segoe UI",Font.PLAIN,FontSizeInput));
            }
            catch(Exception i){
                JOptionPane.showMessageDialog(this,"please enter a number");//handle exception if entered number is not a number
                System.out.println(i.getMessage());
            }
            
        }
    }

    public int countWords(String text) {//method to count words in text
        text = text.trim();
        if (text.isEmpty()) {
            return 0;
        }
        return text.split("\\s+").length;
    }
}
