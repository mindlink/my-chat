package com.mindlinksoft.recruitment.mychat;
import com.mindlinksoft.recruitment.mychat.exceptions.IOProblemException;
import com.mindlinksoft.recruitment.mychat.exceptions.WrongArgumentsException;
import com.mindlinksoft.recruitment.mychat.io.ReadConversation;
import com.mindlinksoft.recruitment.mychat.io.WriteConversation;
import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.model.Message;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class IOTests {

    @Test
    public void testWriter() throws IOProblemException, WrongArgumentsException {
        Conversation conversation = new Conversation("Test",null);
        ConversationExporterConfiguration configuration =
                new ConversationExporterConfiguration("chat.txt","chatTest.json",null);
        File f = new File(configuration.outputFilePath);
        WriteConversation.write(conversation,configuration);
        assertTrue(f.exists());
        f.delete();
    }

    @Test
    public void testReader() throws IOProblemException, WrongArgumentsException {
        Conversation c = ReadConversation.read("chat.txt");
        for(Message m:c.messages){
            assertNotNull(m.timestamp);
            assertNotNull(m.content);
            assertNotNull(m.senderId);
        }
    }
}
