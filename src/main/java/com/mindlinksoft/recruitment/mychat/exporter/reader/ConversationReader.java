package com.mindlinksoft.recruitment.mychat.exporter.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;


public class ConversationReader implements ConversationReaderService {
    
    private final String inputFilePath;
    private final Conversation conversation;

    public ConversationReader(String inputFilePath) {
        this.inputFilePath = inputFilePath;
        this.conversation = new Conversation();
    }

    /**
     * Reads the file in inputFilePath, and returns a Conversation
     * object, complete with titles, map of active users and a list
     * of messages.
     * @return Conversation built from parsing input file
     */
    public Conversation read() {
        try (BufferedReader bReader = Files.newBufferedReader(Paths.get(inputFilePath))) {
            conversation.setName(bReader.readLine()); // header line

            List<Message> messages = bReader.lines()
                .map(Message::parseLine)
                .collect(Collectors.toList());

            conversation.setMessages(messages);
            return conversation;
        } catch (NoSuchFileException e) {
            e.printStackTrace();
            return null; // FIXME: change
        } catch (IOException e) {
            e.printStackTrace();
            return null; // FIXME: change
        }
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public Conversation getConversation() {
        return conversation;
    }

}