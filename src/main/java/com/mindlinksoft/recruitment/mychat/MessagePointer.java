package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;

/**
 * Created by Tom on 04-Jun-16.
 */
public class MessagePointer {

    private Collection<Message> messages;

    public Collection<Message> getMessages() {
        if (isValid())
            return messages;
        else
            return null; //probably should through exception
    }

    public int getStartsAt() {
        if (isValid())
            return startsAt;
        else
            return -1; //probably should through exception
    }

    public int getEndsAt() {
        if (isValid())
            return endsAt;
        else
            return -1; //probably should through exception
    }

    public boolean isValid() {
        return (messages.hashCode() == hash);
    }

    public int getMessageIndex() {
        return messageIndex;
    }

    public void setMessageIndex(int messageIndex) {
        this.messageIndex = messageIndex;
    }

    private int messageIndex;
    private int startsAt;
    private int endsAt;
    private int hash;

    /**
     * Instantiates a new Message pointer.
     *
     * @param Collection<Message>messages       a reference to the collection of messages
     * @param int messageIndex                  is equal to the index of the Message in the collection
     * @param int startsAt                      index where the search term starts in the Message.content string
     * @param int endsAt                        index where the search term ends in the Message.content string
     */

    public MessagePointer(Collection<Message> messages, int messageIndex, int startsAt, int endsAt) {
        this.messageIndex = messageIndex;
        this.messages = messages;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.hash = messages.hashCode();
    }
}
