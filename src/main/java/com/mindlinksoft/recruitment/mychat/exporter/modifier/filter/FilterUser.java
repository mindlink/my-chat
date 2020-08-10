package com.mindlinksoft.recruitment.mychat.exporter.modifier.filter;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;

/**
 * Represents a filter that selects a certain sender
 */
public class FilterUser implements Filter {

    /**
     * The sender as it appears in text
     */
    private final String senderText;

    /**
     * Returns a filter that selects the given sender
     * @param senderText
     */
    public FilterUser(Conversation conversation, String senderText) {
        this.senderText = senderText;
    }

    /**
     * Creates a new Conversation that only contains
     * the specified sender's messages.
     * @return filtered Conversation
     */
    @Override
    public Conversation filter() {
        // TODO Auto-generated method stub
        return null;
    }
    
}