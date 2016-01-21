/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Represents class to test(@link Filter.java) Class
 * @author Carl
 */
public class FilterTest {
    
    public FilterTest() {
    }
    /**
     * Global attribute to hold an instance of the filter class
     */
    public final Filter instance = new Filter();
    
    public final TesterData testData = new TesterData();

    /**
     * Test of retractWords method, of class Filter.
     */
    @Test
    public void testRetractWords() {
        System.out.println("retractWords");
        String [] retractedWord = {"you"};
        Conversation conversation = testData.createTestConversation();
        
        List<Message> messages = new ArrayList<Message>();
        
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901")), "bob", "Hello there!"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470905")), "mike", "how are *redacted*?"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470906")), "bob", "I'm good thanks, do *redacted* like pie?"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470910")), "mike", "no, let me ask Angus...?"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470912")), "angus", "Hell yes! Are we buying some pie?"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470914")), "bob", "No, just want to know if there's anybody else in the pie society..."));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470915")), "angus", "YES! I'm the head pie eater there..."));
        
        Conversation expResult = new Conversation("My Conversation",messages);
        Conversation result = instance.retractWords(retractedWord, conversation);
        System.out.println(result.toString());
        assertEquals(expResult.toString(), result.toString());
        
        //test 2 
        String [] retractedWord2 = {"you","pie"};
        
        List<Message> messages2 = new ArrayList<Message>();
        
        messages2.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901")), "bob", "Hello there!"));
        messages2.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470905")), "mike", "how are *redacted*?"));
        messages2.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470906")), "bob", "I'm good thanks, do *redacted* like *redacted*?"));
        messages2.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470910")), "mike", "no, let me ask Angus...?"));
        messages2.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470912")), "angus", "Hell yes! Are we buying some *redacted*?"));
        messages2.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470914")), "bob", "No, just want to know if there's anybody else in the *redacted* society..."));
        messages2.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470915")), "angus", "YES! I'm the head *redacted* eater there..."));
        
        Conversation expResult2 = new Conversation("My Conversation",messages2);
        Conversation result2 = instance.retractWords(retractedWord2, conversation);
        System.out.println(result2.toString());
        assertEquals(expResult2.toString(), result2.toString());
    }

    /**
     * Test of filterByKeyWord method, of class Filter.
     */
    @Test
    public void testFilterByKeyWords() {
        System.out.println("filterByKeyWords");
        String [] keyWord = {"you"};
        Conversation conversation = testData.createTestConversation();
        
        List<Message> messages = new ArrayList<Message>();
        
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470905")), "mike", "how are you?"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470906")), "bob", "I'm good thanks, do you like pie?"));
        
        Conversation expResult = new Conversation("My Conversation",messages);
        Conversation result = instance.filterByKeyWords(keyWord, conversation);
        System.out.println(result.toString());
        System.out.println(expResult.toString());
        assertEquals(expResult.toString(), result.toString());
        
        String [] keyWord2 = {"you","pie"};
        
        List<Message> messages2 = new ArrayList<Message>();
        
        messages2.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470905")), "mike", "how are you?"));
        messages2.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470906")), "bob", "I'm good thanks, do you like pie?"));
        messages2.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470912")), "angus", "Hell yes! Are we buying some pie?"));
        messages2.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470914")), "bob", "No, just want to know if there's anybody else in the pie society..."));
        messages2.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470915")), "angus", "YES! I'm the head pie eater there..."));
        Conversation expResult2 = new Conversation("My Conversation",messages2);
        Conversation result2 = instance.filterByKeyWords(keyWord2, conversation);
        System.out.println(result2.toString());
        System.out.println(expResult2.toString());
        assertEquals(expResult2.toString(), result2.toString());
        
    }

    /**
     * Test of filterByUsername method, of class Filter.
     */
    @Test
    public void testFilterByUsername() {
        System.out.println("filterByUsername");
        String [] username = {"bob"};
         Conversation conversation = testData.createTestConversation();
        
        List<Message> messages = new ArrayList<Message>();
        
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901")), "bob", "Hello there!"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470906")), "bob", "I'm good thanks, do you like pie?"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470914")), "bob", "No, just want to know if there's anybody else in the pie society..."));
                
        Conversation expResult = new Conversation("My Conversation",messages);
        Conversation result = instance.filterByUsername(username, conversation);
        System.out.println("Result:");
        System.out.println(result.toString());
        System.out.println("Expected:");
        System.out.println(expResult.toString());
        assertEquals(expResult.toString(), result.toString());
        
        //test2
        String usernames2[]= {"mike","bob"};
        List<Message> messages2 = new ArrayList<Message>();
        
        messages2.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901")), "bob", "Hello there!"));
        messages2.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470905")), "mike", "how are you?"));
        messages2.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470906")), "bob", "I'm good thanks, do you like pie?"));
        messages2.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470910")), "mike", "no, let me ask Angus...?"));
        messages2.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470914")), "bob", "No, just want to know if there's anybody else in the pie society..."));

                
        Conversation expResult2 = new Conversation("My Conversation",messages2);
        Conversation result2 = instance.filterByUsername(usernames2, conversation);
        System.out.println("Result:");
        System.out.println(result2.toString());
        System.out.println("Expected:");
        System.out.println(expResult2.toString());
        assertEquals(expResult2.toString(), result2.toString());
    }
    
}
