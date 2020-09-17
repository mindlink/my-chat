package com.mindlinksoft.recruitment.mychat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.*;

public class ConversationFilteringTests {

    @Test
    public void testFilterKeywordExistsInConversation() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"chat.txt", "chat.json","keyword=hello"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        assertEquals(configuration.keyword,"hello");
        exporter.exportConversation(configuration);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        for (Message i: c.messages){
            System.out.println(i.content);
            // included Regex for word to be case insensitive
            assertTrue(i.content.matches("(?i).*" +"hello" + ".*"));
        }
    }


    @Test
    public void testFilterKeywordDoesNotExistInConversation() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"chat.txt", "chat.json","keyword=nope"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        assertEquals(configuration.keyword,"nope");
        exporter.exportConversation(configuration);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        assertEquals(0, c.messages.size());
    }

    @Test
    public void testFilterKeywordNotPartOfOtherWords() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"chat.txt", "chat.json","keyword=i"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        assertEquals(configuration.keyword,"i");
        exporter.exportConversation(configuration);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        assertEquals(c.messages.size(),0);
    }

    @Test
    public void testFilterbyUsername() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"chat.txt","chat.json","user=bob"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        assertEquals(configuration.user,"bob");
        exporter.exportConversation(configuration);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        for (Message i:c.messages){
            assertEquals(i.senderId,"bob");
        }
    }

    @Test
    public void testFilterbyNonExistentUsername() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"chat.txt","chat.json","user=dave"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        assertEquals(configuration.user,"dave");
        exporter.exportConversation(configuration);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        assertEquals(0, c.messages.size());
    }


}
