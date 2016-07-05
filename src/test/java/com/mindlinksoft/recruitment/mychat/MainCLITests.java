package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.time.Instant;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainCLITests {

	final String INPUT = "chat.txt";
	final String OUTPUT = "cliTest.json";
	
	
	@Test
	public void testCLIMinimal() throws IOException {
		//run main with these arguments:
		MainCLI.main(new String[] { INPUT, OUTPUT });
		
		//read c from file:
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

		Gson g = builder.create();

		Conversation c = g.fromJson(new InputStreamReader(new FileInputStream(OUTPUT)), Conversation.class);
		Files.delete(FileSystems.getDefault().getPath(OUTPUT));
		
		//assert:
		assertNotNull(c);
		assertEquals("My Conversation", c.getName());

		assertEquals(7, c.messages.size());

		Message[] ms = new Message[c.messages.size()];
		c.messages.toArray(ms);

		assertEquals(Instant.ofEpochSecond(1448470901), ms[0].getTimestamp());
		assertEquals("bob",ms[0].getSenderId());
		assertEquals("Hello there!", ms[0].getContent());

		assertEquals(Instant.ofEpochSecond(1448470905), ms[1].getTimestamp());
		assertEquals("mike", ms[1].getSenderId());
		assertEquals("how are you?", ms[1].getContent());

		assertEquals(Instant.ofEpochSecond(1448470906), ms[2].getTimestamp());
		assertEquals("bob", ms[2].getSenderId());
		assertEquals("I'm good thanks, do you like pie?", ms[2].getContent());

		assertEquals(Instant.ofEpochSecond(1448470910), ms[3].getTimestamp());
		assertEquals("mike", ms[3].getSenderId());
		assertEquals("no, let me ask Angus...", ms[3].getContent());

		assertEquals(Instant.ofEpochSecond(1448470912), ms[4].getTimestamp());
		assertEquals("angus", ms[4].getSenderId());
		assertEquals("Hell yes! Are we buying some pie?", ms[4].getContent());

		assertEquals(Instant.ofEpochSecond(1448470914), ms[5].getTimestamp());
		assertEquals("bob", ms[5].getSenderId());
		assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].getContent());

		assertEquals(Instant.ofEpochSecond(1448470915), ms[6].getTimestamp());
		assertEquals("angus", ms[6].getSenderId());
		assertEquals("YES! I'm the head pie eater there...", ms[6].getContent());
		
	}
	
	@Test
	public void testCLIGeneral() throws IOException {
		//run main with these arguments:
		MainCLI.main(new String[] { INPUT, OUTPUT, "-blacklist" , 
				":", "yes", "PIE", "eaTER:" , "-u", "angus", "-r", "-o" });

		//read c from file:
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

		Gson g = builder.create();
		Conversation c = g.fromJson(new InputStreamReader(new FileInputStream(OUTPUT)), Conversation.class);
		Files.delete(FileSystems.getDefault().getPath(OUTPUT));

		//assert:
		assertNotNull(c);
		assertEquals("My Conversation", c.getName());

		assertEquals(2, c.messages.size());

		Message[] ms = new Message[c.messages.size()];
		c.messages.toArray(ms);
		
		assertEquals(Instant.ofEpochSecond(1448470912), ms[0].getTimestamp());
		assertEquals("'user" + "angus".hashCode() + "'", ms[0].getSenderId());
		assertEquals("Hell *redacted*! Are we buying some *redacted*?", ms[0].getContent());

		assertEquals(Instant.ofEpochSecond(1448470915), ms[1].getTimestamp());
		assertEquals("'user" + "angus".hashCode() + "'", ms[1].getSenderId());
		assertEquals("*redacted*! I'm the head *redacted* *redacted* there...", ms[1].getContent());
		
		assertNotNull(c.getReport());
		assertEquals(1, c.getReport().length);
		
		assertTrue("angus".compareTo(c.getReport()[0].username) == 0);
		assertTrue(2 == c.getReport()[0].score);
	}

}
