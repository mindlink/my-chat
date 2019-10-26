package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.time.Instant;

import org.junit.Test;

/**
 * Tests for {@link ActivityReport}.
 */
public class ActivityReportTests {
	/**
	 * Tests that a report will be correctly generated for the conversation.
	 * 
	 * @throws IOException Failed to read in or write file.
	 */
	@Test
	public void testActivityReportCreatesCorrectReport() throws IOException {
		ConversationExporter exporter = new ConversationExporter();

		String[] option = { "-report", "" };
		String inputFilePath = "chat.txt";
		String outputFilePath = "chat_report.json";
		exporter.exportConversation(inputFilePath, outputFilePath, option);

		Conversation c = InstantDeserializer.createJsonDeserialized(outputFilePath);
		assertEquals("My Conversation", c.name);

		assertEquals(7, c.messages.size());

		Message[] ms = new Message[c.messages.size()];
		c.messages.toArray(ms);

		assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);
		assertEquals("bob", ms[0].senderId);
		assertEquals("Hello there!", ms[0].content);

		assertEquals(Instant.ofEpochSecond(1448470905), ms[1].timestamp);
		assertEquals("mike", ms[1].senderId);
		assertEquals("how are you?", ms[1].content);

		assertEquals(Instant.ofEpochSecond(1448470906), ms[2].timestamp);
		assertEquals("bob", ms[2].senderId);
		assertEquals("I'm good thanks, do you like pie?", ms[2].content);

		assertEquals(Instant.ofEpochSecond(1448470910), ms[3].timestamp);
		assertEquals("mike", ms[3].senderId);
		assertEquals("no, let me ask Angus...", ms[3].content);

		assertEquals(Instant.ofEpochSecond(1448470912), ms[4].timestamp);
		assertEquals("angus", ms[4].senderId);
		assertEquals("Hell yes! Are we buying some pie?", ms[4].content);

		assertEquals(Instant.ofEpochSecond(1448470914), ms[5].timestamp);
		assertEquals("bob", ms[5].senderId);
		assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].content);

		assertEquals(Instant.ofEpochSecond(1448470915), ms[6].timestamp);
		assertEquals("angus", ms[6].senderId);
		assertEquals("YES! I'm the head pie eater there...", ms[6].content);

		ActivityReport[] rp = new ActivityReport[c.report.size()];
		c.report.toArray(rp);

		assertEquals("bob", rp[0].user);
		assertEquals(3, rp[0].messageCount);

		assertEquals("mike", rp[1].user);
		assertEquals(2, rp[1].messageCount);

		assertEquals("angus", rp[2].user);
		assertEquals(2, rp[2].messageCount);
	}
}
