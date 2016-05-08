using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace TKLABplayer
{
    using System.Timers;
    using System.Threading;
    
    public partial class Game : Form
    {
        public Player myplayer;
        public bool gamestarted = false;
        public int score = 0;
        PictureBox[] pics = new PictureBox[2] {
        new PictureBox(), new PictureBox() };
        public Game(string username)
        {
            InitializeComponent();
            //myplayer = new Player(this, username);
            myplayer = new Player(username);
            myplayer.run(username);
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            this.middlepanel.Visible = true;

        }
        private void Form1_FormClosed(object sender, EventArgs e)
        {
            myplayer.left();
            System.Windows.Forms.Application.Exit();
        }
        private void panel1_Paint(object sender, PaintEventArgs e)
        {

        }
        private void panel2_Paint(object sender, PaintEventArgs e)
        {

        }


        private void panel3_Paint(object sender, PaintEventArgs e)
        {

        }

        private void panel2_Paint_1(object sender, PaintEventArgs e)
        {

        }
    
        private void panel2_Click(object sender, EventArgs e)
        {
            Point point = this.middlepanel.PointToClient(Cursor.Position);
        }

   
        // This delegate enables asynchronous calls for setting
        // the text property on a TextBox control.
        delegate void SetTextCallback(int x, int y);

        private void SetText(int x, int y)
        {
            // InvokeRequired required compares the thread ID of the
            // calling thread to the thread ID of the creating thread.
            // If these threads are different, it returns true.
            if (this.flyBox1.InvokeRequired)
            {
                SetTextCallback d = new SetTextCallback(SetText);
                this.Invoke(d, new object[] { x, y });
            }
            else
            {
                Point point = this.middlepanel.PointToClient(Cursor.Position);
                myplayer.sendmessage(point.X, point.Y, score);
                this.flyBox1.Location = new System.Drawing.Point(myplayer.flycordx, myplayer.flycordy);
                for (int i=0; i < 2; i++)
                {
                    if(i == 0)
                    {
                            Player1Namelabel.Text = myplayer.participants[i].name;
                            Player1ScoreVlabel.Text = myplayer.participants[i].score.ToString();
                        
                    }else if (i == 1)
                    {
                        
                            Player2Namelabel.Text = myplayer.participants[i].name;
                            Player2ScoreVlabel.Text = myplayer.participants[i].score.ToString();
                    }
                }


                    if (myplayer.participants[1].name != "") { 
                        //pics[z] = new PictureBox();
                        pics[0].Location = new Point(myplayer.participants[1].x, myplayer.participants[1].y);
                        pics[0].Name = myplayer.participants[1].name;
                        pics[0].Size = new Size(30, 30);
                        pics[0].Image = global::TKLABplayer.Properties.Resources.pointer2;
                        pics[0].SizeMode = System.Windows.Forms.PictureBoxSizeMode.CenterImage;
                        this.middlepanel.Controls.Add(pics[0]);
                }
                this.bottompanel.Refresh();
                this.middlepanel.Refresh();
            }
        }
        public void setflycordinates(object sender, ElapsedEventArgs e)
        {
            this.SetText(myplayer.flycordx, myplayer.flycordy);
        }

        // This method is executed on the worker thread and makes
        // an unsafe call on the TextBox control.
        private void ThreadProcUnsafe()
        {
            System.Timers.Timer timer = new System.Timers.Timer(50);
            timer.Elapsed += new ElapsedEventHandler(setflycordinates);
            timer.Enabled = true; // Enable it
            timer.Start();
        }

        private void StartButton_Click(object sender, EventArgs e)
        {
            gamestarted = true;
            Thread tmpThread = new Thread(new ThreadStart(this.ThreadProcUnsafe));
            tmpThread.Start();

        }

        private void Closebutton_Click(object sender, EventArgs e)
        {
            myplayer.left();
            System.Windows.Forms.Application.Exit();
        }

        private void panel2_Paint_2(object sender, PaintEventArgs e)
        {

        }

        private void pictureBox1_Click(object sender, EventArgs e)
        {
            if (gamestarted)
            {
                score++;
            }
            

        }

    }
}
