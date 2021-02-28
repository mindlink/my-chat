package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class UserFilterTests {

	/**
     * Tests that it will correctly filter the conversation by a specific user
     * @throws Exception
  	 */
	@Test
	public void test() {
		UserFilter filter = new UserFilter();
    	
    	Message myMessage1 = new Message(Instant.ofEpochSecond(1448470901), "dave", "hello");
    	Message myMessage2 = new Message(Instant.ofEpochSecond(1448470905), "greg", "hello world");
    	Message myMessage3 = new Message(Instant.ofEpochSecond(1448470912), "dave", "hello world!");
    	
    	List<Message> messages = new ArrayList<Message>();
    	messages.add(myMessage1);
    	messages.add(myMessage2);
    	messages.add(myMessage3);

    	Conversation conversation = new Conversation("MyConvo", messages);
    	
    	
    	String user = "dave";
    	
    	Conversation c = filter.filter(conversation, user);
        
    	
        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);
    	
    	assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);
        assertEquals("dave", ms[0].senderId);
        assertEquals("hello", ms[0].content);
        
    	assertEquals(Instant.ofEpochSecond(1448470912), ms[1].timestamp);
        assertEquals("dave", ms[1].senderId);
        assertEquals("hello world!", ms[1].content);
	}

}
