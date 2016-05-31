package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {
    /**
     * Tests that exporting a conversation will export the conversation correctly.
     * @throws Exception When something bad happens.
     */
    @Test
    public void testExportingConversationExportsConversation() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        String[] args = {"chat.txt", "chat.json"};
        exporter.main(args);

        Conversation c = (Conversation) exporter.deserializeFromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        assertEquals(7, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "Hello there!");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].senderId, "mike");
        assertEquals(ms[1].content, "how are you?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(ms[2].content, "I'm good thanks, do you like pie?");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].senderId, "mike");
        assertEquals(ms[3].content, "no, let me ask Angus...");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].senderId, "angus");
        assertEquals(ms[4].content, "Hell yes! Are we buying some pie?");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].senderId, "bob");
        assertEquals(ms[5].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].senderId, "angus");
        assertEquals(ms[6].content, "YES! I'm the head pie eater there...");
              
        assertEquals(3, c.users.size());
        
        User[] us = new User[c.users.size()];
        c.users.toArray(us);
        
        assertEquals(us[0].getName(), "mike");
        assertEquals(us[0].getActivity(), 2);

        assertEquals(us[1].getName(), "angus");
        assertEquals(us[1].getActivity(), 2);

        assertEquals(us[2].getName(), "bob");
        assertEquals(us[2].getActivity(), 3);
        

    }
    
    @Test
    public void testExportingConversationExportsConversationUserFilter() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        String[] args = {"chat.txt", "chat.json","bob"};
        exporter.main(args);

        Conversation c = (Conversation) exporter.deserializeFromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        assertEquals(3, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "Hello there!");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[1].senderId, "bob");
        assertEquals(ms[1].content, "I'm good thanks, do you like pie?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(ms[2].content, "No, just want to know if there's anybody else in the pie society...");      
        
        assertEquals(1, c.users.size());
        
        User[] us = new User[c.users.size()];
        c.users.toArray(us);

        assertEquals(us[0].getName(), "bob");
        assertEquals(us[0].getActivity(), 3);
    }
    
    @Test
    public void testExportingConversationExportsConversationFilterKeyword() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        String[] args = {"chat.txt", "chat.json","","pie"};
        exporter.main(args);

        Conversation c = (Conversation) exporter.deserializeFromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        assertEquals(4, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "I'm good thanks, do you like pie?");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[1].senderId, "angus");
        assertEquals(ms[1].content, "Hell yes! Are we buying some pie?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(ms[2].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[3].senderId, "angus");
        assertEquals(ms[3].content, "YES! I'm the head pie eater there...");
        
        assertEquals(2, c.users.size());
        
        User[] us = new User[c.users.size()];
        c.users.toArray(us);
        
        assertEquals(us[0].getName(), "bob");
        assertEquals(us[0].getActivity(), 2);

        assertEquals(us[1].getName(), "angus");
        assertEquals(us[1].getActivity(), 2);
    }
    
    @Test
    public void testExportingConversationExportsConversationBlacklist() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        String[] args = {"chat.txt", "chat.json","","","blacklist.txt"};
        exporter.main(args);

        Conversation c = (Conversation) exporter.deserializeFromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        assertEquals(7, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "Hello *redacted*!");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].senderId, "mike");
        assertEquals(ms[1].content, "how are you?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(ms[2].content, "I'm good thanks, do you like *redacted*?");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].senderId, "mike");
        assertEquals(ms[3].content, "no, let me ask Angus...");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].senderId, "angus");
        assertEquals(ms[4].content, "Hell yes! Are we buying some *redacted*?");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].senderId, "bob");
        assertEquals(ms[5].content, "No, just want to know if *redacted*'s anybody else in the *redacted* society...");

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].senderId, "angus");
        assertEquals(ms[6].content, "YES! I'm the head *redacted* eater *redacted*...");
              
        assertEquals(3, c.users.size());
        
        User[] us = new User[c.users.size()];
        c.users.toArray(us);
        
        assertEquals(us[0].getName(), "mike");
        assertEquals(us[0].getActivity(), 2);

        assertEquals(us[1].getName(), "angus");
        assertEquals(us[1].getActivity(), 2);

        assertEquals(us[2].getName(), "bob");
        assertEquals(us[2].getActivity(), 3);
    }
      @Test
    public void testExportingConversationExportsConversationNumberFlag() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        String[] args = {"chat2.txt", "chat.json","","","","true"};
        exporter.main(args);

        Conversation c = (Conversation) exporter.deserializeFromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        assertEquals(7, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "Hello there!");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].senderId, "mike");
        assertEquals(ms[1].content, "how are you?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(ms[2].content, "I'm good thanks, do you like pie?");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].senderId, "mike");
        assertEquals(ms[3].content, "Here is the 2nd account *redacted* valid till 2018 my no +*redacted*, let me ask Angus...");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].senderId, "angus");
        assertEquals(ms[4].content, "Hell yes! Are we buying some pie?");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].senderId, "bob");
        assertEquals(ms[5].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].senderId, "angus");
        assertEquals(ms[6].content, "YES! I'm the head pie eater there...");
        c.messages.toArray(ms);
        
        assertEquals(3, c.users.size());
        
        User[] us = new User[c.users.size()];
        c.users.toArray(us);
        
        assertEquals(us[0].getName(), "mike");
        assertEquals(us[0].getActivity(), 2);

        assertEquals(us[1].getName(), "angus");
        assertEquals(us[1].getActivity(), 2);

        assertEquals(us[2].getName(), "bob");
        assertEquals(us[2].getActivity(), 3);

    }
    
        @Test
    public void testExportingConversationExportsConversationObfuscatID() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        String[] args = {"chat.txt", "chat.json","","","","false","true"};
        exporter.main(args);

        Conversation c = (Conversation) exporter.deserializeFromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        assertEquals(7, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "ObfuscateID-0");
        assertEquals(ms[0].content, "Hello there!");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].senderId, "ObfuscateID-1");
        assertEquals(ms[1].content, "how are you?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].senderId, "ObfuscateID-0");
        assertEquals(ms[2].content, "I'm good thanks, do you like pie?");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].senderId, "ObfuscateID-1");
        assertEquals(ms[3].content, "no, let me ask Angus...");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].senderId, "ObfuscateID-2");
        assertEquals(ms[4].content, "Hell yes! Are we buying some pie?");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].senderId, "ObfuscateID-0");
        assertEquals(ms[5].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].senderId, "ObfuscateID-2");
        assertEquals(ms[6].content, "YES! I'm the head pie eater there...");
        c.messages.toArray(ms);

        assertEquals(3, c.users.size());
        
        User[] us = new User[c.users.size()];
        c.users.toArray(us);
        
        assertEquals(us[0].getName(), "ObfuscateID-1");
        assertEquals(us[0].getActivity(), 2);

        assertEquals(us[1].getName(), "ObfuscateID-2");
        assertEquals(us[1].getActivity(), 2);

        assertEquals(us[2].getName(), "ObfuscateID-0");
        assertEquals(us[2].getActivity(), 3);
    }
    
    
    
    
    @Test
    public void testExportingConversationExportsConversationAllCommands() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        String[] args = {"chat3.txt", "chat.json","bob","pie","blacklist.txt","true","true"};
        exporter.main(args);

        Conversation c = (Conversation) exporter.deserializeFromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        assertEquals(3, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[0].senderId, "ObfuscateID-0");
        assertEquals(ms[0].content, "I'm good thanks, do you like *redacted*?");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470910));
        assertEquals(ms[1].senderId, "ObfuscateID-0");
        assertEquals(ms[1].content, "*redacted*! Here is the 1st account *redacted* valid till 2018 my no +*redacted*.");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[2].senderId, "ObfuscateID-0");
        assertEquals(ms[2].content, "No, just want to know if *redacted*'s anybody else in the *redacted* society...");
              
        assertEquals(1, c.users.size());
        
        User[] us = new User[c.users.size()];
        c.users.toArray(us);
       
        assertEquals(us[0].getName(), "ObfuscateID-0");
        assertEquals(us[0].getActivity(), 3);
        

    }


}
