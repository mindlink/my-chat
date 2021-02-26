package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.Collection;

import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;
import com.mindlinksoft.recruitment.mychat.options.Blacklist;

import org.junit.Test;

public class BlacklistTest {

        /**
         * Integration test for the blacklist option.
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
                Conversation conversation = new OptionsTests().generateFakeConversation();
                // run through the rest of the conversation exporter
                exporter.applyOptions(conversation, configuration);
                exporter.writeConversation(conversation, configuration.outputFilePath);

                Collection<Message> messages = conversation.getMessages();
                Message[] messageArray = new Message[messages.size()];
                messages.toArray(messageArray);

                assertEquals(4, messageArray.length);

                assertEquals(Instant.ofEpochSecond(1448470901), messageArray[0].getTimestamp());
                assertEquals("Ralof", messageArray[0].senderId);
                assertEquals("Hey, you. You’re finally awake. You were trying to cross the border, right? Walked "
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
         * First Unit test for the blacklist option.
         * 
         * @throws FileNotFoundException Thrown when the the input is illegal
         * @throws IOException           Thrown when the writting to the output file
         *                               fails
         */
        @Test
        public void firstTestBlacklist() throws FileNotFoundException, IOException {
                Conversation conversation = new OptionsTests().generateFakeConversation();
                String[] blacklist = new String[] { "Empire", "Damn" };
                conversation.messages = new Blacklist(conversation, blacklist).process();

                Collection<Message> messages = conversation.messages;
                Message[] messageArray = new Message[messages.size()];
                messages.toArray(messageArray);

                assertEquals(4, messageArray.length);

                assertEquals(Instant.ofEpochSecond(1448470901), messageArray[0].getTimestamp());
                assertEquals("Ralof", messageArray[0].senderId);
                assertEquals("Hey, you. You’re finally awake. You were trying to cross the border, right? Walked "
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
         * Second Unit test for the blacklist option.
         * 
         * @throws FileNotFoundException Thrown when the the input is illegal
         * @throws IOException           Thrown when the writting to the output file
         *                               fails
         */
        @Test
        public void secondTestBlacklist() throws FileNotFoundException, IOException {
                Conversation conversation = new OptionsTests().generateFakeConversation();
                String[] blacklist = new String[] { "Imperial", "Stormcloaks" };
                conversation.messages = new Blacklist(conversation, blacklist).process();

                Collection<Message> messages = conversation.messages;
                Message[] messageArray = new Message[messages.size()];
                messages.toArray(messageArray);

                assertEquals(4, messageArray.length);

                assertEquals(Instant.ofEpochSecond(1448470901), messageArray[0].getTimestamp());
                assertEquals("Ralof", messageArray[0].senderId);
                assertEquals("Hey, you. You’re finally awake. You were trying to cross the border, right? Walked "
                                + "right into that *redacted* ambush, same as us, and that thief over there.",
                                messageArray[0].getContent());

                assertEquals(Instant.ofEpochSecond(1448470902), messageArray[1].getTimestamp());
                assertEquals("Lokir", messageArray[1].senderId);
                assertEquals("Damn you *redacted*. Skyrim was fine until you came along. Empire was nice and lazy. "
                                + "If they hadn’t been looking for you, I could’ve stolen that horse and been half way "
                                + "to Hammerfell. You there. You and me — we should be here. It’s these *redacted* the "
                                + "Empire wants.", messageArray[1].getContent());

                assertEquals(Instant.ofEpochSecond(1448470903), messageArray[2].getTimestamp());
                assertEquals("Ralof", messageArray[2].getSenderId());
                assertEquals("We’re all brothers and sisters in binds now, thief.", messageArray[2].getContent());

                assertEquals(Instant.ofEpochSecond(1448470904), messageArray[3].getTimestamp());
                assertEquals("Imperial Soldier", messageArray[3].getSenderId());
                assertEquals("Shut up back there!", messageArray[3].getContent());
        }

}
