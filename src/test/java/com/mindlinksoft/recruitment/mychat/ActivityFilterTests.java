package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.models.*;
import com.mindlinksoft.recruitment.mychat.filters.*;

import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ActivityFilterTests {

    /**
     * Test for checking the validity of a generated report given a conversation
     * @throws Exception When bad things happen
     */
    @Test
    public void testActivityCreation() throws Exception {
       List<Message> myMessages = new ArrayList<Message>();
       myMessages.add(0, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "harry", "I am a pie eater"));
       myMessages.add(1, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123457")), "larry", "I am a pie-eater"));
       myMessages.add(2, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "garry", "I am a pie, eater!"));
       myMessages.add(3, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "harry", "This doesn't contain any blacklisted words"));
       myMessages.add(4, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "harry", "I am a /*redacted*? [*redacted*!"));
       myMessages.add(5, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "garry", "I said something"));

       Conversation conversation = new Conversation("Conversation name", myMessages);

       ActivityFilter af = new ActivityFilter();
       af.produceReport(conversation);
       
       List<Activity> resultActivity = (List<Activity>) conversation.activity;
       
       assertEquals(resultActivity.size(), 3);
       for (Activity activity : resultActivity) {
           if (activity.getSender().equals("harry")) {
               assertEquals(activity.getCount(), (Integer) 3);
           } else if (activity.getSender().equals("larry")) {
               assertEquals(activity.getCount(), (Integer) 1);
           } else {
               assertEquals(activity.getCount(), (Integer) 2);
           }
       }
    }
}
