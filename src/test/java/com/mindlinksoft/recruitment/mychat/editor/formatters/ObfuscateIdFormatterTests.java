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
 * Tests for the {@link ObfuscateIdFormatter}
 */
public class ObfuscateIdFormatterTests {
    /**
     * test uses conversation at path: testConversations/importedConversations/ConversationEditorTests/formatters/ObfuscateIdFormatterSample.txt
     * for input
     */
    String inputFilePath = "testConversations/importedConversations/ConversationEditorTests/formatters/ObfuscateIdFormatterSample.txt";
    /**
     * test exports conversation at dummy path
     * must be accurate at export times only
     */
    String outputFilePath = "dummy/path";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Tests obfuscator on sample conversation
     */
    @Test
    public void testObfuscateIdFormatter() throws IOException {
        // java MyChatApplication -o inputFilePath outputFilePath
        String[] args = new String[3];
        args[0] = "-o";
        args[1] = this.inputFilePath;
        args[2] = this.outputFilePath;

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

        //check that usernames have been obfuscated properly
        assertEquals("user1", messages[0].getSenderId());
        assertEquals("user2", messages[1].getSenderId());
        assertEquals("user1", messages[2].getSenderId());
        assertEquals("user2", messages[3].getSenderId());
        assertEquals("user3", messages[4].getSenderId());
        assertEquals("user1", messages[5].getSenderId());
        assertEquals("user3", messages[6].getSenderId());
    }
}
