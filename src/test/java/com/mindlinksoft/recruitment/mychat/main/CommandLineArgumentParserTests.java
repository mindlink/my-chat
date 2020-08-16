package com.mindlinksoft.recruitment.mychat.main;

import com.mindlinksoft.recruitment.mychat.exporter.modifier.Modifier;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CommandLineArgumentParserTests {

    private CommandLineArgumentParser parser;

    // invalid arguments
    private final String[] noArgs = {};
    private final String[] oneArg = {"chat.txt"};
    private final String[] subModifiersNeeded = {"chat.txt", "chat.json", "-fu"};
    private final String[] skippedSubModifiers = {"chat.txt", "chat.json", "-fu", "-ob"};
    private final String[] noSubModifiersNeeded = {"chat.txt", "chat.json", "-ob", "john"};
    private final String[] duplicateModifier = {"chat.txt", "chat.json", "-ob", "-ob"};

    // valid, simple arguments
    private final String[] twoArgs = {"chat.txt", "chat.json"};

    // valid arguments without sub arguments
    private final String[] threeArgsObf = {"chat.txt", "chat.json", "-ob"};
    private final String[] threeArgsHideCredit = {"chat.txt", "chat.json", "-hn"};
    private final String[] threeArgsReportUsers = {"chat.txt", "chat.json", "-rp"};

    // valid arguments with sub arguments
    private final String[] fourArgsFilterUser = {"chat.txt", "chat.json", "-fu", "bob"};
    private final String[] fourArgsFilterWord = {"chat.txt", "chat.json", "-fw", "pie"};
    private final String[] fourArgsHideWords = {"chat.txt", "chat.json", "-hw", "pie", "there"};

    // valid arguments with several modifiers and sub arguments
    private final String[] sixArgs = {"chat.txt", "chat.json", "-hw", "pie", "there", "-fu", "bob", "angus"};
    private final String[] sevenArgs = {"chat.txt", "chat.json", "-hw", "pie", "there", "-fu", "bob", "angus", "-ob"};
    private final String[] eightArgs = {"chat.txt", "chat.json", "-hw", "pie", "there", "-fu", "bob", "angus", "-ob", "-rp"};
    private final String[] eightArgsAlt = {"chat.txt", "chat.json", "-ob", "-hw", "pie", "there", "-rp", "-fu", "bob", "angus"};

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
    public void parseMany() {
        // hide keyword ["pie", "there"] and filter users ["bob", "angus"]
        // set up modifiers and sub arguments
        Set<Modifier> expectedModifiers = new HashSet<>();
        expectedModifiers.add(Modifier.HIDE_KEYWORD);
        expectedModifiers.add(Modifier.FILTER_USER);

        Map<Modifier, List<String>> expectedSubModifiers = new HashMap<>();
        expectedSubModifiers.put(Modifier.HIDE_KEYWORD, Arrays.asList("pie", "there"));
        expectedSubModifiers.put(Modifier.FILTER_USER, Arrays.asList("bob", "angus"));

        // parse arguments
        ConversationExporterConfiguration config = parser.parse(sixArgs);

        // should return input, output, the modifiers in the correct order
        // and a map of sub arguments
        assertEquals("chat.txt", config.getInputFilePath());
        assertEquals("chat.json", config.getOutputFilePath());
        assertEquals(expectedModifiers, config.getModifiers());
        assertEquals(expectedSubModifiers, config.getModifierArguments());

        // add obfuscate to arguments
        expectedModifiers.add(Modifier.OBFUSCATE_USERS);
        config = parser.parse(sevenArgs);

        // input, output and sub arguments remain the same
        // list of modifiers now has obfuscate
        assertEquals("chat.txt", config.getInputFilePath());
        assertEquals("chat.json", config.getOutputFilePath());
        assertEquals(expectedModifiers, config.getModifiers());
        assertEquals(expectedSubModifiers, config.getModifierArguments());

        // add report to arguments
        expectedModifiers.add(Modifier.REPORT_ACTIVE_USERS);
        config = parser.parse(eightArgs);

        // input, output and sub arguments remain the same
        // list of modifiers now also has active user reports
        assertEquals("chat.txt", config.getInputFilePath());
        assertEquals("chat.json", config.getOutputFilePath());
        assertEquals(expectedModifiers, config.getModifiers());
        assertEquals(expectedSubModifiers, config.getModifierArguments());
    }

    @Test
    public void shuffledParseManyArgs() {
        // obfuscate users, hide words ["pie", "there"], report users, filter users ["bob", "angus"]
        // set up expected modifiers
        Set<Modifier> expectedModifiers = new HashSet<>();
        expectedModifiers.add(Modifier.OBFUSCATE_USERS);
        expectedModifiers.add(Modifier.HIDE_KEYWORD);
        expectedModifiers.add(Modifier.REPORT_ACTIVE_USERS);
        expectedModifiers.add(Modifier.FILTER_USER);

        // set up expected sub modifiers
        Map<Modifier, List<String>> expectedSubModifiers = new HashMap<>();
        expectedSubModifiers.put(Modifier.HIDE_KEYWORD, Arrays.asList("pie", "there"));
        expectedSubModifiers.put(Modifier.FILTER_USER, Arrays.asList("bob", "angus"));

        // parse arguments
        ConversationExporterConfiguration config = parser.parse(eightArgsAlt);

        // should return input, output, the modifiers in the correct order
        // and a map of sub arguments
        assertEquals("chat.txt", config.getInputFilePath());
        assertEquals("chat.json", config.getOutputFilePath());
        assertEquals(expectedModifiers, config.getModifiers());
        assertEquals(expectedSubModifiers, config.getModifierArguments());
    }

    @Test
    public void parseThreeOptions() {
        // parse obfuscation only
        ConversationExporterConfiguration config = parser.parse(threeArgsObf);

        // should return input, output and a list of modifiers with only obfuscate present
        assertEquals("chat.txt", config.getInputFilePath());
        assertEquals("chat.json", config.getOutputFilePath());
        assertEquals(1, config.getModifiers().size());
        assertTrue(config.getModifiers().contains(Modifier.OBFUSCATE_USERS));
        assertTrue(config.getModifierArguments().isEmpty());

        // parse hide credit only
        config = parser.parse(threeArgsHideCredit);

        // should return input, output and a list of modifiers with only hide credit present
        assertEquals("chat.txt", config.getInputFilePath());
        assertEquals("chat.json", config.getOutputFilePath());
        assertEquals(1, config.getModifiers().size());
        assertTrue(config.getModifiers().contains(Modifier.HIDE_CREDIT_CARD_AND_PHONE_NUMBERS));
        assertTrue(config.getModifierArguments().isEmpty());

        // parse hide credit only
        config = parser.parse(threeArgsReportUsers);

        // should return input, output and a list of modifiers with only hide credit present
        assertEquals("chat.txt", config.getInputFilePath());
        assertEquals("chat.json", config.getOutputFilePath());
        assertEquals(1, config.getModifiers().size());
        assertTrue(config.getModifiers().contains(Modifier.REPORT_ACTIVE_USERS));
        assertTrue(config.getModifierArguments().isEmpty());
    }

    @Test
    public void parseFourOptions() {
        // parse filter user only, with bob as a sub argument
        ConversationExporterConfiguration config = parser.parse(fourArgsFilterUser);

        // should return input, output, a list of modifiers with only FILTER_USER present,
        // and a map of sub arguments, with FILTER_USER mapped to ["bob"]
        assertEquals("chat.txt", config.getInputFilePath());
        assertEquals("chat.json", config.getOutputFilePath());
        assertEquals(1, config.getModifiers().size());
        assertTrue(config.getModifiers().contains(Modifier.FILTER_USER));
        assertEquals(List.of("bob"), config.getModifierArguments().get(Modifier.FILTER_USER));

        // parse filter keyword only, with pie as sub argument
        config = parser.parse(fourArgsFilterWord);

        // should return input, output, a list of modifiers with only FILTER_KEYWORD present,
        // and a map of sub arguments, with FILTER_KEYWORD mapped to ["pie"]
        assertEquals("chat.txt", config.getInputFilePath());
        assertEquals("chat.json", config.getOutputFilePath());
        assertEquals(1, config.getModifiers().size());
        assertTrue(config.getModifiers().contains(Modifier.FILTER_KEYWORD));
        assertEquals(List.of("pie"), config.getModifierArguments().get(Modifier.FILTER_KEYWORD));

        // parse hide keywords only, with pie and there as sub argument
        config = parser.parse(fourArgsHideWords);

        // should return input, output, a list of modifiers with only HIDE_KEYWORD present,
        // and a map of sub arguments, with HIDE_KEYWORD mapped to ["pie", "there"]
        assertEquals("chat.txt", config.getInputFilePath());
        assertEquals("chat.json", config.getOutputFilePath());
        assertEquals(1, config.getModifiers().size());
        assertTrue(config.getModifiers().contains(Modifier.HIDE_KEYWORD));
        assertEquals(List.of("pie", "there"), config.getModifierArguments().get(Modifier.HIDE_KEYWORD));
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseSingleArgument() {
        // single argument should throw exception
        parser.parse(oneArg);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseNoArguments() {
        // no arguments should throw exception
        parser.parse(noArgs);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseNoSubModifiersNeeded() {
        // writing a sub modifier for a modifier that doesn't require one
        // should lead to an exception being thrown
        parser.parse(noSubModifiersNeeded);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseSkipSubModifiers() {
        // writing a modifier right after a modifier that requires
        // a sub modifier should lead to an exception
        parser.parse(skippedSubModifiers);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseSubModifierNeeded() {
        // not writing a sub modifier when it is needed
        // should lead to an exception
        parser.parse(subModifiersNeeded);
    }

    @Test(expected = IllegalArgumentException.class)
    public void duplicateModifier() {
        // duplicate modifiers should lead to an exception
        parser.parse(duplicateModifier);
    }
}