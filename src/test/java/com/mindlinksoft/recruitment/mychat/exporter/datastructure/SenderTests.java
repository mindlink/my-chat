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
    public void senderHaveCorrectText() {
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

        // objects of same type and same senderText are equal
        assertEquals(senderTom, new Sender("Tom"));
        assertEquals(senderRick, new Sender("Rick"));
        assertEquals(senderJames, new Sender("James"));

        // non-Sender should not be equal
        assertNotEquals(null, senderTom);
        assertNotEquals("james", senderJames);

        // senders with different names should not be equal
        assertNotEquals(senderTom, senderJames);
        assertNotEquals(senderRick, senderJames);
    }
}