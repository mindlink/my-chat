package com.mindlinksoft.recruitment.mychat.filters;

import java.util.ArrayList;
import java.util.Collection;

import com.mindlinksoft.recruitment.mychat.Message;

/**
 * Software feature that filters a conversation to find all the messages sent by a specific user and takes care of
 * writing the result out in JSON.
 * 
 */

public class UserFilter implements ConversationFilter {
	
	private String senderId;
	
	public UserFilter(String senderId){
		this.senderId = senderId;
	}
	
	@Override
    /**
     * Searches through the conversation looking for a specific userID.
     * @param input In this case, userID.
     * @returns A collection of messages sent by that userID.
     */
	public Collection<Message> useFilter(Collection<Message> conversation) {
		
		Collection<Message> result = new ArrayList<Message>();
		
		//Make sure conversation and input are not null.
		if(conversation != null){
				for (Message message : conversation){
					if ((message.getSenderId() != null) && (message.getSenderId().equals(this.senderId))){
						result.add(message);
					}
				}	
		}	
		return result;
	}

}
