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
         * Test the filter by user option.
         * 
         * @throws FileNotFoundException Thrown when the the input is illegal
         * @throws IOException           Thrown when the writting to the output file
         *                               fails
         */
        @Test
        public void firstTestFilterByUser() throws FileNotFoundException, IOException {
                Conversation conversation = new OptionsTests().generateFakeConversation();
                String filterUser = "Ralof";
                conversation.messages = new FilterByUser(conversation, filterUser).process();

                Collection<Message> messages = conversation.messages;
                Message[] messageArray = new Message[messages.size()];
                messages.toArray(messageArray);

                assertEquals(2, messageArray.length);

                assertEquals(Instant.ofEpochSecond(1448470901), messageArray[0].getTimestamp());
                assertEquals("Ralof", messageArray[0].senderId);
                assertEquals("Hey, you. You’re finally awake. You were trying to cross the border, right? Walked "
                                + "right into that Imperial ambush, same as us, and that thief over there.",
                                messageArray[0].getContent());

                assertEquals(Instant.ofEpochSecond(1448470903), messageArray[1].getTimestamp());
                assertEquals("Ralof", messageArray[1].getSenderId());
                assertEquals("We’re all brothers and sisters in binds now, thief.", messageArray[1].getContent());
        }

        /**
         * Test the filter by user option.
         * 
         * @throws FileNotFoundException Thrown when the the input is illegal
         * @throws IOException           Thrown when the writting to the output file
         *                               fails
         */
        @Test
        public void secondTestFilterByUser() throws FileNotFoundException, IOException {
                Conversation conversation = new OptionsTests().generateFakeConversation();
                String filterUser = "Imperial Soldier";
                conversation.messages = new FilterByUser(conversation, filterUser).process();

                Collection<Message> messages = conversation.messages;
                Message[] messageArray = new Message[messages.size()];
                messages.toArray(messageArray);

                assertEquals(1, messageArray.length);

                assertEquals(Instant.ofEpochSecond(1448470904), messageArray[0].getTimestamp());
                assertEquals("Imperial Soldier", messageArray[0].senderId);
                assertEquals("Shut up back there!", messageArray[0].getContent());
        }

}
