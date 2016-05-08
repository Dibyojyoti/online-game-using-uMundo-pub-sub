package org.umundo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

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

import client.FlyPlayerClient;
/**
 * Make sure to set the correct path to umundo.jar in build.properties if you
 * want to use ant!
 */

public class PlayerClass {

	/**
	 * Send and receive serialized chat message objects. This sample uses
	 * protobuf serialization to send chat messages.
	 */

	public Discovery disc;
	public Node chatNode;
	public TypedSubscriber chatSub;
	public TypedPublisher chatPub;
	private String playerName;
	private String coordinateX;
	private String coordinateY;
	private int score;
	public ChatReceiver chatRcv;
	public ChatGreeter chatGreeter;
	public HashMap<String, String> participants = new HashMap<String, String>();
	public HashMap<String, PlayerClass> playerMap = new HashMap<String, PlayerClass>();
	public String getPlayerName() {
		return playerName;
	}

	public HashMap<String, PlayerClass> getPlayerMap() {
		return playerMap;
	}

	public void setPlayerMap(HashMap<String, PlayerClass> playerMap) {
		this.playerMap = playerMap;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(String coordinateX) {
		this.coordinateX = coordinateX;
	}

	public String getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(String coordinateY) {
		this.coordinateY = coordinateY;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public HashMap<String, String> getParticipants() {
		return participants;
	}

	public void setParticipants(HashMap<String, String> participants) {
		this.participants = participants;
	}
	
	public TypedSubscriber getChatSub() {
		return chatSub;
	}

	public void setChatSub(TypedSubscriber chatSub) {
		this.chatSub = chatSub;
	}


	public BufferedReader reader = new BufferedReader(new InputStreamReader(
			System.in));

	public PlayerClass(String playerName, FlyPlayerClient flyPlayerClient) {
		this.setPlayerName(playerName);
		this.setCoordinateX("0");
		this.setCoordinateY("0");
		disc = new Discovery(DiscoveryType.MDNS);
		
		chatNode = new Node();
		disc.add(chatNode);
		
		chatRcv = new ChatReceiver();
		chatRcv.setPlayer(this);
		chatRcv.setPlayerClient(flyPlayerClient);
		chatSub = new TypedSubscriber("s11nChat");
		chatSub.setReceiver(chatRcv);
		chatPub = new TypedPublisher("s11nChat");
		chatSub.registerType(AMessage.class);

		ChatGreeter greeter = new ChatGreeter();
		greeter.userName = playerName;
		chatPub.setGreeter(greeter);
		
		chatNode.addPublisher(chatPub);
		chatNode.addSubscriber(chatSub);
	}

	class ChatGreeter implements ITypedGreeter {
		public String userName;
		@Override
		public void welcome(TypedPublisher pub, SubscriberStub subStub) {
			AMessage welcomeMsg = AMessage.newBuilder().setUsername(getPlayerName())
					.setType(1).build();
			Message greeting = pub.prepareMessage("AMessage", welcomeMsg);
			greeting.setReceiver(subStub.getUUID());
			greeting.putMeta("subscriber", PlayerClass.this.chatSub.getUUID());
			pub.send(greeting);
		}

		@Override
		public void farewell(TypedPublisher pub, SubscriberStub subStub) {
			if (PlayerClass.this.participants.containsKey(subStub.getUUID())) {
				System.out.println(PlayerClass.this.participants.get(subStub.getUUID())
						+ " left the Flygame");
			} else {
				System.out.println("An unknown user left the flygame: " + subStub.getUUID());
			}
		}
	}


	
	class ChatReceiver implements ITypedReceiver {
		private PlayerClass player;
		private FlyPlayerClient playerClient;
		public PlayerClass getPlayer() {
			return player;
		}

		public void setPlayer(PlayerClass player) {
			this.player = player;
		}

		public FlyPlayerClient getPlayerClient() {
			return playerClient;
		}

		public void setPlayerClient(FlyPlayerClient playerClient) {
			this.playerClient = playerClient;
		}

		@Override
		public void receiveObject(Object object, Message msg) {
			if (object != null && object instanceof AMessage) {
				AMessage AMessage = (AMessage) object;
				System.out.println(AMessage.getUsername());
				if (AMessage.getType() == 1 && !AMessage.getUsername().equals("Fly")) {
					PlayerClass.this.participants.put(msg.getMeta("subscriber"),AMessage.getUsername());
					System.out.println(AMessage.getUsername()+ " Player joined the chat");
					playerMap.put(AMessage.getUsername(), this.getPlayer());
				} else if(AMessage.getType() == 3) {
					//set fly coordinates from Fly into Player object
					//System.out.println(AMessage.getFlycordx() + ": " + AMessage.getFlycordy());
					PlayerClass player = this.getPlayer();
					player.setCoordinateX(AMessage.getFlycordx());
					player.setCoordinateY(AMessage.getFlycordy());
				} else if (AMessage.getType() == 4) {
					//normal message for players to send their coordinates and points
					System.out.println("PlayerClass Name : " + AMessage.getUsername() + " Score : "
							+ AMessage.getScore() + " X : " +AMessage.getX() + " Y : " +AMessage.getY());
					PlayerClass player = this.getPlayer();
					//player.setCoordinateX(String.valueOf(AMessage.getX()));
					//player.setCoordinateY(String.valueOf(AMessage.getY()));
					player.setScore(AMessage.getScore());
					playerMap.put(AMessage.getUsername(), player);
					System.out.println(player.getParticipants().size()+ " player joined the chat");
				}
			}
		}
	}

	public void run(AMessage AMessage) {
		chatPub.sendObject("AMessage", AMessage);
		//System.out.println("Start typing messages (empty line to quit):");
//		while (true) {
//			for(int i=0;i < 10;i++) {
//				String line = "i"+i;
//				AMessage AMessage = AMessage.newBuilder().setUsername(playerName)
//						.setMessage(line).build();
//				chatPub.sendObject("AMessage", AMessage);
//			}
//			//break;
//		}
//		while (true) {
//			String line = "msgs";
//			try {
//				line = reader.readLine();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			if (line.length() == 0)
//				break;
//
//			AMessage AMessage = AMessage.newBuilder().setUsername(userName)
//					.setMessage(line).build();
//			chatPub.sendObject("AMessage", AMessage);
//		}
//		chatNode.removePublisher(chatPub);
//		chatNode.removeSubscriber(chatSub);
	}
}
