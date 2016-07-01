package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * class responsible of hiding specific blacklisted words
 * @author Asmaa
 *
 */

public class Blacklisting {
	
	/**
	 * Method responsible for replacing the selected word with *redacted*
	 * @param convo the conversation to have the replacement applied
	 * @param blacklist the list of words to replace 
	 * 
	 */
	
	public Conversation hideWord(Conversation convo, List<String> blacklist){
		
		List<Message> msgs = new ArrayList<Message>();
		
		// for every msg in the conversation
		for (Message msg : convo.getMsg()){
			// loop through every word in the blacklist
			for (String word : blacklist){
				// replace the words
				msg.setContent(msg.getContent().replaceAll("\\b" + Pattern.quote(word) + "\\b" , "*redacted*"));
			}
			
			msgs.add(msg);
		}
		
		return new Conversation(convo.getName(), msgs);
	}

}
