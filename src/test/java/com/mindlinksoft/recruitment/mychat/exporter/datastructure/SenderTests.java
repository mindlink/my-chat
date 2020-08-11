package com.mindlinksoft.recruitment.mychat.exporter.datastructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class SenderTests {
    
    Sender senderTom;
    Sender senderJames;
    Sender senderRick;

    @Before
    public void setUp() {
        senderTom = new Sender("Tom");
        senderJames = new Sender("James");
        senderRick = new Sender("Rick");
    }

    @Test
    public void getSenderText() {
        // should return correct text
        assertEquals("Tom", senderTom.getSenderText());
        assertEquals("James", senderJames.getSenderText());
        assertEquals("Rick", senderRick.getSenderText());
    }

    @Test
    public void equals() {
        // same objects should be equal
        assertEquals(senderTom, senderTom);
        assertEquals(senderRick, senderRick);
        assertEquals(senderJames, senderJames);

        // senders with same name but different id should not be equal
        // to prevent duplicate instances of same sender
        assertNotEquals(senderTom, new Sender("Tom"));
        assertNotEquals(senderRick, new Sender("Rick"));
        assertNotEquals(senderJames, new Sender("James"));

        // non-Sender should not be equal
        assertNotEquals(null, senderTom);
        assertNotEquals("james", senderJames);

        // senders with different names should not be equal
        assertNotEquals(senderTom, senderJames);
        assertNotEquals(senderRick, senderJames);
    }
}