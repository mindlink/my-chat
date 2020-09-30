package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.commands.ExportCommandException;
import com.mindlinksoft.recruitment.mychat.commands.IConversationExportCommand;
import com.mindlinksoft.recruitment.mychat.commands.ObfuscateUsersCommand;
import com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import com.mindlinksoft.recruitment.mychat.conversation.Message;

public class ObfuscateUsersCommandTests {
	
	Random r;
	
	@Test
	public void doCommand_obfuscatesIds() throws ExportCommandException {
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

    	IConversationExportCommand obfuscateUsersCommand = new ObfuscateUsersCommand();
    	
    	// test the method
    	Conversation c = obfuscateUsersCommand.doCommand(testConversation);
    	
    	assertEquals(4, c.getMessages().size());
    	
   	 	Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);
        
        for(Message msg : ms) {
        	boolean containsSenderId = msg.getSenderId().equals(testSender1) 
        						|| msg.getSenderId().equals(testSender2)
        						|| msg.getSenderId().equals(testSender3);
        	assertTrue(!containsSenderId); // check no sender Ids are as initially set
        }
    	 	
	}

	@Test
	public void doCommand_retainsMessageUserRelationship() throws ExportCommandException {
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
    	IConversationExportCommand obfuscateUsersCommand = new ObfuscateUsersCommand();
    	
    	// test the method
    	Conversation c = obfuscateUsersCommand.doCommand(testConversation);
    	
    	assertEquals(4, c.getMessages().size());
    	
   	 	Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);
        
        // check messages 1 and 4 have the same obfuscated senderId
        assertTrue(ms[0].getSenderId().equals(ms[3].getSenderId()));

        // check message 2 and 3 have different senderIds to message 1
        assertTrue(!ms[0].getSenderId().equals(ms[1].getSenderId()));
        assertTrue(!ms[0].getSenderId().equals(ms[2].getSenderId()));
        assertTrue(!ms[1].getSenderId().equals(ms[2].getSenderId()));
    	 	
	}
	
	@Test(expected = ExportCommandException.class)
	public void doCommand_unknownAlgorithm_throwsExportCommandException() throws ExportCommandException, IllegalArgumentException, IllegalAccessException {
		String name = "Test Conversation";

    	String testSender = UUID.randomUUID().toString();
    

    	String testMessage = UUID.randomUUID().toString();
    	

    	Instant testTimestamp = Instant.now().minusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);

    	List<Message> messages = new ArrayList<Message>();
    	messages.add(new Message(testTimestamp, testSender, testMessage));

    	Conversation testConversation = new Conversation(name, messages);
		
		// set up private field access using reflection
		IConversationExportCommand obfUsersCmd = new ObfuscateUsersCommand();
	    Field algorithmField = obfUsersCmd.getClass().getDeclaredFields()[0];
	    algorithmField.setAccessible(true);
	    algorithmField.set(obfUsersCmd, "badOrUnknownAlgorithm");
	    
	    // should throw ExportCommandException
	    obfUsersCmd.doCommand(testConversation);
	    
	    
	}
	
	
	/**
	 * Sets up before each test
	 */
	@Before
    public void setUp() {
        r = new Random();
    }

}
