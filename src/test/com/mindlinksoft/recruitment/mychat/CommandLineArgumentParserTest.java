package com.mindlinksoft.recruitment.mychat;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests class for CommandLineArgumentParser
 */
public class CommandLineArgumentParserTest {

    @Test(expected=InvalidArgumentException.class)
    public void testEmpty() throws InvalidArgumentException {
        String[] args = new String[0];

        new CommandLineArgumentParser().parseCommandLineArguments(args);
    }

    @Test(expected=InvalidArgumentException.class)
    public void testInputArgument() throws InvalidArgumentException {
        String[] args = {"-i",  "chat.txt"};

        new CommandLineArgumentParser().parseCommandLineArguments(args);
    }

    @Test
    public void testDefaults() throws InvalidArgumentException {
        ConversationExporterConfiguration configuration;
        String[] args = {"-i", "chat.txt", "-o", "output.json"};

        configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        assertEquals(configuration.inputFilePath, args[1]);
        assertEquals(configuration.outputFilePath, args[3]);
    }

    @Test
    public void testWithOneExtraArgument() throws InvalidArgumentException {
        ConversationExporterConfiguration configuration;
        String[] args = {"-i", "chat.txt", "-o", "output.json", "-u", "mike"};

        configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        assertEquals(configuration.inputFilePath, args[1]);
        assertEquals(configuration.outputFilePath, args[3]);
        assertEquals(configuration.username, args[5]);
    }

}
