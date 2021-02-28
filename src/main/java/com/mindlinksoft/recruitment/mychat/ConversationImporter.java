package com.mindlinksoft.recruitment.mychat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;



/**
 * Represents a conversation exporter that can read a conversation from a file into a Conversation object
 *
 */
public class ConversationImporter {
	
    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws FileNotFoundException Thrown when the given file is not found.
     * @throws IOException Thrown when the file failed to open
     */
    public static Conversation readConversation(String inputFilePath) throws Exception {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ", 3);
                //Previously splitting the message after the first word, fixed by setting a max of three strings to be returned by split 


                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file at " + inputFilePath + " was not found.",e);
        } catch (IOException e) {
            throw new IOException("File at" + inputFilePath + " Failed to open",e);
        }
    }
}
