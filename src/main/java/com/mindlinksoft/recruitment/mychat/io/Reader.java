package com.mindlinksoft.recruitment.mychat.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.TreeSet;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * Reader class used to parse chat from a provided text file
 * @author Mohamed Yusuf
 *
 */
public class Reader {
	
    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param config Chat configurations.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(ConversationExporterConfiguration config) throws Exception {
    	String inputFilePath = config.getInputFilePath();
    	BufferedReader r = null;
        try {
        	r = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)));
        	//TreeSet ensure no duplicate and custom ordering
        	TreeSet<Message> messages = new TreeSet<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(config.getDELIMITER(), config.getDELIMITER_LIMIT());
                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file [" + inputFilePath + "] was not found. \n" + e.getMessage());
        } catch (IOException e) {
            throw new IOException("Something went wrong: " + e.getMessage());
        } finally {
        	r.close();
        }
    }
}
