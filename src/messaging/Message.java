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
	}
	
}
