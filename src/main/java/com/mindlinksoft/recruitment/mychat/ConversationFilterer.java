package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Helper class to filter conversations.
 */
public class ConversationFilterer {
	
	private String userFilter;
    private String keyWord;
    private ArrayList<String> blackList;
    private boolean isHideNumbers;
    private boolean isObfuscateIDs;
    private String redactStr = "*redacted*";
    
    /**
     * Initialises a new instance of the {@link ConversationFilterer} class.
     * @param userFilter Name of the user to filter messages by
     * @param keyWord Keyword to filter by
     * @param blackList	Words to be censored from the output
     * @param isHideNumbers Flag to censor phone and credit card numbers
     * @param isObfuscateIDs Flag to obfuscate IDs
     */
	public ConversationFilterer(String userFilter, String keyWord, ArrayList<String> blackList, boolean isHideNumbers,
			boolean isObfuscateIDs) {
		super();
		this.userFilter = userFilter;
		this.keyWord = keyWord;
		this.blackList = blackList;
		this.isHideNumbers = isHideNumbers;
		this.isObfuscateIDs = isObfuscateIDs;
	}
	
	/**
	 * Applies any selected filters to a conversation and returns the modified version
	 * @param conversation An unfiltered conversation
	 * @return The modified conversation with filters applied
	 */
	public Conversation filterConversation(Conversation conversation) {
		
		ArrayList<Message> messages = conversation.getMessages();
		
		if(userFilter != null) {
			messages = userFilter(messages);
		}
		
		if(keyWord != null) {
			messages = keywordFilter(messages);
		}
		
		if(blackList != null) {
			messages = blacklistFilter(messages);
		}
		
		if(isHideNumbers) {
			messages = hideNumbers(messages);
		}
		
		if(isObfuscateIDs) {
			messages = obfuscateIDs(messages);
		}
		
    	return(new Conversation(conversation.getName(), messages));
	}
	
	/**
	 * Print friendly version of filters for logging
	 */
	@Override
	public String toString() {
		String printUserFilter = (userFilter == null) ? "None" : userFilter;
		String printKeyWord = (keyWord == null) ? "None" : keyWord;
		String printBlacklist = "None";
		if(blackList != null) {
			printBlacklist = "";
			for (String word : blackList) {
				printBlacklist += word + " ";
			}
		}
		
		String filters = String.format("User filter: %s \n"
				+ "Keyword filter: %s \n"
				+ "Blacklist words: %s \n"
				+ "Hide credit card and phone numbers: %b \n"
				+ "Obfuscate user IDs: %b \n", printUserFilter, printKeyWord, printBlacklist, isHideNumbers, isObfuscateIDs);
		return filters;	
	}
	
	/**
	 * Selects only those messages sent by a specific user
	 * @param messages Unfiltered ArrrayList of messages
	 * @return Filtered messages
	 */
	private ArrayList<Message> userFilter(ArrayList<Message> messages) {
		ArrayList<Message> filteredMessages = new ArrayList<Message>();
		
		for(Message message : messages) {
			if(message.getSenderId().equals(userFilter)) {
				filteredMessages.add(message);
			}
		}
		return filteredMessages;
	}

	/**
	 * Selects only those messages containing a specific keyword
	 * @param messages Unfiltered ArrrayList of messages
	 * @return Filtered messages
	 */
	private ArrayList<Message> keywordFilter(ArrayList<Message> messages) {
		ArrayList<Message> filteredMessages = new ArrayList<Message>();;
		for(Message message : messages) {
			if(message.getContent().toLowerCase().contains(keyWord)) {
				filteredMessages.add(message);
			}
		}
		return filteredMessages;
	}

	/**
	 * Replaces any words in a given blacklist with a redacted string
	 * @param messages Unfiltered ArrrayList of messages
	 * @return Filtered messages
	 */
	private ArrayList<Message> blacklistFilter(ArrayList<Message> messages) {
		ArrayList<Message> filteredMessages = new ArrayList<Message>();;
		for(Message message : messages) {
			String[] contentArray = message.getContent().split(" ");
			for(int i = 0; i<contentArray.length; i++) {
				for(String word : blackList) {
					if(word.equals(contentArray[i].toLowerCase().replaceAll("[^a-z]", ""))) {
						contentArray[i] = contentArray[i].replaceAll((String.format("(?i)%s",word)), redactStr);
					}
				}
			}
			String content = String.join(" ", contentArray);
			filteredMessages.add(new Message(message.getTimestamp(), message.getSenderId(), content));
		}
		return filteredMessages;
	}

	/**
	 * Replaces any phone or credit card numbers with a redacted string
	 * @param messages Unfiltered ArrrayList of messages
	 * @return Filtered messages
	 */
	private ArrayList<Message> hideNumbers(ArrayList<Message> messages) {
		ArrayList<Message> filteredMessages = new ArrayList<Message>();;
		for(Message message : messages) {
			String[] contentArray = message.getContent().split(" ");
			for(int i = 0; i<contentArray.length; i++) {
				contentArray[i] = contentArray[i].replaceAll("[0-9]{11,16}", redactStr);
			}
			String content = String.join(" ", contentArray);
			filteredMessages.add(new Message(message.getTimestamp(), message.getSenderId(), content));
		}
		return filteredMessages;
	}

	/**
	 * Replaces user IDs with obfuscated IDs. The same original user ID in any single 
	 * export is replaced with the same obfuscated user ID
	 * @param messages Unfiltered ArrrayList of messages
	 * @return Filtered messages
	 */
	private ArrayList<Message> obfuscateIDs(ArrayList<Message> messages) {
		ArrayList<Message> filteredMessages = new ArrayList<Message>();;
		HashMap<String, String> userIDs = new HashMap<>();
		int IDnumber = 1;
		for(Message message : messages) {
			if(!userIDs.containsKey(message.getSenderId())) {
				userIDs.put(message.getSenderId(), "User" + IDnumber);
				IDnumber++;
			}
			filteredMessages.add(new Message(message.getTimestamp(), userIDs.get(message.getSenderId()), message.getContent()));
		}
		return filteredMessages;
	}

}
