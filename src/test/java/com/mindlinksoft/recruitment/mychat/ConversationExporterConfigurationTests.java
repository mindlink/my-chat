package com.mindlinksoft.recruitment.mychat;


import org.junit.Test;
import picocli.CommandLine;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterConfigurationTests {
    /**
     * Tests that parameters are correctly parsed into the configuration
     */
    @Test
    public void testParameterParsing() {
        ConversationExporterConfiguration configuration;
        CommandLine cmd;
        String[] args;

        // - JUST INPUT AND OUTPUT FILES -----------------------------------------------------
        args = new String[]{"-i", "chat.txt", "-o", "out.json"};

        configuration = new ConversationExporterConfiguration();
        cmd = new CommandLine(configuration);
        cmd.parseArgs(args);

        // input and output file parameters
        assertEquals("chat.txt", configuration.inputFilePath);
        assertEquals("out.json", configuration.outputFilePath);
        // extra feature parameters
        assertNull(configuration.user);
        assertNull(configuration.keyword);
        assertNull(configuration.blacklistedWords);
        assertFalse(configuration.report);


        // - EXTRA PARAMETERS ----------------------------------------------------------------
        args = new String[]{"-i", "chat2.txt", "-o", "out2.json", "-b", "you", "-u", "mike", "-k", "pie", "-b", "there", "-r"};

        configuration = new ConversationExporterConfiguration();
        cmd = new CommandLine(configuration);
        cmd.parseArgs(args);

        // input and output file parameters
        assertEquals("chat2.txt", configuration.inputFilePath);
        assertEquals("out2.json", configuration.outputFilePath);
        // extra feature parameters
        assertEquals("mike", configuration.user);
        assertEquals("pie", configuration.keyword);
        assertEquals(Arrays.toString(new String[]{"you, there"}), Arrays.toString(configuration.blacklistedWords));
        assertTrue(configuration.report);
    }
}
