using System;
using System.Runtime.InteropServices;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Timers;
using org.umundo;
using org.umundo.s11n;
using org.umundo.s11n.demo;

namespace TKLABV1
{
    using org.umundo.core;
    class Gamecontroller
    {
        [DllImport("kernel32.dll", CharSet = CharSet.Auto)]
        private static extern void SetDllDirectory(string lpPathName);

        /**Send and receive serialized chat message objects. This sample uses
	 * protobuf serialization to send chat messages.*/
        public org.umundo.core.Discovery disc;
        public org.umundo.core.Node chatNode;
        public TypedSubscriber chatSub;
        public TypedPublisher chatPub;
        //public ChatGreeter chatGreeter;
        public ChatReceiver chatRcv;
        public String userName = "Fly";
        public Dictionary<String, String> participants = new Dictionary<String, String>();
        //public BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        private static Flyclass Fly;
        public Timer timer = null;

        public Gamecontroller()
        {
            //disc = new Discovery(DiscoveryType.MDNS);
            disc = new org.umundo.core.Discovery(Discovery.DiscoveryType.MDNS);
            //chatNode = new Node();
            chatNode = new org.umundo.core.Node();

            disc.add(chatNode);

            chatRcv = new ChatReceiver(this);
            chatSub = new TypedSubscriber("s11nChat");
            chatSub.setReceiver(chatRcv);
            chatPub = new TypedPublisher("s11nChat");
            //chatSub.registerType(ChatMsg.class);
            AMessage msg = new AMessage();
            chatSub.RegisterType(msg.GetType().Name, msg.GetType());

		    chatNode.addPublisher(chatPub);
		    chatNode.addSubscriber(chatSub);
	}

   public class ChatReceiver : ITypedReceiver
    {
            Gamecontroller localgc;
            public ChatReceiver(Gamecontroller g)
                {
                localgc = g;
                }

                public void ReceiveObject(Object o, Message msg){
                AMessage chatMsg = (AMessage)o;

                Console.WriteLine("controller message by: " + chatMsg.username + "   type:" + chatMsg.type);
            }
    }

    public void run() {
        Fly = new Flyclass();

            Timer timer = new Timer(100);
            timer.Elapsed += new ElapsedEventHandler(sendnewcordinates);
            timer.Enabled = true; // Enable it
            timer.Start();                                          //start timer to update fly cordinates 
            while (true) ;
	 }
    
        public void sendnewcordinates(object sender, ElapsedEventArgs e)
        {
            AMessage msg = new AMessage();
            msg.message = "flycord";
            msg.type = 3; //FLY CORDINATES
            msg.username = "fly";
            msg.flycordx =Fly.getFlyX();
            msg.flycordy = Fly.getFlyY();
            msg.x = 0;
            msg.y = 0;
            chatPub.SendObject("AMessage", msg); //Send fly cordinates
        }



        public static void Main(String[] args)
    {
                /*
                * Make sure this path contains the umundoNativeCSharp.dll!
                */
            if (System.Environment.Is64BitProcess)
            {
                //SetDllDirectory("C:\\Program Files (x86)\\uMundo\\share\\umundo\\bindings\\csharp64");
                SetDllDirectory("C:\\Program Files\\uMundo\\share\\umundo\\bindings\\csharp64");
            }
            else
            {
                //SetDllDirectory("C:\\Program Files (x86)\\uMundo\\share\\umundo\\bindings\\csharp");
                SetDllDirectory("C:\\Program Files\\uMundo\\share\\umundo\\bindings\\csharp");
            }

            Gamecontroller chat = new Gamecontroller();
            chat.run();
    }
}
}