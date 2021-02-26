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
        }

}
