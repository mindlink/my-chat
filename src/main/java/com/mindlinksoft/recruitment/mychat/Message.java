package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;

/**
 * Represents a chat message.
 */
public final class Message {
    /**
     * The message content.
     */
    public String content;

    /**
     * The message timestamp.
     */
    public Instant timestamp;

    /**
     * The message sender.
     */
    public String senderId;

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

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSentBy(String userName){
        if(this.senderId.equals(userName)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean contentContains (String keyWord){
        if(this.content.contains(keyWord)){
            return true;
        }
        else{
            return false;
        }
    }

    public void redact(String[] redactedWords){
        if(redactedWords.length > 0){
            for(int i = 0; i < redactedWords.length; i++) {
                setContent(this.content.replace(redactedWords[i], "*redacted*"));
            }
        } else {
            System.out.println("No input given to be redacted");
        }
    }

}
