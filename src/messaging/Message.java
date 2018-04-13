package messaging;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class Message {
	Calendar cal;
	String message;
	int receiverID; // user receiving message
	int senderID; // user sending message
	
	// Message constructor
	Message(String message, int sender, int receiver) {
		this.cal = Calendar.getInstance();
		this.message = message;
		this.receiverID = receiver;
		this.senderID = sender;
		updateMessages(this);
	}
	
	// add the Message to message database
	public void updateMessages(Message msg) {
		Connection conn = null;
		Statement st = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/messages?user=root&password=root&useSSL=false");
			st = conn.createStatement();
			
			String sqlInsert = "INSERT INTO messagedata " +
							"VALUES ('" + this.message + "', " + this.cal + ", " + this.senderID + ", " + this.receiverID + ")";
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
