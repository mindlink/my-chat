package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Test;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link FilterServices}.
 */
public class FilterServicesTests {

    private ConversationExporter exporter;
    private ConversationExporterConfiguration config;
    private FilterServices filter;
    private IOFileServices fileServices;
    
    
    /**
     * Set up the dummy objects to be used by the tests
     */
    @Before
    public void initialiseConversationObjects ()
    {
        exporter = new ConversationExporter();
        config = new ConversationExporterConfiguration("chat.txt", "chat.json");
        filter = new FilterServices(config);
        fileServices = new IOFileServices();  
    }
    
     /**
     * Tests the filter by keyword function
     * @throws Exception When something bad happens.
     */
    @Test
    public void FilterByKeyWordTest()throws Exception
    {
        config.setfilterKeyWord("pie");
        Conversation conversation = fileServices.readConversation(config.getInputFilePath());
        Conversation filteredConversation = filter.FilterByKeyWord(config, conversation);
        
        ArrayList<Message> ms = filteredConversation.getMessages();
        
        assertEquals(ms.get(0).getTimestamp(), Instant.ofEpochSecond(1448470906));
        assertEquals(ms.get(0).getSenderId(), "bob");
        assertEquals(ms.get(0).getContent(), "I'm good thanks, do you like pie?");

        assertEquals(ms.get(1).getTimestamp(), Instant.ofEpochSecond(1448470912));
        assertEquals(ms.get(1).getSenderId(), "angus");
        assertEquals(ms.get(1).getContent(), "Hell yes! Are we buying some pie?");

        assertEquals(ms.get(2).getTimestamp(), Instant.ofEpochSecond(1448470914));
        assertEquals(ms.get(2).getSenderId(), "bob");
        assertEquals(ms.get(2).getContent(), "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms.get(3).getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms.get(3).getSenderId(), "angus");
        assertEquals(ms.get(3).getContent(), "YES! I'm the head pie eater there...");
    }
    
    /**
     * Tests the filter by username function
     * @throws Exception When something bad happens.
     */
    @Test
    public void FilterByUsernameTest()throws Exception
    {
        config.setFilterUserName("angus");
        Conversation conversation = fileServices.readConversation(config.getInputFilePath());
        Conversation filteredConversation = filter.FilterByUsername(config, conversation);
        
        ArrayList<Message> ms = filteredConversation.getMessages();
        
        assertEquals(ms.get(0).getTimestamp(), Instant.ofEpochSecond(1448470912));
        assertEquals(ms.get(0).getSenderId(), "angus");
        assertEquals(ms.get(0).getContent(), "Hell yes! Are we buying some pie?");
        
        assertEquals(ms.get(1).getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms.get(1).getSenderId(), "angus");
        assertEquals(ms.get(1).getContent(), "YES! I'm the head pie eater there...");
    }
    
    /**
     * Test redacted sensor on blacklisted words
     * @throws Exception When something bad happens.
     */
    @Test
    public void FilterByBlackList()throws Exception
    {
        ArrayList<String> testBlackList = new ArrayList();
        testBlackList.add("pie");
        testBlackList.add("Hello");
        testBlackList.add("Hell");
        
        Conversation conversation = fileServices.readConversation(config.getInputFilePath());
        Conversation filteredConversation = filter.FilterByBlackList(config, testBlackList, conversation);
        
        ArrayList<Message> ms = filteredConversation.getMessages();
        
        assertEquals(ms.get(0).getTimestamp(), Instant.ofEpochSecond(1448470901));
        assertEquals(ms.get(0).getSenderId(), "bob");
        assertEquals(ms.get(0).getContent(), "*redacted* there!");

        assertEquals(ms.get(1).getTimestamp(), Instant.ofEpochSecond(1448470905));
        assertEquals(ms.get(1).getSenderId(), "mike");
        assertEquals(ms.get(1).getContent(), "how are you?");

        assertEquals(ms.get(2).getTimestamp(), Instant.ofEpochSecond(1448470906));
        assertEquals(ms.get(2).getSenderId(), "bob");
        assertEquals(ms.get(2).getContent(), "I'm good thanks, do you like *redacted*?");

        assertEquals(ms.get(3).getTimestamp(), Instant.ofEpochSecond(1448470910));
        assertEquals(ms.get(3).getSenderId(), "mike");
        assertEquals(ms.get(3).getContent(), "no, let me ask Angus...");

        assertEquals(ms.get(4).getTimestamp(), Instant.ofEpochSecond(1448470912));
        assertEquals(ms.get(4).getSenderId(), "angus");
        assertEquals(ms.get(4).getContent(), "*redacted* yes! Are we buying some *redacted*?");

        assertEquals(ms.get(5).getTimestamp(), Instant.ofEpochSecond(1448470914));
        assertEquals(ms.get(5).getSenderId(), "bob");
        assertEquals(ms.get(5).getContent(), "No, just want to know if there's anybody else in the *redacted* society...");

        assertEquals(ms.get(6).getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms.get(6).getSenderId(), "angus");
        assertEquals(ms.get(6).getContent(), "YES! I'm the head *redacted* eater there...");
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
