using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using org.umundo;
using org.umundo.s11n.demo;
using org.umundo.s11n;

namespace TKLABplayer
{
    using org.umundo.core;

    public struct playerdetails
    {
        public string name;
        public int x;
        public int y;
        public int score;

        public playerdetails(string pname, int px, int py, int pscore)
        {
            name = pname;
            x = px;
            y = py;
            score = pscore;

        }
    }

    public class Player
    {

        /**
         * Send and receive serialized chat message objects. This sample uses
         * protobuf serialization to send chat messages.
         */

        public org.umundo.core.Discovery disc;
        public org.umundo.core.Node chatNode;
        public TypedSubscriber chatSub;
        public TypedPublisher chatPub;
        //public ChatGreeter chatGreeter;
        //public Game Mygame;
        public String userName;
        public int flycordx;
        public int flycordy;
        public int x;
        public int y;
        public playerdetails[] participants = new playerdetails[2] {
        new playerdetails("",0,0,0),
        new playerdetails("",0,0,0)};

        //public HashMap<String, String> participants = new HashMap<String, String>();
        //public Dictionary<String, String> participants = new Dictionary<String, String>();

        //public BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

         //public Player( Game mygame , string playername)
        public Player(string playername)
        {
            userName = playername;
            disc = new org.umundo.core.Discovery(Discovery.DiscoveryType.MDNS);
            chatNode = new org.umundo.core.Node();
            disc.add(chatNode);

            ChatReceiver chatRcv = new ChatReceiver(this);
            chatSub = new TypedSubscriber("s11nChat");
            chatSub.setReceiver(chatRcv);
            chatPub = new TypedPublisher("s11nChat");

            AMessage msg = new AMessage();
            chatSub.RegisterType(msg.GetType().Name, msg.GetType());
            msg.username = userName ;

            /*ChatGreeter greeter = new ChatGreeter(this);
            greeter.userName = userName;
            chatPub.setGreeter(greeter);*/

            x = 0;
            y = 0;

            chatNode.addPublisher(chatPub);
            chatNode.addSubscriber(chatSub);
        }

        class ChatReceiver : ITypedReceiver
        {
            Player p;
            public ChatReceiver(Player powner)
            {
                p = powner;
            }

            public void ReceiveObject(Object o, Message msg)
            {
                AMessage chatMsg = (AMessage)o;
                Console.WriteLine("message by: " + chatMsg.username + "   type:" + chatMsg.type);
                if (o != null)
                {
                    

                    
                    // NORMAL type 0
                    // JOINED type 1
                    // LEFT type 2
                    // FLYCORD 3
                    // FLYSTOPPED 4
                    if (chatMsg.type == 4)
                    { 
                        for (int i = 0; i < 2; i++)
                        {
                            if (p.participants[i].name == chatMsg.username) 
                            {
                                p.participants[i].score = chatMsg.score;
                                p.participants[i].x = chatMsg.x;
                                p.participants[i].y = chatMsg.y;
                                break;
                            }
                            if (p.participants[i].name == "")
                            {
                                
                                p.participants[i].name = chatMsg.username;
                                p.participants[i].score = chatMsg.score;
                                p.participants[i].x = chatMsg.x;
                                p.participants[i].y = chatMsg.y;
                                break;
                            }
                        }
                    }
                    else if (chatMsg.type == 3)
                    {
                        //fly coordinate message
                        p.flycordx = Int32.Parse(chatMsg.flycordx);
                        p.flycordy = Int32.Parse(chatMsg.flycordy);

                    } else if (chatMsg.type == 2)
                    {
                        Console.WriteLine("left received");
                        for (int i = 0; i < 2; i++)
                        {
                            Console.WriteLine(p.participants[i].name);
                            Console.WriteLine(p.participants[i].score);
                        }
                        for (int i = 0; i < 2; i++)
                        {
                            if (p.participants[i].name == chatMsg.username)
                            {
                                p.participants[i].score = 0;
                                p.participants[i].x = -100;
                                p.participants[i].y = -100;
                                p.participants[i].name = "";
                                break;
                            }
                        }
                        Console.WriteLine("left received : after");
                        for (int i = 0; i < 2; i++)
                        {
                            Console.WriteLine(p.participants[i].name);
                            Console.WriteLine(p.participants[i].score);
                        }
                        

                    }

                }
            }
        }

        
        AMessage classchatMsg = new AMessage();
        public void sendmessage(int cordX, int cordY, int pscore)
        {

            classchatMsg.username = userName;
            classchatMsg.message = "This is test msg";
            classchatMsg.type = 4;
            classchatMsg.flycordx = "10";
            classchatMsg.flycordy = "20";
            classchatMsg.x = cordX;
            classchatMsg.y = cordY;
            classchatMsg.score = pscore;
            participants[0].score = pscore;
            participants[0].x = cordX;
            participants[0].y = cordY;
            chatPub.SendObject("AMessage", classchatMsg);
        }

        public void left()
        {

            classchatMsg.username = userName;
            classchatMsg.message = "This is test msg";
            classchatMsg.type = 2;
            classchatMsg.flycordx = "-100";
            classchatMsg.flycordy = "-200";
            classchatMsg.x = 0;
            classchatMsg.y = 0;
            classchatMsg.score = 0;
            chatPub.SendObject("AMessage",classchatMsg);
            Console.WriteLine("user left " + classchatMsg.username);
            chatNode.removePublisher(chatPub);
            chatNode.removeSubscriber(chatSub);
        }

        public void run(string name)
        {

                userName = name;
                Console.WriteLine("user Joined " + userName + ":" + name);
                classchatMsg.username = name;
                classchatMsg.message = "This is test msg";
                classchatMsg.type = 4;
                classchatMsg.flycordx = "10";
                classchatMsg.flycordy = "20";
                classchatMsg.score = 0;
                classchatMsg.x = 0;
                classchatMsg.y = 0;
                participants[0].name = userName;
                participants[0].score = 0;
                participants[0].x = 0;
                participants[0].y = 0;


            chatPub.SendObject("AMessage", classchatMsg);
           
        }
    }

}
