package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.Constructs.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.Utilities.CommandLineArgumentParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class testParser {
    @Test
    public void testAllArguments() {
        CommandLineArgumentParser commandLineArgumentParser = new CommandLineArgumentParser();

        String[] args = new String[]{"chat.txt", "chat.json", "-name", "bob", "-obf", "-details", "-report"};

        ConversationExporterConfiguration cEC = commandLineArgumentParser.parseCommandLineArguments(args);

        assertEquals(cEC.inputFilePath, "chat.txt");
        assertEquals(cEC.outputFilePath, "chat.json");
        assertEquals(cEC.argument_1, "-name");
        assertEquals(cEC.argument_2, "bob");
        assertEquals(cEC.argument_3, "-obf");
        assertEquals(cEC.argument_4, "-details");
        assertEquals(cEC.argument_5, "-report");
    }

    @Test
    public void testTwoArguments() {
        CommandLineArgumentParser commandLineArgumentParser = new CommandLineArgumentParser();

        String[] args = new String[]{"chat.txt", "chat.json", "-name", "bob"};

        ConversationExporterConfiguration cEC = commandLineArgumentParser.parseCommandLineArguments(args);

        assertEquals(cEC.inputFilePath, "chat.txt");
        assertEquals(cEC.outputFilePath, "chat.json");
        assertEquals(cEC.argument_1, "-name");
        assertEquals(cEC.argument_2, "bob");
        assertEquals(cEC.argument_3, "");
        assertEquals(cEC.argument_4, "");
        assertEquals(cEC.argument_5, "");
    }

    @Test
    public void testDefaultArguments() {
        CommandLineArgumentParser commandLineArgumentParser = new CommandLineArgumentParser();

        String[] args = new String[]{"chat.txt", "chat.json"};

        ConversationExporterConfiguration cEC = commandLineArgumentParser.parseCommandLineArguments(args);

        assertEquals(cEC.inputFilePath, "chat.txt");
        assertEquals(cEC.outputFilePath, "chat.json");
        assertEquals(cEC.argument_1, "");
        assertEquals(cEC.argument_2, "");
        assertEquals(cEC.argument_3, "");
        assertEquals(cEC.argument_4, "");
        assertEquals(cEC.argument_5, "");
    }
}
