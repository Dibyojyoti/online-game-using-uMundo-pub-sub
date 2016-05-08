package client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.umundo.PlayerClass;
import org.umundo.protobuf.Amessage.AMessage;


//**************This is the Client program for player screen**********/  
public class FlyPlayerClient extends JPanel{

	//Dimension of window
	private  final int D_HEIGHT 	=  463;
    private  final int D_WIDTH 		=  584;
    private   String myName			= " ";
    private   JFrame frame;
    WindowListener Wadpt;
    //Depending on this Timer the coordinate of the object and and player info is updated periodically 
    private Timer timerClient;
    private Timer timerServerMsg;
    //players own mouse coordinates
    private int MyPointerX = 0;
    private int MyPointerY = 0;
    //getting message after connection is made to sever
    //labels to show player name and score and status
    private JLabel Player1Name;
    private JLabel Player1NameLabel;
    private JLabel Player1Score;
    private JLabel Player1ScoreLabel;
    
    private JLabel Player2Name;
    private JLabel Player2NameLabel;
    private JLabel Player2Score;
    private JLabel Player2ScoreLabel;

    private JLabel Player3Name;
    private JLabel Player3NameLabel;
    private JLabel Player3Score;
    private JLabel Player3ScoreLabel;
    
    //player scores
    private static int PlayerScore1 		= 0;
    private static int PlayerScore2 		= 0;
    private static int PlayerScore3 		= 0;
    //player pointer coordinates
    private static int Player1PointerX 		= 0;
    private static int Player1PointerY 		= 0;
    //player count
    private static int playerCounter 		= 0;
    //player login and start status
    //Fly component
    private ImageShowingComponent ImgSC;
    //other two players mouse pointer component
    private playerPointer otherPlayer1Pointer;
    private playerPointer otherPlayer2Pointer;
    //panels
    JPanel panellTop;
    JPanel panelCenter;
    JPanel panelBottom;
    JPanel panelPlayer1;
    JPanel panelPlayer2;
    JPanel panelPlayer3;
    PlayerClass player;
    
    
        
    public FlyPlayerClient(String userName) {
         
        //Panel for start and close button
    	panellTop = new JPanel();
    	JButton resetButton = new JButton("Close");
    	resetButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			try {
    				logOut(myName);						//on close logout
    			} catch (Exception e1) {
    				System.out.println("Exception while trying to logout by player: ");
    				e1.printStackTrace();
    			}
    			frame.dispose();									//close window
    		}
    	});

    	panellTop.add(resetButton);
        //Middle Panel for playing
        setMiddlePanel();
        //Panel for players info
        setBottomPanel();
        
        setLayout(new BorderLayout());
        add(panellTop, BorderLayout.PAGE_START);
        add(panelCenter,BorderLayout.CENTER);
        add(panelBottom,BorderLayout.PAGE_END);
        //set the player name from login screen  
  		myName = userName;
		//timer to send mouse coordinate after player presses start button to play
 	    timerClient 	= new Timer(50, new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            getMyPointer();														//get mouse coordinate
	            try {
	            	 
	            	//sendPlayerPointer(myName,MyPointerX,MyPointerY);		//send mouse coordinate
				} catch (Exception e1) {
					System.out.println("Exception while sending mouse coordinate: " + e1.getMessage());
					e1.printStackTrace();
				}
	        	repaint();
	        }
	    });
 	    //timer to retrieve player name, score and status 
 	    timerServerMsg 	= new Timer(50, new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	repaint();
	        }
	    });
 	    timerServerMsg.start();
	}
