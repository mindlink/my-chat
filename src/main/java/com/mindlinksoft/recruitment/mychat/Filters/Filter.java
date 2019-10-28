package com.mindlinksoft.recruitment.mychat.Filters;

import com.mindlinksoft.recruitment.mychat.Conversation;

/**
 * Abstract class {@link Filter}.
 */
public abstract class Filter {


	/**
	 * Abstract function that will filter the conversation.
	 * 
	 * @param convo {@link Conversation} object.
	 * @return Modified {@link Conversation} object.
	 */
	public abstract Conversation filterMessages(Conversation convo);
}
