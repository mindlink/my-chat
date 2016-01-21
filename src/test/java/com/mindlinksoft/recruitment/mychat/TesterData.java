/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * contains sample test data used over more than one test.
 * @author Carl
 */
public class TesterData {
    
    public TesterData(){
        
    }
    
    /**
     * Represents helper method to create a test conversation for test data reuse.
     * @return The newly create test conversation.
     */
    public Conversation createTestConversation()
    {
        List<Message> messages = new ArrayList<Message>();
        
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901")), "bob", "Hello there!"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470905")), "mike", "how are you?"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470906")), "bob", "I'm good thanks, do you like pie?"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470910")), "mike", "no, let me ask Angus...?"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470912")), "angus", "Hell yes! Are we buying some pie?"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470914")), "bob", "No, just want to know if there's anybody else in the pie society..."));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470915")), "angus", "YES! I'm the head pie eater there..."));
        
        Conversation conversation = new Conversation("My Conversation",messages);
        return conversation;
    }
}
