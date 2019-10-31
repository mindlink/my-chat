package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;
import java.util.UUID;

/**
 * Represents a chat message.
 */
public final class Message {

    /**
     * The message timestamp.
     */
    public Instant unix_timestamp;

    /**
     * The message sender.
     */
    public String username;

    /**
     * The message content.
     */
    public String message;

    /**
     * Initializes a new instance of the {@link Message} class.
     * @param timestamp The timestamp at which the message was sent.
     * @param senderId The ID of the sender.
     * @param message The message content.
     */

    public Message( Instant timestamp, String senderId, String message) {
        this.unix_timestamp = timestamp;
        this.message = message;
        this.username = senderId;
    }

    public String toString(){
        return "unix_timeStamp ," + unix_timestamp + ", " + "username , " +  username + ", " + "message , " + message + ", \n ";
    }
}
