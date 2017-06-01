package com.mindlinksoft.recruitment.mychat.loader;

import com.mindlinksoft.recruitment.mychat.conversation.ConversationInterface;
import com.mindlinksoft.recruitment.mychat.message.MessageInterface;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.time.Instant;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link ConversationLoader}.
 */
public class ConversationLoaderTests {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Testing Exception
     * Loader using incorrect path exception
     * file: dummy.txt - nonexistent
     * @throws IllegalArgumentException
     */
    @Test
    public void testConversationLoaderIncorrectPath() throws IOException, IllegalArgumentException {
        //points to non-existent file
        String inputPath = "testConversations/importedConversations/ConversationLoaderTests/dummy.txt";

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The file was not found.");
        ConversationInterface conversation = new ConversationLoader().loadConversation(inputPath);
    }

    /**
     * Testing Conversation object message entries created by loader
     * Loader using correct path with correct file
     * file: chatForConversationLoaderTest1.txt
     * @throws IOException
     */
    @Test
    public void testConversationLoaderCorrectPath() throws IOException{
        String inputPath = "testConversations/importedConversations/ConversationLoaderTests/chatForConversationLoaderTest1.txt";

        //check conversation name
        ConversationInterface conversation = new ConversationLoader().loadConversation(inputPath);
        assertEquals("ConversationLoaderTestChat1", conversation.getName());

        MessageInterface[] messages = new MessageInterface[conversation.getMessages().size()];
        conversation.getMessages().toArray(messages);

        assertEquals(7, messages.length);


        assertEquals( Instant.ofEpochSecond(1448470901), messages[0].getTimestamp() );
        assertEquals("bob", messages[0].getSenderId());
        assertEquals("Hello there!", messages[0].getContent());

        assertEquals( Instant.ofEpochSecond(1448470905), messages[1].getTimestamp() );
        assertEquals("mike", messages[1].getSenderId());
        assertEquals("how are you?", messages[1].getContent());

        assertEquals( Instant.ofEpochSecond(1448470906), messages[2].getTimestamp() );
        assertEquals("bob", messages[2].getSenderId());
        assertEquals("I'm good thanks, do you like pie?", messages[2].getContent());

        assertEquals( Instant.ofEpochSecond(1448470910), messages[3].getTimestamp() );
        assertEquals("mike", messages[3].getSenderId());
        assertEquals("no, let me ask Angus...", messages[3].getContent());

        assertEquals( Instant.ofEpochSecond(1448470912), messages[4].getTimestamp() );
        assertEquals("angus", messages[4].getSenderId());
        assertEquals("Hell yes! Are we buying some pie?", messages[4].getContent());

        assertEquals( Instant.ofEpochSecond(1448470914), messages[5].getTimestamp());
        assertEquals("bob", messages[5].getSenderId());
        assertEquals("No, just want to know if there's anybody else in the pie society...", messages[5].getContent());

        assertEquals( Instant.ofEpochSecond(1448470915), messages[6].getTimestamp());
        assertEquals("angus", messages[6].getSenderId());
        assertEquals("YES! I'm the head pie eater there...", messages[6].getContent());


    }

    /**
     * Testing Exception with malformed file
     * Loader using file with empty message
     * file: chatForConversationLoaderTest2.txt
     * @throws ArrayIndexOutOfBoundsException
     */
    @Test
    public void testConversationLoaderCorrectPathMalformedMessage() throws IOException, ArrayIndexOutOfBoundsException{
        String inputPath = "testConversations/importedConversations/ConversationLoaderTests/chatForConversationLoaderTest2.txt";

        thrown.expect(ArrayIndexOutOfBoundsException.class);
        thrown.expectMessage("Malformed messages: Check for empty messages. Accepted format: \n<conversation_name><new_line>\n" +
                "(<unix_timestamp><space><username><space><message><new_line>)*");
        ConversationInterface conversation = new ConversationLoader().loadConversation(inputPath);

    }

    /**
     * Testing Exception with malformed timestamps
     * file: chatForConversationLoaderTest3.txt
     * @throws NumberFormatException
     */
    @Test
    public void testConversationLoaderCorrectPathMalformedTimeStamp() throws NumberFormatException, IOException{
        String inputPath = "testConversations/importedConversations/ConversationLoaderTests/chatForConversationLoaderTest3.txt";

        thrown.expect(NumberFormatException.class);
        thrown.expectMessage("Malformed messages: Check for missing timestamp.");
        ConversationInterface conversation = new ConversationLoader().loadConversation(inputPath);

    }
}
