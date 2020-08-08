package com.mindlinksoft.recruitment.mychat.exporter.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;

public class ConversationReader {
    
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
    public Conversation read() throws IOException {
        List<Message> messages = Files.lines(Paths.get(inputFilePath))
            .skip(1) // skips header line
            .map(Message::parseLine)
            .collect(Collectors.toList());
        
        conversation.setMessages(messages);
        
        String name = Files.lines(Paths.get(inputFilePath)).findFirst().get();
        conversation.setName(name);
        
        return conversation;
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public Conversation getConversation() {
        return conversation;
    }

}