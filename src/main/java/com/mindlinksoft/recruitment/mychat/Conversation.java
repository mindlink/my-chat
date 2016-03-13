package com.mindlinksoft.recruitment.mychat;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {
    /**
     * The name of the conversation.
     */
    public String name;

    /**
     * The messages in the conversation.
     */
    public Collection<Message> messages;
    
    /**
     * The messages in the conversation.
     */
    public List<TreeMap<String, Integer>> report;
    

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }
    
    /**
     * Applies one of the following filtres: username or keyword
     * @param filterType The filter to be applied
     * @param value The value of the filter
     */
    public void applyFilter(String filterType, String value) {
    	if (filterType.equals(AppConstant.USERNAME)) {
    		applyUsernameFilter(value);
    	}
    	
    	if (filterType.equals(AppConstant.KEYWORD)) {
    		applyKeywordFilter(value);
    	}
    }

    /**
     * Applies the keyword filter
     * @param value The value of the filter
     */
	private void applyKeywordFilter(String value) {
		Collection<Message> filteredMessages = new ArrayList<Message>();
		for (Message message : messages) {
			if (message.content.contains(value)) {
				filteredMessages.add(message);
			}
		}
		this.messages = filteredMessages;
	}

    /**
     * Applies the username filter
     * @param value The value of the filter
     */
	private void applyUsernameFilter(String value) {
		Collection<Message> filteredMessages = new ArrayList<Message>();
		for (Message message : messages) {
			if (message.senderId.equals(value)) {
				filteredMessages.add(message);
			}
		}
		this.messages = filteredMessages;
	}

	/**
	 * Search for blacklisted word and replaces it
	 * @param blacklist The blacklisted word
	 */
	public void blacklistKeyword(String blacklist) {
		for (Message message : messages) {
			message.content = message.content.replace(blacklist, AppConstant.BLACKLIST_REPLACEMENT);
		}
	}

	/**
	 * Hides credit card numbers
	 * Example of credit card numbers: 1234 5678 9012 3456 | 1234.5678.9012.3456 | 1234-5678-9012-3456
	 * 								   12 34 56 78 90 12 34 56 | 12.34.56.78.90.12.34.56
	 * 								   1234567890123456 or any other combination
	 */
	public void hideCreditCard() {
		for (Message message : messages) {
			String content = message.content;
			int startPosition = 0; 
			int nrOfOccurence = 0;
			
			char[] charArray = content.toCharArray();
			for (int i = 0; i < content.length(); i++) {
				String character = String.valueOf(charArray[i]);
				if (Character.isDigit(charArray[i])) {
					if (nrOfOccurence == 0) {
						startPosition = i;
					} 
					++nrOfOccurence;
				} else {
					//ignore " ",".","-" in order to find a card number
					if (!AppConstant.CREDIT_CARD_SEPARATORS.contains(character)) {
						nrOfOccurence = 0;
					}
				} 
				
				if (nrOfOccurence == AppConstant.CARD_NUMBER_LENGTH) {
					nrOfOccurence = 0;
					int endPosition = i + 1;
					String cardNumber = content.substring(startPosition, endPosition);
					message.content = message.content.replace(cardNumber, AppConstant.BLACKLIST_REPLACEMENT);
				}
			}
		}
	}

	/**
	 * Obfuscate id's using md5 + salt
	 */
	public void obfuscateId() {
		for (Message message : messages) {
			String userId = message.senderId;
			if (message.content.contains(userId)) {
				String obfuscatedUser = DigestUtils.md5Hex(userId + AppConstant.OBFUSCATION_SALT);
				message.content = message.content.replace(userId, obfuscatedUser);
			}
		}
	}
	
	/**
	 * Adds report to the conversation 
	 */
	public void includeReport() {
		this.report = Report.getByUsername(messages);
	}
}
