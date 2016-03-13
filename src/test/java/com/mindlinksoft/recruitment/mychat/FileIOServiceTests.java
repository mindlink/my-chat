package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.helpers.ConversationTestHelper;
import com.mindlinksoft.recruitment.mychat.helpers.ReadFileHelper;
import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;
import com.mindlinksoft.recruitment.mychat.services.FileIOService;

/**
 * Tests for the {@link FileIOService}.
 */
public class FileIOServiceTests {
	
	/**
     * Tests that the {@link FileIOService} will read a file properly.
     */
    @Test
    public void testReadFile() throws Exception {
    	FileIOService fileService = new FileIOService();
    	Conversation conversation = fileService.readConversation("chat.txt");
    	
    	ConversationTestHelper.testConversation(conversation);
    }
    
    /**
     * Tests that the {@link FileIOService} will write a file properly.
     */
    @Test
    public void testWriteFile() throws Exception {
    	FileIOService fileService = new FileIOService();
    	
    	List<Message> messages = new ArrayList<Message>();
    	messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901")), "bob", "Hello there!"));
    	messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470905")), "mike", "how are you?"));
    	messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470906")), "bob", "I'm good thanks, do you like pie?"));
    	messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470910")), "mike", "no, let me ask Angus..."));
    	messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470912")), "angus", "Hell yes! Are we buying some pie?"));
    	messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470914")), "bob", "No, just want to know if there's anybody else in the pie society..."));
    	messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470915")), "angus", "YES! I'm the head pie eater there..."));
    	
    	Conversation stubConversation = new Conversation("My Conversation" , messages);
    	fileService.writeConversation(stubConversation, "chat.json");
    	
    	Conversation conversation = ReadFileHelper.readTestFile();
    	ConversationTestHelper.testConversation(conversation);
    }
}
