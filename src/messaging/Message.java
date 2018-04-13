package messaging;

import java.util.Calendar;

public class Message {
	String datetime;
	String message;
	int receiverID; // user receiving message
	int senderID; // user sending message
	
	// Message constructor
	Message(String message, int sender, int receiver) {
		Calendar cal = Calendar.getInstance();
		datetime = "" + cal.get(Calendar.YEAR); 
		datetime += "-" + (cal.get(Calendar.MONTH)+1);
		datetime += "-" + cal.get(Calendar.DATE);
		datetime += " " + cal.get(Calendar.HOUR_OF_DAY);
		datetime += ":" + cal.get(Calendar.MINUTE);
		datetime += ":" + cal.get(Calendar.SECOND);
		this.message = message;
		this.receiverID = receiver;
		this.senderID = sender;
	}
}
