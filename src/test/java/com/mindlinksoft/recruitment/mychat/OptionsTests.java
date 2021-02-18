package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;
import com.mindlinksoft.recruitment.mychat.models.User;
import com.mindlinksoft.recruitment.mychat.options.Options;

import org.junit.Test;

public class OptionsTests {
    /**
     * Test for illegal input
     * 
     * @throws FileNotFoundException Thrown when the the input is illegal
     * @throws IOException           Thrown when the writting to the output file
     *                               fails
     */
    @Test
    public void testOptionsAreCorrectlyLoaded() throws FileNotFoundException, IOException {
        // fake configuration
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration();
        configuration.inputFilePath = "chat.txt";
        configuration.outputFilePath = "testChat.json";
        configuration.filterUser = "Ralof";
        configuration.filterKeyword = "and";
        configuration.blacklist = new String[] { "Hey" };
        configuration.report = true;

        // fake conversation to generate the Options class
        Conversation conversation = generateFakeConversation();
        Options options = new Options(conversation, configuration);

        assertEquals(options.getFilterUser(), configuration.filterUser);
        assertEquals(options.getFilterKeyword(), configuration.filterKeyword);
        assertEquals(options.getReport(), configuration.report);

        String[] blacklistArray = options.getBlacklist();
        for (int i = 0; i < configuration.blacklist.length; i++) {
            assertEquals(blacklistArray[i], configuration.blacklist[i]);
        }

    }

    /**
     * Test filter by user option.
     * 
     * @throws FileNotFoundException Thrown when the the input is illegal
     * @throws IOException           Thrown when the writting to the output file
     *                               fails
     */
    @Test
    public void testFilterByUser() throws FileNotFoundException, IOException {
        ConversationExporter exporter = new ConversationExporter();

        // fake configuration
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration();
        configuration.inputFilePath = "chat.txt";
        configuration.outputFilePath = "testChat.json";
        configuration.filterUser = "Ralof";

        // fake conversation
        Conversation conversation = generateFakeConversation();
        // run through the rest of the conversation exporter
        exporter.applyOptions(conversation, configuration);
        exporter.writeConversation(conversation, configuration.outputFilePath);

        Collection<Message> messages = conversation.getMessages();
        Message[] messageArray = new Message[messages.size()];
        messages.toArray(messageArray);

        assertEquals(2, messageArray.length);

        assertEquals(Instant.ofEpochSecond(1448470901), messageArray[0].getTimestamp());
        assertEquals("Ralof", messageArray[0].senderId);
        assertEquals(
                "Hey, you. You’re finally awake. You were trying to cross the border, right? Walked "
                        + "right into that Imperial ambush, same as us, and that thief over there.",
                messageArray[0].getContent());

        assertEquals(Instant.ofEpochSecond(1448470903), messageArray[1].getTimestamp());
        assertEquals("Ralof", messageArray[1].getSenderId());
        assertEquals("We’re all brothers and sisters in binds now, thief.", messageArray[1].getContent());
    }

    /**
     * Test filter by keyword option.
     * 
     * @throws FileNotFoundException Thrown when the the input is illegal
     * @throws IOException           Thrown when the writting to the output file
     *                               fails
     */
    @Test
    public void testFilterByKeyword() throws FileNotFoundException, IOException {
        ConversationExporter exporter = new ConversationExporter();

        // fake configuration
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration();
        configuration.inputFilePath = "chat.txt";
        configuration.outputFilePath = "testChat.json";
        configuration.filterKeyword = "damn";

        // fake conversation
        Conversation conversation = generateFakeConversation();
        // run through the rest of the conversation exporter
        exporter.applyOptions(conversation, configuration);
        exporter.writeConversation(conversation, configuration.outputFilePath);

        Collection<Message> messages = conversation.getMessages();
        Message[] messageArray = new Message[messages.size()];
        messages.toArray(messageArray);

        assertEquals(1, messageArray.length);

        assertEquals(Instant.ofEpochSecond(1448470902), messageArray[0].getTimestamp());
        assertEquals("Lokir", messageArray[0].senderId);
        assertEquals("Damn you Stormcloaks. Skyrim was fine until you came along. Empire was nice and lazy. "
                + "If they hadn’t been looking for you, I could’ve stolen that horse and been half way "
                + "to Hammerfell. You there. You and me — we should be here. It’s these Stormcloaks the "
                + "Empire wants.", messageArray[0].getContent());
    }

