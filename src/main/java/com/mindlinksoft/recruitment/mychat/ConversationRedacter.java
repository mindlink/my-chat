package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ConversationRedacter {
    /**
     * Replaces all words in the {@code blacklist} with *redacted* in the given {@code conversation}
     * @param conversation The conversation to be redacted
     * @param blacklist The list of words to redact
     * @return A new {@link Conversation} with the messages redacted
     */
    public Conversation blacklistConversation(Conversation conversation, List<String> blacklist) {
    	
    	
    	Collection<Message> newMessages = new ArrayList<Message>();
    	Iterator<Message> messageIterator = conversation.messages.iterator();
    	
    	while(messageIterator.hasNext()) {
    		Message message = messageIterator.next();
    		String newContent = message.content;
   		    		
    		for (String word : blacklist) {
        		newContent = newContent.replaceAll(word, "*redacted*");
    		}
    		
    		Message newMessage = new Message(message.timestamp,message.senderId,newContent);
    		newMessages.add(newMessage);
    		
    	}
    	
    	return new Conversation(conversation.name,newMessages,conversation.activity);
    }
}
