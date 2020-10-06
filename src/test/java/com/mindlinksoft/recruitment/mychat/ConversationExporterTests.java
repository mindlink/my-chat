package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {
    /**
     * Tests that exporting a conversation will export the conversation correctly.
     * @throws Exception When something bad happens.
     */
    @Test
    public void testExportingConversationExportsConversation() throws Exception {
		ConversationExporterConfiguration conf = new ConversationExporterConfiguration("chat.txt", "chat.json");

        Conversation c = getConversationFromFile(conf);

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
    }

	/**
     * Tests that exporting a conversation will export the conversation correctly and include report.
     * @throws Exception When something bad happens.
     */
    @Test
    public void testExportingConversationExportsConversationWithReport() throws Exception {
		ConversationExporterConfiguration conf = new ConversationExporterConfiguration("chat.txt", "chat.json");
		conf.report = true;
		
        Conversation c = getConversationFromFile(conf);
		
        assertEquals("My Conversation", c.name);

        assertEquals(7, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);
		
		UserReport[] us = new UserReport[c.activity.size()];
        c.activity.toArray(us);
		assertEquals(3, c.activity.size());
		
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
		
		assertEquals("bob", us[0].sender);
		assertEquals(3, us[0].count);
		
		assertEquals("angus", us[1].sender);
		assertEquals(2, us[1].count);
		
		assertEquals("mike", us[2].sender);
		assertEquals(2, us[1].count);
		
    }
	/**
     * Tests that filtering by user only shows messages from that user
     * @throws Exception When something bad happens.
     */
	@Test
    public void testExportConversationWithUserFilter() throws Exception {
		String[] users = {"bob", "mike", "angus"};
		
		for(String user : users){
			ConversationExporterConfiguration conf = new ConversationExporterConfiguration("chat.txt", "chat.json");
			conf.filter_user = user;

			Conversation c = getConversationFromFile(conf);

			assertEquals("My Conversation", c.name);


			Message[] ms = new Message[c.messages.size()];
			c.messages.toArray(ms);
			
			for(Message m : ms){
				assertEquals(user, m.senderId);
			}
		}
    }


	/**
     * Tests that filtering by word only shows messages containing that word
     * @throws Exception When error occurs.
     */
	@Test
    public void testExportConversationWithWordFilter() throws Exception {

		String[] word_filter = {"pie", "hello", "society"};

		//Check that all the "pie" messages are correct
		ConversationExporterConfiguration conf = new ConversationExporterConfiguration("chat.txt", "chat.json");
		conf.filter_word = word_filter[0];
		
		Conversation c = getConversationFromFile(conf);

		assertEquals("My Conversation", c.name);


		Message[] ms = new Message[c.messages.size()];
		c.messages.toArray(ms);
		
		assertEquals(4, c.messages.size());
		
		assertEquals(Instant.ofEpochSecond(1448470906), ms[0].timestamp);
        assertEquals("bob", ms[0].senderId);
        assertEquals("I'm good thanks, do you like pie?", ms[0].content);

        assertEquals(Instant.ofEpochSecond(1448470912), ms[1].timestamp);
        assertEquals("angus", ms[1].senderId);
        assertEquals("Hell yes! Are we buying some pie?", ms[1].content);

        assertEquals(Instant.ofEpochSecond(1448470914), ms[2].timestamp);
        assertEquals("bob", ms[2].senderId);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].content);

        assertEquals(Instant.ofEpochSecond(1448470915), ms[3].timestamp);
        assertEquals("angus", ms[3].senderId);
        assertEquals("YES! I'm the head pie eater there...", ms[3].content);
		
		
		//Check that all the "hello" messages are correct
		conf = new ConversationExporterConfiguration("chat.txt", "chat.json");
		conf.filter_word = word_filter[1];
		
		c = getConversationFromFile(conf);

		assertEquals("My Conversation", c.name);


		ms = new Message[c.messages.size()];
		c.messages.toArray(ms);
		
		assertEquals(1, c.messages.size());
		
		assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);
        assertEquals("bob", ms[0].senderId);
        assertEquals("Hello there!", ms[0].content);
		
		//Check that all the "society" messages are correct
		conf = new ConversationExporterConfiguration("chat.txt", "chat.json");
		conf.filter_word = word_filter[2];
		
		c = getConversationFromFile(conf);

		assertEquals("My Conversation", c.name);


		ms = new Message[c.messages.size()];
		c.messages.toArray(ms);
		
		assertEquals(1, c.messages.size());
		
		assertEquals(Instant.ofEpochSecond(1448470914), ms[0].timestamp);
        assertEquals("bob", ms[0].senderId);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[0].content);
		
    }
	
	/**
     * Tests that method for retrieving command line options works
     */
	@Test
	public void testgetOptions(){
		ConversationExporter exporter = new ConversationExporter();
		ConversationExporterConfiguration conf = new ConversationExporterConfiguration("chat.txt", "chat.json");
		assertEquals("", exporter.getOptions(conf));
		
		conf.filter_user = "bob";
		assertEquals("User filter : bob\n", exporter.getOptions(conf));
		
		conf.filter_word = "cat";
		assertEquals("User filter : bob\nWord filter : cat\n", exporter.getOptions(conf));
		
		conf.report = true;
		assertEquals("User filter : bob\nWord filter : cat\nAdding report\n", exporter.getOptions(conf));
		
		String[] s = {"hat"};
		conf.blacklist = s;
		assertEquals("User filter : bob\nWord filter : cat\nAdding report\nBlacklisted words : hat\n", exporter.getOptions(conf));
		
	}
	
	/**
     * Tests that blacklisted words get redacted
     */
	@Test
	public void testBlacklist(){
		ConversationExporter exporter = new ConversationExporter();
		
		String s = "I like pie";
		String s2 = "I do not like pie";
		
		List<String> l = new ArrayList<String>(Arrays.asList(s.split(" ")));
		String[] blacklist = {"like"};
		List<String> redacted = exporter.blacklistMessages(l, blacklist);
		String re = "";
		for(String word : redacted){
			re += word + " ";
		}
		re = re.trim();
		assertEquals("I *redacted* pie", re);
		
		l = new ArrayList<String>(Arrays.asList(s2.split(" ")));
		String[] blacklist2 = {"like", "do"};
		redacted = exporter.blacklistMessages(l, blacklist2);
		re = "";
		for(String word : redacted){
			re += word + " ";
		}
		re = re.trim();
		assertEquals("I *redacted* not *redacted* pie", re);
		
	}
	
	/**
     * Tests that reports can be generated
     */
	@Test
	public void testReportGeneration(){
		ConversationExporter exporter = new ConversationExporter();
		Map<String, Integer> msgcount = new HashMap<String, Integer>();
		msgcount.put("bob", 2);
		List<UserReport> li = exporter.getReports(msgcount);
		assertEquals("bob", li.get(0).sender);
		assertEquals(2, li.get(0).count);
	}
	
	/**
     * Helper class to import conversation from file for testing
	 * @param conf contains command line arguments
	 * @return Conversation with messages
     */
	public Conversation getConversationFromFile(ConversationExporterConfiguration conf) throws Exception{
		ConversationExporter exporter = new ConversationExporter();
		exporter.exportConversation(conf);
		
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

		Gson g = builder.create();

		Conversation c = g.fromJson(new InputStreamReader(new FileInputStream(conf.outputFilePath)), Conversation.class);
		return c;
	}
	
    class InstantDeserializer implements JsonDeserializer<Instant> {

        @Override
        public Instant deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (!jsonElement.isJsonPrimitive()) {
                throw new JsonParseException("Expected instant represented as JSON number, but no primitive found.");
            }

            JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();

            if (!jsonPrimitive.isNumber()) {
                throw new JsonParseException("Expected instant represented as JSON number, but different primitive found.");
            }

            return Instant.ofEpochSecond(jsonPrimitive.getAsLong());
        }
    }
}
