package com.mindlinksoft.recruitment.mychat.exporter.reader;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;

/**
 * Represents the reading service which will read from a text file
 */
public interface ConversationReaderService {
    /**
     * Starts the writer service, which will create a conversation
     * from the provided text file
     */
    Conversation read();
}