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
 * Tests for the {@link SensitiveDataFormatter}
 */
public class SensitiveDataFormatterTests {
    /**
     * test uses conversation at path: testConversations/importedConversations/ConversationEditorTests/formatters/SensitiveDataFormatterSample.txt
     * for input
     */
    String inputFilePath = "testConversations/importedConversations/ConversationEditorTests/formatters/SensitiveDataFormatterSample.txt";
    /**
     * test exports conversation at dummy path
     * must be accurate at export times only
     */
    String outputFilePath = "dummy/path";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Test that sensitive data (phone numbers/credit cards) are replaced with *redacted*
     */
    @Test
    public void testSensitiveDataFormatter() throws IOException {
        // java MyChatApplication -x inputFilePath outputFilePath
        String[] args = new String[3];
        args[0] = "-x";
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

        assertEquals(8, messages.length);

        assertEquals("My uk number is *redacted*! My CY number is *redacted*", messages[2].getContent());
        assertEquals("Your credit card number is: visa *redacted*...", messages[3].getContent());
        assertEquals("No, Do you also have a mastercard with number: *redacted*.", messages[5].getContent());
        assertEquals("YES! I also have AmEx with numnber *redacted*... or dashed if you prefer: *redacted*", messages[6].getContent());
        assertEquals("my phone number is *redacted* and card number: *redacted*", messages[7].getContent());
    }
}
