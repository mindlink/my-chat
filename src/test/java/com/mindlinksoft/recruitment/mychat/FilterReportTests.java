package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.time.Instant;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FilterReportTests {

	@Test
	public void testConstructor() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		FilterReport report = new FilterReport();
		
		Field field = FilterReport.class.getDeclaredField("report");

		field.setAccessible(true);
		assertNotNull(field.get(report));
	}
	
	@Test
	public void testCompareTo() {
		ReportEntry e1 = new ReportEntry("first", 10);
		ReportEntry e2 = new ReportEntry("second", 5);
		ReportEntry e3 = new ReportEntry("third", 3);
		ReportEntry e4 = new ReportEntry("fourth", 3);
		ReportEntry e5 = new ReportEntry("fifth", 1);
		
		assertTrue(e1.compareTo(e2) < 0);
		assertTrue(e1.compareTo(e3) < 0);
		assertTrue(e1.compareTo(e4) < 0);
		assertTrue(e1.compareTo(e5) < 0);

		assertTrue(e2.compareTo(e3) < 0);
		assertTrue(e2.compareTo(e4) < 0);
		assertTrue(e2.compareTo(e5) < 0);
		assertTrue(e2.compareTo(e1) > 0);

		assertTrue(e3.compareTo(e4) == 0);
		assertTrue(e3.compareTo(e3) == 0);

		assertTrue(e5.compareTo(e4) > 0);
		assertTrue(e5.compareTo(e3) > 0);
		assertTrue(e5.compareTo(e2) > 0);
		assertTrue(e5.compareTo(e1) > 0);
	}
	
	@Test
	public void testApply() throws IOException {
		ConversationReader reader = new ConversationReader(new FileReader("chatReportTest.txt"));
		Conversation c = reader.readConversation();
		reader.close();
		
		assertNotNull(c);
		assertTrue(15 == c.messages.size());
		
		new FilterReport().apply(c);
		
		assertNotNull(c.getReport());
		assertTrue(5 == c.getReport().length);
		
		assertTrue("first".compareTo(c.getReport()[0].username) == 0);
		assertTrue(5 == c.getReport()[0].score);
		assertTrue("second".compareTo(c.getReport()[1].username) == 0);
		assertTrue(4 == c.getReport()[1].score);
		assertTrue("third".compareTo(c.getReport()[2].username) == 0);
		assertTrue(3 == c.getReport()[2].score);
		assertTrue("fourth".compareTo(c.getReport()[3].username) == 0);
		assertTrue(2 == c.getReport()[3].score);
		assertTrue("fifth".compareTo(c.getReport()[4].username) == 0);
		assertTrue(1 == c.getReport()[4].score);
	}
	
	@Test
	public void testApplyAndRead() throws IOException {
		//read:
		ConversationReader reader = new ConversationReader(new FileReader("chatReportTest.txt"));
		Conversation c = reader.readConversation();
		reader.close();
		
		//filter:
		new FilterReport().apply(c);
		
		//write:
		ConversationWriter writer = new ConversationWriter(new FileWriter("chatReportTest.json"));
		writer.writeConversation(c);
		writer.close();
		
		//read again:
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
		Gson g = builder.create();
		c = g.fromJson(new InputStreamReader(new FileInputStream("chatReportTest.json")), Conversation.class);
		
		//verify:
		assertNotNull(c);
		assertTrue(15 == c.messages.size());
		
		assertNotNull(c.getReport());
		assertTrue(5 == c.getReport().length);
		
		assertTrue("first".compareTo(c.getReport()[0].username) == 0);
		assertTrue(5 == c.getReport()[0].score);
		assertTrue("second".compareTo(c.getReport()[1].username) == 0);
		assertTrue(4 == c.getReport()[1].score);
		assertTrue("third".compareTo(c.getReport()[2].username) == 0);
		assertTrue(3 == c.getReport()[2].score);
		assertTrue("fourth".compareTo(c.getReport()[3].username) == 0);
		assertTrue(2 == c.getReport()[3].score);
		assertTrue("fifth".compareTo(c.getReport()[4].username) == 0);
		assertTrue(1 == c.getReport()[4].score);
	}

}
