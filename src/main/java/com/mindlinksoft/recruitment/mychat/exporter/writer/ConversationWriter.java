package com.mindlinksoft.recruitment.mychat.exporter.writer;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;

public class ConversationWriter implements ConversationWriterService {
    
    private final String outputFilePath;
    private final Conversation conversation;
    
    public ConversationWriter(String outputFilePath, Conversation conversation) {
        this.conversation = conversation;
        this.outputFilePath = outputFilePath;
    }

    public void write() {
        
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public Conversation getConversation() {
        return conversation;
    }
}