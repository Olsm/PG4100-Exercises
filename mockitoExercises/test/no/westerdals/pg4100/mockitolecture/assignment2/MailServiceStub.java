package no.westerdals.pg4100.mockitolecture.assignment2;

import java.util.ArrayList;

import javax.mail.Message;
import javax.mail.MessagingException;

public class MailServiceStub implements MailService {
	ArrayList<Message> messages = new ArrayList<Message>();

	/**
	 * Stub by placing the incoming message in a list of message (and do NOT
	 * send anything). Also implement a method for determining how many messages
	 * have been sent (been placed in the list). Implementation needed for
	 * exercise 2.
	 */
	public void send(Message msg) throws MessagingException {
		messages.add(msg);
	}
	
	public int messageCount () {
		return messages.size();
	}

}
