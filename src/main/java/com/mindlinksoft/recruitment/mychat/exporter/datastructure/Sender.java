package com.mindlinksoft.recruitment.mychat.exporter.datastructure;

/**
 * Represents the sender in a message
 */
public class Sender {

    private final String senderText; 
    private final long senderId;
    private static long senderCount = 0;

    public Sender(String senderText) {
        this.senderText = senderText;
        this.senderId = ++senderCount;
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
     * Returns the long id of this sender, assigned in order
     * of appearance
     * 
     * @return long id of this sender
     */
    public long getSenderId() {
        return senderId;
    }

    /**
     * Checks if given object is a Sender and has same id
     * 
     * @param other object you wish to compare to
     * @return true if other sender has same id, else false
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        else if (!(other instanceof Sender)) return false;
        Sender otherSender = (Sender) other;
        return this.senderId == otherSender.getSenderId();
    }
}