//******This function gets the info of remote Fly, player info by calling the methods of remote object******/
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        	 try{
        		 if(player.participants.size() == 0) {
        			 Player1Name.setText(myName);						//shows player name and score
        			 Player1Score.setText(Player1Score.getText());
        		 } else if(player.participants.size() == 1) {
    				 Player2Name.setText(player.participants.values().toArray()[0].toString());
    				 if(player.getPlayerMap().get(Player2Name.getText()) != null)
    					 Player2Score.setText(String.valueOf(((PlayerClass)player.getPlayerMap().get(Player2Name.getText())).getScore()));
    			 } else if(player.participants.size() == 2) {
    				 if(Player2Name.getText().equals(player.participants.values().toArray()[1].toString()))
    					 Player3Name.setText(player.participants.values().toArray()[0].toString());
    				 else 
    					 Player3Name.setText(player.participants.values().toArray()[1].toString());
    				 if(player.getPlayerMap().get(Player3Name.getText()) != null)
    					 Player3Score.setText(String.valueOf(((PlayerClass)player.getPlayerMap().get(Player3Name.getText())).getScore()));
    			 }
    			 timerClient.start();
       			 ImgSC.setBounds(Integer.valueOf(player.getCoordinateX()).intValue(), Integer.valueOf(player.getCoordinateY()).intValue(), 250, 250);	
//    			 otherPlayer1Pointer.setBounds(Integer.valueOf(player.getCoordinateX()).intValue(), Integer.valueOf(player.getCoordinateY()).intValue(), 250, 250);	
        			 
        	 }  catch (Exception e) {
     			System.out.println("Exception in paintComponent method: " + e.getMessage());
    			e.printStackTrace();
    		}
    }	
	//*******sets size of client window*****/
	public Dimension getPreferredSize() {									
		return new Dimension(D_WIDTH, D_HEIGHT);
    }
	//*******send own pointer coordinate after adjusting according to main play screen,middle panel and mouse Jcomponent width and height
	public void getMyPointer(){
		
 		Point p1 = frame.getLocation();
 		PointerInfo a = MouseInfo.getPointerInfo();
 		Point p = a.getLocation();
 		MyPointerX = (int) (p.getX() - p1.getX() - 20 );
 		MyPointerY = (int) (p.getY() - p1.getY() - 36 - 40 );
   
	}
	//*******This method shows the main frame and check the message after login******/
    public void go(){
                frame = new JFrame(myName);
                frame.add(this);
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setResizable(false);
           	 
                Wadpt = new WindowAdapter(){
      	 	   public void windowClosing(java.awt.event.WindowEvent e) {
      	           try {
      	        	 logOut(myName);
				   } catch (Exception e1) {
					   System.out.println("Exception while trying to logout by player: "+myName);
					  e1.printStackTrace();
				   }
      	           frame.dispose();
      	         }
      	       };
      	       frame.addWindowListener(Wadpt);

   			try {
   				//flyObj.sendStartCount(myName);			//send own start status
   			} catch (Exception e1) {
   				System.out.println("Exception while sending status of player: ");
   				e1.printStackTrace();
   			} 
   			player = new PlayerClass(myName,this);
   			timerClient.start();
      	       
	}
    //********This method sets middle panel in frame*********/
    private void setMiddlePanel(){
        panelCenter 		= new JPanel();
        ImgSC 				= new ImageShowingComponent();
        otherPlayer1Pointer = new playerPointer();
        otherPlayer2Pointer = new playerPointer();
        panelCenter.setPreferredSize(new Dimension(500,800));
        panelCenter.setBackground(Color.blue);
        panelCenter.setLayout(null);
        panelCenter.setVisible(true);
        ImgSC.setVisible(true);
        otherPlayer1Pointer.setVisible(true);
        otherPlayer2Pointer.setVisible(true);
        panelCenter.add(ImgSC);
        panelCenter.add(otherPlayer1Pointer);
        panelCenter.add(otherPlayer2Pointer);

    }
    //********This method sets bottom panel in frame*********/
    private void setBottomPanel(){

    	Player1NameLabel 		= new JLabel("Player1: ");
    	Player1Name 			= new JLabel("No Player");
        Player1ScoreLabel 		= new JLabel("Score: ");
        Player1Score 			= new JLabel("0");
        
        Player2NameLabel 		= new JLabel("Player2: ");
    	Player2Name 			= new JLabel("No Player");
        Player2ScoreLabel 		= new JLabel("Score: ");
        Player2Score 			= new JLabel("0");
    	
        Player3NameLabel 		= new JLabel("Player3: ");
    	Player3Name 			= new JLabel("No Player");
        Player3ScoreLabel 		= new JLabel("Score: ");
        Player3Score 			= new JLabel("0");
        
        
        panelPlayer1 = new JPanel();
        panelPlayer1.setLayout(new GridLayout(1,4));
        panelPlayer1.add(Player1NameLabel);
        panelPlayer1.add(Player1Name);
        panelPlayer1.add(Player1ScoreLabel);
        panelPlayer1.add(Player1Score);

        panelPlayer2 = new JPanel();
        panelPlayer2.setLayout(new GridLayout(1,8));
        panelPlayer2.add(Player2NameLabel);
        panelPlayer2.add(Player2Name);
        panelPlayer2.add(Player2ScoreLabel);
        panelPlayer2.add(Player2Score);

        panelPlayer3 = new JPanel();
        panelPlayer3.setLayout(new GridLayout(1,8));
        panelPlayer3.add(Player3NameLabel);
        panelPlayer3.add(Player3Name);
        panelPlayer3.add(Player3ScoreLabel);
        panelPlayer3.add(Player3Score);
        
        
        panelBottom = new JPanel();
        panelBottom.setLayout(new GridLayout(4,1));
        panelBottom.add(panelPlayer1);
        panelBottom.add(panelPlayer2);
       // panelBottom.add(panelPlayer3); Player 3 not supported yet
   }
    synchronized void logOut(String playerName) {
    	if(Player1Name.getText().equals(playerName)){ 
    		PlayerScore1 		= 0; 
    		Player1Name.setText("No Player");
    		Player1Score.setText("0");
    	} else if(Player2Name.getText().equals(playerName)){ 
    		PlayerScore2 		= 0;
    		Player2Name.setText("No Player");
    		Player2Score.setText("0");
    	} else if(Player3Name.getText().equals(playerName)){ 
    		PlayerScore3 		= 0;
    		Player3Name.setText("No Player");
    		Player3Score.setText("0");
    	}
    }
    synchronized void huntFly(String playerName){
    	
    	if(Player1Name.getText().equals(playerName)) {
    		PlayerScore1 = PlayerScore1 + 1;
    		Player1Score.setText(String.valueOf(PlayerScore1));
    		AMessage chatMsg = AMessage.newBuilder().setUsername(playerName).setScore(PlayerScore1).
        			setX(Integer.valueOf(player.getCoordinateX()).intValue()).
        			setY(Integer.valueOf(player.getCoordinateY()).intValue()).
        			setType(4).build();
    		player.run(chatMsg);
		 } else if(Player2Name.getText().equals(playerName)) {
			 PlayerScore2 = PlayerScore2 + 1;
    		 Player2Score.setText(String.valueOf(PlayerScore2));
    		 AMessage chatMsg = AMessage.newBuilder().setUsername(playerName).setScore(PlayerScore2).
        			setX(Integer.valueOf(player.getCoordinateX()).intValue()).
        			setY(Integer.valueOf(player.getCoordinateY()).intValue()).
        			setType(4).build();
    		 player.run(chatMsg);
		 } else if(Player3Name.getText().equals(playerName)) {
			PlayerScore3 = PlayerScore3 + 1; 
    		Player3Score.setText(String.valueOf(PlayerScore3));
    		AMessage chatMsg = AMessage.newBuilder().setUsername(playerName).setScore(PlayerScore3).
        			setX(Integer.valueOf(player.getCoordinateX()).intValue()).
        			setY(Integer.valueOf(player.getCoordinateY()).intValue()).
        			setType(4).build();
    		player.run(chatMsg);
		 }
    }
    //***************JComponent for the FLy************/
    class ImageShowingComponent extends JComponent {

        private BufferedImage loadImg;
	  // The MouseListener that handles the click.
	    private MouseListener listener = new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	    		try {
	    			huntFly(myName);
	    		} catch (Exception e1) {
	    			System.out.println("Exception while hunting fly");  
	    			e1.printStackTrace();
	    		}
	    	}
	    };
	    
  	  ImageShowingComponent() {
  		  addMouseListener(listener);
  		  setLocation(10,10);
  		  // Load the image.
  		  try {
  			  loadImg = ImageIO.read(new File ("Fly.png"));
  		  } catch (IOException e) {
  			  System.out.println("Exception while loding Fly image: ");
  			  e.printStackTrace();
  		  }
	    
	  }
  	  public void paintComponent(Graphics g) {
	    g.drawImage(loadImg, 0, 0, null);
  	  }
	  public Dimension getPreferredSize() {
	    return new Dimension(loadImg.getWidth(), loadImg.getHeight());
	  }
	}
	//**************JComponent for other player mouse************/
    class playerPointer extends JComponent {

        private BufferedImage loadImg;		//mouse pointer image
        private String label;				//mouse pointer name

        playerPointer() {
        	setLocation(10,10);
        	// Load the image.
        	try {
        		loadImg = ImageIO.read(new File ("cursor1.jpg"));
        	} catch (IOException e) {
        		System.out.println("Exception while loading corsur image");
        		e.printStackTrace();
        	}
        }
        public void setLabel(String s){ 
        	label = s;
        }
        public void paintComponent(Graphics g) {
        	g.drawImage(loadImg, 0, 0, null);
        	g.setColor(Color.WHITE);
        	g.drawString(label, 10, 10);
	    
        }
        public Dimension getPreferredSize() {
        	return new Dimension(loadImg.getWidth(), loadImg.getHeight());
        }
	}

}

