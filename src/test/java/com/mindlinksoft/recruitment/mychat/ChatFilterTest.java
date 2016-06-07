package com.mindlinksoft.recruitment.mychat;

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;

import java.time.Instant;

import static org.junit.Assert.assertEquals;

/** 
* ChatFilter Tester. 
* 
* @author <Authors name> 
* @since <pre>Jun 7, 2016</pre> 
* @version 1.0 
*/ 
public class ChatFilterTest {

    ConversationExporter exporter;
    Conversation conv;
@Before
public void before() throws Exception {
    exporter = new ConversationExporter();
    conv = exporter.readConversation("in.txt");
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: filterByUser(Conversation conv, String user) 
* 
*/ 
@Test
public void testFilterByUser() throws Exception {
    Conversation filteredConversation = ChatFilter.filterByUser(conv, "bob");
    Message[] ms = new Message[filteredConversation.getMessages().size()];
    filteredConversation.getMessages().toArray(ms);

    assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470901));
    assertEquals(ms[0].getSenderId(), "bob");
    assertEquals(ms[0].getContent(), "Hello there!");

    assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470906));
    assertEquals(ms[1].getSenderId(), "bob");
    assertEquals(ms[1].getContent(), "I'm good thanks, do you like pie?");

    assertEquals(ms[2].getTimestamp(), Instant.ofEpochSecond(1448470914));
    assertEquals(ms[2].getSenderId(), "bob");
    assertEquals(ms[2].getContent(), "No, just want to know if there's anybody else in the pie society...");

    assertEquals(ms[3].getTimestamp(), Instant.ofEpochSecond(1448470925));
    assertEquals(ms[3].getSenderId(), "bob");
    assertEquals(ms[3].getContent(), "I had no idea. 4539643180553967 07700900308 29081992");

} 

/** 
* 
* Method: filterByKeyword(Conversation conv, String keyword) 
* 
*/ 
@Test
public void testFilterByKeyword() throws Exception {
    Conversation filteredConversation = ChatFilter.filterByKeyword(conv, "pie");
    Message[] ms = new Message[filteredConversation.getMessages().size()];
    filteredConversation.getMessages().toArray(ms);

    assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470906));
    assertEquals(ms[0].getSenderId(), "bob");
    assertEquals(ms[0].getContent(), "I'm good thanks, do you like pie?");

    assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470912));
    assertEquals(ms[1].getSenderId(), "angus");
    assertEquals(ms[1].getContent(), "Hell yes! Are we buying some pie?");

    assertEquals(ms[2].getTimestamp(), Instant.ofEpochSecond(1448470914));
    assertEquals(ms[2].getSenderId(), "bob");
    assertEquals(ms[2].getContent(), "No, just want to know if there's anybody else in the pie society...");

    assertEquals(ms[3].getTimestamp(), Instant.ofEpochSecond(1448470915));
    assertEquals(ms[3].getSenderId(), "angus");
    assertEquals(ms[3].getContent(), "YES! I'm the head pie eater there...");

    assertEquals(ms[4].getTimestamp(), Instant.ofEpochSecond(1448470915));
    assertEquals(ms[4].getSenderId(), "angus");
    assertEquals(ms[4].getContent(), "I wish pies were actually good for you, then I would eat nothing but pie.");

} 

/** 
* 
* Method: filterOutWord(Conversation conv, String word) 
* 
*/ 
@Test
public void testFilterOutWord() throws Exception {
    Conversation filteredConversation = ChatFilter.filterOutWord(conv, "pie");
    Message[] ms = new Message[filteredConversation.getMessages().size()];
    filteredConversation.getMessages().toArray(ms);

    assertEquals(ms[2].getTimestamp(), Instant.ofEpochSecond(1448470906));
    assertEquals(ms[2].getSenderId(), "bob");
    assertEquals(ms[2].getContent(), "I'm good thanks, do you like \\*redacted\\*?");

    assertEquals(ms[4].getTimestamp(), Instant.ofEpochSecond(1448470912));
    assertEquals(ms[4].getSenderId(), "angus");
    assertEquals(ms[4].getContent(), "Hell yes! Are we buying some \\*redacted\\*?");

    assertEquals(ms[5].getTimestamp(), Instant.ofEpochSecond(1448470914));
    assertEquals(ms[5].getSenderId(), "bob");
    assertEquals(ms[5].getContent(), "No, just want to know if there's anybody else in the \\*redacted\\* society...");

    assertEquals(ms[6].getTimestamp(), Instant.ofEpochSecond(1448470915));
    assertEquals(ms[6].getSenderId(), "angus");
    assertEquals(ms[6].getContent(), "YES! I'm the head \\*redacted\\* eater there...");

    assertEquals(ms[7].getTimestamp(), Instant.ofEpochSecond(1448470915));
    assertEquals(ms[7].getSenderId(), "angus");
    assertEquals(ms[7].getContent(), "I wish \\*redacted\\*s were actually good for you, then I would eat nothing but \\*redacted\\*.");

} 

