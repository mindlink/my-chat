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
 * Tests for the {@link UserNameFilter}
 */
public class UserNameFilterTests {
    /**
     * test uses conversation at path: testConversations/importedConversations/ConversationEditorTests/filters/UserNameFilterSample.txt
     * for input
     */
    String inputFilePath = "testConversations/importedConversations/ConversationEditorTests/filters/UserNameFilterSample.txt";
    /**
     * test exports conversation at dummy path
     * must be accurate at export times only
     */
    String outputFilePath = "dummy/path";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Test the messages are filtered by the correct user
     * username: bob
     */
    @Test
    public void testUserNameFilterBob() throws IOException{
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

        assertEquals(3, messages.length);
        //for all messages in filtered conversation sender must be "bob"
        for (MessageInterface message : messages){
            assertEquals("bob", message.getSenderId());
        }
    }

    /**
     * Test the messages are filtered by the correct user
     * username: bob
     */
    @Test
    public void testUserNameFilterAngus() throws IOException{
        // java MyChatApplication -u angus inputFilePath outputFilePath
        String[] args = new String[4];
        args[0] = "-u";
        args[1] = "angus";
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

        assertEquals(2, messages.length);
        //for all messages in filtered conversation sender must be "bob"
        for (MessageInterface message : messages){
            assertEquals("angus", message.getSenderId());
        }
    }

    /**
     * Test the messages are filtered by the correct user
     * username: emmanuel (doesn't exist in the conversation)
     */
    @Test
    public void testUserNameFilterEmmanuel() throws IOException{
        // java MyChatApplication -u emmanuel inputFilePath outputFilePath
        String[] args = new String[4];
        args[0] = "-u";
        args[1] = "emmanuel";
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

        assertEquals(0, messages.length);
    }

}
