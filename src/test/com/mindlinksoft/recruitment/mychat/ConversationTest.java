package com.mindlinksoft.recruitment.mychat;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by alvaro on 15/03/16.
 */
public class ConversationTest {

    public Conversation messages;

    @Before
    public void initialization(){
        messages = new Conversation("Non sense talk", new ArrayList<>());
        Message message = new Message(Instant.ofEpochSecond(1448470906), "bob", "I want pie for lunch");
        messages.addMessage(message);

        message = new Message(Instant.ofEpochSecond(1448470910), "mike", "Me too. Call 123456789");
        messages.addMessage(message);
    }

    @Test
    public void testApplyBlacklistFilter() {
        messages.applyBlacklistFilter(new String[] {"pie"});
        assertEquals(messages.getMessageContent(0), "I want " + mychatConstants.BLACKLIST_SYMBOL + " for lunch");
    }

    @Test
    public void testApplyKeywordFilter()  {
        messages.applyKeywordFilter("pie");
        assertEquals(messages.getLenght(), 1);
    }

    @Test
    public void testApplyUserFilter()  {
        messages.applyUserFilter("bob");
        assertEquals(messages.getLenght(), 1);

        messages.applyUserFilter("han solo");
        assertEquals(messages.getLenght(), 0);
    }

    @Test
    public void testApplyHideNumbers()  {
        messages.applyHideNumbers();
        assertEquals(messages.getMessageContent(1), "Me too. Call " + mychatConstants.HIDENUMBERS_SYMBOL);
    }
}