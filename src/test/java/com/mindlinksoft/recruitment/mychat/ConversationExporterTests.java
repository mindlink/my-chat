package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

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
     * Tests that it will correctly generate a report and add it to the conversation.
     * @throws Exception
     */
    @Test
    public void testGeneratingReport() throws Exception {
    	ConversationExporter exporter = new ConversationExporter();
    	
    	Message myMessage1 = new Message(Instant.ofEpochSecond(1448470901), "greg", "hello");
    	Message myMessage2 = new Message(Instant.ofEpochSecond(1448470905), "dave", "world");
    	Message myMessage3 = new Message(Instant.ofEpochSecond(1448470912), "dave", "longer message with hello in it");
    	Message myMessage4 = new Message(Instant.ofEpochSecond(1448470919), "mark", "new sentance");
    	
    	List<Message> messages = new ArrayList<Message>();
    	messages.add(myMessage1);
    	messages.add(myMessage2);
    	messages.add(myMessage3);
    	messages.add(myMessage4);

    	Conversation conversation = new Conversation("MyConvo", messages);

    	Collection<Report> activity = new ArrayList<Report>();

    	
    	activity = exporter.createReport(conversation);
    	

    	Report[] rs = new Report[activity.size()];
        activity.toArray(rs);
    	
        
    	
    	assertEquals("dave", rs[0].sender);
    	assertEquals(2, rs[0].count);
    	assertEquals("mark", rs[1].sender);
    	assertEquals(1, rs[1].count);
    	assertEquals("greg", rs[2].sender);
    	assertEquals(1, rs[2].count);
    	
    	
    	
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
