package com.mindlinksoft.recruitment.mychat.filters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mindlinksoft.recruitment.mychat.Message;

/**
 * Software feature that filters a conversation to find all the messages with a specific keyword, 
 * replaces such words with "\*redacted\*" and takes care of writing the result out in JSON.
 * 
 */


public class BlacklistFilter implements ConversationFilter {
	
	private List<String> blacklist = new ArrayList<String>();
	
	public BlacklistFilter(List<String> blacklist){
		this.blacklist = blacklist;
	}
	
	
	@Override
	public Collection<Message> useFilter(Collection<Message> conversation) {
		
		//Replace the words with "\*redacted\*"
		
		//Make sure conversation is not null.
		if(conversation != null){
			for (Message message : conversation){
				for (String word : this.blacklist){
					String text = message.getContent().toLowerCase();
					if (text.contains(word.toLowerCase())){
						//Replace text with "\*redacted\*"
						String filteredContent = text.replaceAll(word, "\\*redacted\\*");
						message.setContent(filteredContent);
					}
				}			
			}
		}
		return conversation;			
			
	}

}
