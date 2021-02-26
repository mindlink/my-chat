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
                assertEquals("Ralof", messageArray[0].getSenderId());
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
                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470904")),
                                "Imperial Soldier", "Shut up back there!"));

                return new Conversation("Imperial wagon Chat", messages);

        }
}
