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
    public partial class Newuser : Form
    {
        public Newuser()
        {
            InitializeComponent();
        }

        private void Newuser_Load(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            
            this.Hide();

            try
            {
                Game gamewindow = new Game(this.textBox1.Text);
                gamewindow.Visible = true;
                //Console.WriteLine("hi1");
                gamewindow.Show();
              //  gamewindow.ShowDialog();
            }
            catch (InvalidOperationException) { Console.WriteLine("hi"); }
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {
        }
    }
}
