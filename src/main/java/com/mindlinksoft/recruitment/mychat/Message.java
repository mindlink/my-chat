package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;

/**
 * Represents a chat message.
 */
public final class Message implements Comparable<Message>{
    //Attributes ---------------------------------------------------------------
    /**
     * The message content.
     */
    private String content;

    /**
     * The message timestamp.
     */
    private Instant timestamp;

    /**
     * The message sender.
     */
    private String senderId;
    
    //Constructor---------------------------------------------------------------
    /**
     * Initializes a new instance of the {@link Message} class.
     * @param timestamp The timestamp at which the message was sent.
     * @param senderId The ID of the sender.
     * @param content The message content.
     */
    public Message(Instant timestamp, String senderId, String content) {
        this.content = content;
        this.timestamp = timestamp;
        this.senderId = senderId;
    }
    
    //Accesors -----------------------------------------------------------------
    /**
     * Method to get sender Id.
     * @return Message senderId.
     */
    public String getSenderId(){
        return this.senderId;
    }
    /**
     * Method to get timestamp.
     * @return Message content.
     */
    public Instant getTimestamp(){
        return this.timestamp;
    }
    /**
     * Method to get content
     * @return Message content
     */
    public String getContent(){
        return this.content;
    }
    
    //Mutators -----------------------------------------------------------------
    /**
     * Replaces content with new content.
     * @param content New content.
     */
    public void setContent(String content)
    {
        this.content=content;
    }

    @Override
    public int compareTo(Message m) {
        return(this.timestamp.compareTo(m.getTimestamp()));
    }
}//end class
