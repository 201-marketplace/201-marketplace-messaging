package messaging;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MessageForm
 * When a user sends a message, forward to servlet with the message text, the receiver's user ID and the sender's user ID
 */
@WebServlet("/MessageForm")
public class MessageForm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		// get request
		String messageText = request.getParameter("text");
		int receiver = Integer.parseInt(request.getParameter("receiverID"));
		int sender = Integer.parseInt(request.getParameter("senderID"));
		
		// Create new Message object which will also get date/time
		// Insert message into SQL database
		Message msg = new Message(messageText, sender, receiver);
		updateMessages(msg);
		out.println("Message recorded!");
		
		//no response text needed
	}
	
	//add Message to message database	
	protected void updateMessages(Message msg) {
		Connection conn = null;
		Statement st = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/messages?user=root&password=root&useSSL=false");
			st = conn.createStatement();
			// example query
			/* 
			 * insert into messagedata
				values (5, 'hello world', '2008-11-11 13:23:44', 6, 6)
			 * */
			String sqlInsert = "INSERT INTO messagedata (messageText, messageTime, senderID, receiverID) " +
							"VALUES ('" + msg.message + "', '" + msg.datetime + "', " + msg.senderID + ", " + msg.receiverID + ")";
			st.executeUpdate(sqlInsert);
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} 
		finally {
			try {
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				System.out.println("Close exception: " + e.getMessage());
			}
		}
	}
}


