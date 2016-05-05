package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Redaction Filter masks user-specified phrases with redaction placeholder.
 */
public class RedactFilter implements Filter {

	/**
	 * Keyword to be redacted.
	 */
	private final String word;
	
	/**
	 * Constructor for Redaction Filter.
	 * @param word Keyword to be redacted.
	 */
	public RedactFilter(String word) {
		this.word = word;
	}
	
	/**
	 * Apply Redaction Filter to conversation.
	 * @param conversation.
	 * @return conversation.
	 */
	public Conversation apply(Conversation c) {
		List<Message> filteredMessages = new ArrayList<Message>();
		
		for(Message m: c.getMessages()) {
			Pattern pattern = Pattern.compile("\\b" + word + "\\b", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(m.getContent());
			
			if(matcher.find()) {
				String redactedContent = matcher.replaceAll(Const.REDACTED);	
				Message redactedMessage = new Message(m.getTimestamp(), m.getSenderId(), redactedContent);	
				
				filteredMessages.add(redactedMessage);
			}
			else {
				filteredMessages.add(m);
			}
		}
		
		return new Conversation(c.getName(), filteredMessages);
	}
}
