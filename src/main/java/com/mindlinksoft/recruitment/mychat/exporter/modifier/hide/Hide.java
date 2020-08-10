package com.mindlinksoft.recruitment.mychat.exporter.modifier.hide;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;

/**
 * Represents a modifier that hides certain messages that fulfil a
 * certain criteria, usually set in the constructor of the implementing Hide
 * class
 */
public interface Hide {
    /**
     * Implementing methods will create a new Conversation
     * without the messages that fulfil a certain criteria
     * @return new Conversation with hidden messages removed
     */
    Conversation hide();
}