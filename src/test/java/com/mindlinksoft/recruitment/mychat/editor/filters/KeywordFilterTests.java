package com.mindlinksoft.recruitment.mychat.editor.filters;

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
 * Tests for the {@link KeywordFilter}
 */
public class KeywordFilterTests {
    /**
     * test uses conversation at path: testConversations/importedConversations/ConversationEditorTests/filters/KeywordFilterSample.txt
     * for input
     */
    String inputFilePath = "testConversations/importedConversations/ConversationEditorTests/filters/KeywordFilterSample.txt";
    /**
     * test exports conversation at dummy path
     * must be accurate at export times only
     */
    String outputFilePath = "dummy/path";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Test the messages are filtered by the correct keyword
     * keyword: pie
     */
    @Test
    public void testKeywordFilterPie() throws IOException{
        // java MyChatApplication -f pie inputFilePath outputFilePath
        String[] args = new String[4];
        args[0] = "-f";
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
        conversation = editor.applyFilters(conversation);

        MessageInterface[] messages = new MessageInterface[conversation.getMessages().size()];
        conversation.getMessages().toArray(messages);

        assertEquals(4, messages.length);

        assertEquals("I'm good thanks, do you like pie?", messages[0].getContent());
        assertEquals("Hell yes! Are we buying some pie?", messages[1].getContent());
        assertEquals("No, just want to know if there's anybody else in the pie society...", messages[2].getContent());
        assertEquals("YES! I'm the head pie eater there...", messages[3].getContent());
    }

    /**
     * Test the messages are filtered by the correct keyword
     * keyword: are
     */
    @Test
    public void testKeywordFilterAre() throws IOException{
        // java MyChatApplication -f pie inputFilePath outputFilePath
        String[] args = new String[4];
        args[0] = "-f";
        args[1] = "are";
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
        conversation = editor.applyFilters(conversation);

        MessageInterface[] messages = new MessageInterface[conversation.getMessages().size()];
        conversation.getMessages().toArray(messages);

        //case sensitive so loads only 1 message
        assertEquals(1, messages.length);
        assertEquals("how are you?", messages[0].getContent());

    }
}
