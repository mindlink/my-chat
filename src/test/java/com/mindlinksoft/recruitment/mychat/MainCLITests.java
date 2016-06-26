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

	/**
	 * 2 valid path arguments
	 * */
	@Test
	public void testCLI() throws IOException {
		//run main with these arguments:
		MainCLI.main(new String[] { "chat.txt", "chat.json" });
		
		//read c from file:
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

		Gson g = builder.create();

		Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
		Files.delete(FileSystems.getDefault().getPath("chat.json"));
		
		//assert:
		assertNotNull(c);
		assertEquals("My Conversation", c.name);

		assertEquals(7, c.messages.size());

		Message[] ms = new Message[c.messages.size()];
		c.messages.toArray(ms);

		assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);
		assertEquals("bob",ms[0].senderId);
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
		
		//run main with these arguments:
		MainCLI.main(new String[] { "chat.txt", "chat.json", "-blacklist" , 
				"'yes", "PIE", "eaTER'" , "-u", "angus" });

		//read c from file:
		c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
		Files.delete(FileSystems.getDefault().getPath("chat.json"));

		//assert:
		assertNotNull(c);
		assertEquals("My Conversation", c.name);

		assertEquals(2, c.messages.size());

		ms = new Message[c.messages.size()];
		c.messages.toArray(ms);
		
		assertEquals(Instant.ofEpochSecond(1448470912), ms[0].timestamp);
		assertEquals("angus", ms[0].senderId);
		assertEquals("Hell *redacted*! Are we buying some *redacted*?", ms[0].content);

		assertEquals(Instant.ofEpochSecond(1448470915), ms[1].timestamp);
		assertEquals("angus", ms[1].senderId);
		assertEquals("*redacted*! I'm the head *redacted* *redacted* there...", ms[1].content);
	}

	/**
	 * No arguments provided, or too few
	 * */
	@Test()
	public void testCLINoArguments () {
		MainCLI.main(null);
	}
}
