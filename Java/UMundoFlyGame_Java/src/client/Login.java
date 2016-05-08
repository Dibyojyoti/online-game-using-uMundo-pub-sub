package client;

/* ----Login Page--- */ 
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
 
public class Login  {
    
   private JFrame frame;
   private JLabel header;
   private JPanel control;
   private JTextField userID;
   
   public Login(){
	   createGUI();
   }  
   
   /*Creates the GUI for login screen */
   private void createGUI() {

	   frame = new JFrame("Login to Play");
	   frame.setSize(500,300);
	   frame.setLayout(new GridLayout(3, 1));
	   frame.setLocationRelativeTo(null);
	   frame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
       });    
       header  = new JLabel("", JLabel.CENTER);        
       control = new JPanel();
       control.setLayout(new FlowLayout());
	   frame.add(header);
       frame.add(control);   
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setVisible(true);  
       
       header.setText("Please provide a Name to continue"); 	
       JLabel  userName= new JLabel("Name: ", JLabel.CENTER);
       userID = new JTextField(6);  
       JButton loginButton = new JButton("Start");
       loginButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              frame.dispose();
              new FlyPlayerClient(userID.getText()).go();
         }
       }); 

       control.add(userName);
       control.add(userID);
       userID.setFocusable(true);
       userID.requestFocusInWindow();
       control.add(loginButton);
       frame.setFocusable(true);
       frame.validate();
       frame.requestFocusInWindow();
   }
   public static void main(String[] args) {
		Login login = new Login();
	}
}