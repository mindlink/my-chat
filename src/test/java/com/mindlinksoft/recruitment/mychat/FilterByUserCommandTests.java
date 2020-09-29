package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.commands.FilterByKeywordCommand;
import com.mindlinksoft.recruitment.mychat.commands.FilterByUserCommand;
import com.mindlinksoft.recruitment.mychat.commands.IConversationExportCommand;

public class FilterByUserCommandTests {
	
	Random r;

	@Test
	public void doCommand_returnsFilteredConversation() {
		//set up conversation
	  	String name = "Test Conversation";

    	String testSender1 = UUID.randomUUID().toString();
    	String testSender2 = UUID.randomUUID().toString();
    	String testSender3 = UUID.randomUUID().toString();

    	String testMessage1 = UUID.randomUUID().toString();
    	String testMessage2 = UUID.randomUUID().toString();
    	String testMessage3 = UUID.randomUUID().toString();
    	String testMessage4 = UUID.randomUUID().toString();
    	

    	Instant testTimestamp1 = Instant.now().minusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);
    	Instant testTimestamp2 = testTimestamp1.plusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);
    	Instant testTimestamp3 = testTimestamp2.plusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);
    	
    	
    	List<Message> messages = new ArrayList<Message>();
    	messages.add(new Message(testTimestamp1, testSender1, testMessage1));
    	messages.add(new Message(testTimestamp2, testSender2, testMessage2));
    	messages.add(new Message(testTimestamp3, testSender3, testMessage3));
    	messages.add(new Message(testTimestamp3, testSender1, testMessage4));
    	
    	Conversation testConversation = new Conversation(name, messages);
    	
    	//set up to filter by testSender1
    	IConversationExportCommand filterByUserCommand = new FilterByUserCommand(testSender1);
    	
    	// test the method
    	Conversation c = filterByUserCommand.doCommand(testConversation);
    	 	
    	assertEquals(2, c.getMessages().size());
    	
    	 Message[] ms = new Message[c.getMessages().size()];
         c.getMessages().toArray(ms);

     	assertEquals(testMessage1, ms[0].getContent());	
    	assertEquals(testMessage4, ms[1].getContent());	
    	
	}
	
	@Test
	public void doCommand_nonexistantUser_returnsEmptyConversation() {
		//set up conversation
	  	String name = "Test Conversation";

    	String testSender1 = UUID.randomUUID().toString();
    	String testSender2 = UUID.randomUUID().toString();
    	
    	String nonexistantSender = UUID.randomUUID().toString();

    	String testMessage1 = UUID.randomUUID().toString();
    	String testMessage2 = UUID.randomUUID().toString();
    	

    	Instant testTimestamp1 = Instant.now().minusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);
    	Instant testTimestamp2 = testTimestamp1.plusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);
	
    	List<Message> messages = new ArrayList<Message>();
    	messages.add(new Message(testTimestamp1, testSender1, testMessage1));
    	messages.add(new Message(testTimestamp2, testSender2, testMessage2));
    	
    	Conversation testConversation = new Conversation(name, messages);
    	
    	//set up to filter by testSender1
    	IConversationExportCommand filterByUserCommand = new FilterByUserCommand(nonexistantSender);
    	
    	// test the method
    	Conversation c = filterByUserCommand.doCommand(testConversation);
    	 	
    	assertEquals(0, c.getMessages().size());
    	
	}
	
	/**
	 * Sets up before each test
	 */
	@Before
    public void setUp() {
        r = new Random();
    }
    

}
