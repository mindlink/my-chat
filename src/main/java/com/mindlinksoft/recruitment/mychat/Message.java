package com.mindlinksoft.recruitment.mychat;

import java.time.DateTimeException;
import java.time.Instant;

/**
 * Represents an immutable chat message.
 */
public final class Message {
    /**
     * The message content.
     */
    private final String content;

    /**
     * The message timestamp.
     */
    private final Instant timestamp;

    /**
     * The message sender.
     */
    private final String senderId;

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
    
    /**
     * Getter for Content
     * @return String {@code content}
     */
    public String getContent() {
    	return content;
    }
    
    /**
     * Getter for Timestamp
     * @return String {@code timestamp}
     */
    public Instant getTimestamp() {
    	return timestamp;
    }
    
    /**
     * Getter for SenderId
     * @return String {@code senderId}
     */
    public String getSenderId() {
    	return senderId;
    }
    
    /**
     * Message parser to instantiate Message objects from String
     * @param msgString String representing message to be parsed
     * @return Message Constructed with arguments parsed from {@code msgString}
     */
    public static Message parseMessage(String msgString) {
        try {
        	String[] split = msgString.split(" ",3);
        	
        	String timestampString = split[0];
        	String senderId = split[1];
        	String content = split[2];
        	
            Instant timestamp = Instant.ofEpochSecond(Long.parseUnsignedLong(timestampString));
            
            return new Message(timestamp,senderId,content);
        } catch (ArrayIndexOutOfBoundsException e) {
        	throw new RuntimeException(String.format(Const.INVALID_MESSAGE, msgString), e);
        } catch (NumberFormatException | DateTimeException e) {
        	throw new RuntimeException(String.format(Const.INVALID_TIMESTAMP, msgString), e);
        }
    }
}
