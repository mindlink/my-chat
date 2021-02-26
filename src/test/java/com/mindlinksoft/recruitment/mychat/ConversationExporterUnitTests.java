package com.mindlinksoft.recruitment.mychat;

import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;
import com.mindlinksoft.recruitment.mychat.models.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterUnitTests {
    /**
     * Tests that read a conversation from a file will return the correct data.
     * 
     * @throws FileNotFoundException Thrown when the the input is illegal
     * @throws IOException           Thrown when the writting to the output file
     *                               fails
     */
    @Test
    public void testReadCovnversation() throws FileNotFoundException, IOException {
        ConversationExporter exporter = new ConversationExporter();
        Conversation conversation = exporter.readConversation("chat.txt");

        Collection<Message> messages = conversation.getMessages();
        Message[] messageArray = new Message[messages.size()];
        messages.toArray(messageArray);

        assertEquals(7, messageArray.length);

        assertEquals(conversation.getName(), "My Conversation");

        assertEquals(Instant.ofEpochSecond(1448470901), messageArray[0].getTimestamp());
        assertEquals("bob", messageArray[0].getSenderId());
        assertEquals("Hello there!", messageArray[0].getContent());

        assertEquals(Instant.ofEpochSecond(1448470905), messageArray[1].getTimestamp());
        assertEquals("mike", messageArray[1].getSenderId());
        assertEquals("how are you?", messageArray[1].getContent());

        assertEquals(Instant.ofEpochSecond(1448470906), messageArray[2].getTimestamp());
        assertEquals("bob", messageArray[2].getSenderId());
        assertEquals("I'm good thanks, do you like pie?", messageArray[2].getContent());

        assertEquals(Instant.ofEpochSecond(1448470910), messageArray[3].getTimestamp());
        assertEquals("mike", messageArray[3].getSenderId());
        assertEquals("no, let me ask Angus...", messageArray[3].getContent());

        assertEquals(Instant.ofEpochSecond(1448470912), messageArray[4].getTimestamp());
        assertEquals("angus", messageArray[4].getSenderId());
        assertEquals("Hell yes! Are we buying some pie?", messageArray[4].getContent());

        assertEquals(Instant.ofEpochSecond(1448470914), messageArray[5].getTimestamp());
        assertEquals("bob", messageArray[5].getSenderId());
        assertEquals("No, just want to know if there's anybody else in the pie society...",
                messageArray[5].getContent());

        assertEquals(Instant.ofEpochSecond(1448470915), messageArray[6].getTimestamp());
        assertEquals("angus", messageArray[6].getSenderId());
        assertEquals("YES! I'm the head pie eater there...", messageArray[6].getContent());
    }

    /**
     * Tests that the correct options are applied to the conversation.
     * 
     * @throws FileNotFoundException Thrown when the the input is illegal
     * @throws IOException           Thrown when the writting to the output file
     *                               fails
     */
    @Test
    public void testApplyOptions() throws FileNotFoundException, IOException {
        ConversationExporter exporter = new ConversationExporter();

        // fake conversation
        Conversation conversation = new OptionsTests().generateFakeConversation();

        // fake configuration
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration();
        configuration.inputFilePath = "chat.txt";
        configuration.outputFilePath = "testChat.json";
        configuration.filterUser = "Ralof";
        configuration.filterKeyword = "brothers";
        configuration.blacklist = new String[] { "thief" };
        configuration.report = true;

        Conversation conversationWithOptionsApplied = exporter.applyOptions(conversation, configuration);

        Collection<Message> messages = conversationWithOptionsApplied.getMessages();
        Message[] messageArray = new Message[messages.size()];
        messages.toArray(messageArray);

        assertEquals(1, messageArray.length);

        assertEquals(Instant.ofEpochSecond(1448470903), messageArray[0].getTimestamp());
        assertEquals("Ralof", messageArray[0].getSenderId());
        assertEquals("We’re all brothers and sisters in binds now, *redacted*.", messageArray[0].getContent());

        // check report
        List<User> report = conversationWithOptionsApplied.getActivity();

        assertEquals(1, report.size());
        assertEquals("Ralof", report.get(0).getSender());
        assertEquals(Integer.valueOf(1), report.get(0).getCount());
    }

    /**
     * Tests that the correct conversation is written to the determined file.
     * 
     * @throws FileNotFoundException Thrown when the the input is illegal
     * @throws IOException           Thrown when the writting to the output file
     *                               fails
     */
    @Test
    public void testWriteConversation() throws FileNotFoundException, IOException {
        ConversationExporter exporter = new ConversationExporter();
        Conversation conversation = new OptionsTests().generateFakeConversation();
        exporter.writeConversation(conversation, "testChat.json");

        Conversation conversationFromJsonFile = new ConversationExporterTests().getJsonFileContents("testChat.json");

        assertEquals("Imperial wagon Chat", conversationFromJsonFile.getName());

        assertEquals(4, conversationFromJsonFile.getMessages().size());

        Message[] messages = new Message[conversationFromJsonFile.getMessages().size()];
        conversationFromJsonFile.getMessages().toArray(messages);

        // TODO: handle JSON issue where some symbols are broken
        // currently just checks for the converted punctuation
        assertEquals(Instant.ofEpochSecond(1448470901), messages[0].getTimestamp());
        assertEquals("Ralof", messages[0].getSenderId());
        assertEquals(
                "Hey, you. You’re finally awake. You were trying to cross the border, right? Walked "
                        + "right into that Imperial ambush, same as us, and that thief over there.",
                messages[0].getContent());

        assertEquals(Instant.ofEpochSecond(1448470902), messages[1].getTimestamp());
        assertEquals("Lokir", messages[1].getSenderId());
        assertEquals("Damn you Stormcloaks. Skyrim was fine until you came along. Empire was nice and lazy. "
                + "If they hadn’t been looking for you, I could’ve stolen that horse and been half way "
                + "to Hammerfell. You there. You and me — we should be here. It’s these Stormcloaks the "
                + "Empire wants.", messages[1].getContent());

        assertEquals(Instant.ofEpochSecond(1448470903), messages[2].getTimestamp());
        assertEquals("Ralof", messages[2].getSenderId());
        assertEquals("We’re all brothers and sisters in binds now, thief.", messages[2].getContent());

        assertEquals(Instant.ofEpochSecond(1448470904), messages[3].getTimestamp());
        assertEquals("Imperial Soldier", messages[3].getSenderId());
        assertEquals("Shut up back there!", messages[3].getContent());
    }
}
