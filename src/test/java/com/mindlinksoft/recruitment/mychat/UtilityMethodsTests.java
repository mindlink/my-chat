package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.exceptions.IOProblemException;
import com.mindlinksoft.recruitment.mychat.exceptions.WrongArgumentsException;
import com.mindlinksoft.recruitment.mychat.features.AdditionalFeatures;
import com.mindlinksoft.recruitment.mychat.io.ReadConversation;
import com.mindlinksoft.recruitment.mychat.io.WriteConversation;
import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.model.Message;
import com.mindlinksoft.recruitment.mychat.utils.GsonConverter;
import org.junit.Test;

import java.io.*;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UtilityMethodsTests {

    @Test
    public void testObfuscate(){
        Message m = new Message(Instant.ofEpochSecond(1448470901),"bob","Hello");
        AdditionalFeatures.obfuscate(m);
        assertEquals("Ym9i", m.senderId);
        assertEquals("bob",new String(Base64.getDecoder().decode("Ym9i")));
    }

    @Test
    public void testMapAdd(){
        HashMap<String, Integer> userMessageCount = new HashMap<>();
        AdditionalFeatures.add("bob",userMessageCount);
        assertTrue(userMessageCount.keySet().size() > 0);
    }

    @Test
    public void testSortByValue(){
        HashMap<String, Integer> userMessageCount = new HashMap<>();
        AdditionalFeatures.add("bob",userMessageCount);
        AdditionalFeatures.add("bob",userMessageCount);
        AdditionalFeatures.add("mike",userMessageCount);
        AdditionalFeatures.add("angus",userMessageCount);
        assertEquals(userMessageCount.toString(),"{angus=1, bob=2, mike=1}");
        userMessageCount = AdditionalFeatures.sortByValue(userMessageCount);
        assertEquals(userMessageCount.toString(),"{bob=2, angus=1, mike=1}");
    }

    @Test
    public void testLineToMessage(){
        String str = "1448470901 bob Hello there!";
        Message m = ReadConversation.lineToMessageBuilder(str);
        assertEquals(m.senderId,"bob");
        assertEquals(m.content,"Hello there!");
        assertEquals(m.timestamp,Instant.ofEpochSecond(1448470901));

    }
}
