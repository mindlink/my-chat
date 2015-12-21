package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import org.junit.Test;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for the {@link Conversation}.
 */
public class ConversationTests {
   
	
    /**
     * Tests that the correct sender ID gets filtered
     * @throws Exception
     * @result Only messages from the matching sender ID will remain in the list.
     */
    @Test
    public void testConversationFilterSenderId() throws Exception {

    	String input1 = "testSenderId";
    	String input2 = "anotherTestSenderId";
    	
    	//initialize conversation and manually add variables
    	List<Message> messages = new ArrayList<Message>();
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901")), input1, "testContent"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901")), input2, "testContent"));
        Conversation conversation = new Conversation ("Test", messages);
        
        conversation.filterSenderId(input1);
        
        //iterate through all messages and check sender ID
        for(Message e : conversation.getMessages()){
        	
        	//tests that all messages are from specific sender ID
        	assertTrue("Messages of Sender ID have not been filtered correclty!",e.getSenderId().equals(input1));
        }
    }
    
    
    /**
     * Tests that the correct keyword gets filtered
     * @throws Exception
     * @result Only messages with the keyword in the content will remain in the list.
     */
    @Test
    public void testConversationFilterKeyword() throws Exception {

    	String input1 = "This is test content containing the Keyword";
    	String input2 = "This is some other content";
    	String keyword = "Keyword";
    	
    	//initialize conversation and manually add variables
    	List<Message> messages = new ArrayList<Message>();
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901")), "testSenderId", input1));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901")), "testSenderId", input2));
        Conversation conversation = new Conversation ("Test", messages);
        
        conversation.filterKeyword(keyword);

        
        //iterate through all messages and make sure correct messages are remaining
        for(Message e : conversation.getMessages()){

        	assertTrue("Messages does not contain keyword!",e.getContent().equals(input1));
        }
    }
    
    
    /**
     * Tests that the blacklisted words have been replaced correctly
     * @throws Exception
     * @result All blacklisted words have been replaced by "*redacted*".
     */
    @Test
    public void testConversationBlackList() throws Exception {

    	String input1 = "This is test content containing the blackList word";
    	String expectedOutput = "This is test *redacted* containing the *redacted* word";
    	List<String> blacklist = new ArrayList<>();
    	blacklist.add("blackList");
    	blacklist.add("content");
    	
    	//initialize conversation and manually add variables
    	List<Message> messages = new ArrayList<Message>();
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901")), "testSenderId", input1));
        Conversation conversation = new Conversation ("Test", messages);
        
        conversation.blacklist(blacklist);
        

        //iterate through all messages and make sure all blacklisted words have been replaced
        for(Message e : conversation.getMessages()){

        	assertTrue("Blacklisted word has not been replaced!",e.getContent().equals(expectedOutput));
        }
    }
    
    /**
     * Helper method that deals with deserialization of Instants for JSON
     * @throws Exception
     */
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