/** 
* 
* Method: filterOutWordList(Conversation conv, String[] wordList) 
* 
*/ 
@Test
public void testFilterOutWordList() throws Exception {
    String[] wordList = {"pie", "society"};
    Conversation filteredConversation = ChatFilter.filterOutWordList(conv, wordList);
    Message[] ms = new Message[filteredConversation.getMessages().size()];
    filteredConversation.getMessages().toArray(ms);

    assertEquals(ms[2].getTimestamp(), Instant.ofEpochSecond(1448470906));
    assertEquals(ms[2].getSenderId(), "bob");
    assertEquals(ms[2].getContent(), "I'm good thanks, do you like \\*redacted\\*?");

    assertEquals(ms[4].getTimestamp(), Instant.ofEpochSecond(1448470912));
    assertEquals(ms[4].getSenderId(), "angus");
    assertEquals(ms[4].getContent(), "Hell yes! Are we buying some \\*redacted\\*?");

    assertEquals(ms[5].getTimestamp(), Instant.ofEpochSecond(1448470914));
    assertEquals(ms[5].getSenderId(), "bob");
    assertEquals(ms[5].getContent(), "No, just want to know if there's anybody else in the \\*redacted\\* \\*redacted\\*...");

    assertEquals(ms[6].getTimestamp(), Instant.ofEpochSecond(1448470915));
    assertEquals(ms[6].getSenderId(), "angus");
    assertEquals(ms[6].getContent(), "YES! I'm the head \\*redacted\\* eater there...");

    assertEquals(ms[7].getTimestamp(), Instant.ofEpochSecond(1448470915));
    assertEquals(ms[7].getSenderId(), "angus");
    assertEquals(ms[7].getContent(), "I wish \\*redacted\\*s were actually good for you, then I would eat nothing but \\*redacted\\*.");

} 

/** 
* 
* Method: filterOutCardNumbers(Conversation conv, String user) 
* 
*/ 
@Test
public void testFilterOutCardNumbers() throws Exception {
    Conversation filteredConversation = ChatFilter.filterOutCardNumbers(conv);
    Message[] ms = new Message[filteredConversation.getMessages().size()];
    filteredConversation.getMessages().toArray(ms);

    assertEquals(ms[12].getTimestamp(), Instant.ofEpochSecond(1448470925));
    assertEquals(ms[12].getSenderId(), "bob");
    assertEquals(ms[12].getContent(), "I had no idea. \\*redacted\\* 07700900308 29081992");

    assertEquals(ms[13].getTimestamp(), Instant.ofEpochSecond(1448470912));
    assertEquals(ms[13].getSenderId(), "angus");
    assertEquals(ms[13].getContent(), "\\*redacted\\* +44 7700 900187");
} 

/** 
* 
* Method: filterOutPhoneNumbers(Conversation conv, String user) 
* 
*/ 
@Test
public void testFilterOutPhoneNumbers() throws Exception {
    Conversation filteredConversation = ChatFilter.filterOutPhoneNumbers(conv);
    Message[] ms = new Message[filteredConversation.getMessages().size()];
    filteredConversation.getMessages().toArray(ms);

    assertEquals(ms[12].getTimestamp(), Instant.ofEpochSecond(1448470925));
    assertEquals(ms[12].getSenderId(), "bob");
    assertEquals(ms[12].getContent(), "I had no idea. 4539643180553967 \\*redacted\\* 29081992");

    assertEquals(ms[13].getTimestamp(), Instant.ofEpochSecond(1448470912));
    assertEquals(ms[13].getSenderId(), "angus");
    assertEquals(ms[13].getContent(), "4916238985683193 \\*redacted\\* \\*redacted\\*");
} 


} 
