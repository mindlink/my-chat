package com.mindlinksoft.recruitment.mychat.Filters;

import com.mindlinksoft.recruitment.mychat.Conversation;

/**
 * Abstract class {@link Filter}.
 */
public abstract class Filter {
	/**
	 * Filter {@code option} chosen when program is launched.
	 */
	private String option;

	/**
	 * Constructor for the {@link Filter} abstract class.
	 * 
	 * @param option Array initialised from launch argument parameters.
	 */
	public Filter(String[] option) {
		this.option = option[0];
	}

	/**
	 * Abstract function that will filter the conversation.
	 * 
	 * @param convo {@link Conversation} object.
	 * @return Modified {@link Conversation} object.
	 */
	public abstract Conversation filterMessages(Conversation convo);

	/**
	 * Get function for the type of filter.
	 * 
	 * @return {@link String} {@code option} object.
	 */
	public String getOption() {
		return this.option;
	}
}
