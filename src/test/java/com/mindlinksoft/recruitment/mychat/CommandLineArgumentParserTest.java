package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.constructs.ConversationExporterConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * Tests for the {@link CommandLineArgumentParser}.
 */
public class CommandLineArgumentParserTest
{
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void parseCommandLineArguments_basic()
    {
        String[] arguments = {"chat.txt", "chat.json"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);

        assertEquals("chat.txt", c.getInputFilePath());
        assertEquals("chat.json", c.getOutputFilePath());
        assertNull(c.getUser());
        assertNull(c.getKeyword());
        assertNull(c.getWordsToHide());
    }

    @Test
    public void parseCommandLineArguments_user()
    {
        String[] arguments = {"chat.txt", "chat.json", "-u", "bob"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);

        assertEquals("chat.txt", c.getInputFilePath());
        assertEquals("chat.json", c.getOutputFilePath());
        assertEquals("bob", c.getUser());
        assertNull(c.getKeyword());
        assertNull(c.getWordsToHide());
    }

    @Test
    public void parseCommandLineArguments_user_exception()
    {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("No user specified");

        String[] arguments = {"chat.txt", "chat.json", "-u"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);
    }

    @Test
    public void parseCommandLineArguments_keyword()
    {
        String[] arguments = {"chat.txt", "chat.json", "-k", "hello"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);

        assertEquals("chat.txt", c.getInputFilePath());
        assertEquals("chat.json", c.getOutputFilePath());
        assertNull(c.getUser());
        assertEquals("hello", c.getKeyword());
        assertNull(c.getWordsToHide());
    }

    @Test
    public void parseCommandLineArguments_keyword_exception()
    {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("No keyword specified");

        String[] arguments = {"chat.txt", "chat.json", "-k"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);
    }

    @Test
    public void parseCommandLineArguments_wordsToHide()
    {
        String[] arguments = {"chat.txt", "chat.json", "-w", "hello,there,person"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);

        String[] wordsToHideExpected = {"hello", "there", "person"};

        assertEquals("chat.txt", c.getInputFilePath());
        assertEquals("chat.json", c.getOutputFilePath());
        assertNull(c.getUser());
        assertNull(c.getKeyword());
        assertArrayEquals(wordsToHideExpected, c.getWordsToHide());
    }

    @Test
    public void parseCommandLineArguments_wordsToHide_exception()
    {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("No words to hide (blacklisted words) specified");

        String[] arguments = {"chat.txt", "chat.json", "-w"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);
    }

    @Test
    public void parseCommandLineArguments_user_keyword_1()
    {
        String[] arguments = {"chat.txt", "chat.json", "-u", "mike", "-k", "long"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);

        assertEquals("chat.txt", c.getInputFilePath());
        assertEquals("chat.json", c.getOutputFilePath());
        assertEquals("mike", c.getUser());
        assertEquals("long", c.getKeyword());
        assertNull(c.getWordsToHide());
    }

    @Test
    public void parseCommandLineArguments_user_keyword_2()
    {
        String[] arguments = {"chat.txt", "chat.json", "-k", "long", "-u", "mike"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);

        assertEquals("chat.txt", c.getInputFilePath());
        assertEquals("chat.json", c.getOutputFilePath());
        assertEquals("mike", c.getUser());
        assertEquals("long", c.getKeyword());
        assertNull(c.getWordsToHide());
    }

    @Test
    public void parseCommandLineArguments_user_keyword_exception_1()
    {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("No user specified");

        String[] arguments = {"chat.txt", "chat.json", "-u", "-k", "long"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);
    }

    @Test
    public void parseCommandLineArguments_user_keyword_exception_2()
    {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("No keyword specified");

        String[] arguments = {"chat.txt", "chat.json", "-k", "-u", "timothy"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);
    }

    @Test
    public void parseCommandLineArguments_user_wordsToHide_1()
    {
        String[] arguments = {"chat.txt", "chat.json", "-u", "mike", "-w", "hello,there,person"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);

        String[] wordsToHideExpected = {"hello", "there", "person"};

        assertEquals("chat.txt", c.getInputFilePath());
        assertEquals("chat.json", c.getOutputFilePath());
        assertEquals("mike", c.getUser());
        assertNull(c.getKeyword());
        assertArrayEquals(wordsToHideExpected, c.getWordsToHide());
    }

