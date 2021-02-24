package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ConversationRedacterTests {
 
    
    /**
     * Tests that it will correctly redact the conversation by a given list of words.
     * @throws Exception
     */
    @Test
    public void testRedactingConversationByKeywords() throws Exception {
    	ConversationRedacter redacter = new ConversationRedacter();
    	
    	Message myMessage1 = new Message(Instant.ofEpochSecond(1448470901), "greg", "hello");
    	Message myMessage2 = new Message(Instant.ofEpochSecond(1448470905), "dave", "world");
    	Message myMessage3 = new Message(Instant.ofEpochSecond(1448470912), "sam", "longer message with hello in it");
    	Message myMessage4 = new Message(Instant.ofEpochSecond(1448470919), "mark", "new sentance");
    	
    	List<Message> messages = new ArrayList<Message>();
    	messages.add(myMessage1);
    	messages.add(myMessage2);
    	messages.add(myMessage3);
    	messages.add(myMessage4);

    	Conversation conversation = new Conversation("MyConvo", messages);
    	
    	
    	List<String> blacklist = new ArrayList<String>();
    	
    	blacklist.add("hello");
    	
    	Conversation c = redacter.blacklistConversation(conversation, blacklist);
        
    	
        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);
    	
    	assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);
        assertEquals("greg", ms[0].senderId);
        assertEquals("*redacted*", ms[0].content);
        
        assertEquals("longer message with *redacted* in it", ms[2].content);
        
        assertEquals("new sentance", ms[3].content);
    	
    }

}
