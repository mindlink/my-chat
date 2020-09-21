package com.mindlinksoft.recruitment.mychat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CommandLineArgumentParserTest {

    Model model;
    CommandLineArgumentParser parser;
    Controller controller;
    private final InputStream sysInBackup = System.in;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    public CommandLineArgumentParserTest() {
    }

    @Before
    public void setUp() {
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
    public void printWelcome() {

        String outcome = parser.printCommands();

        String main = "\nEnter command: user [userID], keyword [keyword], hide [keyword], hideUsers, exit\n";
        String line = "> ";

        String result = main + line;

        assertEquals(result, outcome);

    }

    @Test
    public void wrongCommand() throws Exception {

        ByteArrayInputStream in = new ByteArrayInputStream("send\nexit".getBytes("UTF-8"));

        System.setIn(in);
        ConversationExporter.main(new String[]{"chat.txt", "chat.json"});

        String result = "You can't use this command right now..";
        String result2 = "Command is not recognised, try again and select one of the commands available.";

        String out = output.toString("UTF-8");

        assertTrue(out.contains(result));
        assertTrue(out.contains(result2));

    }
}
