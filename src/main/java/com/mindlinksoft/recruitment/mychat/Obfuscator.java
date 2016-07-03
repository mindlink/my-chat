package com.mindlinksoft.recruitment.mychat;

import java.util.List;

/**
 * Class responsible for obfuscation/redacting of terms from the conversation*/
class Obfuscator {

	/**
	 * Blacklists the specified target word from the specified
	 * collection of messages. Will locate the target case insensitively and
	 * trim leading and trailing spaces from the target parameter.
	 * 
	 * @param target the word to blacklist
	 * @param replacement what will replace each occurrence of the target
	 * @messages the {@link List} object storing the {@link Message} 
	 * instances from which the word is to be redacted*/
	static void obfuscateWord(String target, String replacement, 
			List<Message> messages) {
		
		target = target.trim().toLowerCase();
		
		for(Message message : messages) {
			message.content = message.content.replaceAll("(?i)" + target, 
					replacement);
		}
	}
	
	/**
	 * Replaces the specified target with the specified replacement, wherever
	 * it occurs as the message sender
	 * @param target the senderId t blacklist
	 * @param replacement what will replace the target
	 * @messages the {@link List} object storing the {@link Message} 
	 * instances in which the target is to be replaced
	 * */
	static void obfuscateSender(String target, String replacement, 
			List<Message> messages) {
		
		for(Message message : messages) {
			if(message.senderId.compareTo(target) == 0)
				message.senderId = replacement;
		}
	}
}
