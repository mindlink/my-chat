/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindlinksoft.recruitment.mychat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author esteban
 */
public class FilterKeywordTest {

    Model model;
    CommandLineArgumentParser parser;
    Controller controller;
    private final InputStream sysInBackup = System.in;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Before
    public void setUp() throws Exception {
        model = new Model("chat.txt", "chat.json");
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
        ByteArrayInputStream in = new ByteArrayInputStream("keyword there\nexit".getBytes("UTF-8"));

        System.setIn(in);
        ConversationExporter.main(new String[]{"chat.txt", "chat.json"});
        String out = output.toString("UTF-8");
        assertFalse(out.contains("Invalid argument"));
    }

    @Test
    public void filterWrongKeyword() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream("keyword ciao\nexit".getBytes("UTF-8"));

        System.setIn(in);
        ConversationExporter.main(new String[]{"chat.txt", "chat.json"});
        String out = output.toString("UTF-8");
        assertTrue(out.contains("Conversation has been exported empty, Keyword is not present within the messages."));
    }

    @Test
    public void filterWithNoArgs() throws Exception {
        String input = "keyword \n" + "exit\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes("UTF-8"));

        System.setIn(in);
        ConversationExporter.main(new String[]{"chat.txt", "chat.json"});
        String out = output.toString("UTF-8");
        assertTrue(out.contains("Invalid or empty argument, please try again"));

    }
}
