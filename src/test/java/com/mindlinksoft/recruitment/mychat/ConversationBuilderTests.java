package com.mindlinksoft.recruitment.mychat;


import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ConversationBuilderTests {

    /**
     * Tests if the conversation has been correctly filtered according to the specified userId
     */
    @Test
    public void testFilterByUser() throws Exception {
        ConversationBuilder cb = prepareConversation();
        String user;

        user = "bob";
        cb.filterByUser(user);
        Conversation c = cb.build();

        assertEquals(3, c.messages.size()); // should contain 3 messages

        user = "CompleteStranger";
        cb = prepareConversation();
        cb.filterByUser(user);
        c = cb.build();
        assertEquals(0, c.messages.size()); // should contain 3 messages

    }

    /**
     * Helper function to initialise the default version of the conversation from chat.txt
     * @throws Exception error if the exporter was unable to read the conversation (e.g. file doesn't exist)
     * @return the original conversation
     */
    private ConversationBuilder prepareConversation() throws Exception
    {
        ConversationExporter exporter = new ConversationExporter();
        Conversation conversation = exporter.readConversation("chat.txt");
        return new ConversationBuilder(conversation);
    }

}
