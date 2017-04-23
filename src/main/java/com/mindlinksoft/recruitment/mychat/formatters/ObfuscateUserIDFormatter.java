package com.mindlinksoft.recruitment.mychat.formatters;

import java.util.HashMap;
import java.util.Map;

import com.mindlinksoft.recruitment.mychat.messages.Message;


public class ObfuscateUserIDFormatter implements IFormatter{
	
	/**
	 * A {@link Formatter} that Obfuscates Messages SenderIDs 
	 * @author Omar
	 *
	 */
	Map<String,String> userIDObfuscates = new HashMap<String, String>();	
	
	private final String OBFUSCATE_PREFIX = "UserID_";
	
	/**
	 * Replaces Sender IDs by 
	 * "UserID_" followed by an Integer
     * @param message
	 */
    public Message apply(Message message)
    {
    	String userID= message.getSenderId();

    	if(!userIDObfuscates.containsKey(userID))
    	{
    		userIDObfuscates.put(userID, OBFUSCATE_PREFIX+userIDObfuscates.size());
    	}
		message.setSenderId(userIDObfuscates.get(userID));
    	return message;
    }
}
