package com.mindlinksoft.recruitment.mychat.exporter.datastructure;

/**
 * Represents the sender in a message
 */
public class Sender {

    private final String senderText; 

    public Sender(String senderText) {
        this.senderText = senderText;
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
     * Checks if given object has the same name
     * 
     * @return true if other object has the same senderText
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        else if (!(other instanceof Sender)) return false;
        Sender otherSender = (Sender) other;
        return this.senderText.equals(otherSender.getSenderText());
    }
}