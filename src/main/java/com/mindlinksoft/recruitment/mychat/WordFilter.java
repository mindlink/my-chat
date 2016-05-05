package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Word Filter removes all messages from conversation that don't contain specified keyword.
 */
public class WordFilter implements Filter {

	/**
	 * Word by which to filter .
	 */
	private final String word;
	
	/**
	 * Constructor for Word Filter.
	 * @param word by which to filter.
	 */
	public WordFilter(String word) {
		this.word = word;
	}
	
	/**
	 * Apply Word Filter to conversation.
	 * @param conversation.
	 * @return conversation.
	 */
	public Conversation apply(Conversation c) {
		List<Message> filteredMessages = new ArrayList<Message>();
		
		for(Message m: c.getMessages()) {
			Pattern pattern = Pattern.compile("\\b" + word + "\\b", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(m.getContent());
			
			if(matcher.find()) {
				filteredMessages.add(m);
			}
		}
		
		return new Conversation(c.getName(), filteredMessages);
	}
}
