package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {
	
	static Gson g;
	
	@BeforeClass
    public static void setup() {
		GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        g = builder.create();
	}
	
    /**
     * Tests that exporting a conversation will export the conversation correctly.
     * @throws IOException Thrown when either the input file cannot be read from or the output file cannot be written to.
     */
    @Test
    public void exportTest() throws IOException{
         
        ConversationExporter.main(new String[]{"-i", "chat1.txt", "-o", "chat.json"});
    	
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        
        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);
        //Message[] ms = c.getMessageArray();
        
        assertEquals("My Conversation", c.getName());
        assertEquals(7, ms.length);
        
        assertEquals(Instant.ofEpochSecond(1448470901),ms[0].getTimestamp());
        assertEquals("bob", ms[0].getSenderId());
        assertEquals( "Hello there!", ms[0].getContent());

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
    
    /**
     * Tests filtering by user.
     * @throws IOException Thrown when either the input file cannot be read from or the output file cannot be written to.
     */
    @Test
    public void userFilterTest() throws IOException{ //TODO exceptions
         
        ConversationExporter.main(new String[]{"-i", "chat1.txt", "-o", "chat.json", "-u", "bob"});
    	
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);
        //Message[] ms = c.getMessageArray();
        
        assertEquals("My Conversation", c.getName());
        assertEquals(3, ms.length);
        
        assertEquals(Instant.ofEpochSecond(1448470901),ms[0].getTimestamp());
        assertEquals("bob", ms[0].getSenderId());
        assertEquals( "Hello there!", ms[0].getContent());

        assertEquals(Instant.ofEpochSecond(1448470906), ms[1].getTimestamp());
        assertEquals("bob", ms[1].getSenderId());
        assertEquals("I'm good thanks, do you like pie?", ms[1].getContent());

        assertEquals("bob", ms[2].getSenderId());
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].getContent());
    }
    
    /**
     * Tests filtering by keyword.
     * @throws IOException Thrown when either the input file cannot be read from or the output file cannot be written to.
     */
    @Test
    public void keywordFilterTest() throws IOException{ //TODO exceptions
         
        ConversationExporter.main(new String[]{"-i", "chat1.txt", "-o", "chat.json", "-k", "pie"});
    	
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);
        //Message[] ms = c.getMessageArray();
        
        assertEquals("My Conversation", c.getName());
        assertEquals(4, ms.length);

        assertEquals(Instant.ofEpochSecond(1448470906), ms[0].getTimestamp());
        assertEquals("bob", ms[0].getSenderId());
        assertEquals("I'm good thanks, do you like pie?", ms[0].getContent());

        assertEquals(Instant.ofEpochSecond(1448470912), ms[1].getTimestamp());
        assertEquals("angus", ms[1].getSenderId());
        assertEquals("Hell yes! Are we buying some pie?", ms[1].getContent());

        assertEquals(Instant.ofEpochSecond(1448470914), ms[2].getTimestamp());
        assertEquals("bob", ms[2].getSenderId());
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].getContent());

        assertEquals(Instant.ofEpochSecond(1448470915), ms[3].getTimestamp());
        assertEquals("angus", ms[3].getSenderId());
        assertEquals("YES! I'm the head pie eater there...", ms[3].getContent());
    }
    
    /**
     * Tests hiding a word from a blacklist.
     * @throws IOException Thrown when either the input file cannot be read from or the output file cannot be written to.
     */
    @Test
    public void blackListSingleFilterTest() throws IOException{ //TODO exceptions
         
        ConversationExporter.main(new String[]{"-i", "chat1.txt", "-o", "chat.json", "-b", "pie"});
    	
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);
        //Message[] ms = c.getMessageArray();
        
        assertEquals("My Conversation", c.getName());
        assertEquals(7, ms.length);
        
        assertEquals(Instant.ofEpochSecond(1448470901),ms[0].getTimestamp());
        assertEquals("bob", ms[0].getSenderId());
        assertEquals( "Hello there!", ms[0].getContent());

        assertEquals(Instant.ofEpochSecond(1448470905), ms[1].getTimestamp());
        assertEquals("mike", ms[1].getSenderId());
        assertEquals("how are you?", ms[1].getContent());

        assertEquals(Instant.ofEpochSecond(1448470906), ms[2].getTimestamp());
        assertEquals("bob", ms[2].getSenderId());
        assertEquals("I'm good thanks, do you like *redacted*?", ms[2].getContent());

        assertEquals(Instant.ofEpochSecond(1448470910), ms[3].getTimestamp());
        assertEquals("mike", ms[3].getSenderId());
        assertEquals("no, let me ask Angus...", ms[3].getContent());

        assertEquals(Instant.ofEpochSecond(1448470912), ms[4].getTimestamp());
        assertEquals("angus", ms[4].getSenderId());
        assertEquals("Hell yes! Are we buying some *redacted*?", ms[4].getContent());

        assertEquals(Instant.ofEpochSecond(1448470914), ms[5].getTimestamp());
        assertEquals("bob", ms[5].getSenderId());
        assertEquals("No, just want to know if there's anybody else in the *redacted* society...", ms[5].getContent());

        assertEquals(Instant.ofEpochSecond(1448470915), ms[6].getTimestamp());
        assertEquals("angus", ms[6].getSenderId());
        assertEquals("YES! I'm the head *redacted* eater there...", ms[6].getContent());
    }
    
    /**
     * Tests hiding multiple words from a blacklist.
     * @throws IOException Thrown when either the input file cannot be read from or the output file cannot be written to.
     */
    @Test
    public void blackListMultiFilterTest() throws IOException{ //TODO exceptions
         
        ConversationExporter.main(new String[]{"-i", "chat1.txt", "-o", "chat.json", "-b", "pie no are"});
    	
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);
        //Message[] ms = c.getMessageArray();
        
        assertEquals("My Conversation", c.getName());
        assertEquals(7, ms.length);
        
        assertEquals(Instant.ofEpochSecond(1448470901),ms[0].getTimestamp());
        assertEquals("bob", ms[0].getSenderId());
        assertEquals( "Hello there!", ms[0].getContent());

        assertEquals(Instant.ofEpochSecond(1448470905), ms[1].getTimestamp());
        assertEquals("mike", ms[1].getSenderId());
        assertEquals("how *redacted* you?", ms[1].getContent());

        assertEquals(Instant.ofEpochSecond(1448470906), ms[2].getTimestamp());
        assertEquals("bob", ms[2].getSenderId());
        assertEquals("I'm good thanks, do you like *redacted*?", ms[2].getContent());

        assertEquals(Instant.ofEpochSecond(1448470910), ms[3].getTimestamp());
        assertEquals("mike", ms[3].getSenderId());
        assertEquals("*redacted*, let me ask Angus...", ms[3].getContent());

        assertEquals(Instant.ofEpochSecond(1448470912), ms[4].getTimestamp());
        assertEquals("angus", ms[4].getSenderId());
        assertEquals("Hell yes! *redacted* we buying some *redacted*?", ms[4].getContent());

        assertEquals(Instant.ofEpochSecond(1448470914), ms[5].getTimestamp());
        assertEquals("bob", ms[5].getSenderId());
        assertEquals("*redacted*, just want to know if there's anybody else in the *redacted* society...", ms[5].getContent());

        assertEquals(Instant.ofEpochSecond(1448470915), ms[6].getTimestamp());
        assertEquals("angus", ms[6].getSenderId());
        assertEquals("YES! I'm the head *redacted* eater there...", ms[6].getContent());
    }
    
    /**
     * Tests hiding the same word from a blacklist repeated in a message.
     * @throws IOException Thrown when either the input file cannot be read from or the output file cannot be written to.
     */
    @Test
    public void blackListRepeatFilterTest() throws IOException{ //TODO exceptions
         
        ConversationExporter.main(new String[]{"-i", "chat2.txt", "-o", "chat.json", "-b", "pie"});
    	
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);
        //Message[] ms = c.getMessageArray();
        
        assertEquals("My Conversation", c.getName());
        assertEquals(6, ms.length);
        
        assertEquals(Instant.ofEpochSecond(1448470901),ms[0].getTimestamp());
        assertEquals("bob", ms[0].getSenderId());
        assertEquals( "Hello there!", ms[0].getContent());

        assertEquals(Instant.ofEpochSecond(1448470905), ms[1].getTimestamp());
        assertEquals("mike", ms[1].getSenderId());
        assertEquals("how are you?", ms[1].getContent());

        assertEquals(Instant.ofEpochSecond(1448470906), ms[2].getTimestamp());
        assertEquals("bob", ms[2].getSenderId());
        assertEquals("I'm *redacted* thanks, do you like *redacted*?", ms[2].getContent());

        assertEquals(Instant.ofEpochSecond(1448470910), ms[3].getTimestamp());
        assertEquals("mike", ms[3].getSenderId());
        assertEquals("no, let me ask Angus...", ms[3].getContent());

        assertEquals(Instant.ofEpochSecond(1448470912), ms[4].getTimestamp());
        assertEquals("angus", ms[4].getSenderId());
        assertEquals("Hell yes! Are we buying some *redacted*?", ms[4].getContent());

        assertEquals(Instant.ofEpochSecond(1448470914), ms[5].getTimestamp());
        assertEquals("bob", ms[5].getSenderId());
        assertEquals("*redacted* *redacted* *redacted*", ms[5].getContent());
    }
    
    /**
     * Tests hiding credit card and phone numbers.
     * @throws IOException Thrown when either the input file cannot be read from or the output file cannot be written to.
     */
    @Test
    public void numberFilterTest() throws IOException{ //TODO exceptions
         
        ConversationExporter.main(new String[]{"-i", "chat3.txt", "-o", "chat.json", "-h"});
    	
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);
        //Message[] ms = c.getMessageArray();
        
        assertEquals("My Conversation", c.getName());
        assertEquals(12, ms.length);
        
        assertEquals(Instant.ofEpochSecond(1448470901),ms[0].getTimestamp());
        assertEquals("bob", ms[0].getSenderId());
        assertEquals( "Hello there!", ms[0].getContent());

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
        
        assertEquals(Instant.ofEpochSecond(1448470916), ms[7].getTimestamp());
        assertEquals("angus", ms[7].getSenderId());
        assertEquals("Phone number: *redacted*", ms[7].getContent());
        
        assertEquals(Instant.ofEpochSecond(1448470918), ms[8].getTimestamp());
        assertEquals("angus", ms[8].getSenderId());
        assertEquals("01234", ms[8].getContent());
        
        assertEquals(Instant.ofEpochSecond(1448470920), ms[9].getTimestamp());
        assertEquals("angus", ms[9].getSenderId());
        assertEquals("+*redacted*!", ms[9].getContent());
        
        assertEquals(Instant.ofEpochSecond(1448470921), ms[10].getTimestamp());
        assertEquals("angus", ms[10].getSenderId());
        assertEquals("Pie *redacted*", ms[10].getContent());
        
        assertEquals(Instant.ofEpochSecond(1448470922), ms[11].getTimestamp());
        assertEquals("angus", ms[11].getSenderId());
        assertEquals("Credit card: *redacted*", ms[11].getContent());
    }
    
    /**
     * Tests obfuscating user IDs.
     * @throws IOException Thrown when either the input file cannot be read from or the output file cannot be written to.
     */
    @Test
    public void obfuscateIDsTest() throws IOException{ //TODO exceptions
         
        ConversationExporter.main(new String[]{"-i", "chat1.txt", "-o", "chat.json", "-f"});
    	
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);
        //Message[] ms = c.getMessageArray();
        
        assertEquals("My Conversation", c.getName());
        assertEquals(7, ms.length);
        
        assertEquals(Instant.ofEpochSecond(1448470901),ms[0].getTimestamp());
        assertEquals("User1", ms[0].getSenderId());
        assertEquals( "Hello there!", ms[0].getContent());

        assertEquals(Instant.ofEpochSecond(1448470905), ms[1].getTimestamp());
        assertEquals("User2", ms[1].getSenderId());
        assertEquals("how are you?", ms[1].getContent());

        assertEquals(Instant.ofEpochSecond(1448470906), ms[2].getTimestamp());
        assertEquals("User1", ms[2].getSenderId());
        assertEquals("I'm good thanks, do you like pie?", ms[2].getContent());

        assertEquals(Instant.ofEpochSecond(1448470910), ms[3].getTimestamp());
        assertEquals("User2", ms[3].getSenderId());
        assertEquals("no, let me ask Angus...", ms[3].getContent());

        assertEquals(Instant.ofEpochSecond(1448470912), ms[4].getTimestamp());
        assertEquals("User3", ms[4].getSenderId());
        assertEquals("Hell yes! Are we buying some pie?", ms[4].getContent());

        assertEquals(Instant.ofEpochSecond(1448470914), ms[5].getTimestamp());
        assertEquals("User1", ms[5].getSenderId());
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].getContent());

        assertEquals(Instant.ofEpochSecond(1448470915), ms[6].getTimestamp());
        assertEquals("User3", ms[6].getSenderId());
        assertEquals("YES! I'm the head pie eater there...", ms[6].getContent());
    }
    
    /**
     * Tests a combination of all the other filters.
     * @throws IOException Thrown when either the input file cannot be read from or the output file cannot be written to.
     */
    @Test
    public void multiTest() throws IOException{ //TODO exceptions
         
        ConversationExporter.main(new String[]{"-i", "chat3.txt", "-o", "chat.json", "-u", "angus", "-k", "pie", "-b", "head", "-h", "-f"});
    	
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);
        
        assertEquals("My Conversation", c.getName());
        assertEquals(3, ms.length);

        assertEquals(Instant.ofEpochSecond(1448470912), ms[0].getTimestamp());
        assertEquals("User1", ms[0].getSenderId());
        assertEquals("Hell yes! Are we buying some pie?", ms[0].getContent());

        assertEquals(Instant.ofEpochSecond(1448470915), ms[1].getTimestamp());
        assertEquals("User1", ms[1].getSenderId());
        assertEquals("YES! I'm the *redacted* pie eater there...", ms[1].getContent());

        assertEquals(Instant.ofEpochSecond(1448470921), ms[2].getTimestamp());
        assertEquals("User1", ms[2].getSenderId());
        assertEquals("Pie *redacted*", ms[2].getContent());
    }
    
    /**
     * Tests that user activity is correctly recorded.
     * @throws IOException Thrown when either the input file cannot be read from or the output file cannot be written to.
     */
    @Test
    public void userActivityTest() throws IOException{
    	ConversationExporter.main(new String[]{"-i", "chat2.txt", "-o", "chat.json"});
    	
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        
        UserActivity[] activeUsers = c.getActiveUsers(); 
        
        assertEquals("bob", activeUsers[0].getUserID());
        assertEquals((Integer) 3, activeUsers[0].getMessagesSent());
        
        assertEquals("mike", activeUsers[1].getUserID());
        assertEquals((Integer) 2, activeUsers[1].getMessagesSent());
        
        assertEquals("angus", activeUsers[2].getUserID());
        assertEquals((Integer) 1, activeUsers[2].getMessagesSent());
    	
    }

    static class InstantDeserializer implements JsonDeserializer<Instant> {

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
