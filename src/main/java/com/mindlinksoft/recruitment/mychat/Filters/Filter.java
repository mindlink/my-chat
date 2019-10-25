package com.mindlinksoft.recruitment.mychat.Filters;

import com.mindlinksoft.recruitment.mychat.Conversation;

/**
 * Abstract class Filter.
 */
public abstract class Filter {
	/**
	 * Filter option chosen when program is launched.
	 */
	private String option;
	
	/**
	 * Constructor for the filter abstract class.
	 * @param option Option array chosen in launch argument parameters.
	 */
	public Filter(String[] option) {
		this.option = option[0];
	}
	
	/**
	 * Abstract function that will filter the conversation.
	 * @param convo Conversation object .
	 * @return Modified conversation object.
	 */
	public abstract Conversation filterMessages(Conversation convo);
	
}
