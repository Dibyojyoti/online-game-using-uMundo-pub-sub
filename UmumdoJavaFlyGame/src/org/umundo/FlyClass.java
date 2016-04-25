package org.umundo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import javax.swing.Timer;
// This is the object which gives the coordinates of the fly and stores and gives player names and scores and status
// To be available for remote connection should implement the remote object reference (JavaRMIflyInterface) and
// extend UnicastRemoteObject

//public class FlyRemoteObj extends UnicastRemoteObject implements JavaRMIflyInterface {
public class FlyClass {

	private static final int D_HEIGHT 		= 400;
    private static final int D_WIDTH 		= 800;
    private static final int INCREMENT 		= 8;
    //X and Y coordinate of the FLy object
    private int randXLoc					= 0;						
    private int randYLoc					= 0;
    //variables to control Fly movement
    private boolean godown 					= false;
    private boolean goLeft					= false;
    private int randUpDown					= 0;
    private int randLeftRight				= 0;
    private Random random1;
    //timers to change coordinate and direction
    private Timer timerCoordinate 			= null;
    private Timer timerDirection 			= null;
    private int playerCount = 1;
    //latch for updating score
    //private boolean Mutex 					= false;
    
    //*********constructor*****************// 
//	public FlyClass() throws RemoteException {
	public FlyClass() {

		super();
        random1 = new Random();											//initialize random object
        
		timerCoordinate = new Timer(100, new ActionListener() {			//Timer to change the coordinate of the remote object
	        public void actionPerformed(ActionEvent e) {
	        	if(playerCount > 0 ){ //start changing coordinate if any two player started game 									
	        			moveFly();
	        	}
	        }
	    });
	    timerDirection = new Timer(700, new ActionListener() {			//Timer to change the direction of the remote object
	        public void actionPerformed(ActionEvent e) {
	        	if(playerCount > 0 ){	//start changing coordinate if any two player started game
	        			setFlyDirection();
	        	}
	        }
	    });
    	timerDirection.start();											//start's both timers
        timerCoordinate.start();
        
        setFlyAxis();													//initialize Fly Coordinate
	}
   public void SetPlayerCount(int count){ playerCount = count;}
	//*********This method changes the coordinates of the Fly*********/
    public void moveFly() {
    	if(randYLoc > D_HEIGHT || randYLoc < 0 || randXLoc > D_WIDTH || randXLoc < 0 ) {
    		setFlyAxis();setFlyDirection();								//if Fly flees out of window reset coordinate and direction
    	}
        if (godown) {
            if(goLeft){            	randYLoc += INCREMENT;                    randXLoc -= INCREMENT;            }
            else      {        		randYLoc += INCREMENT;                    randXLoc += INCREMENT;            }
         } 
         else {
            if(goLeft){            	randYLoc -= INCREMENT;                    randXLoc -= INCREMENT;            }
            else      {            	randYLoc -= INCREMENT;                    randXLoc += INCREMENT;            }
         }
    }
    
    //*********This method changes the moving direction of the Fly*********/
    public void setFlyDirection(){
    	randUpDown    = random1.nextInt(D_HEIGHT);
        randLeftRight = random1.nextInt(D_WIDTH);
    	if (randUpDown    >=250) { godown=true; }   else { godown=false; }
    	if (randLeftRight >=400) { goLeft=true; }	else { goLeft=false; }
    }
    
    //*********This method sets the coordinates of the object first time and every time its relocated*********/
    public void setFlyAxis(){
        randXLoc = random1.nextInt(D_WIDTH);
        randYLoc = random1.nextInt(D_HEIGHT);
    }
    
    //*********This methods gives X,Y coordinate of Fly to player*********/
    public int getFlyX(){ 	    			return randXLoc;     }
    public int getFlyY(){     				return randYLoc;     }

}