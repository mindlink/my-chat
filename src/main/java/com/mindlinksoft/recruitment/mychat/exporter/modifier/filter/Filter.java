package com.mindlinksoft.recruitment.mychat.exporter.modifier.filter;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;

/**
 * Represents filters that modify a conversation by 
 * only writing messages that fulfil a certain criteria,
 * usually set in the constructor of the implementing
 * Filter class
 */
public interface Filter {
    /**
     * Implementing methods will create a new Conversation
     * with messages that fulfil a certain criteria
     * @return new, filtered Conversation
     */
    Conversation filter();
}