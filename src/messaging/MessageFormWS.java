package messaging;

import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Vector;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ServerEndpoint(value = "/ws")
public class MessageFormWS {
	// static so that it's shared among all ServerSocket instances
		private static Vector<Session> sessionVec = new Vector<Session>();
		
		// The following three methods are automatically called
		@OnOpen
		public void open(Session session) {
			System.out.println("Client connected");
			sessionVec.add(session);
		}
		
		@OnMessage
		public void onMessage(String message, Session session) {
			System.out.println(message);
			try {
				for (Session s : sessionVec) {
					s.getBasicRemote().sendText(message);
				}
			} catch (IOException ioe) {
				System.out.println("ioe: " + ioe.getMessage());
			}
		}
		
		@OnClose
		public void close(Session session) {
			System.out.println("Client disconnected");
			sessionVec.remove(session);
		}
}
