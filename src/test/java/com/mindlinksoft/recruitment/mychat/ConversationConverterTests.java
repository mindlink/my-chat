package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.models.*;
import com.mindlinksoft.recruitment.mychat.config.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.converter.ConversationConverter;

import org.junit.Test;

import picocli.CommandLine;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ConversationConverterTests {
    
    public ConversationExporterConfiguration configuration;
    public Conversation conversation;

    @Test
    public void testConverterNoFlags() throws Exception {
        configuration = new ConversationExporterConfiguration();
        CommandLine cmd = new CommandLine(configuration);

        String[] command = {"-i", "chat.txt", "-o", "temp.json"};
        cmd.parseArgs(command);
        
        ConversationConverter conversationConverter = new ConversationConverter(configuration);
        conversationConverter.convertAll(conversation);

        assertEquals(conversation.name, "Conversation name");
        assertEquals(conversation.getMessages().size(), 6);
    }


    @Test
    public void testConverterFilterByName() throws Exception {
        configuration = new ConversationExporterConfiguration();
        CommandLine cmd = new CommandLine(configuration);

        String[] command = {"-i", "chat.txt", "-o", "temp.json", "--filterByUser", "harry"};
        cmd.parseArgs(command);
        
        ConversationConverter conversationConverter = new ConversationConverter(configuration);
        conversationConverter.convertAll(conversation);

        assertEquals(conversation.getMessages().size(), 3);
        assertEquals(conversation.getMessages().get(0).getContent(), "I am a pie eater");
        assertEquals(conversation.getMessages().get(1).getContent(), "This doesn't contain any blacklisted words");
        assertEquals(conversation.getMessages().get(2).getContent(), "I am a /*redacted*? [*redacted*!");
    }


    @Test
    public void testConverterBlacklistAndUser() throws Exception {
        configuration = new ConversationExporterConfiguration();
        CommandLine cmd = new CommandLine(configuration);

        String[] command = {"-i", "chat.txt", "-o", "temp.json", "--filterByUser", "harry", "-b", "pie", "-b", "i"};
        cmd.parseArgs(command);
        
        ConversationConverter conversationConverter = new ConversationConverter(configuration);
        conversationConverter.convertAll(conversation);

        assertEquals(conversation.getMessages().size(), 3);
        assertEquals(conversation.getMessages().get(0).getContent(), "*redacted* am a *redacted* eater");
        assertEquals(conversation.getMessages().get(1).getContent(), "This doesn't contain any blacklisted words");
        assertEquals(conversation.getMessages().get(2).getContent(), "*redacted* am a /*redacted*? [*redacted*!");
    }
    

    @Test
    public void testConverterFilterByWord() throws Exception {
        configuration = new ConversationExporterConfiguration();
        CommandLine cmd = new CommandLine(configuration);

        String[] command = {"-i", "chat.txt", "-o", "temp.json", "--filterByKeyword", "pie"};
        cmd.parseArgs(command);
        
        ConversationConverter conversationConverter = new ConversationConverter(configuration);
        conversationConverter.convertAll(conversation);

        assertEquals(conversation.getMessages().size(), 2);
        assertEquals(conversation.getMessages().get(0).getContent(), "I am a pie eater");
        assertEquals(conversation.getMessages().get(1).getContent(), "I am a pie, eater!");
    }


    public ConversationConverterTests() {
        List<Message> myMessages = new ArrayList<Message>();
        myMessages.add(0, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "harry", "I am a pie eater"));
        myMessages.add(1, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123457")), "larry", "I am a pie-eater"));
        myMessages.add(2, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "garry", "I am a pie, eater!"));
        myMessages.add(3, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "harry", "This doesn't contain any blacklisted words"));
        myMessages.add(4, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "harry", "I am a /*redacted*? [*redacted*!"));
        myMessages.add(5, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "garry", "I said something"));
 
        conversation = new Conversation("Conversation name", myMessages);
    }

}
