package com.mindlinksoft.recruitment.mychat.editor;

import com.mindlinksoft.recruitment.mychat.commandlineparser.CommandLineArgumentParser;
import com.mindlinksoft.recruitment.mychat.conversation.ConversationInterface;
import com.mindlinksoft.recruitment.mychat.editor.filters.ConversationFilterInterface;
import com.mindlinksoft.recruitment.mychat.editor.filters.KeywordFilter;
import com.mindlinksoft.recruitment.mychat.editor.filters.UserNameFilter;
import com.mindlinksoft.recruitment.mychat.editor.formatters.BlacklistFormatter;
import com.mindlinksoft.recruitment.mychat.editor.formatters.MessageFormatterInterface;
import com.mindlinksoft.recruitment.mychat.editor.formatters.ObfuscateIdFormatter;
import com.mindlinksoft.recruitment.mychat.editor.formatters.SensitiveDataFormatter;
import com.mindlinksoft.recruitment.mychat.loader.ConversationLoader;
import com.mindlinksoft.recruitment.mychat.message.MessageInterface;
import org.apache.commons.cli.CommandLine;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the {@link ConversationEditor}
 */
public class ConversationEditorTests {

    /**
     * test uses conversation at path: testConversations/importedConversations/ConversationEditorTests/chat.txt
     * for input
     */
    String inputFilePath = "testConversations/importedConversations/ConversationEditorTests/chat.txt";
    /**
     * test exports conversation at dummy path
     * must be accurate at export times only
     */
    String outputFilePath = "dummy/path";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Test that the correct filter/formatter
     * are loaded based on the command line options presented
     */
    @Test
    public void testLoadSomeFiltersAndFormatters() throws IOException{
        // java MyChatApplication -u bob --obfuscate-user-ids inputFilePath outputFilePath
        String[] args = new String[5];
        args[0] = "-u";
        args[1] = "bob";
        args[2] = "--obfuscate-user-ids";
        args[3] = this.inputFilePath;
        args[4] = this.outputFilePath;

        CommandLine cli = new CommandLineArgumentParser().parseCommandLineArguments(args);
        String[] cliArgs = cli.getArgs();
        ConversationInterface conversation = new ConversationLoader().loadConversation(cliArgs[0]);

        //create editor instance
        ConversationEditor editor = new ConversationEditor();
        //load filters and formatters using cli options
        editor.loadFiltersAndFormatters(cli);

        ConversationFilterInterface[] filtersArray = new ConversationFilterInterface[editor.getConversationFilters().size()];
        editor.getConversationFilters().toArray(filtersArray);
        //1 filter must be loaded
        assertEquals(1, filtersArray.length);
        //check that correct filter is loaded UserNameFilter
        assertTrue(filtersArray[0] instanceof UserNameFilter);

        MessageFormatterInterface[] formattersArray = new MessageFormatterInterface[editor.getMessageFormatters().size()];
        editor.getMessageFormatters().toArray(formattersArray);
        //1 formatter must be loaded
        assertEquals(1, formattersArray.length);
        //check that correct formatter is loaded ObfuscateIdFormatter
        assertTrue(formattersArray[0] instanceof ObfuscateIdFormatter);
    }

    /**
     * Test that ALL filters/formatters are loaded
     * when all options are set from the command line
     */
    @Test
    public void testLoadAllFiltersAndFormatters() throws IOException{
        // java MyChatApplication -u bob -f pie -b like,hello --obfuscate-user-ids -x inputFilePath outputFilePath
        String[] args = new String[10];
        args[0] = "-u";
        args[1] = "bob";
        args[2] = "-f";
        args[3] = "pie";
        args[4] = "-b";
        args[5] = "like,hello";
        args[6] = "--obfuscate-user-ids";
        args[7] = "-x";
        args[8] = this.inputFilePath;
        args[9] = this.outputFilePath;

        CommandLine cli = new CommandLineArgumentParser().parseCommandLineArguments(args);
        String[] cliArgs = cli.getArgs();
        ConversationInterface conversation = new ConversationLoader().loadConversation(cliArgs[0]);

        //create editor instance
        ConversationEditor editor = new ConversationEditor();
        //load filters and formatters using cli options
        editor.loadFiltersAndFormatters(cli);

        //check filters
        ConversationFilterInterface[] filtersArray = new ConversationFilterInterface[editor.getConversationFilters().size()];
        editor.getConversationFilters().toArray(filtersArray);
        //1 filter must be loaded
        assertEquals(2, filtersArray.length);
        //check that correct filter is loaded UserNameFilter
        assertTrue(filtersArray[0] instanceof UserNameFilter);
        assertTrue(filtersArray[1] instanceof KeywordFilter);

        //check formatters
        MessageFormatterInterface[] formattersArray = new MessageFormatterInterface[editor.getMessageFormatters().size()];
        editor.getMessageFormatters().toArray(formattersArray);
        //1 formatter must be loaded
        assertEquals(3, formattersArray.length);
        //check that correct formatter is loaded ObfuscateIdFormatter
        assertTrue(formattersArray[0] instanceof BlacklistFormatter);
        assertTrue(formattersArray[1] instanceof ObfuscateIdFormatter);
        assertTrue(formattersArray[2] instanceof SensitiveDataFormatter);

    }

    /**
     * Test that the filter gets applied
     * Depends on correct functionality of the filter itself.
     * Assumes filters have been tested against message entries
     */
    @Test
    public void testApplyFilters() throws IOException{
        // java MyChatApplication -u bob inputFilePath outputFilePath
        String[] args = new String[4];
        args[0] = "-u";
        args[1] = "bob";
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

        assertEquals(6, messages.length);
        //for all messages in filtered conversation sender must be "bob"
        for (MessageInterface message : messages){
            assertEquals("bob", message.getSenderId());
        }
    }

    /**
     * Test that the formatter gets applied
     * Depends on correct functionality of the formatter itself.
     * Assumes formatters have been tested against message entries
     */
    @Test
    public void testApplyFormatter() throws IOException{
        // java MyChatApplication -u bob inputFilePath outputFilePath
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

        assertEquals(11, messages.length);
        //entries that should be updated
        assertEquals("I'm good thanks, do you like *redacted*?", messages[2].getContent());
        assertEquals("Hell yes! Are we buying some *redacted*?", messages[4].getContent());
        assertEquals("No, just want to know if there's anybody else in the *redacted* society...", messages[5].getContent());
        assertEquals("YES! I'm the head *redacted* eater there...", messages[6].getContent());
        assertEquals("or is it *redacted* like 4333-2343-2334-3221...", messages[8].getContent());

    }
}
