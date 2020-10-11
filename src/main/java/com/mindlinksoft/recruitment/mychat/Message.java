package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import com.google.gson.annotations.*;

/**
 * Represents a chat message.
 */
public final class Message {
    /**
     * The message content.
     */
	@Expose public String content;
    /**
     * The message timestamp.
     */
    @Expose public Instant timestamp;

    /**
     * The message sender.
     */
    @Expose public String senderId;

	/**
	* Boolean set if message should be converted to json
	*/
	public boolean convert;
    /**
     * Initializes a new instance of the {@link Message} class.
     * @param timestamp The timestamp at which the message was sent.
     * @param senderId The ID of the sender.
     * @param content The message content.
     */
    public Message(Instant timestamp, String senderId, String content) {
        this.timestamp = timestamp;
        this.senderId = senderId;
		this.content = content;
		convert = true;
    }
	
	public void redact(String[] blacklist){
		if(blacklist != null){
			String blacklisted_message = "";
			String[] message = content.split(" ");
				for(String s : message){
					for(String word : blacklist){
						if(s.toLowerCase().contains(word.toLowerCase())){
							s = s.toLowerCase().replace(word.toLowerCase(), "*redacted*");
						}
					}
					blacklisted_message += s + " ";
				}
			content = blacklisted_message.trim();
		}
	}
	
	public void filterByWord(String filter_word){
		if(filter_word != null){
			if(content.toLowerCase().contains(filter_word.toLowerCase())){
				convert = false;
			}
		}
		
	}
	
	public void filterByUser(String filter_user){
		if(filter_user != null){
			if(senderId.toLowerCase().equals(filter_user.toLowerCase())){
				convert = false;
			}
		}
	}
}
