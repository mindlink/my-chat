package com.mindlinksoft.recruitment.mychat.filters;

import java.util.ArrayList;
import java.util.Collection;

import com.mindlinksoft.recruitment.mychat.Message;

/**
 * Software feature that filters a conversation to find all the messages with a specific keyword and takes care of
 * writing the result out in JSON.
 * 
 */

public class KeywordFilter implements ConversationFilter {
	
	private String keyword;
	
	public KeywordFilter(String keyword){
		this.keyword = keyword;
	}
	
	
	@Override
	/**
     * Searches through the conversation looking for messages with the specific keyword.
     * @param input In this case, the keyword.
     * @returns A collection of messages sent containing that keyword.
     */
	public Collection<Message> useFilter(Collection<Message> conversation) {
		
		Collection<Message> result = new ArrayList<Message>();
		//Make sure conversation is not null.
		if(conversation != null){
			for (Message message : conversation){
				String text = message.getContent().toLowerCase();
				if (text.contains(this.keyword.toLowerCase())){
					result.add(message);
				}
			}
			
		}
		return result;
	}

}
