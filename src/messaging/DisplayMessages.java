package messaging;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DisplayMessages
 */
@WebServlet("/DisplayMessages")
public class DisplayMessages extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// queries SQL database for messages between sender and receiver
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int receiver = Integer.parseInt(request.getParameter("receiverID"));
		int sender = Integer.parseInt(request.getParameter("senderID"));
		
		Connection conn = null;
		Statement st = null;
		ResultSet sentMessages = null;
		ResultSet receivedMessages = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/messages?user=root&password=root&useSSL=false");
			st = conn.createStatement();
			sentMessages = st.executeQuery("SELECT * " + 
								"FROM messages m " + 
								"WHERE m.receiverID=" + receiver + " " +
								"AND m.senderID=" + sender + " " + 
								"ORDER BY m.messageTime ASC;");
			receivedMessages = st.executeQuery("SELECT * " + 
								"FROM messages m " + 
								"WHERE m.receiverID=" + sender + " " +
								"AND m.senderID=" + receiver + " " + 
								"ORDER BY m.messageTime ASC;");
			while(sentMessages.next()) {
				// code from class
				// not sure what to do with result sets
				// need to be returned to front-end
				String fname = sentMessages.getString("fname");
				String lname = sentMessages.getString("lname");
				String prefix = sentMessages.getString("prefix");
				int num = sentMessages.getInt("num");
				String letterGrade = sentMessages.getString("letterGrade");
				System.out.println(fname + "\t" + lname + "\t" + prefix + "\t" + num + "\t" + letterGrade);
			}
			while(receivedMessages.next()) {
				// not sure what to do with result sets
			}
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} 
		finally {
			try {
				if (sentMessages != null) {
					sentMessages.close();
				}
				
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
