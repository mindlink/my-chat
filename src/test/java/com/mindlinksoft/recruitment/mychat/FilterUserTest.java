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
public class FilterUserTest {

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
//        ConversationExporter.main(new String[]{"chat.txt", "chat.json"});
    }

    @After
    public void tearDown() {
        System.setIn(sysInBackup);
        System.setOut(null);
    }

    @Test
    public void filterUser() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream("user bob\nexit".getBytes("UTF-8"));

        System.setIn(in);
        ConversationExporter.main(new String[]{"chat.txt", "chat.json"});
        String out = output.toString("UTF-8");
        assertTrue(out.contains("Conversation exported from chat.txt to chat.json by user: bob"));
    }

    @Test
    public void filterWrongUser() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream("user juan\nexit".getBytes("UTF-8"));

        System.setIn(in);
        ConversationExporter.main(new String[]{"chat.txt", "chat.json"});
        String out = output.toString("UTF-8");
        assertTrue(out.contains("Conversation has been exported empty, User does not exist."));
    }

    @Test
    public void filterWithNoArgs() throws Exception {
        String input = "user \n" + "exit\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes("UTF-8"));

        System.setIn(in);
        ConversationExporter.main(new String[]{"chat.txt", "chat.json"});
        String out = output.toString("UTF-8");
        assertTrue(out.contains("Invalid argument"));
    }

}
