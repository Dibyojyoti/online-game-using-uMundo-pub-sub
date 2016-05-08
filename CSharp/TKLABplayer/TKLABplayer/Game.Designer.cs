namespace TKLABplayer
{
    partial class Game
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.toppanel = new System.Windows.Forms.Panel();
            this.Closebutton = new System.Windows.Forms.Button();
            this.StartButton = new System.Windows.Forms.Button();
            this.bottompanel = new System.Windows.Forms.Panel();
            this.Player2ScoreVlabel = new System.Windows.Forms.Label();
            this.Player2Scorelabel = new System.Windows.Forms.Label();
            this.Player1ScoreVlabel = new System.Windows.Forms.Label();
            this.Player1Scorelabel = new System.Windows.Forms.Label();
            this.Player2Namelabel = new System.Windows.Forms.Label();
            this.Player2label = new System.Windows.Forms.Label();
            this.Player1Namelabel = new System.Windows.Forms.Label();
            this.Player1label = new System.Windows.Forms.Label();
            this.middlepanel = new System.Windows.Forms.Panel();
            this.flyBox1 = new System.Windows.Forms.PictureBox();
            this.toppanel.SuspendLayout();
            this.bottompanel.SuspendLayout();
            this.middlepanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.flyBox1)).BeginInit();
            this.SuspendLayout();
            // 
            // toppanel
            // 
            this.toppanel.BackColor = System.Drawing.SystemColors.ButtonFace;
            this.toppanel.Controls.Add(this.Closebutton);
            this.toppanel.Controls.Add(this.StartButton);
            this.toppanel.Location = new System.Drawing.Point(1, 0);
            this.toppanel.Margin = new System.Windows.Forms.Padding(2);
            this.toppanel.Name = "toppanel";
            this.toppanel.Size = new System.Drawing.Size(568, 37);
            this.toppanel.TabIndex = 0;
            this.toppanel.Paint += new System.Windows.Forms.PaintEventHandler(this.panel1_Paint);
            // 
            // Closebutton
            // 
            this.Closebutton.Location = new System.Drawing.Point(299, 10);
            this.Closebutton.Margin = new System.Windows.Forms.Padding(2);
            this.Closebutton.Name = "Closebutton";
            this.Closebutton.Size = new System.Drawing.Size(56, 19);
            this.Closebutton.TabIndex = 1;
            this.Closebutton.Text = "Close";
            this.Closebutton.UseVisualStyleBackColor = true;
            this.Closebutton.Click += new System.EventHandler(this.Closebutton_Click);
            // 
            // StartButton
            // 
            this.StartButton.Location = new System.Drawing.Point(187, 10);
            this.StartButton.Margin = new System.Windows.Forms.Padding(2);
            this.StartButton.Name = "StartButton";
            this.StartButton.Size = new System.Drawing.Size(56, 19);
            this.StartButton.TabIndex = 0;
            this.StartButton.Text = "Start";
            this.StartButton.UseVisualStyleBackColor = true;
            this.StartButton.Click += new System.EventHandler(this.StartButton_Click);
            // 
            // bottompanel
            // 
            this.bottompanel.BackColor = System.Drawing.SystemColors.ButtonFace;
            this.bottompanel.Controls.Add(this.Player2ScoreVlabel);
            this.bottompanel.Controls.Add(this.Player2Scorelabel);
            this.bottompanel.Controls.Add(this.Player1ScoreVlabel);
            this.bottompanel.Controls.Add(this.Player1Scorelabel);
            this.bottompanel.Controls.Add(this.Player2Namelabel);
            this.bottompanel.Controls.Add(this.Player2label);
            this.bottompanel.Controls.Add(this.Player1Namelabel);
            this.bottompanel.Controls.Add(this.Player1label);
            this.bottompanel.Location = new System.Drawing.Point(1, 342);
            this.bottompanel.Margin = new System.Windows.Forms.Padding(2);
            this.bottompanel.Name = "bottompanel";
            this.bottompanel.Size = new System.Drawing.Size(568, 78);
            this.bottompanel.TabIndex = 2;
            this.bottompanel.Paint += new System.Windows.Forms.PaintEventHandler(this.panel3_Paint);
            // 
            // Player2ScoreVlabel
            // 
            this.Player2ScoreVlabel.AutoSize = true;
            this.Player2ScoreVlabel.Location = new System.Drawing.Point(506, 7);
            this.Player2ScoreVlabel.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.Player2ScoreVlabel.Name = "Player2ScoreVlabel";
            this.Player2ScoreVlabel.Size = new System.Drawing.Size(13, 13);
            this.Player2ScoreVlabel.TabIndex = 10;
            this.Player2ScoreVlabel.Text = "0";
            // 
            // Player2Scorelabel
            // 
            this.Player2Scorelabel.AutoSize = true;
            this.Player2Scorelabel.Location = new System.Drawing.Point(454, 7);
            this.Player2Scorelabel.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.Player2Scorelabel.Name = "Player2Scorelabel";
            this.Player2Scorelabel.Size = new System.Drawing.Size(38, 13);
            this.Player2Scorelabel.TabIndex = 8;
            this.Player2Scorelabel.Text = "Score:";
            // 
            // Player1ScoreVlabel
            // 
            this.Player1ScoreVlabel.AutoSize = true;
            this.Player1ScoreVlabel.Location = new System.Drawing.Point(198, 7);
            this.Player1ScoreVlabel.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.Player1ScoreVlabel.Name = "Player1ScoreVlabel";
            this.Player1ScoreVlabel.Size = new System.Drawing.Size(13, 13);
            this.Player1ScoreVlabel.TabIndex = 7;
            this.Player1ScoreVlabel.Text = "0";
            // 
            // Player1Scorelabel
            // 
            this.Player1Scorelabel.AutoSize = true;
            this.Player1Scorelabel.Location = new System.Drawing.Point(146, 7);
            this.Player1Scorelabel.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.Player1Scorelabel.Name = "Player1Scorelabel";
            this.Player1Scorelabel.Size = new System.Drawing.Size(38, 13);
            this.Player1Scorelabel.TabIndex = 6;
            this.Player1Scorelabel.Text = "Score:";
            // 
            // Player2Namelabel
            // 
            this.Player2Namelabel.AutoSize = true;
            this.Player2Namelabel.Location = new System.Drawing.Point(377, 7);
            this.Player2Namelabel.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.Player2Namelabel.Name = "Player2Namelabel";
            this.Player2Namelabel.Size = new System.Drawing.Size(53, 13);
            this.Player2Namelabel.TabIndex = 4;
            this.Player2Namelabel.Text = "No Player";
            // 
            // Player2label
            // 
            this.Player2label.AutoSize = true;
            this.Player2label.Location = new System.Drawing.Point(310, 7);
            this.Player2label.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.Player2label.Name = "Player2label";
            this.Player2label.Size = new System.Drawing.Size(45, 13);
            this.Player2label.TabIndex = 2;
            this.Player2label.Text = "Player2:";
            // 
            // Player1Namelabel
            // 
            this.Player1Namelabel.AutoSize = true;
            this.Player1Namelabel.Location = new System.Drawing.Point(69, 7);
            this.Player1Namelabel.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.Player1Namelabel.Name = "Player1Namelabel";
            this.Player1Namelabel.Size = new System.Drawing.Size(53, 13);
            this.Player1Namelabel.TabIndex = 1;
            this.Player1Namelabel.Text = "No Player";
            // 
            // Player1label
            // 
            this.Player1label.AutoSize = true;
            this.Player1label.Location = new System.Drawing.Point(2, 7);
            this.Player1label.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.Player1label.Name = "Player1label";
            this.Player1label.Size = new System.Drawing.Size(45, 13);
            this.Player1label.TabIndex = 0;
            this.Player1label.Text = "Player1:";
            // 
            // middlepanel
            // 
            this.middlepanel.Controls.Add(this.flyBox1);
            this.middlepanel.Location = new System.Drawing.Point(1, 41);
            this.middlepanel.Margin = new System.Windows.Forms.Padding(2);
            this.middlepanel.Name = "middlepanel";
            this.middlepanel.Size = new System.Drawing.Size(568, 296);
            this.middlepanel.TabIndex = 3;
            this.middlepanel.Click += new System.EventHandler(this.panel2_Click);
            this.middlepanel.Paint += new System.Windows.Forms.PaintEventHandler(this.panel2_Paint_2);
            // 
            // flyBox1
            // 
            this.flyBox1.AccessibleName = "";
            this.flyBox1.ErrorImage = global::TKLABplayer.Properties.Resources.fly;
            this.flyBox1.Image = global::TKLABplayer.Properties.Resources.fly;
            this.flyBox1.InitialImage = global::TKLABplayer.Properties.Resources.fly;
            this.flyBox1.Location = new System.Drawing.Point(11, 16);
            this.flyBox1.Name = "flyBox1";
            this.flyBox1.Size = new System.Drawing.Size(25, 25);
            this.flyBox1.TabIndex = 0;
            this.flyBox1.TabStop = false;
            this.flyBox1.Tag = "";
            this.flyBox1.Click += new System.EventHandler(this.pictureBox1_Click);
            // 
            // Game
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ActiveCaption;
            this.ClientSize = new System.Drawing.Size(568, 423);
            this.Controls.Add(this.middlepanel);
            this.Controls.Add(this.bottompanel);
            this.Controls.Add(this.toppanel);
            this.Margin = new System.Windows.Forms.Padding(2);
            this.Name = "Game";
            this.Text = "Flygame";
            this.TransparencyKey = System.Drawing.Color.FromArgb(((int)(((byte)(128)))), ((int)(((byte)(128)))), ((int)(((byte)(255)))));
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.Form1_FormClosed);
            this.Load += new System.EventHandler(this.Form1_Load);
            this.toppanel.ResumeLayout(false);
            this.bottompanel.ResumeLayout(false);
            this.bottompanel.PerformLayout();
            this.middlepanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.flyBox1)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel toppanel;
        private System.Windows.Forms.Panel bottompanel;
        private System.Windows.Forms.Button Closebutton;
        private System.Windows.Forms.Button StartButton;
        private System.Windows.Forms.Label Player1label;
        private System.Windows.Forms.Label Player2ScoreVlabel;
        private System.Windows.Forms.Label Player2Scorelabel;
        private System.Windows.Forms.Label Player1ScoreVlabel;
        private System.Windows.Forms.Label Player1Scorelabel;
        private System.Windows.Forms.Label Player2Namelabel;
        private System.Windows.Forms.Label Player2label;
        private System.Windows.Forms.Label Player1Namelabel;
        private System.Windows.Forms.Panel middlepanel;
        public System.Windows.Forms.PictureBox flyBox1;
    }
}