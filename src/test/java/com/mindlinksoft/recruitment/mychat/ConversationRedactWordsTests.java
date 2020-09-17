package com.mindlinksoft.recruitment.mychat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.*;
public class ConversationRedactWordsTests {

    @Test
    public void testRedactWords() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args1 = new String[]{"chat.txt","chat.json"};
        ConversationExporterConfiguration configuration1 = new CommandLineArgumentParser().parseCommandLineArguments(args1);
        exporter.exportConversation(configuration1);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation initial_conv = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        ;
        String[] args = new String[]{"chat.txt","chat.json","blacklist=[pie,society]"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        String[] blacklist = new String[]{"pie","society"};
        assertArrayEquals(blacklist,configuration.blacklist);
        exporter.exportConversation(configuration);
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Conversation redacted_conv = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        Iterator <Message> initial_iter = initial_conv.messages.iterator();
        Iterator <Message> redact_iter = redacted_conv.messages.iterator();

        while (initial_iter.hasNext() && redact_iter.hasNext()){
            for (String j:blacklist){
                if (!initial_iter.hasNext() && !redact_iter.hasNext()){
                    break;
                }
                Message i = initial_iter.next();
                if (i.content.contains(j)) {
                    Message redacted_msg = redact_iter.next();
                    assertTrue(i.content.contains(j)
                            && !redacted_msg.content.contains(j)
                            && redacted_msg.content.contains("*redacted*"));
                }
                else {
                    redact_iter.next();
                }
            }
        }
    }

    @Test
    public void testRedactWordsEmpty() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args1 = new String[]{"chat.txt","chat.json"};
        ConversationExporterConfiguration configuration1 = new CommandLineArgumentParser().parseCommandLineArguments(args1);
        exporter.exportConversation(configuration1);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation initial_conv = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        ;
        String[] args = new String[]{"chat.txt","chat.json","blacklist=[]"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        assertArrayEquals(null,configuration.blacklist);
        exporter.exportConversation(configuration);
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        Iterator <Message> initial_iter = initial_conv.messages.iterator();
        Iterator <Message> redacted_iter = c.messages.iterator();

        while (initial_iter.hasNext() && redacted_iter.hasNext()){
                Message unfiltered = initial_iter.next();
                Message filtered = redacted_iter.next();
                assertEquals(unfiltered, filtered);
        }
    }
}
