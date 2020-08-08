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
    public void haveUniqueSenderId() {
        // senders have unique ids
        assertNotEquals(senderTom.getSenderId(), senderJames.getSenderId());
        assertNotEquals(senderRick.getSenderId(), senderJames.getSenderId());
    }

    @Test
    public void notEqualIfNotSenderType() {
        assertNotEquals(null, senderTom);
        assertNotEquals("hello", senderJames);
    }

    @Test
    public void equalIfSameId() {
        assertEquals(senderTom, senderTom);
        assertEquals(senderRick, senderRick);
        assertEquals(senderJames, senderJames);
    }
}