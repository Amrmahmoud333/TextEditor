import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

public class TextEditor extends JFrame implements ActionListener{

 JTextArea textArea; 
 JScrollPane scrollPane;
 JLabel fontLabel;
 JSpinner fontSizeSpinner;
 JButton fontColorButton;
 JComboBox fontBox;
 
 JMenuBar menuBar;
 JMenu fileMenu;
 JMenuItem openItem;
 JMenuItem saveItem;
 JMenuItem exitItem;

 TextEditor(){
	 
  this.setTitle("Text Editor"); 
  this.setSize(500, 560); // to set the size of window
  this.setLayout(new FlowLayout()); // to make appBar 
  this.setLocationRelativeTo(null); // to make window in the middle of screen 
  
  textArea = new JTextArea(); 
  textArea.setLineWrap(true); // to make a new line when write many words
  textArea.setWrapStyleWord(true); // 
  textArea.setFont(new Font("Arial",Font.PLAIN,20)); // the default font 
  
  scrollPane = new JScrollPane(textArea);
  scrollPane.setPreferredSize(new Dimension(450,450));
  // make a vertical scrollbar 
  scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
  
  fontLabel = new JLabel("Font: "); // make a lable to fonts 
  // make spinner to chooses font 
  fontSizeSpinner = new JSpinner(); 
  fontSizeSpinner.setPreferredSize(new Dimension(50,25));
  fontSizeSpinner.setValue(20); // default value
  
  fontSizeSpinner.addChangeListener(new ChangeListener() {
	  
   @Override
   public void stateChanged(ChangeEvent e) {
    
    textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int) fontSizeSpinner.getValue())); 
   }
   
  });
  
  fontColorButton = new JButton("Color");
  fontColorButton.addActionListener(this); // because we implemented ActionListener we will use actionPerformed
  
  // make array of font and add it to JComboBox
  String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
  fontBox = new JComboBox(fonts); 
  fontBox.addActionListener(this);
  fontBox.setSelectedItem("Arial"); // default font  
  
 
  
   menuBar = new JMenuBar();
   fileMenu = new JMenu("File");
   openItem = new JMenuItem("Open");
   saveItem = new JMenuItem("Save");
   exitItem = new JMenuItem("Exit");
   
   openItem.addActionListener(this);
   saveItem.addActionListener(this);
   exitItem.addActionListener(this);
   
   fileMenu.add(openItem);
   fileMenu.add(saveItem);
   fileMenu.add(exitItem);
   menuBar.add(fileMenu);
  

   
  this.setJMenuBar(menuBar);
  this.add(fontLabel);
  this.add(fontSizeSpinner);
  this.add(fontColorButton);
  this.add(fontBox);
  this.add(scrollPane);
  this.setVisible(true);
 }
 
 @Override
 public void actionPerformed(ActionEvent e) {
  
  if(e.getSource() == fontColorButton) {
   JColorChooser colorChooser = new JColorChooser();
   
   Color color = colorChooser.showDialog(null, "Choose a color", Color.black);
   
   textArea.setForeground(color);
  }
  
  if(e.getSource()==fontBox) {
   textArea.setFont(new Font( (String) fontBox.getSelectedItem(), Font.PLAIN ,textArea.getFont().getSize() /* to keep the font size */));
  }
  
  if(e.getSource()==openItem) {
   JFileChooser fileChooser = new JFileChooser();
   fileChooser.setCurrentDirectory(new File("."));
   
   FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
   fileChooser.setFileFilter(filter);
   
   int check = fileChooser.showOpenDialog(null);
   
   if(check == JFileChooser.APPROVE_OPTION) {
    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
    Scanner fileIn = null;
    
    try {
     fileIn = new Scanner(file);
     if(file.isFile()) {
      while(fileIn.hasNextLine()) {
       String line = fileIn.nextLine()+"\n";
       textArea.append(line);
      }
     }
    } catch (FileNotFoundException e1) {
     // TODO Auto-generated catch block
     e1.printStackTrace();
    }
    finally {
     fileIn.close();
    }
   }
  }
  
  if(e.getSource()==saveItem) {
   JFileChooser fileChooser = new JFileChooser(); // to choose the path 
   fileChooser.setCurrentDirectory(new File(".")); // default path in the project file 
   
   int check = fileChooser.showSaveDialog(null);
   
   if(check == JFileChooser.APPROVE_OPTION) {
    File file;
    PrintWriter fileOut = null;
    
    file = new File(fileChooser.getSelectedFile().getAbsolutePath());
    try {
     fileOut = new PrintWriter(file);
     fileOut.println(textArea.getText());
    } 
    catch (FileNotFoundException e1) {
     // TODO Auto-generated catch block
     e1.printStackTrace();
    }
    finally {
     fileOut.close();
    }   
   }
  }
  if(e.getSource()==exitItem) {
   System.exit(0);
  }  
 }
}