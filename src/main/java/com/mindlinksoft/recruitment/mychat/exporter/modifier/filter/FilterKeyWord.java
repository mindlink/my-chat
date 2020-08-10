package com.mindlinksoft.recruitment.mychat.exporter.modifier.filter;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;

public class FilterKeyWord implements Filter {

    /**
     * The key word to filter
     */
    private final String keyWord;
    
    /**
     * The conversation to be filtered
     */
    private final Conversation conversation;

    /**
     * Returns a filter that selects the given key word
     * @param conversation contains the messages you wish to filter
     * @param keyWord the key word to filter
     */
    public FilterKeyWord(Conversation conversation, String keyWord) {
        this.conversation = conversation;
        this.keyWord = keyWord;
    }

    @Override
    public Conversation filter() {
        return null;
    }
}