package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.ConversationFilterers.UserFilterer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserFiltererTests {
    /**
     * Tests that filtering by username functions correctly
     */
    @Test
    public void testFilterConversationByUser() throws Exception {
        UserFilterer userFilterer = new UserFilterer();
        ConversationExporterIO exporterIO = new ConversationExporterIO();
        Conversation c;

        // - BOB ---------------------------------------------------------
        c = exporterIO.readConversation("chat.txt");

        userFilterer.setUser("bob");
        c = userFilterer.filter(c);

        for (Message m : c.getMessages()) {
            assertEquals("bob", m.getSenderId());
        }

        assertEquals(3, c.getMessages().size());

        // - BOB (CAPITALISED) -----------------------------------------
        c = exporterIO.readConversation("chat.txt");
        // should work the same way as with its lowercase version
        userFilterer.setUser("BOB");
        c = userFilterer.filter(c);

        for (Message m : c.getMessages()) {
            assertEquals("bob", m.getSenderId());
        }

        assertEquals(3, c.getMessages().size());

        // - MIKE --------------------------------------------------------
        c = exporterIO.readConversation("chat.txt");

        userFilterer.setUser("mike");
        c = userFilterer.filter(c);

        for (Message m : c.getMessages()) {
            assertEquals("mike", m.getSenderId());
        }

        assertEquals(2, c.getMessages().size());

        // - ALICE -------------------------------------------------------
        c = exporterIO.readConversation("chat.txt");
        // no alice in the conversation
        userFilterer.setUser("alice");
        c = userFilterer.filter(c);

        assertEquals(0, c.getMessages().size());

        // - NULL --------------------------------------------------------
        c = exporterIO.readConversation("chat.txt");

        userFilterer.setUser(null);
        c = userFilterer.filter(c);

        assertEquals(7, c.getMessages().size());

        // - EMPTY STRING ------------------------------------------------
        c = exporterIO.readConversation("chat.txt");

        userFilterer.setUser("");
        c = userFilterer.filter(c);

        assertEquals(7, c.getMessages().size());
    }
}
