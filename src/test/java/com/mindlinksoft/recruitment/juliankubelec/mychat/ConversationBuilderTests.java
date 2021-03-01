package com.mindlinksoft.recruitment.juliankubelec.mychat;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ConversationBuilderTests {

    /**
     * Tests if the conversation has been correctly filtered according to the specified userId
     */
    @Test
    public void testFilterByUser(){
        try
        {
            ConversationBuilder cb = prepareConversation();
            String user;

            user = "bob";
            cb.filterByUser(user);
            Conversation c = cb.build();

            assertEquals(3, c.messages.size()); // should contain 3 messages

            user = "CompleteStranger";
            cb = prepareConversation();
            cb.filterByUser(user);
            c = cb.build();
            assertEquals(0, c.messages.size()); // should contain 0 messages
        }catch(Exception e){
            fail("chat.txt not found");
        }

    }

    /**
     * Tests if the conversation has been correctly filtered according to the specified userId
     */
    @Test
    public void testFilterByKeyword(){
        try{
            ConversationBuilder cb = prepareConversation();
            String keyword;

            keyword = "pie";
            cb.filterByKeyword(keyword);
            Conversation c = cb.build();

            assertEquals(4, c.messages.size()); // should contain 3 messages

            keyword = "pasty";
            cb = prepareConversation();
            cb.filterByUser(keyword);
            c = cb.build();
            assertEquals(0, c.messages.size()); // should contain 0 messages
        }
        catch (Exception e)
        {
            fail("unable to retrieve chat.txt");
        }

    }

    /**
     * Test helper method to build regex that is used find blacklisted words
     */
    @Test
    public void testBlacklistWord()
    {
        try
        {
            String blacklistWord = "pie";
            String blacklistWord2 = "no";
            ConversationBuilder cb = prepareConversation();
            cb.blacklistWord(blacklistWord);
            cb.blacklistWord(blacklistWord2);
            Conversation c = cb.build();
            Message[] ms = new Message[c.messages.size()];
            c.messages.toArray(ms);

            assertEquals("Hello there!", ms[0].content);

            assertEquals("how are you?", ms[1].content);

            assertEquals("I'm good thanks, do you like *redacted*?", ms[2].content);

            assertEquals("*redacted*, let me ask Angus...", ms[3].content);

            assertEquals("Hell yes! Are we buying some *redacted*?", ms[4].content);

            assertEquals("*redacted*, just want to know if there's anybody else in the *redacted* society...", ms[5].content);

            assertEquals("YES! I'm the head *redacted* eater there...", ms[6].content);


        } catch (Exception e)
        {
            fail("unable to retrieve chat.txt");
        }
    }


    /**
     * Helper function to initialise the default version of the conversation from chat.txt
     * @throws Exception error if the exporter was unable to read the conversation (e.g. file doesn't exist)
     * @return the original conversation
     */
    private ConversationBuilder prepareConversation() throws Exception
    {
        ConversationExporter exporter = new ConversationExporter();
        Conversation conversation = exporter.readConversation("chat.txt");
        return new ConversationBuilder(conversation);
    }

}
