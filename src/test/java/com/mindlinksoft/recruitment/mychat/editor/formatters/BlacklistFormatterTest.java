package com.mindlinksoft.recruitment.mychat.editor.formatters;

import com.mindlinksoft.recruitment.mychat.commandlineparser.CommandLineArgumentParser;
import com.mindlinksoft.recruitment.mychat.conversation.ConversationInterface;
import com.mindlinksoft.recruitment.mychat.editor.ConversationEditor;
import com.mindlinksoft.recruitment.mychat.loader.ConversationLoader;
import com.mindlinksoft.recruitment.mychat.message.MessageInterface;
import org.apache.commons.cli.CommandLine;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link BlacklistFormatter}
 */
public class BlacklistFormatterTest {
    /**
     * test uses conversation at path: testConversations/importedConversations/ConversationEditorTests/formatters/BlacklistFormatterSample.txt
     * for input
     */
    String inputFilePath = "testConversations/importedConversations/ConversationEditorTests/formatters/BlacklistFormatterSample.txt";
    /**
     * test exports conversation at dummy path
     * must be accurate at export times only
     */
    String outputFilePath = "dummy/path";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Test the blacklisted word is formatted
     * blacklist: pie
     */
    @Test
    public void testBlacklistFormatterWithPie() throws IOException {
        // java MyChatApplication -b pie inputFilePath outputFilePath
        String[] args = new String[4];
        args[0] = "-b";
        args[1] = "pie";
        args[2] = this.inputFilePath;
        args[3] = this.outputFilePath;

        CommandLine cli = new CommandLineArgumentParser().parseCommandLineArguments(args);
        String[] cliArgs = cli.getArgs();
        ConversationInterface conversation = new ConversationLoader().loadConversation(cliArgs[0]);

        //create editor instance
        ConversationEditor editor = new ConversationEditor();
        //load filters and formatters using cli options
        editor.loadFiltersAndFormatters(cli);
        //apply filter
        conversation = editor.applyFormatters(conversation);

        MessageInterface[] messages = new MessageInterface[conversation.getMessages().size()];
        conversation.getMessages().toArray(messages);

        assertEquals(7, messages.length);

        assertEquals("Hello there!", messages[0].getContent());
        assertEquals("how are you?", messages[1].getContent());
        assertEquals("I'm good thanks, do you like *redacted*?", messages[2].getContent());
        assertEquals("no, let me ask Angus...", messages[3].getContent());
        assertEquals("Hell yes! Are we buying some *redacted*?", messages[4].getContent());
        //all occurrences of pie have been replaced
        assertEquals("No, just want to know if anyone likes *redacted* or if there's anybody else in the *redacted* society...", messages[5].getContent());
        assertEquals("YES! I'm the head *redacted* eater there...", messages[6].getContent());
    }

    /**
     * Test the blacklisted word is formatted
     * blacklist: head,pie
     */
    @Test
    public void testBlacklistFormatterWithHeadPie() throws IOException {
        // java MyChatApplication -b head,pie inputFilePath outputFilePath
        String[] args = new String[4];
        args[0] = "-b";
        args[1] = "head,pie";
        args[2] = this.inputFilePath;
        args[3] = this.outputFilePath;

        CommandLine cli = new CommandLineArgumentParser().parseCommandLineArguments(args);
        String[] cliArgs = cli.getArgs();
        ConversationInterface conversation = new ConversationLoader().loadConversation(cliArgs[0]);

        //create editor instance
        ConversationEditor editor = new ConversationEditor();
        //load filters and formatters using cli options
        editor.loadFiltersAndFormatters(cli);
        //apply formatter
        conversation = editor.applyFormatters(conversation);

        MessageInterface[] messages = new MessageInterface[conversation.getMessages().size()];
        conversation.getMessages().toArray(messages);

        assertEquals(7, messages.length);

        //same as test above
        assertEquals("Hello there!", messages[0].getContent());
        assertEquals("how are you?", messages[1].getContent());
        assertEquals("I'm good thanks, do you like *redacted*?", messages[2].getContent());
        assertEquals("no, let me ask Angus...", messages[3].getContent());
        assertEquals("Hell yes! Are we buying some *redacted*?", messages[4].getContent());
        //all occurrences of pie have been replaced
        assertEquals("No, just want to know if anyone likes *redacted* or if there's anybody else in the *redacted* society...", messages[5].getContent());
        //important test, both words redacted
        assertEquals("YES! I'm the *redacted* *redacted* eater there...", messages[6].getContent());
    }
}
