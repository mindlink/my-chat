package com.mindlinksoft.recruitment.mychat;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class Reader {
    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(String inputFilePath) throws Exception {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ");

                /**
                *  split[0]        ->  timestamp
                *  split[1]        ->  username
                *  split[2 .. n]   ->  message content
                */

                String messageContent = "";
                for(int wordIndex = 2; wordIndex < split.length; wordIndex++) {
                    messageContent += split[wordIndex];
                    // if not last word, add space
                    if(wordIndex != split.length - 1) {
                        messageContent += " ";
                    }
                }

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], messageContent));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong");
        }
    }
}