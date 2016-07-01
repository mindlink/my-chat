package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * class that holds a copy of the conversation 
 * used for testing
 * @author Asmaa
 *
 */
public class convoCopy {
	public static Conversation copy() {
		List<Message> messages = new ArrayList<Message>();
		messages.add(new Message(Instant.ofEpochSecond(1448470901), "bob", "Hello there!"));
		messages.add(new Message(Instant.ofEpochSecond(1448470905), "mike", "how are you?"));
		messages.add(new Message(Instant.ofEpochSecond(1448470906), "bob", "I'm good thanks, do you like pie?"));
		messages.add(new Message(Instant.ofEpochSecond(1448470910), "mike", "no, let me ask Angus..."));
		messages.add(new Message(Instant.ofEpochSecond(1448470912), "angus", "Hell yes! Are we buying some pie?"));
		messages.add(new Message(Instant.ofEpochSecond(1448470914), "bob", "No, just want to know if there's anybody else in the pie society..."));
		messages.add(new Message(Instant.ofEpochSecond(1448470915), "angus", "YES! I'm the head pie eater there..."));

		return new Conversation("My Conversation" , messages);
	}
}
