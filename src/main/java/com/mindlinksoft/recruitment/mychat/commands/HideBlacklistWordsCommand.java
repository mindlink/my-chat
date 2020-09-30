package com.mindlinksoft.recruitment.mychat.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * Command for hiding/redacting words from messages
 */
public class HideBlacklistWordsCommand implements IConversationExportCommand{
	
	private String[] blacklistWords;
	private final String redactedStr = "*redacted*";
	
	public HideBlacklistWordsCommand(String[] words) {
		this.blacklistWords = words;
	}

	@Override
	public Conversation doCommand(Conversation conversation) {
		Collection<Message> allMessages = conversation.getMessages();
		Collection<Message> filteredMessages = new ArrayList<Message>();
		
		for(Message msg : allMessages) {
			boolean doHide = Arrays.stream(blacklistWords).anyMatch(msg.getContent()::contains);
			if (doHide) {
				String filteredMsg = redactBlacklistWords(msg.getContent());
				filteredMessages.add(new Message(msg.getTimestamp(), msg.getSenderId(), filteredMsg));
			}
			else filteredMessages.add(msg);
		}
		
		return new Conversation(conversation.getName(), filteredMessages);
	}
	
	private String redactBlacklistWords(String msg) {
		String newMsg = msg;
		for (String word : blacklistWords) {
			newMsg = newMsg.replace(word, redactedStr);
		}
		return newMsg;
	}
	
	public String[] getBlacklistWords() {
		return blacklistWords;
	}

}