    @Test
    public void parseCommandLineArguments_user_wordsToHide_2()
    {
        String[] arguments = {"chat.txt", "chat.json", "-w", "hello,there,person", "-u", "mike"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);

        String[] wordsToHideExpected = {"hello", "there", "person"};

        assertEquals("chat.txt", c.getInputFilePath());
        assertEquals("chat.json", c.getOutputFilePath());
        assertEquals("mike", c.getUser());
        assertNull(c.getKeyword());
        assertArrayEquals(wordsToHideExpected, c.getWordsToHide());
    }

    @Test
    public void parseCommandLineArguments_keyword_wordsToHide_1()
    {
        String[] arguments = {"chat.txt", "chat.json", "-k", "before", "-w", "hello,there,person"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);

        String[] wordsToHideExpected = {"hello", "there", "person"};

        assertEquals("chat.txt", c.getInputFilePath());
        assertEquals("chat.json", c.getOutputFilePath());
        assertNull(c.getUser());
        assertEquals("before", c.getKeyword());
        assertArrayEquals(wordsToHideExpected, c.getWordsToHide());
    }

    @Test
    public void parseCommandLineArguments_keyword_wordsToHide_2()
    {
        String[] arguments = {"chat.txt", "chat.json", "-w", "hello,there,person", "-k", "before"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);

        String[] wordsToHideExpected = {"hello", "there", "person"};

        assertEquals("chat.txt", c.getInputFilePath());
        assertEquals("chat.json", c.getOutputFilePath());
        assertNull(c.getUser());
        assertEquals("before", c.getKeyword());
        assertArrayEquals(wordsToHideExpected, c.getWordsToHide());
    }

    @Test
    public void parseCommandLineArguments_user_keyword_wordsToHide_1()
    {
        String[] arguments = {"chat.txt", "chat.json", "-u", "timothy", "-k", "hair", "-w", "price,object,bread"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);

        String[] wordsToHideExpected = {"price", "object", "bread"};

        assertEquals("chat.txt", c.getInputFilePath());
        assertEquals("chat.json", c.getOutputFilePath());
        assertEquals("timothy", c.getUser());
        assertEquals("hair", c.getKeyword());
        assertArrayEquals(wordsToHideExpected, c.getWordsToHide());
    }

    @Test
    public void parseCommandLineArguments_user_keyword_wordsToHide_2()
    {
        String[] arguments = {"chat.txt", "chat.json", "-u", "timothy", "-w", "price,object,bread", "-k", "hair"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);

        String[] wordsToHideExpected = {"price", "object", "bread"};

        assertEquals("chat.txt", c.getInputFilePath());
        assertEquals("chat.json", c.getOutputFilePath());
        assertEquals("timothy", c.getUser());
        assertEquals("hair", c.getKeyword());
        assertArrayEquals(wordsToHideExpected, c.getWordsToHide());
    }

    @Test
    public void parseCommandLineArguments_user_keyword_wordsToHide_3()
    {
        String[] arguments = {"chat.txt", "chat.json", "-k", "hair", "-u", "timothy", "-w", "price,object,bread"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);

        String[] wordsToHideExpected = {"price", "object", "bread"};

        assertEquals("chat.txt", c.getInputFilePath());
        assertEquals("chat.json", c.getOutputFilePath());
        assertEquals("timothy", c.getUser());
        assertEquals("hair", c.getKeyword());
        assertArrayEquals(wordsToHideExpected, c.getWordsToHide());
    }

    @Test
    public void parseCommandLineArguments_user_keyword_wordsToHide_4()
    {
        String[] arguments = {"chat.txt", "chat.json", "-k", "hair", "-w", "price,object,bread", "-u", "timothy"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);

        String[] wordsToHideExpected = {"price", "object", "bread"};

        assertEquals("chat.txt", c.getInputFilePath());
        assertEquals("chat.json", c.getOutputFilePath());
        assertEquals("timothy", c.getUser());
        assertEquals("hair", c.getKeyword());
        assertArrayEquals(wordsToHideExpected, c.getWordsToHide());
    }

    @Test
    public void parseCommandLineArguments_user_keyword_wordsToHide_5()
    {
        String[] arguments = {"chat.txt", "chat.json", "-w", "price,object,bread", "-u", "timothy", "-k", "hair",};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);

        String[] wordsToHideExpected = {"price", "object", "bread"};

