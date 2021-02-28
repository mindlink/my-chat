package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class KeywordFilterTests {

    /**
     * Tests that it will correctly filter the conversation by a specific word
     * @throws Exception
     */
	@Test
	public void test() {
		ConversationFilterer filter = new ConversationFilterer();
    	
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
    	
    	
    	String keyword = "hello";
    	
    	Conversation c = filter.filterConversationByKeyword(conversation, keyword);
        
    	
        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);
    	
    	assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);
        assertEquals("greg", ms[0].senderId);
        assertEquals("hello", ms[0].content);
        
    	assertEquals(Instant.ofEpochSecond(1448470912), ms[1].timestamp);
        assertEquals("sam", ms[1].senderId);
        assertEquals("longer message with hello in it", ms[1].content);
	}

}
