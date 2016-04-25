package org.umundo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import javax.swing.Timer;
import org.umundo.core.Discovery;
import org.umundo.core.Discovery.DiscoveryType;
import org.umundo.core.Message;
import org.umundo.core.Node;
import org.umundo.core.SubscriberStub;
import org.umundo.protobuf.ChatS11N.ChatMsg;
import org.umundo.s11n.ITypedGreeter;
import org.umundo.s11n.ITypedReceiver;
import org.umundo.s11n.TypedPublisher;
import org.umundo.s11n.TypedSubscriber;

/**Make sure to set the correct path to umundo.jar in build.properties if you want to use ant!*/
public class GameControllerClass {
	/**Send and receive serialized chat message objects. This sample uses
	 * protobuf serialization to send chat messages.*/
	public Discovery disc;
	public Node chatNode;
	public TypedSubscriber chatSub;
	public TypedPublisher chatPub;
	public ChatGreeter chatGreeter;
	public ChatReceiver chatRcv;

	
	public String userName="Fly";
	public HashMap<String, String> participants = new HashMap<String, String>();
	public BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	private static  FlyClass Fly;
	public Timer timer = null;
	public GameControllerClass() {
		disc = new Discovery(DiscoveryType.MDNS);
		
		chatNode = new Node();
		disc.add(chatNode);
		
		chatRcv = new ChatReceiver();
		chatSub = new TypedSubscriber("s11nChat");
		chatSub.setReceiver(chatRcv);
		chatPub = new TypedPublisher("s11nChat");
		chatSub.registerType(ChatMsg.class);

		ChatGreeter greeter = new ChatGreeter();
		greeter.userName = userName;
		chatPub.setGreeter(greeter);

		chatNode.addPublisher(chatPub);
		chatNode.addSubscriber(chatSub);
	}

	class ChatReceiver implements ITypedReceiver {

		@Override
		public void receiveObject(Object object, Message msg) {
			if (object != null) {
				ChatMsg chatMsg = (ChatMsg) object;

				if (chatMsg.getType() == ChatMsg.Type.JOINED) {
					//grettings for players
					GameControllerClass.this.participants.put(msg.getMeta("subscriber"),
							chatMsg.getUsername());
					System.out.println(chatMsg.getUsername()
							+ " joined the in Fly Game");
				} else if (chatMsg.getType() == ChatMsg.Type.NORMAL) {
					//normal message for players to send their coordinates and points 
					System.out.println(chatMsg.getUsername() + ": "
							+ chatMsg.getMessage());
				}								
			}
		}
	}

	class ChatGreeter implements ITypedGreeter {
		public String userName;

		@Override
		public void welcome(TypedPublisher pub, SubscriberStub subStub) {
			ChatMsg welcomeMsg = ChatMsg.newBuilder().setUsername(userName)
					.setType(ChatMsg.Type.JOINED).build();
			Message greeting = pub.prepareMessage("ChatMsg", welcomeMsg);
			greeting.setReceiver(subStub.getUUID());
			greeting.putMeta("subscriber", GameControllerClass.this.chatSub.getUUID());
			pub.send(greeting);
		}

		@Override
		public void farewell(TypedPublisher pub, SubscriberStub subStub) {
			if (GameControllerClass.this.participants.containsKey(subStub.getUUID())) {
				System.out.println(GameControllerClass.this.participants.get(subStub.getUUID())
						+ " left the Flygame");
			} else {
				System.out.println("An unknown user left the flygame: " + subStub.getUUID());
			}
		}
	}

	public void run() {
			Fly = new FlyClass();
			 timer = new Timer(100, new ActionListener() {			//Timer to change the coordinate of the remote object
		        public void actionPerformed(ActionEvent e) {
					String s1=""+Fly.getFlyX();
					String s2=""+Fly.getFlyY();
					//System.out.println(s1+" : "+s2);

					ChatMsg FLYcord = ChatMsg.newBuilder().setType(ChatMsg.Type.FLYCORD)
							.setFlyCordX(s1).setFlyCordY(s2).build();
					chatPub.sendObject("ChatMsg", FLYcord);

		        }
		    });
	    	timer.start();											//start's both timers
	    	while(true);
		//chatNode.removePublisher(chatPub);
		//chatNode.removeSubscriber(chatSub);
	}

	public static void main(String[] args) {
		GameControllerClass chat = new GameControllerClass();
		chat.run();
	}
}
