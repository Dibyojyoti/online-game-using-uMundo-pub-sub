using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Timers;

namespace TKLABV1 
{
    public class Flyclass
    {
    private const int D_HEIGHT = 296;
    private const int D_WIDTH = 568;
    private const int INCREMENT = 8;
    //X and Y coordinate of the FLy object
    private int randXLoc = 0;
    private int randYLoc = 0;
    //variables to control Fly movement
    private bool godown = false;
    private bool goLeft = false;
    private int randUpDown = 0;
    private int randLeftRight = 0;
    private Random random1;
    //timers to change coordinate and direction
    private Timer timerCoordinate = null;
    private Timer timerDirection = null;
    private int playerCount = 1;
        //latch for updating score
        //private boolean Mutex 					= false;
       
            
        //*********constructor*****************// 
        //	public FlyClass() throws RemoteException {
        public Flyclass()
        {
            //super();
            random1 = new Random();                                         //initialize random object

            Timer timerCoordinate = new Timer(500);                         //Move fly x,y cordinates
            timerCoordinate.Elapsed += new ElapsedEventHandler(domoveflycordinate);
            timerCoordinate.Enabled = true; // Enable it

            Timer timerDirection = new Timer(500);
            timerDirection.Elapsed += new ElapsedEventHandler(domoveflydirection);
            timerDirection.Enabled = true; // Enable it
            timerDirection.Start();											//start's both timers
            timerCoordinate.Start();
            setFlyAxis();													//initialize Fly Coordinate
	}

    public void domoveflycordinate(object sender, ElapsedEventArgs e )
    {
        if (playerCount > 0)
        { //start changing coordinate if any two player started game 									
            moveFly();
        }
    }

      public void domoveflydirection(object sender, ElapsedEventArgs e)
        {
            if (playerCount > 0)
            { //start changing coordinate if any two player started game
                setFlyDirection();
            }
        }

        public void SetPlayerCount(int count) { playerCount = count; }

    //*********This method changes the coordinates of the Fly*********/
    public void moveFly()
    {
        if (randYLoc > D_HEIGHT || randYLoc < 0 || randXLoc > D_WIDTH || randXLoc < 0)
        {
            setFlyAxis(); setFlyDirection();                                //if Fly flees out of window reset coordinate and direction
        }
        if (godown)
        {
            if (goLeft) { randYLoc += INCREMENT; randXLoc -= INCREMENT; }
            else { randYLoc += INCREMENT; randXLoc += INCREMENT; }
        }
        else
        {
            if (goLeft) { randYLoc -= INCREMENT; randXLoc -= INCREMENT; }
            else { randYLoc -= INCREMENT; randXLoc += INCREMENT; }
        }
    }

    //*********This method changes the moving direction of the Fly*********/
    public void setFlyDirection()
    {
        randUpDown = random1.Next(D_HEIGHT);
        randLeftRight = random1.Next(D_WIDTH);
        if (randUpDown >= 250) { godown = true; } else { godown = false; }
        if (randLeftRight >= 400) { goLeft = true; } else { goLeft = false; }
    }

    //*********This method sets the coordinates of the object first time and every time its relocated*********/
    public void setFlyAxis()
    {
        randXLoc = random1.Next(D_WIDTH);
        randYLoc = random1.Next(D_HEIGHT);
    }

    //*********This methods gives X,Y coordinate of Fly to player*********/
    public string getFlyX() { return Convert.ToString(randXLoc); }
    public string getFlyY() { return Convert.ToString(randYLoc); }

}
}

