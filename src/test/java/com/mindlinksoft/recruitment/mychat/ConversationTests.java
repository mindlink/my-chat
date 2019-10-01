package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;

import org.junit.Test;

public class ConversationTests {

	//tests that conversations return the expected name and messages
	@Test
	public void testConversationReturnMethods() {
		Instant testInstant = Instant.parse("2010-09-21T11:35:23Z");
		
		Message message1 = new Message(Instant.parse("2010-09-21T11:35:23Z"), "23", "This is the first message");
		Message message2 = new Message(Instant.parse("2010-09-21T11:37:32Z"), "24", "This is the second message");
		Message message3 = new Message(Instant.parse("2010-09-22T10:23:53Z"), "25", "This is the third message");
		
		ArrayList<Message> messages = new ArrayList<Message>();
		
		messages.add(message1);
		messages.add(message2);
		messages.add(message3);
		
		ConversationAnalysis conAnalysis = new ConversationAnalysis();
		
		Conversation testConversation = new Conversation("testCon", messages, conAnalysis.getUserActivity(messages));
		
		assert(testConversation.getName() == "testCon");
		
		Message[] returnedMessagesArray = new Message[testConversation.getMessages().size()];
        testConversation.getMessages().toArray(returnedMessagesArray);
        
		assert(returnedMessagesArray[0] == message1);
		assert(returnedMessagesArray[2] == message3);
		assert(returnedMessagesArray[1] == message2);
		
	}
	
	//tests that the user report returns with the users and their number of messages sorted correctly
	@Test
	public void testConversationAnalysis() {
		Instant testInstant = Instant.parse("2010-09-21T11:35:23Z");
		
		Message message1 = new Message(Instant.parse("2010-09-21T11:35:23Z"), "User2", "This is the first message");
		Message message2 = new Message(Instant.parse("2010-09-21T11:37:32Z"), "User2", "This is the second message");
		Message message3 = new Message(Instant.parse("2010-09-22T10:23:53Z"), "User1", "This is the third message");
		Message message4 = new Message(Instant.parse("2010-09-21T11:35:23Z"), "User3", "This is the fourth message");
		Message message5 = new Message(Instant.parse("2010-09-21T11:37:32Z"), "User2", "This is the fifth message");
		Message message6 = new Message(Instant.parse("2010-09-22T10:23:53Z"), "User1", "This is the sixth message");
		
		ArrayList<Message> messages = new ArrayList<Message>();
		
		messages.add(message1);
		messages.add(message2);
		messages.add(message3);
		messages.add(message4);
		messages.add(message5);
		messages.add(message6);
		
		ConversationAnalysis conAnalysis = new ConversationAnalysis();
		
		Conversation testConversation = new Conversation("testCon", messages, conAnalysis.getUserActivity(messages));
		
		ArrayList<String[]> userReport = testConversation.getSortedUserData();
		
		assert(userReport.get(0)[0].equals("User2"));
		assert(userReport.get(0)[1].equals("3"));
		assert(userReport.get(1)[0].equals("User1"));
		assert(userReport.get(1)[1].equals("2"));
		assert(userReport.get(2)[0].equals("User3"));
		assert(userReport.get(2)[1].equals("1"));
	}

}
