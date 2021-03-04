package com.mindlinksoft.recruitment.mychat;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ConversationExporterIOTests {
    /**
     * Tests that exporting a conversation will export the conversation correctly.
     *
     * @throws Exception When something bad happens.
     */
    @Test
    public void testReadConversation() throws Exception {
        ConversationExporterIO exporterIO = new ConversationExporterIO();
        Conversation c;

        // - reading from normal text file -------------------------------------------

        c = exporterIO.readConversation("chat.txt");

        assertEquals("My Conversation", c.getName());

        assertEquals(7, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(Instant.ofEpochSecond(1448470901), ms[0].getTimestamp());
        assertEquals("bob", ms[0].getSenderId());
        assertEquals("Hello there!", ms[0].getContent());

        assertEquals(Instant.ofEpochSecond(1448470905), ms[1].getTimestamp());
        assertEquals("mike", ms[1].getSenderId());
        assertEquals("how are you?", ms[1].getContent());

        assertEquals(Instant.ofEpochSecond(1448470906), ms[2].getTimestamp());
        assertEquals("bob", ms[2].getSenderId());
        assertEquals("I'm good thanks, do you like pie?", ms[2].getContent());

        assertEquals(Instant.ofEpochSecond(1448470910), ms[3].getTimestamp());
        assertEquals("mike", ms[3].getSenderId());
        assertEquals("no, let me ask Angus...", ms[3].getContent());

        assertEquals(Instant.ofEpochSecond(1448470912), ms[4].getTimestamp());
        assertEquals("angus", ms[4].getSenderId());
        assertEquals("Hell yes! Are we buying some pie?", ms[4].getContent());

        assertEquals(Instant.ofEpochSecond(1448470914), ms[5].getTimestamp());
        assertEquals("bob", ms[5].getSenderId());
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].getContent());

        assertEquals(Instant.ofEpochSecond(1448470915), ms[6].getTimestamp());
        assertEquals("angus", ms[6].getSenderId());
        assertEquals("YES! I'm the head pie eater there...", ms[6].getContent());

        // - reading from file that does not exist -----------------------------------

        try {
            c = exporterIO.readConversation("does_not_exist.txt");
        } catch (Exception e) {
            assertEquals("Cannot find file named 'does_not_exist.txt'.", e.getMessage());
        }

        // - reading from a null file ------------------------------------------------

        try {
            c = exporterIO.readConversation(null);
        } catch (Exception e) {
            assertEquals("An error has occurred.", e.getMessage());
        }
    }

    /**
     * Tests that exporting a conversation will export the conversation correctly.
     *
     * @throws Exception When something bad happens.
     */
    @Test
    public void testWriteConversation() throws Exception {
        ConversationExporterIO exporterIO = new ConversationExporterIO();
        Conversation dummyConversation = new Conversation("Empty Convo", new ArrayList<>());

        // - writing to dummy .json file -----------------------------------------------------------------

        exporterIO.writeConversation(dummyConversation, "dummy.json");

        assertTrue((new File("dummy.json")).exists());

        // - writing to dummy .txt file ------------------------------------------------------------------

        exporterIO.writeConversation(dummyConversation, "dummy.txt");
        // though it writes to a .txt file, it writes in .json format, so it is not incorrect
        assertTrue((new File("dummy.txt")).exists());

        // - writing to a null file ----------------------------------------------------------------------

        try {
            exporterIO.writeConversation(dummyConversation,null);
        } catch (Exception e) {
            assertEquals("An error has occurred.", e.getMessage());
        }

    }
}