package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.commands.ExportCommandException;
import com.mindlinksoft.recruitment.mychat.commands.IConversationExportCommand;
import com.mindlinksoft.recruitment.mychat.commands.ReportCommand;
import com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import com.mindlinksoft.recruitment.mychat.conversation.Message;
import com.mindlinksoft.recruitment.mychat.conversation.UserActivity;

public class ReportCommandTests {

	@Test
	public void noCommand_ConversationReportIsNull() {
		String name = "Test Conversation";

    	String testSender1 = UUID.randomUUID().toString();
    	String testSender2 = UUID.randomUUID().toString();
    	String testSender3 = UUID.randomUUID().toString();

    	String testMessage1 = UUID.randomUUID().toString();
    	String testMessage2 = UUID.randomUUID().toString();
    	String testMessage3 = UUID.randomUUID().toString();
    	String testMessage4 = UUID.randomUUID().toString();
    	

    	Instant testTimestamp1 = Instant.now();
    	Instant testTimestamp2 = testTimestamp1.plusSeconds(10);
    	Instant testTimestamp3 = testTimestamp2.plusSeconds(10);
    	
    	
    	List<Message> messages = new ArrayList<Message>();
    	messages.add(new Message(testTimestamp1, testSender1, testMessage1));
    	messages.add(new Message(testTimestamp2, testSender2, testMessage2));
    	messages.add(new Message(testTimestamp3, testSender3, testMessage3));
    	messages.add(new Message(testTimestamp3, testSender1, testMessage4));
      	Conversation testConversation = new Conversation(name, messages);	
      	
		assertNull(testConversation.getReport());
	}
	
	@Test
	public void doCommand_ConversationContainsReport() throws ExportCommandException {
		String name = "Test Conversation";

    	String testSender1 = UUID.randomUUID().toString();
    	String testSender2 = UUID.randomUUID().toString();
    	String testSender3 = UUID.randomUUID().toString();

    	String testMessage1 = UUID.randomUUID().toString();
    	String testMessage2 = UUID.randomUUID().toString();
    	String testMessage3 = UUID.randomUUID().toString();
    	String testMessage4 = UUID.randomUUID().toString();
    	

    	Instant testTimestamp1 = Instant.now();
    	Instant testTimestamp2 = testTimestamp1.plusSeconds(10);
    	Instant testTimestamp3 = testTimestamp2.plusSeconds(10);
    	
    	
    	List<Message> messages = new ArrayList<Message>();
    	messages.add(new Message(testTimestamp1, testSender1, testMessage1));
    	messages.add(new Message(testTimestamp2, testSender2, testMessage2));
    	messages.add(new Message(testTimestamp3, testSender3, testMessage3));
    	messages.add(new Message(testTimestamp3, testSender1, testMessage4));
      	Conversation testConversation = new Conversation(name, messages);
      	
    	IConversationExportCommand reportCommand = new ReportCommand();
    	Conversation c = reportCommand.doCommand(testConversation);
		
		List<UserActivity> report = c.getReport();
		assertNotNull(report);

		// check the report has one entry per sender
		assertEquals(3, report.size()); 
		// check testSender1 is first in the list, as they sent the most messages
		assertEquals(testSender1, report.get(0).getUsername()); 
		assertEquals(1, report.get(1).getMessageCount());
		assertEquals(1, report.get(2).getMessageCount());
	}

}
