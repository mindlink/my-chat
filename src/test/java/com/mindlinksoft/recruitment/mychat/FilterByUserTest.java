package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.Collection;

import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;
import com.mindlinksoft.recruitment.mychat.options.FilterByUser;

import org.junit.Test;

public class FilterByUserTest {

        /**
         * Integration test for the filter by user option.
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
                Conversation conversation = new OptionsTests().generateFakeConversation();
                // run through the rest of the conversation exporter
                exporter.applyOptions(conversation, configuration);
                exporter.writeConversation(conversation, configuration.outputFilePath);

                Collection<Message> messages = conversation.getMessages();
                Message[] messageArray = new Message[messages.size()];
                messages.toArray(messageArray);

                assertEquals(2, messageArray.length);

                assertEquals(Instant.ofEpochSecond(1448470901), messageArray[0].getTimestamp());
                assertEquals("Ralof", messageArray[0].getSenderId());
                assertEquals("Hey, you. You’re finally awake. You were trying to cross the border, right? Walked "
                                + "right into that Imperial ambush, same as us, and that thief over there.",
                                messageArray[0].getContent());

                assertEquals(Instant.ofEpochSecond(1448470903), messageArray[1].getTimestamp());
                assertEquals("Ralof", messageArray[1].getSenderId());
                assertEquals("We’re all brothers and sisters in binds now, thief.", messageArray[1].getContent());
        }

        /**
         * First Unit test for the filter by user option.
         * 
         * @throws FileNotFoundException Thrown when the the input is illegal
         * @throws IOException           Thrown when the writting to the output file
         *                               fails
         */
        @Test
        public void firstUnitTestFilterByUser() throws FileNotFoundException, IOException {
                Conversation conversation = new OptionsTests().generateFakeConversation();
                String filterUser = "Ralof";
                conversation.setMessages(new FilterByUser(conversation, filterUser).process());

                Collection<Message> messages = conversation.getMessages();
                Message[] messageArray = new Message[messages.size()];
                messages.toArray(messageArray);

                assertEquals(2, messageArray.length);

                assertEquals(Instant.ofEpochSecond(1448470901), messageArray[0].getTimestamp());
                assertEquals("Ralof", messageArray[0].getSenderId());
                assertEquals("Hey, you. You’re finally awake. You were trying to cross the border, right? Walked "
                                + "right into that Imperial ambush, same as us, and that thief over there.",
                                messageArray[0].getContent());

                assertEquals(Instant.ofEpochSecond(1448470903), messageArray[1].getTimestamp());
                assertEquals("Ralof", messageArray[1].getSenderId());
                assertEquals("We’re all brothers and sisters in binds now, thief.", messageArray[1].getContent());
        }

        /**
         * Second Unit test for the filter by user option.
         * 
         * @throws FileNotFoundException Thrown when the the input is illegal
         * @throws IOException           Thrown when the writting to the output file
         *                               fails
         */
        @Test
        public void secondUnitTestFilterByUser() throws FileNotFoundException, IOException {
                Conversation conversation = new OptionsTests().generateFakeConversation();
                String filterUser = "Imperial Soldier";
                conversation.setMessages(new FilterByUser(conversation, filterUser).process());

                Collection<Message> messages = conversation.getMessages();
                Message[] messageArray = new Message[messages.size()];
                messages.toArray(messageArray);

                assertEquals(1, messageArray.length);

                assertEquals(Instant.ofEpochSecond(1448470904), messageArray[0].getTimestamp());
                assertEquals("Imperial Soldier", messageArray[0].getSenderId());
                assertEquals("Shut up back there!", messageArray[0].getContent());
        }

}
