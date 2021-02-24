package com.mindlinksoft.recruitment.mychat;

import java.util.Iterator;
import java.util.List;

public class ConversationRedacter {
    /**
     * Replaces all words in the {@code blacklist} with *redacted* in the given {@code conversation}
     * @param conversation The conversation to be redacted
     * @param blacklist The list of words to redact
     * @return
     */
    public Conversation blacklistConversation(Conversation conversation, List<String> blacklist) {
    	
    	Iterator<Message> messageIterator = conversation.messages.iterator();
    	
    	while(messageIterator.hasNext()) {
    		Message message = messageIterator.next();
    		
    		blacklist.forEach((word) -> {
        		String redactedContent = message.content.replaceAll(word, "*redacted*");
        		message.content = redactedContent;
        	});
    		
    	}
    	
    	return conversation;
    }
}
