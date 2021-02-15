package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;
import com.mindlinksoft.recruitment.mychat.models.User;

import com.mindlinksoft.recruitment.mychat.options.ReportOption;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExportReportOptionTest {
    @Test
    public void testFilteringByUserReturnsFilteredConversationByUser() throws Exception {
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        config.reportState = true;

        Conversation conversation = createTestConversation();
        Collection<Message> conversationMessages = conversation.getMessages();

        // Check that the test conversation created is correct.
        assertEquals(conversation.getName(), "Chuckle Brothers Test Conversation");
        assertEquals(conversationMessages.size(), 7);

        //Process conversation to form report
        Conversation processedConversation = new ReportOption().process(conversation);
        List<User> conversationActivityReport = processedConversation.getActivity();

        assertEquals(conversationActivityReport.get(0).getSenderID(), "barry");
        assertEquals(conversationActivityReport.get(1).getSenderID(), "paul");

        assertEquals(conversationActivityReport.get(0).getMessageCount(), 4);
        assertEquals(conversationActivityReport.get(1).getMessageCount(), 3);

        // Also check the activity report is in descending order of number of messages sent
        assertTrue(conversationActivityReport.get(0).getMessageCount() >= conversationActivityReport.get(1).getMessageCount());
    }

    public Conversation createTestConversation() {
        List<Message> testMessages = new ArrayList<>();

        testMessages.add(0, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123000")), "barry", "Hello Paul!"));
        testMessages.add(1, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123001")), "paul", "Hello Barry, how are you?"));
        testMessages.add(2, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123002")), "barry", "Here take this ladder, Paul!"));
        testMessages.add(3, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123003")), "paul", "To me..."));
        testMessages.add(4, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123004")), "barry", "To you..."));
        testMessages.add(5, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123005")), "paul", "To me..."));
        testMessages.add(6, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123006")), "barry", "To you..."));

        Conversation testConversation = new Conversation("Chuckle Brothers Test Conversation", testMessages);

        return testConversation;
    }
}
