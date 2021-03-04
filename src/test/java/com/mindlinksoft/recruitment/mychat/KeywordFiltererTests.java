package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.ConversationFilterers.KeywordFilterer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class KeywordFiltererTests {
    /**
     * Tests that filtering for keywords functions correctly
     */
    @Test
    public void testFilterConversationByKeyword() throws Exception {
        KeywordFilterer keywordFilterer = new KeywordFilterer();
        ConversationExporterIO exporterIO = new ConversationExporterIO();
        Conversation c;
        List<Message> ms;

        // - PIE ---------------------------------------------------------
        c = exporterIO.readConversation("chat.txt");

        keywordFilterer.setKeyword("pie");
        c = keywordFilterer.filter(c);
        ms = (ArrayList<Message>) c.getMessages();

        assertEquals("I'm good thanks, do you like pie?", ms.get(0).getContent());
        assertEquals("Hell yes! Are we buying some pie?", ms.get(1).getContent());
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms.get(2).getContent());
        assertEquals("YES! I'm the head pie eater there...", ms.get(3).getContent());

        // - HELL ---------------------------------------------------------
        c = exporterIO.readConversation("chat.txt");

        keywordFilterer.setKeyword("Hell");
        c = keywordFilterer.filter(c);
        ms = (ArrayList<Message>) c.getMessages();

        assertEquals("Hello there!", ms.get(0).getContent());
        assertEquals("Hell yes! Are we buying some pie?", ms.get(1).getContent());

        // - NOPE ---------------------------------------------------------
        c = exporterIO.readConversation("chat.txt");

        keywordFilterer.setKeyword("nope");
        c = keywordFilterer.filter(c);
        ms = (ArrayList<Message>) c.getMessages();

        assertEquals(0, ms.size());

        // - NULL ---------------------------------------------------------
        c = exporterIO.readConversation("chat.txt");

        keywordFilterer.setKeyword(null);
        c = keywordFilterer.filter(c);
        ms = (ArrayList<Message>) c.getMessages();

        assertEquals("Hello there!", ms.get(0).getContent());
        assertEquals("how are you?", ms.get(1).getContent());
        assertEquals("I'm good thanks, do you like pie?", ms.get(2).getContent());
        assertEquals("no, let me ask Angus...", ms.get(3).getContent());
        assertEquals("Hell yes! Are we buying some pie?", ms.get(4).getContent());
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms.get(5).getContent());
        assertEquals("YES! I'm the head pie eater there...", ms.get(6).getContent());

        // - EMPTY STRING -------------------------------------------------
        c = exporterIO.readConversation("chat.txt");

        keywordFilterer.setKeyword("");
        c = keywordFilterer.filter(c);
        ms = (ArrayList<Message>) c.getMessages();

        assertEquals("Hello there!", ms.get(0).getContent());
        assertEquals("how are you?", ms.get(1).getContent());
        assertEquals("I'm good thanks, do you like pie?", ms.get(2).getContent());
        assertEquals("no, let me ask Angus...", ms.get(3).getContent());
        assertEquals("Hell yes! Are we buying some pie?", ms.get(4).getContent());
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms.get(5).getContent());
        assertEquals("YES! I'm the head pie eater there...", ms.get(6).getContent());
    }

}
