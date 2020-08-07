package com.mindlinksoft.recruitment.mychat;

/**
 * Represents the sender in a message
 */
public class Sender {

    private static transient long senderCount; // used to set id
    private transient long senderId; // for equals() comparison
    private String senderText; 

    Sender(String senderText) {
        this.senderId = senderCount++;
        this.senderText = senderText;
    }

    /**
     * Returns this sender's unique identifier
     * 
     * @return unique identifier in long
     */
    public long getSenderId() {
        return this.senderId;
    }

    /**
     * Returns the sender's name, as it appears in text
     * 
     * @return sender's name, as it appears in text
     */
    public String getSenderText() {
        return senderText;
    }

    /**
     * Checks if given object is same sender as this
     * 
     * @return true if senderId is equal, else false
     */
    public boolean equals(Object other) {
        if (other instanceof Sender) {
            Sender otherSender = (Sender) other;
            return otherSender.getSenderId() == this.getSenderId();
        } else {
            return false;
        }
    }
}