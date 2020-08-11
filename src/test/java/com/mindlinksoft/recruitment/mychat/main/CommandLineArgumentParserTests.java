package com.mindlinksoft.recruitment.mychat.main;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.mindlinksoft.recruitment.mychat.exporter.modifier.Modifier;

import org.junit.Before;
import org.junit.Test;

public class CommandLineArgumentParserTests {
    
    private CommandLineArgumentParser parser;

    private final String[] twoArgs = {"chat.txt", "chat.json"};
    private final String[] threeArgsObf = {"chat.txt", "chat.json", "-ob"};
    private final String[] threeArgsHideCredit = {"chat.txt", "chat.json", "-hn"};
    private final String[] fourArgsFilterUser = {"chat.txt", "chat.json", "-fu", "bob"};
    private final String[] fourArgsFilterWord = {"chat.txt", "chat.json", "-fw", "pie"};
    private final String[] fourArgsHideWords = {"chat.txt", "chat.json", "-hw", "pie", "there"};

    @Before
    public void setUp() {
        parser = new CommandLineArgumentParser();
    }

    @Test
    public void simpleConfiguration() {
        ConversationExporterConfiguration config = parser.parse(twoArgs);
        
        assertEquals("chat.txt", config.getInputFilePath());
        assertEquals("chat.json", config.getOutputFilePath());
    }

    @Test
    public void parseThreeOptions() {
        ConversationExporterConfiguration config = parser.parse(threeArgsObf);
        
        assertEquals("chat.txt", config.getInputFilePath());
        assertEquals("chat.json", config.getOutputFilePath());
        assertEquals(Modifier.OBFUSCATE_USERS, config.getModifier());
        assertNull(config.getModifierArguments());

        config = parser.parse(threeArgsHideCredit);
        
        assertEquals("chat.txt", config.getInputFilePath());
        assertEquals("chat.json", config.getOutputFilePath());
        assertEquals(Modifier.HIDE_CREDIT_CARD_AND_PHONE_NUMBERS, config.getModifier());
        assertNull(config.getModifierArguments());
    }

    @Test
    public void parseFourOptions() {
        ConversationExporterConfiguration config = parser.parse(fourArgsFilterUser);
        
        assertEquals("chat.txt", config.getInputFilePath());
        assertEquals("chat.json", config.getOutputFilePath());
        assertEquals(Modifier.FILTER_USER, config.getModifier());
        assertArrayEquals(new String[]{"bob"}, config.getModifierArguments());

        config = parser.parse(fourArgsFilterWord);
        
        assertEquals("chat.txt", config.getInputFilePath());
        assertEquals("chat.json", config.getOutputFilePath());
        assertEquals(Modifier.FILTER_KEYWORD, config.getModifier());
        assertArrayEquals(new String[]{"pie"}, config.getModifierArguments());

        config = parser.parse(fourArgsHideWords);
        
        assertEquals("chat.txt", config.getInputFilePath());
        assertEquals("chat.json", config.getOutputFilePath());
        assertEquals(Modifier.HIDE_KEYWORD, config.getModifier());
        assertArrayEquals(new String[]{"pie", "there"}, config.getModifierArguments());
    }
}