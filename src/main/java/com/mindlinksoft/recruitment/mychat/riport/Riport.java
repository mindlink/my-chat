package com.mindlinksoft.recruitment.mychat.riport;

/**
 * Riport object for the most active users.
 *
 * @author Gabor
 */
public class Riport {

    /**
     * Id of the sender.
     */
    private String senderId;

    /**
     * Count of messages in a conversation.
     */
    private int messageCount;

    public Riport(String senderId) {
        this.senderId = senderId;
        this.messageCount = 0;
    }

    public String getSenderId() {
        return senderId;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void increaseMessageCount() {
        this.messageCount++;
    }
}
