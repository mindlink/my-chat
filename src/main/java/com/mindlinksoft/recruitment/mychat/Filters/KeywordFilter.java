package com.mindlinksoft.recruitment.mychat.Filters;

import java.util.ArrayList;
import java.util.List;
import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * Filters the list of messages of a {@link Conversation} object based on chosen
 * keyword. Extends abstract class {@link Filter}.
 */
public class KeywordFilter extends Filter {

	/**
	 * The {@code keyword} included in the messages that will not be filtered.
	 */
	private String keyword;

	/**
	 * Initialises a new instance of the {@link keywordFilter} class.
	 * 
	 * @param key String containing the {@code keyword} to be filtered.
	 */
	public KeywordFilter(String key) {
		this.keyword = key;
	}

	/**
	 * Filters the messages in a given {@link Conversation} object to remove
	 * messages not containing the {@code keyword}.
	 * 
	 * @param convo {@link Conversation} object to be filtered.
	 * @return New {@link Conversation} object with filtered messages.
	 */
	@Override
	public Conversation filterMessages(Conversation convo) {
		List<Message> filteredMessages = new ArrayList<Message>();
		String conversationName = convo.name;

		for (Message m : convo.messages) {
			if (m.content.toLowerCase().contains(keyword.toLowerCase())) {
				filteredMessages.add(m);
			}
		}
		return new Conversation(conversationName, filteredMessages);
	}

	/**
	 * Get function for the {@code keyword} to filter.
	 * 
	 * @return {@link String} {@code keyword} object.
	 */
	public String getKeyword() {
		return this.keyword;
	}
}
