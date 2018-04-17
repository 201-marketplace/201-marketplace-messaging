package messaging;

import java.io.IOException;
import java.util.HashMap;
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
				if 
				// insert message into database
				
				// look through sessionMap if other user online
				if (sessionMap.containsKey(username)) {
					Session s = sessionMap.get(username);
					s.getBasicRemote().sendText(message);
				}
				
//				for (Session s : sessionMap) {
//					s.getBasicRemote().sendText(message);
//				}
			} catch (IOException ioe) {
				System.out.println("ioe: " + ioe.getMessage());
			}
		}
		
		@OnClose
		public void close(Session session) {
			System.out.println("Client disconnected");
			String str = "";
			sessionMap.remove(str, session);
		}
}
