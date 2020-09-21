package com.mindlinksoft.recruitment.mychat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.Instant;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class FilterKeywordTest {

    Model model;
    FilterKeyword filterKeyword;
    CommandLineArgumentParser parser;
    Controller controller;
    private final InputStream sysInBackup = System.in;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Before
    public void setUp() throws Exception {
        model = new Model("chat.txt");
        parser = new CommandLineArgumentParser();
        System.setOut(new PrintStream(output));
    }

    @After
    public void tearDown() {
        System.setIn(sysInBackup);
        System.setOut(null);
    }

    @Test
    public void filterKeyword() throws Exception {
        String[] argument = {"Keyword", "there"};

        filterKeyword = new FilterKeyword(argument);
        Conversation conversation = model.readConversation("chat.txt");

        Conversation filtConv = filterKeyword.filterConversation(conversation, argument[1]);

        Message[] ms = new Message[filtConv.getMessages().size()];
        filtConv.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].getSenderId(), "bob");
        assertEquals(ms[0].getContent(), "Hello there!");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470914));
        assertEquals(ms[1].getSenderId(), "bob");
        assertEquals(ms[1].getContent(), "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[2].getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms[2].getSenderId(), "angus");
        assertEquals(ms[2].getContent(), "YES! I'm the head pie eater there...");
    }

    @Test
    public void exportFilterKeyword() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream("keyword there\nexit".getBytes("UTF-8"));

        System.setIn(in);
        ConversationExporter.main(new String[]{"chat.txt"});
        String out = output.toString("UTF-8");
        assertFalse(out.contains("Invalid argument"));
    }

    @Test
    public void filterWrongKeyword() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream("keyword ciao\nexit".getBytes("UTF-8"));

        System.setIn(in);
        ConversationExporter.main(new String[]{"chat.txt"});
        String out = output.toString("UTF-8");
        assertTrue(out.contains("conversation not exported, messages do not include the keyword specified."));
        assertTrue(out.contains("try again with a different keyword."));

    }

    @Test
    public void filterWithNoArgs() throws Exception {
        String input = "keyword \n" + "exit\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes("UTF-8"));

        System.setIn(in);
        ConversationExporter.main(new String[]{"chat.txt"});
        String out = output.toString("UTF-8");
        assertTrue(out.contains("Invalid or empty argument, please try again"));

    }
}
