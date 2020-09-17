package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * Mock class used to generate messages.
 * 
 * @author Mohamed Yusuf
 *
 */
public class GenerateMockMessages {
	public Set<Message> genMockMessages() {
		Set<Message> mess = new HashSet<Message>();
		
		mess.add(new Message(Instant.ofEpochSecond(1448470901), 
				"bob", 
				"Hello there!"));
		mess.add(new Message(Instant.ofEpochSecond(1448470905), 
				"mike", 
				"how are you?"));
		mess.add(new Message(Instant.ofEpochSecond(1448470906), 
				"bob", 
				"I'm good thanks, do you like pie?"));
		mess.add(new Message(Instant.ofEpochSecond(1448470910), 
				"mike", 
				"no, let me ask Angus..."));
		mess.add(new Message(Instant.ofEpochSecond(1448470912), 
				"angus", 
				"Hell yes! Are we buying some pie?\""));
		mess.add(new Message(Instant.ofEpochSecond(1448470914), 
				"bob", 
				"No, just want to know if there's anybody else in the pie society..."));
		mess.add(new Message(Instant.ofEpochSecond(1448470915), 
				"angus", 
				"YES! I'm the head pie eater there..."));
		mess.add(new Message(Instant.ofEpochSecond(1448470930), 
				"john", 
				"It's all on me, use this card 1234 1234 1234 1234"));
		mess.add(new Message(Instant.ofEpochSecond(1448470931 ), 
				"alan", 
				"Or try it in this format 1234-1234-1234-1234"));
		return mess;
	}

}
