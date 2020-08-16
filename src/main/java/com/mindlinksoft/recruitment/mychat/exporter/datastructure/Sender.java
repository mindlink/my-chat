package com.mindlinksoft.recruitment.mychat.exporter.datastructure;

/**
 * Represents the sender in a message
 */
public class Sender {

    /**
     * The sender as it appears in text
     */
    private final String senderText;

    /**
     * The number of messages sent by this sender
     */
    private final long messageCount;

    /**
     * The id of this instance
     */
    private final transient long senderId;

    /**
     * The number of instances of Sender
     */
    private static transient long senderCount = 0;

    /**
     * Short-hand constructor. Assigns zero messages for given
     * sender.
     *
     * @param senderText the sender as it appears in text
     */
    public Sender(String senderText) {
        this(senderText, 0);
    }

    /**
     * Returns an instance of Sender.
     *
     * @param senderText   the sender as it appears in text
     * @param messageCount number of messages sent by this sender
     */
    public Sender(String senderText, long messageCount) {
        this.senderText = senderText;
        this.messageCount = messageCount;
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
     * of instantiation
     *
     * @return long id of this sender
     */
    public long getSenderId() {
        return senderId;
    }

    /**
     * Returns the number of messages sent by this sender
     *
     * @return numbers of messages sent
     */
    public long getMessageCount() {
        return messageCount;
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

    /**
     * Returns a string representation of this object
     *
     * @return string this sender's text, message count and id
     */
    @Override
    public String toString() {
        return "Sender{" +
                "senderText='" + senderText + '\'' +
                ", messageCount=" + messageCount +
                ", senderId=" + senderId +
                '}';
    }
}