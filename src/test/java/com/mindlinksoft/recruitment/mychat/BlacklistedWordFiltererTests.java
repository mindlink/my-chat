package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.ConversationFilterers.BlacklistedWordFilterer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BlacklistedWordFiltererTests {
    /**
     * Tests that redacting blacklisted words functions correctly
     */
    @Test
    public void testRedactBlacklistedWords() throws Exception {
        BlacklistedWordFilterer blacklistedWordFilterer = new BlacklistedWordFilterer();
        ConversationExporterIO exporterIO = new ConversationExporterIO();
        Conversation originalConversation;
        Conversation c;
        List<Message> originalMessages;
        List<Message> ms;

        // gets the original conversation (unfiltered)
        originalConversation = exporterIO.readConversation("chat.txt");
        originalMessages = (ArrayList<Message>) originalConversation.getMessages();

        // - [PIE] ---------------------------------------------------------
        c = exporterIO.readConversation("chat.txt");

        blacklistedWordFilterer.setBlacklistedWords(new String[]{"pie"});
        c = blacklistedWordFilterer.filter(c);
        ms = (ArrayList<Message>) c.getMessages();

        assertEquals("Hello there!", ms.get(0).getContent());
        assertEquals("how are you?", ms.get(1).getContent());
        assertEquals("I'm good thanks, do you like *redacted*?", ms.get(2).getContent());
        assertEquals("no, let me ask Angus...", ms.get(3).getContent());
        assertEquals("Hell yes! Are we buying some *redacted*?", ms.get(4).getContent());
        assertEquals("No, just want to know if there's anybody else in the *redacted* society...", ms.get(5).getContent());
        assertEquals("YES! I'm the head *redacted* eater there...", ms.get(6).getContent());

        // - [I'M, YOU] -----------------------------------------------------
        c = exporterIO.readConversation("chat.txt");

        blacklistedWordFilterer.setBlacklistedWords(new String[]{"I'm", "you"});
        c = blacklistedWordFilterer.filter(c);
        ms = (ArrayList<Message>) c.getMessages();

        assertEquals("Hello there!", ms.get(0).getContent());
        assertEquals("how are *redacted*?", ms.get(1).getContent());
        assertEquals("*redacted* good thanks, do *redacted* like pie?", ms.get(2).getContent());
        assertEquals("no, let me ask Angus...", ms.get(3).getContent());
        assertEquals("Hell yes! Are we buying some pie?", ms.get(4).getContent());
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms.get(5).getContent());
        assertEquals("YES! *redacted* the head pie eater there...", ms.get(6).getContent());

        // - [NON-EXISTENT] -----------------------------------------------------
        c = exporterIO.readConversation("chat.txt");

        blacklistedWordFilterer.setBlacklistedWords(new String[]{"non-existent"});
        c = blacklistedWordFilterer.filter(c);
        ms = (ArrayList<Message>) c.getMessages();

        // messages should be the same as the original as the "non-existent" string has no occurrences in the conversation
        for (int i = 0; i < ms.size(); i++) {
            assertEquals(originalMessages.get(i).getContent(), ms.get(i).getContent());
        }

        // - NULL ---------------------------------------------------------------
        c = exporterIO.readConversation("chat.txt");

        blacklistedWordFilterer.setBlacklistedWords(null);
        c = blacklistedWordFilterer.filter(c);
        ms = (ArrayList<Message>) c.getMessages();

        // messages should be the same as the original as the "non-existent" string has no occurrences in the conversation
        for (int i = 0; i < ms.size(); i++) {
            assertEquals(originalMessages.get(i).getContent(), ms.get(i).getContent());
        }

        // - EMPTY ARRAY --------------------------------------------------------
        c = exporterIO.readConversation("chat.txt");

        blacklistedWordFilterer.setBlacklistedWords(new String[]{});
        c = blacklistedWordFilterer.filter(c);
        ms = (ArrayList<Message>) c.getMessages();

        // messages should be the same as the original as the "non-existent" string has no occurrences in the conversation
        for (int i = 0; i < ms.size(); i++) {
            assertEquals(originalMessages.get(i).getContent(), ms.get(i).getContent());
        }
    }
}
