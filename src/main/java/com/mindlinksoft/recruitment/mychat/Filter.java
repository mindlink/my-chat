package com.mindlinksoft.recruitment.mychat;

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
	 * @param op Option chosen in launch argument parameters.
	 */
	public Filter(String op) {
		this.option = op;
	}
	
	/**
	 * Abstract function that will filter the conversation.
	 * @param c Conversation object .
	 * @return Modified conversation object.
	 */
	public abstract Conversation filterMessages(Conversation c);
	
}
