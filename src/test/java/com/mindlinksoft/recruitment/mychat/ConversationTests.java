package com.mindlinksoft.recruitment.mychat;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

public class ConversationTests {
    /**
     * Tests that a conversation's activity is generated correctly
     */
    @Test
    public void testActivityGeneration() throws Exception {
        Collection<Message> testMessages = new ArrayList<>();

        testMessages.add(new Message(null, "Annie", "*content*"));
        testMessages.add(new Message(null, "Carol", "*content*"));
        testMessages.add(new Message(null, "Annie", "*content*"));
        testMessages.add(new Message(null, "Carol", "*content*"));
        testMessages.add(new Message(null, "Dave", "*content*"));
        testMessages.add(new Message(null, "Annie", "*content*"));
        testMessages.add(new Message(null, "Dave", "*content*"));
        testMessages.add(new Message(null, "Carol", "*content*"));
        testMessages.add(new Message(null, "Kieran", "*content*"));

        Conversation c = new Conversation("Test", testMessages);

        // - before tracking activity -----------------------------------------

        assertNull(c.getActivity());

        // - track activity ---------------------------------------------------

        c.trackActivity();

        assertEquals("Annie", c.getActivity().get(0).getName());
        assertEquals(3, c.getActivity().get(0).getCount());

        assertEquals("Carol", c.getActivity().get(1).getName());
        assertEquals(3, c.getActivity().get(1).getCount());

        assertEquals("Dave", c.getActivity().get(2).getName());
        assertEquals(2, c.getActivity().get(2).getCount());

        assertEquals("Kieran", c.getActivity().get(3).getName());
        assertEquals(1, c.getActivity().get(3).getCount());

        // - added more messages ----------------------------------------------

        testMessages.add(new Message(null, "Carol", "*content*"));
        testMessages.add(new Message(null, "Kieran", "*content*"));
        testMessages.add(new Message(null, "Carol", "*content*"));
        testMessages.add(new Message(null, "Dave", "*content*"));
        testMessages.add(new Message(null, "Ella", "*content*"));
        testMessages.add(new Message(null, "Kieran", "*content*"));

        c.trackActivity();

        assertEquals("Annie", c.getActivity().get(0).getName());
        assertEquals(3, c.getActivity().get(0).getCount());

        assertEquals("Carol", c.getActivity().get(1).getName());
        assertEquals(5, c.getActivity().get(1).getCount());

        assertEquals("Dave", c.getActivity().get(2).getName());
        assertEquals(3, c.getActivity().get(2).getCount());

        assertEquals("Kieran", c.getActivity().get(3).getName());
        assertEquals(3, c.getActivity().get(3).getCount());

        assertEquals("Ella", c.getActivity().get(4).getName());
        assertEquals(1, c.getActivity().get(4).getCount());

        // - new conversation -------------------------------------------------

        testMessages = new ArrayList<>();
        c = new Conversation("Test", testMessages);

        c.trackActivity();

        assertEquals(0, c.getActivity().size());

        // - null conversation ------------------------------------------------

        testMessages = null;
        c = new Conversation("Test", testMessages);

        c.trackActivity();

        assertEquals(null, c.getActivity());
    }

    /**
     * Tests that a conversation's activity is sorted correctly
     */
    @Test
    public void testActivitySorting() throws Exception {
        Collection<Message> testMessages = new ArrayList<>();

        testMessages.add(new Message(null, "Annie", "*content*"));
        testMessages.add(new Message(null, "Carol", "*content*"));
        testMessages.add(new Message(null, "Annie", "*content*"));
        testMessages.add(new Message(null, "Carol", "*content*"));
        testMessages.add(new Message(null, "Dave", "*content*"));
        testMessages.add(new Message(null, "Carol", "*content*"));
        testMessages.add(new Message(null, "Dave", "*content*"));
        testMessages.add(new Message(null, "Carol", "*content*"));
        testMessages.add(new Message(null, "Kieran", "*content*"));

        Conversation c = new Conversation("Test", testMessages);

        // - track activity ---------------------------------------------------

        c.trackActivity();
        c.sortActivity();

        assertEquals("Carol", c.getActivity().get(0).getName());
        assertEquals(4, c.getActivity().get(0).getCount());

        assertEquals("Annie", c.getActivity().get(1).getName());
        assertEquals(2, c.getActivity().get(1).getCount());

        assertEquals("Dave", c.getActivity().get(2).getName());
        assertEquals(2, c.getActivity().get(2).getCount());

        assertEquals("Kieran", c.getActivity().get(3).getName());
        assertEquals(1, c.getActivity().get(3).getCount());

        // - added more messages ----------------------------------------------

        testMessages.add(new Message(null, "Carol", "*content*"));
        testMessages.add(new Message(null, "Kieran", "*content*"));
        testMessages.add(new Message(null, "Kieran", "*content*"));
        testMessages.add(new Message(null, "Annie", "*content*"));
        testMessages.add(new Message(null, "Ella", "*content*"));
        testMessages.add(new Message(null, "Kieran", "*content*"));

        c.trackActivity();
        c.sortActivity();

        assertEquals("Carol", c.getActivity().get(0).getName());
        assertEquals(5, c.getActivity().get(0).getCount());

        assertEquals("Kieran", c.getActivity().get(1).getName());
        assertEquals(4, c.getActivity().get(1).getCount());

        assertEquals("Annie", c.getActivity().get(2).getName());
        assertEquals(3, c.getActivity().get(2).getCount());

        assertEquals("Dave", c.getActivity().get(3).getName());
        assertEquals(2, c.getActivity().get(3).getCount());

        assertEquals("Ella", c.getActivity().get(4).getName());
        assertEquals(1, c.getActivity().get(4).getCount());
    }

}
