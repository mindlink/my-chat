package com.mindlinksoft.recruitment.mychat;

import java.util.List;

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
}
