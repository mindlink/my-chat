package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import com.google.gson.annotations.*;

/**
 * Represents a user message count report.
 */
public class UserReport implements Comparable<UserReport>{
    /**
     * The message sender.
     */
	@Expose public String sender;
    /**
     * The message count.
     */
    @Expose public int count;

    /**
     * Initializes a new instance of the {@link UserReport} class.
     * @param sender The ID of the sender.
     * @param count The message count.
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
