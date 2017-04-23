package com.mindlinksoft.recruitment.mychat.reports;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import com.mindlinksoft.recruitment.mychat.messages.InvalidMessageException;
import com.mindlinksoft.recruitment.mychat.messages.Message;


public class UserActivityReportTest {
	private static UserActivityReport userActivityReport;

	@BeforeClass
	public static void Setup()
	{
		userActivityReport = new UserActivityReport();
	}
	@Test
	public void generateReport_Conversation_ReportIsGenerated() throws NumberFormatException, InvalidMessageException
	{
        List<Message> messages = new ArrayList<Message>();
		messages.add(new Message(Instant.ofEpochSecond(1448470905),"bob","how are you?"));
		messages.add(new Message(Instant.ofEpochSecond(1448470905),"john","I'm good thanks"));
		messages.add(new Message(Instant.ofEpochSecond(1448470905),"john","how are you?"));
		messages.add(new Message(Instant.ofEpochSecond(1448470905),"bob","I'm good thanks"));
		messages.add(new Message(Instant.ofEpochSecond(1448470905),"john","good"));
		
		Conversation conversation = new Conversation("TestConversation", messages);
		
		userActivityReport.generateReport(conversation);
		
		Map<String, Integer> usersActivityReport = conversation.getUsersActivityReport();

		Assert.assertEquals(2,usersActivityReport.size());
		
		Assert.assertEquals(Integer.valueOf(3),usersActivityReport.values().toArray()[0]);
		Assert.assertEquals(Integer.valueOf(3),usersActivityReport.get("john"));
		
		Assert.assertEquals(Integer.valueOf(2),usersActivityReport.values().toArray()[1]);
		Assert.assertEquals(Integer.valueOf(2),usersActivityReport.get("bob"));

		



	}
}
