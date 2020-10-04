package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * Represents a chat message.
 */
public class UserReport implements Comparable<UserReport>{
    /**
     * The message content.
     */
	public String sender;
    /**
     * The message timestamp.
     */
    public int count;

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
    public UserReport(String sender, int count) {
        this.sender = sender;
        this.count = count;
    }
	
	@Override 
	public int compareTo(UserReport ur){
		return ur.count - this.count;
	}
}
