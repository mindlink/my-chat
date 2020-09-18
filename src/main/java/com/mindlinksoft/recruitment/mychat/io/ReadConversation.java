package com.mindlinksoft.recruitment.mychat.io;

import com.mindlinksoft.recruitment.mychat.exceptions.IOProblemException;
import com.mindlinksoft.recruitment.mychat.exceptions.WrongArgumentsException;
import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.Message;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ReadConversation {
    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     *
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public static Conversation read(String inputFilePath) throws WrongArgumentsException, IOProblemException {
        try (InputStream is = new FileInputStream(inputFilePath);
             BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();
            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                Message m = lineToMessageBuilder(line);
                messages.add(m);
            }

            return new Conversation(conversationName, messages);

        } catch (FileNotFoundException e) {
            throw new WrongArgumentsException();
        } catch (IOException e) {
            throw new IOProblemException(e.getMessage(), e.getCause());
        }
    }

    /**
     * Reads a full line and builds a Message
     *
     * @param line is a line from the txt file
     * @return a Message composed with the information
     */
    public static Message lineToMessageBuilder(String line) {
        String[] splitLine = line.split(" ", 3);
        Instant time = Instant.ofEpochSecond(Long.parseUnsignedLong(splitLine[0]));
        String senderId = splitLine[1];
        String content = splitLine[2];
        return new Message(time, senderId, content);
    }
}