    /**
     * Test the blacklist option.
     * 
     * @throws FileNotFoundException Thrown when the the input is illegal
     * @throws IOException           Thrown when the writting to the output file
     *                               fails
     */
    @Test
    public void testBlacklist() throws FileNotFoundException, IOException {
        ConversationExporter exporter = new ConversationExporter();

        // fake configuration
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration();
        configuration.inputFilePath = "chat.txt";
        configuration.outputFilePath = "testChat.json";
        configuration.blacklist = new String[] { "Empire", "Damn" };

        // fake conversation
        Conversation conversation = generateFakeConversation();
        // run through the rest of the conversation exporter
        exporter.applyOptions(conversation, configuration);
        exporter.writeConversation(conversation, configuration.outputFilePath);

        Collection<Message> messages = conversation.getMessages();
        Message[] messageArray = new Message[messages.size()];
        messages.toArray(messageArray);

        assertEquals(4, messageArray.length);

        assertEquals(Instant.ofEpochSecond(1448470901), messageArray[0].getTimestamp());
        assertEquals("Ralof", messageArray[0].senderId);
        assertEquals(
                "Hey, you. You’re finally awake. You were trying to cross the border, right? Walked "
                        + "right into that Imperial ambush, same as us, and that thief over there.",
                messageArray[0].getContent());

        assertEquals(Instant.ofEpochSecond(1448470902), messageArray[1].getTimestamp());
        assertEquals("Lokir", messageArray[1].senderId);
        assertEquals("*redacted* you Stormcloaks. Skyrim was fine until you came along. *redacted* was nice and lazy. "
                + "If they hadn’t been looking for you, I could’ve stolen that horse and been half way "
                + "to Hammerfell. You there. You and me — we should be here. It’s these Stormcloaks the "
                + "*redacted* wants.", messageArray[1].getContent());

        assertEquals(Instant.ofEpochSecond(1448470903), messageArray[2].getTimestamp());
        assertEquals("Ralof", messageArray[2].getSenderId());
        assertEquals("We’re all brothers and sisters in binds now, thief.", messageArray[2].getContent());

        assertEquals(Instant.ofEpochSecond(1448470904), messageArray[3].getTimestamp());
        assertEquals("Imperial Soldier", messageArray[3].getSenderId());
        assertEquals("Shut up back there!", messageArray[3].getContent());
    }

    /**
     * Test report generation.
     * 
     * @throws FileNotFoundException Thrown when the the input is illegal
     * @throws IOException           Thrown when the writting to the output file
     *                               fails
     */
    @Test
    public void testReportGeneration() throws FileNotFoundException, IOException {
        ConversationExporter exporter = new ConversationExporter();

        // fake configuration
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration();
        configuration.inputFilePath = "chat.txt";
        configuration.outputFilePath = "testChat.json";
        configuration.report = true;

        // fake conversation
        Conversation conversation = generateFakeConversation();
        // run through the rest of the conversation exporter
        exporter.applyOptions(conversation, configuration);
        exporter.writeConversation(conversation, configuration.outputFilePath);

        List<User> report = conversation.getActivity();

        assertEquals(3, report.size());

        assertEquals("Ralof", report.get(0).getSender());
        assertEquals(Integer.valueOf(2), report.get(0).getCount());

        assertEquals("Imperial Soldier", report.get(1).getSender());
        assertEquals(Integer.valueOf(1), report.get(1).getCount());

        assertEquals("Lokir", report.get(2).getSender());
        assertEquals(Integer.valueOf(1), report.get(2).getCount());
    }

    /**
     * Test report generation.
     * 
     * @throws FileNotFoundException Thrown when the the input is illegal
     * @throws IOException           Thrown when the writting to the output file
     *                               fails
     */
    @Test
    public void testAllOptions() throws FileNotFoundException, IOException {
        ConversationExporter exporter = new ConversationExporter();

        // fake configuration
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration();
        configuration.inputFilePath = "chat.txt";
        configuration.outputFilePath = "testChat.json";
        configuration.filterUser = "Ralof";
        configuration.filterKeyword = "brothers";
        configuration.blacklist = new String[] { "thief" };
        configuration.report = true;

        // fake conversation
        Conversation conversation = generateFakeConversation();
        // run through the rest of the conversation exporter
        exporter.applyOptions(conversation, configuration);
        exporter.writeConversation(conversation, configuration.outputFilePath);

        Collection<Message> messages = conversation.getMessages();
        Message[] messageArray = new Message[messages.size()];
        messages.toArray(messageArray);

        assertEquals(1, messageArray.length);

        assertEquals(Instant.ofEpochSecond(1448470903), messageArray[0].getTimestamp());
        assertEquals("Ralof", messageArray[0].senderId);
        assertEquals("We’re all brothers and sisters in binds now, *redacted*.", messageArray[0].getContent());

        // check report
        List<User> report = conversation.getActivity();

        assertEquals(1, report.size());
        assertEquals("Ralof", report.get(0).getSender());
        assertEquals(Integer.valueOf(1), report.get(0).getCount());
    }

    public Conversation generateFakeConversation() {
        List<Message> messages = new ArrayList<Message>();

        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901")), "Ralof",
                "Hey, you. You’re finally awake. You were trying to cross the border, right? Walked "
                        + "right into that Imperial ambush, same as us, and that thief over there."));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470902")), "Lokir",
                "Damn you Stormcloaks. Skyrim was fine until you came along. Empire was nice and lazy. "
                        + "If they hadn’t been looking for you, I could’ve stolen that horse and been half way "
                        + "to Hammerfell. You there. You and me — we should be here. It’s these Stormcloaks the "
                        + "Empire wants."));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470903")), "Ralof",
                "We’re all brothers and sisters in binds now, thief."));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470904")), "Imperial Soldier",
                "Shut up back there!"));

        return new Conversation("Imperial wagon Chat", messages);

    }
}
