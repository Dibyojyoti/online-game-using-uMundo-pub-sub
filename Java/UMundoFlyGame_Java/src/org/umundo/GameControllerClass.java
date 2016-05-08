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
import org.umundo.protobuf.Amessage.AMessage;
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
	
	public static FlyClass getFly() {
		return Fly;
	}

	public static void setFly(FlyClass fly) {
		Fly = fly;
	}

	public Timer timer = null;
	public GameControllerClass() {
		disc = new Discovery(DiscoveryType.MDNS);
		chatNode = new Node();
		disc.add(chatNode);
		
		chatRcv = new ChatReceiver();
		chatSub = new TypedSubscriber("s11nChat");
		chatSub.setReceiver(chatRcv);
		chatPub = new TypedPublisher("s11nChat");
		chatSub.registerType(AMessage.class);

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
				AMessage chatMsg = (AMessage) object;

					//if (chatMsg.getType() == 1) {
						//grettings for players
						GameControllerClass.this.participants.put(msg.getMeta("subscriber"),chatMsg.getUsername());
						System.out.println(chatMsg.getUsername()+ " joined the Fly Game ");
					
					//} 
			}
		}
	}

	class ChatGreeter implements ITypedGreeter {
		public String userName;

		@Override
		public void welcome(TypedPublisher pub, SubscriberStub subStub) {
			AMessage welcomeMsg = AMessage.newBuilder().setUsername(userName)
					.setType(1).build();
			Message greeting = pub.prepareMessage("AMessage", welcomeMsg);
			greeting.setReceiver(subStub.getUUID());
			greeting.putMeta("subscriber", GameControllerClass.this.chatSub.getUUID());
			pub.send(greeting);
		}

		@Override
		public void farewell(TypedPublisher pub, SubscriberStub subStub) {
			if (GameControllerClass.this.participants.containsKey(subStub.getUUID())) {
				System.out.println(GameControllerClass.this.participants.get(subStub.getUUID())
						+ " left the Fly Game");
			} else {
				System.out.println("An unknown user left the Fly Game: " + subStub.getUUID());
			}
		}
	}

	public void run() {
		Fly = new FlyClass();
			 timer = new Timer(100, new ActionListener() {			//Timer to change the coordinate of the remote object
		        public void actionPerformed(ActionEvent e) {
					String s1=""+Fly.getFlyX();
					String s2=""+Fly.getFlyY();
					AMessage FLYcord = AMessage.newBuilder().setType(3)
							.setFlycordx(s1).setFlycordy(s2).build();
					chatPub.sendObject("AMessage", FLYcord);

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
