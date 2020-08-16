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
        senderTom = new Sender("Tom", 2);
        senderJames = new Sender("James", 4);
        senderRick = new Sender("Rick", 1);
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
        assertNotEquals(senderTom, new Sender("Tom", 2));
        assertNotEquals(senderRick, new Sender("Rick", 4));
        assertNotEquals(senderJames, new Sender("James", 1));

        // non-Sender should not be equal
        assertNotEquals(null, senderTom);
        assertNotEquals("james", senderJames);

        // senders with different names should not be equal
        assertNotEquals(senderTom, senderJames);
        assertNotEquals(senderRick, senderJames);
    }

    @Test
    public void toStringTest() {
        String id = String.valueOf(senderTom.getSenderId()); // static counter of id
        assertEquals(senderTom.toString(), "Sender{senderText='Tom', messageCount=2, senderId="+ id + "}");
    }
}