        assertEquals("chat.txt", c.getInputFilePath());
        assertEquals("chat.json", c.getOutputFilePath());
        assertEquals("timothy", c.getUser());
        assertEquals("hair", c.getKeyword());
        assertArrayEquals(wordsToHideExpected, c.getWordsToHide());
    }

    @Test
    public void parseCommandLineArguments_user_keyword_wordsToHide_6()
    {
        String[] arguments = {"chat.txt", "chat.json", "-w", "price,object,bread", "-k", "hair", "-u", "timothy"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);

        String[] wordsToHideExpected = {"price", "object", "bread"};

        assertEquals("chat.txt", c.getInputFilePath());
        assertEquals("chat.json", c.getOutputFilePath());
        assertEquals("timothy", c.getUser());
        assertEquals("hair", c.getKeyword());
        assertArrayEquals(wordsToHideExpected, c.getWordsToHide());
    }

    @Test
    public void parseCommandLineArguments_user_keyword_wordsToHide_exception()
    {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("No words to hide (blacklisted words) specified");

        String[] arguments = {"chat.txt", "chat.json", "-w", "-k", "hair", "-u", "timothy"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);
    }

    @Test
    public void parseCommandLineArguments_basic_exception()
    {
        exceptionRule.expect(IllegalStateException.class);
        exceptionRule.expectMessage("Unexpected command line parameter");

        String[] arguments = {"chat.txt", "chat.json", "-z"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);
    }

    @Test
    public void parseCommandLineArguments_hideCCPN()
    {
        String[] arguments = {"chatDetails.txt", "chatDetails.json", "-hideCCPN"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);

        assertEquals("chatDetails.txt", c.getInputFilePath());
        assertEquals("chatDetails.json", c.getOutputFilePath());
        assertNull(c.getUser());
        assertNull(c.getKeyword());
        assertNull(c.getWordsToHide());
        assertTrue(c.isHideCCPN());
    }

    @Test
    public void parseCommandLineArguments_user_tom_hideCCPN()
    {
        String[] arguments = {"chatDetails.txt", "chatDetails.json", "-u", "tom", "-hideCCPN"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);

        assertEquals("chatDetails.txt", c.getInputFilePath());
        assertEquals("chatDetails.json", c.getOutputFilePath());
        assertEquals("tom", c.getUser());
        assertNull(c.getKeyword());
        assertNull(c.getWordsToHide());
        assertTrue(c.isHideCCPN());
    }

    @Test
    public void parseCommandLineArguments_keyword_chair_hideCCPN()
    {
        String[] arguments = {"chatDetails.txt", "chatDetails.json", "-hideCCPN", "-k", "chair"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);

        assertEquals("chatDetails.txt", c.getInputFilePath());
        assertEquals("chatDetails.json", c.getOutputFilePath());
        assertNull(c.getUser());
        assertEquals("chair", c.getKeyword());
        assertNull(c.getWordsToHide());
        assertTrue(c.isHideCCPN());
    }

    @Test
    public void parseCommandLineArguments_obfuscate()
    {
        String[] arguments = {"chatDetails.txt", "chatDetails.json", "-obfUsers"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);

        assertEquals("chatDetails.txt", c.getInputFilePath());
        assertEquals("chatDetails.json", c.getOutputFilePath());
        assertNull(c.getUser());
        assertNull(c.getKeyword());
        assertNull(c.getWordsToHide());
        assertTrue(c.isObf());
    }

    @Test
    public void parseCommandLineArguments_user_tom_obfuscate()
    {
        String[] arguments = {"chatDetails.txt", "chatDetails.json", "-u", "tom", "-obfUsers"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);

        assertEquals("chatDetails.txt", c.getInputFilePath());
        assertEquals("chatDetails.json", c.getOutputFilePath());
        assertEquals("tom", c.getUser());
        assertNull(c.getKeyword());
        assertNull(c.getWordsToHide());
        assertTrue(c.isObf());
    }

    @Test
    public void parseCommandLineArguments_keyword_chair_obfuscate()
    {
        String[] arguments = {"chatDetails.txt", "chatDetails.json", "-obfUsers", "-k", "chair"};
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration c = parser.parseCommandLineArguments(arguments);

        assertEquals("chatDetails.txt", c.getInputFilePath());
        assertEquals("chatDetails.json", c.getOutputFilePath());
        assertNull(c.getUser());
        assertEquals("chair", c.getKeyword());
        assertNull(c.getWordsToHide());
        assertTrue(c.isObf());
    }

    // TODO: Add unit test for new command line args.
}
