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

import com.google.gson.GsonBuilder;
import com.mindlinksoft.recruitment.mychat.ConversationExporterTests.InstantDeserializer;
import com.mindlinksoft.recruitment.mychat.commands.ExportCommandException;
import com.mindlinksoft.recruitment.mychat.commands.FilterByKeywordCommand;
import com.mindlinksoft.recruitment.mychat.commands.IConversationExportCommand;

public class FilterByKeywordCommandTests {
	
	Random r;

	@Test
	public void doCommand_returnsFilteredConversation() throws ExportCommandException {
		//set up conversation
	  	String name = "Test Conversation";
	  	
    	String testSender = UUID.randomUUID().toString();

    	String testMessage1 = UUID.randomUUID().toString();
    	String testMessage2 = UUID.randomUUID().toString();
    	String testMessage3 = UUID.randomUUID().toString();

    	Instant testTimestamp1 = Instant.now().minusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);
    	Instant testTimestamp2 = testTimestamp1.plusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);
    	Instant testTimestamp3 = testTimestamp2.plusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);
    	
    	
    	List<Message> messages = new ArrayList<Message>();
    	messages.add(new Message(testTimestamp1, testSender, testMessage1));
    	messages.add(new Message(testTimestamp2, testSender, testMessage2));
    	messages.add(new Message(testTimestamp3, testSender, testMessage3));
    	
    	Conversation testConversation = new Conversation(name, messages);
    	
    	//set up keyword to filter by
    	String keyword = testMessage2.substring(0, 10); // take first 10 characters of message2 as keyword
    	
    	// test the method
    	IConversationExportCommand filterByKeywordCommand = new FilterByKeywordCommand(keyword);
    	Conversation c = filterByKeywordCommand.doCommand(testConversation);
    	
    	
    	assertEquals(1, c.getMessages().size());
    	
    	 Message[] ms = new Message[c.getMessages().size()];
         c.getMessages().toArray(ms);
         
    	assertEquals(testMessage2, ms[0].getContent());
	}
	@Test
	public void doCommand_nonexistantKeyword_returnsNoMessages() throws ExportCommandException {
		//set up conversation
	  	String name = "Test Conversation";
	  	
    	String testSender = UUID.randomUUID().toString();

    	String testMessage1 = UUID.randomUUID().toString();
    	String testMessage2 = UUID.randomUUID().toString();
    	String testMessage3 = UUID.randomUUID().toString();

    	Instant testTimestamp1 = Instant.now().minusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);
    	Instant testTimestamp2 = testTimestamp1.plusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);
    	Instant testTimestamp3 = testTimestamp2.plusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);
    	
    	
    	List<Message> messages = new ArrayList<Message>();
    	messages.add(new Message(testTimestamp1, testSender, testMessage1));
    	messages.add(new Message(testTimestamp2, testSender, testMessage2));
    	messages.add(new Message(testTimestamp3, testSender, testMessage3));
    	
    	Conversation testConversation = new Conversation(name, messages);
    	
    	//set up keyword to filter by
    	String keyword = "nonexistantKeyword";
    	
    	// test the method
    	IConversationExportCommand filterByKeywordCommand = new FilterByKeywordCommand(keyword);
    	Conversation c = filterByKeywordCommand.doCommand(testConversation);
    	
    	
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
