package com.mindlinksoft.recruitment.mychat.filters;

import java.util.ArrayList;
import java.util.Collection;

import com.mindlinksoft.recruitment.mychat.Message;


/**
 * Obfuscate user IDs
 * A flag can be specified to obfuscate user IDs
 * All user IDs are obfuscated in the output.
 * The same original user ID in any single export is replaced with the same obfuscated user ID 
 * i.e. messages retain their relationship with the sender, only the ID that represents the sender is changed.
 *
 */

public class ObfuscateFilter implements ConversationFilter {

	private ArrayList<String> userIdArray = new ArrayList<String>();
	
	@Override
	public Collection<Message> useFilter(Collection<Message> conversation) {
		
		//Make sure conversation and input are not null.
		
		
		//Make sure conversation is not null.
		if(conversation != null){
			for (Message message : conversation){
				if(!userIdArray.contains(message.getSenderId())){
					userIdArray.add(message.getSenderId());
				}
				message.setSenderId("User #" + (userIdArray.indexOf(message.getSenderId()) + 1));
			}	
		}
		return conversation;
	}

}
