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

import com.mindlinksoft.recruitment.mychat.commands.ExportCommandException;
import com.mindlinksoft.recruitment.mychat.commands.HideBlacklistWordsCommand;
import com.mindlinksoft.recruitment.mychat.commands.IConversationExportCommand;
import com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import com.mindlinksoft.recruitment.mychat.conversation.Message;

public class HideBlacklistWordsCommandTests {

	Random r;
	String redactedStr = "*redacted*";

	@Test
	public void doCommand_oneWord_returnsFilteredConversation() throws ExportCommandException {
		//set up conversation
	  	String name = "Test Conversation";

    	String testSender1 = UUID.randomUUID().toString();
    	String testSender2 = UUID.randomUUID().toString();
    	
    	String wordToHide = "wordToHide";

    	String testMessage1 = UUID.randomUUID().toString();
    	String testMessage2 = UUID.randomUUID().toString();
    	

    	Instant testTimestamp1 = Instant.now().minusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);
    	Instant testTimestamp2 = testTimestamp1.plusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS); 	
    	
    	List<Message> messages = new ArrayList<Message>();
    	messages.add(new Message(testTimestamp1, testSender1, testMessage1 + wordToHide));
    	messages.add(new Message(testTimestamp2, testSender2, wordToHide + testMessage2));

    	Conversation testConversation = new Conversation(name, messages);
    	
    	//set up to filter by testSender1
    	IConversationExportCommand hideWordCommand = new HideBlacklistWordsCommand(new String[] {wordToHide});
    	
    	// test the method
    	Conversation c = hideWordCommand.doCommand(testConversation);
    	 	
    	assertEquals(2, c.getMessages().size());
    	
    	 Message[] ms = new Message[c.getMessages().size()];
         c.getMessages().toArray(ms);

     	assertEquals(testMessage1 + redactedStr, ms[0].getContent());	
    	assertEquals(redactedStr + testMessage2, ms[1].getContent());		
	}
	
	@Test
	public void doCommand_multipleWords_returnsFilteredConversation() throws ExportCommandException {
		//set up conversation
	  	String name = "Test Conversation";

    	String testSender1 = UUID.randomUUID().toString();
    	String testSender2 = UUID.randomUUID().toString();

    	String wordToHide1 = "wordToHide1";
    	String wordToHide2 = "wordToHide2";
    	String wordToHide3 = "wordToHide3";
    	String wordToHide4 = "wordToHide4";

    	String testMessage1 = UUID.randomUUID().toString();
    	String testMessage2 = UUID.randomUUID().toString();
    	

    	Instant testTimestamp1 = Instant.now().minusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);
    	Instant testTimestamp2 = testTimestamp1.plusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS); 	
    	
    	List<Message> messages = new ArrayList<Message>();
    	messages.add(new Message(testTimestamp1, testSender1, wordToHide1 + testMessage1 + wordToHide2));
    	messages.add(new Message(testTimestamp2, testSender2, wordToHide3 + testMessage2 + wordToHide4));

    	Conversation testConversation = new Conversation(name, messages);
    	
    	//set up to filter by testSender1
    	IConversationExportCommand hideWordCommand = new HideBlacklistWordsCommand(
    			new String[] {wordToHide1, wordToHide2, wordToHide3, wordToHide4});
    	
    	// test the method
    	Conversation c = hideWordCommand.doCommand(testConversation);
    	 	
    	assertEquals(2, c.getMessages().size());
    	
    	 Message[] ms = new Message[c.getMessages().size()];
         c.getMessages().toArray(ms);

     	assertEquals(redactedStr + testMessage1 + redactedStr, ms[0].getContent());	
    	assertEquals(redactedStr + testMessage2 + redactedStr, ms[1].getContent());		
	}
	

	@Test
	public void doCommand_nonexistantWord_returnsSameConversation() throws ExportCommandException {
		//set up conversation
	  	String name = "Test Conversation";

    	String testSender1 = UUID.randomUUID().toString();
    	String testSender2 = UUID.randomUUID().toString();
    	
    	String wordToHide = "wordToHide";


    	String testMessage1 = UUID.randomUUID().toString();
    	String testMessage2 = UUID.randomUUID().toString();
    	

    	Instant testTimestamp1 = Instant.now().minusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);
    	Instant testTimestamp2 = testTimestamp1.plusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS); 	
    	
    	List<Message> messages = new ArrayList<Message>();
    	messages.add(new Message(testTimestamp1, testSender1, testMessage1));
    	messages.add(new Message(testTimestamp2, testSender2, testMessage2));

    	Conversation testConversation = new Conversation(name, messages);
    	
    	//set up to filter by testSender1
    	IConversationExportCommand hideWordCommand = new HideBlacklistWordsCommand(
    			new String[] {wordToHide});
    	
    	// test the method
    	Conversation c = hideWordCommand.doCommand(testConversation);
    	 	
    	assertEquals(2, c.getMessages().size());
    	
    	 Message[] ms = new Message[c.getMessages().size()];
         c.getMessages().toArray(ms);

     	assertEquals(testMessage1, ms[0].getContent());	
    	assertEquals(testMessage2, ms[1].getContent());		
	}
	
	/**
	 * Sets up before each test
	 */
	@Before
    public void setUp() {
        r = new Random();
    }
}
