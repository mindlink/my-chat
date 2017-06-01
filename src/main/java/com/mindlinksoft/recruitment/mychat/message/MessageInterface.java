package com.mindlinksoft.recruitment.mychat.message;

import java.time.Instant;

/**
 * Message Interface must be implemented by message objects
 */
public interface MessageInterface {
    /**
     * method definition to get the content of a message
     * @return String the message content
     */
    String getContent();

    /**
     * method definition to get the sender id
     * @return String the senderId
     */
    String getSenderId();

    /**
     * method definition to get the timestamp
     * @return Instant the timestamp
     */
    Instant getTimestamp();

    /**
     * method definition to set the message content
     * @param content String the message content
     */
    void setContent(String content);

    /**
     * method definiton to set the senderId
     * @param senderId String the senderId
     */
    void setSenderId(String senderId);

    /**
     * method definition to set timestamp
     * @param timestamp Instant the timpestamp
     */
    void setTimestamp(Instant timestamp);
}