package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.Collection;

import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;
import com.mindlinksoft.recruitment.mychat.options.FilterByKeyword;

import org.junit.Test;

public class FilterByKeywordTest {
        /**
         * TODO: this test should also test for issues with what is considered a word
         * for example: - if its just if the substring appears - if the word needs to be
         * surrounded by spaces or punctuation - if the word can be singular or plural -
         * if the word could be misspelt or replaced in parts with numbers or symbols
         */

        /**
         * Integration test for the filter by keyword option.
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
                Conversation conversation = new OptionsTests().generateFakeConversation();
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
         * First Unit test for the filter by user option.
         * 
         * @throws FileNotFoundException Thrown when the the input is illegal
         * @throws IOException           Thrown when the writting to the output file
         *                               fails
         */
        @Test
        public void firstUnitTestFilterByKeyword() throws FileNotFoundException, IOException {
                Conversation conversation = new OptionsTests().generateFakeConversation();
                String keyword = "damn";
                conversation.messages = new FilterByKeyword(conversation, keyword).process();

                Collection<Message> messages = conversation.messages;
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
         * Second Unit test for the filter by user option.
         * 
         * @throws FileNotFoundException Thrown when the the input is illegal
         * @throws IOException           Thrown when the writting to the output file
         *                               fails
         */
        @Test
        public void secondUnitTestFilterByKeyword() throws FileNotFoundException, IOException {
                Conversation conversation = new OptionsTests().generateFakeConversation();
                String keyword = "finally";
                conversation.messages = new FilterByKeyword(conversation, keyword).process();

                Collection<Message> messages = conversation.messages;
                Message[] messageArray = new Message[messages.size()];
                messages.toArray(messageArray);

                assertEquals(1, messageArray.length);

                assertEquals(Instant.ofEpochSecond(1448470901), messageArray[0].getTimestamp());
                assertEquals("Ralof", messageArray[0].senderId);
                assertEquals("Hey, you. You’re finally awake. You were trying to cross the border, right? Walked "
                                + "right into that Imperial ambush, same as us, and that thief over there.",
                                messageArray[0].getContent());

        }

}
