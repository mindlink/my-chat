package com.mindlinksoft.recruitment.mychat;

/**
 * Filter interface defines behavior of conversation filters.
 */
public interface Filter {

	/**
	 * Applies filters to conversation.
	 * @param conversation
	 * @return conversation
	 */
	public Conversation apply(Conversation c);
}
