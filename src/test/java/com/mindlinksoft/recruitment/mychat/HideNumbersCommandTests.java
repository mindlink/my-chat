package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.commands.ExportCommandException;
import com.mindlinksoft.recruitment.mychat.commands.HideNumbersCommand;
import com.mindlinksoft.recruitment.mychat.commands.IConversationExportCommand;
import com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import com.mindlinksoft.recruitment.mychat.conversation.Message;

public class HideNumbersCommandTests {
	
	Random r; 
	
	/**
	 * valid phone numbers to test
	 */
	private String[] validPhoneNumbers = new String[] {
			"08888-123-123",
			"+81(0)8888 123 123",
			"8888 123 123",
			"+448888123123"};
	
	/**
	 * valid card numbers (format only, not checking card company) to test
	 */
	private String[] validCardNumbers = new String[] {
			"1010 9999 1234 9876",
			"1010-9999-1234-9876",
			"1010999912349876",
			};

	/**
	 * the string to replace numbers with
	 */
	private final String redactedStr = "*redacted*";

	@Test
	public void doCommand_hidesPhoneNumbers() throws ExportCommandException {
	  	String name = "Test Conversation";

    	String testSender1 = "testsender1";
    	String testSender2 =  "testsender2";
    

    	String testMessage1 =  "testmessage1";
    	String testMessage2 =  "testmessage2";
    	
    	Instant testTimestamp1 = Instant.now().minusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);
    	Instant testTimestamp2 = testTimestamp1.plusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS); 	
    	
    	List<Message> messages = new ArrayList<Message>();
    	messages.add(new Message(testTimestamp1, testSender1, validPhoneNumbers[0] + " " + testMessage1 + " " + validPhoneNumbers[1]));
    	messages.add(new Message(testTimestamp2, testSender2, validPhoneNumbers[2] + " " + testMessage2 + " " + validPhoneNumbers[3]));

    	Conversation testConversation = new Conversation(name, messages);
    	
    	//set up to filter by testSender1
    	IConversationExportCommand hideNumsCommand = new HideNumbersCommand();
    	
    	// test the method
    	Conversation c = hideNumsCommand.doCommand(testConversation);
    	 	
    	assertEquals(2, c.getMessages().size());
    	
    	 Message[] ms = new Message[c.getMessages().size()];
         c.getMessages().toArray(ms);

     	assertEquals(redactedStr + " " + testMessage1 + " " + redactedStr, ms[0].getContent());	
    	assertEquals(redactedStr + " " + testMessage2 + " " + redactedStr, ms[1].getContent());
	}
	
	@Test
	public void doCommand_invalidNumber_doesNotHideNumber() throws ExportCommandException {
			
			
	  	String name = "Test Conversation";

    	String testSender1 = "testsender1";
    	String testSender2 =  "testsender2";
    

    	String testMessage1 =  "testmessage1";
    	String testMessage2 =  "testmessage2";
    	
    	String invalidCard = "1234 12345 12345 12345";
    	String invalidPhone = "07999 9999 99";
    	

    	Instant testTimestamp1 = Instant.now().minusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);
    	Instant testTimestamp2 = testTimestamp1.plusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS); 	
    	
    	List<Message> messages = new ArrayList<Message>();
    	messages.add(new Message(testTimestamp1, testSender1, invalidCard + " " + testMessage1));
    	messages.add(new Message(testTimestamp2, testSender2, invalidPhone + " " + testMessage2));

    	Conversation testConversation = new Conversation(name, messages);
    	
    	//set up to filter by testSender1
    	IConversationExportCommand hideNumsCommand = new HideNumbersCommand();
    	
    	// test the method
    	Conversation c = hideNumsCommand.doCommand(testConversation);
    	 	
    	assertEquals(2, c.getMessages().size());
    	
    	Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

         // there is a chance this may ocassionally fail if the random GUID matches
         //  the phone number pattern
     	assertEquals(invalidCard + " " + testMessage1, ms[0].getContent());	
    	assertEquals(invalidPhone + " " + testMessage2, ms[1].getContent());
    	assertTrue(!ms[0].getContent().contains(redactedStr));
    	assertTrue(!ms[1].getContent().contains(redactedStr));
	}
	
	
	@Test
	public void doCommand_hidesCreditCardNumbers() throws ExportCommandException {
	  	String name = "Test Conversation";

    	String testSender1 = "testsender1";
    	String testSender2 =  "testsender2";
    

    	String testMessage1 =  "testmessage1";
    	String testMessage2 =  "testmessage2";
    	
    	

    	Instant testTimestamp1 = Instant.now().minusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);
    	Instant testTimestamp2 = testTimestamp1.plusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS); 	
    	
    	List<Message> messages = new ArrayList<Message>();
    	messages.add(new Message(testTimestamp1, testSender1, validCardNumbers[0] + " " + testMessage1 + " " + validCardNumbers[1]));
    	messages.add(new Message(testTimestamp2, testSender2, validCardNumbers[2] + " " + testMessage2 + " " + validCardNumbers[0]));

    	Conversation testConversation = new Conversation(name, messages);
    	
    	//set up to filter by testSender1
    	IConversationExportCommand hideNumsCommand = new HideNumbersCommand();
    	
    	// test the method
    	Conversation c = hideNumsCommand.doCommand(testConversation);
    	 	
    	assertEquals(2, c.getMessages().size());
    	
    	Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

     	assertEquals(redactedStr + " " + testMessage1 + " " + redactedStr, ms[0].getContent());	
    	assertEquals(redactedStr + " " + testMessage2 + " " + redactedStr, ms[1].getContent());
	}
	
	/**
	 * Sets up before each test
	 */
	@Before
    public void setUp() {
        r = new Random();
    }

}
