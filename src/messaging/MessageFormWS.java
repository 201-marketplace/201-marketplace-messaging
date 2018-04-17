package messaging;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/ws")
public class MessageFormWS {
	// static so that it's shared among all ServerSocket instances
		private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();
		
		// The following three methods are automatically called
		@OnOpen
		public void open(Session session) {
			System.out.println("Client connected");
			String username = "";
			sessionMap.put(username, session);
		}
		
		@OnMessage
		public void onMessage(String message, Session session) {
			//printing on server-side
			System.out.println(message);
			try {
				// message is sending a username
				if (message.contains("[USERNAME]")) {
					String username = message.substring(10);
					sessionMap.put(username, session);
				}
				
				// user is sending a normal message
				else {
					String[] split = message.trim().split("\\s+");
					// [0]From, [1] username, [2] To, [3] username, [4] Message, [5] message text
					String sender = split[1];
					String receiver = split[3];
					String msgText = split[5];
					
					// insert message into SQL database
					
					// sendText to receiver
					if (sessionMap.containsKey(receiver)) {
						Session s = sessionMap.get(receiver);
						s.getBasicRemote().sendText(msgText);
					}
				}
			} catch (IOException ioe) {
				System.out.println("ioe: " + ioe.getMessage());
			}
		}
		
		@OnClose
		public void close(Session session) {
			System.out.println("Client disconnected");
			
			// find username key from value
			String username = "";
			for (Entry<String, Session> entry : sessionMap.entrySet()) {
				if (Objects.equals(session, entry.getValue())) {
					username = entry.getKey();
				}
			}
			
			// remove the user's session
			sessionMap.remove(username, session);
		}
}
