package com.mindlinksoft.recruitment.mychat.commandlineparser;

import org.apache.commons.cli.CommandLine;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link CommandLineArgumentParser}
 */
public class CommandLineArgumentParserTest {

    /**
     * test uses conversation at path: testConversations/importedConversations/CommandLineParserTests/chat.txt
     * for input
     */
    String inputFilePath = "testConversations/importedConversations/CommandLineParserTests/chat.txt";
    /**
     * test uses conversation at path: testConversations/importedConversations/CommandLineParserTests/chat.json
     * for output
     */
    String outputFilePath = "testConversations/importedConversations/CommandLineParserTests/chat.json";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Testing Arguments
     * 2 arguments presented.
     */
    @Test
    public void testCommandLineParserCorrectArguments() {

        String[] args = new String[2];
        args[0] = this.inputFilePath;
        args[1] = this.outputFilePath;

        CommandLine cli = new CommandLineArgumentParser().parseCommandLineArguments(args);
        String[] cliArgs = cli.getArgs();

        assertEquals(2, cliArgs.length);
        assertEquals( "testConversations/importedConversations/CommandLineParserTests/chat.txt", cliArgs[0] );
        assertEquals( "testConversations/importedConversations/CommandLineParserTests/chat.json", cliArgs[1] );
    }

    /**
     * Testing Exception
     * 1 arguments presented.
     * @throws IllegalArgumentException
     */
    @Test
    public void testCommandLineParserIncorrectArguments1() throws IllegalArgumentException{
        String[] args = new String[1];
        args[0] = this.inputFilePath;

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Wrong number of arguments - (1) presented. 2 arguments required");
        CommandLine cli = new CommandLineArgumentParser().parseCommandLineArguments(args);
    }

    /**
     * Testing Exception
     * 3 arguments presented.
     * @throws IllegalArgumentException
     */
    @Test
    public void testCommandLineParserIncorrectArguments2() throws IllegalArgumentException{
        String[] args = new String[3];
        args[0] = this.inputFilePath;
        args[1] = this.outputFilePath;
        args[2] = this.inputFilePath;

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Wrong number of arguments - (3) presented. 2 arguments required");
        CommandLine cli = new CommandLineArgumentParser().parseCommandLineArguments(args);
    }

    /**
     * Testing if correct options are created.
     */
    @Test
    public void testCommandLineParserCorrectWithOptions() {
        String[] args = new String[4];
        //testing hide sensitive info short option
        args[0] = "-x";
        //testing obfuscate user ids long option
        args[1] = "--obfuscate-user-ids";
        args[2] = this.inputFilePath;
        args[3] = this.outputFilePath;

        CommandLine cli = new CommandLineArgumentParser().parseCommandLineArguments(args);
        String[] cliArgs = cli.getArgs();

        assertEquals(2, cliArgs.length);
        assertEquals( "testConversations/importedConversations/CommandLineParserTests/chat.txt", cliArgs[0] );
        assertEquals( "testConversations/importedConversations/CommandLineParserTests/chat.json", cliArgs[1] );
        //testing if hide sensitive info exists with long option
        assertEquals( true, cli.hasOption("hide-sensitive-data"));
        //testing if obfuscate user ids exists with short option
        assertEquals( true, cli.hasOption("o"));
    }

    /**
     * Testing Exception
     * -u expects a username as value but none is presented
     * @throws IllegalArgumentException
     */
    @Test
    public void testCommandLineParserIncorrectWithOptionThatTakesArgument1() throws IllegalArgumentException{
        String[] args = new String[4];
        args[0] = "-u";
        args[1] = "--obfuscate-user-ids";
        args[2] = this.inputFilePath;
        args[3] = this.outputFilePath;

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Options parsing failed. Use \"java MyChatApplication -h\" or \"java MyChatApplication --help\" form a qualified directory for a list of available options.");
        CommandLine cli = new CommandLineArgumentParser().parseCommandLineArguments(args);
    }

    /**
     * Testing exception
     * testing user short option without value or other options.
     * "chat.txt" will be considered as the username value which means
     * there are is only one argument now - "chat.json"
     * @throws IllegalArgumentException
     */
    @Test
    public void testCommandLineParserIncorrectWithOptionThatTakesArgument2() throws IllegalArgumentException{
        String[] args = new String[3];
        args[0] = "-u";
        args[1] = this.inputFilePath;
        args[2] = this.outputFilePath;

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Wrong number of arguments - (1) presented. 2 arguments required");
        CommandLine cli = new CommandLineArgumentParser().parseCommandLineArguments(args);

    }

    /**
     * Testing option that takes value
     * testing user short option with value.
     * "bob" will be considered as the username
     */
    @Test
    public void testCommandLineParserCorrectWithOptionThatTakesArgument() {
        String[] args = new String[4];
        args[0] = "-u";
        args[1] = "bob";
        args[2] = this.inputFilePath;
        args[3] = this.outputFilePath;

        CommandLine cli = new CommandLineArgumentParser().parseCommandLineArguments(args);
        String[] cliArgs = cli.getArgs();

        assertEquals(2, cliArgs.length);
        assertEquals( "testConversations/importedConversations/CommandLineParserTests/chat.txt", cliArgs[0] );
        assertEquals( "testConversations/importedConversations/CommandLineParserTests/chat.json", cliArgs[1] );
        assertEquals( true, cli.hasOption("username"));
        assertEquals( "bob", cli.getOptionValue("u"));
    }
}
