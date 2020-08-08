package com.mindlinksoft.recruitment.mychat.exporter.reader;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.*;

public class ConversationReader {
    
    private String inputFilePath;
    private Conversation conversation;

    ConversationReader() {
        // visible for testing
    }

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
        return null;
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}