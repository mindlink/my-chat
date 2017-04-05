package com.mindlinksoft.recruitment.mychat.message;

import org.apache.commons.lang3.Validate;

/**
 * Message filter that validates any message the contains a given keyword.
 *
 */
public class KeywordMessageFilter implements IMessageFilter {

	private final String keyword;
	
	public KeywordMessageFilter(String keyword) {
		this.keyword = Validate.notEmpty(keyword);
	}
	
	@Override
	public boolean filterMessage(IMessage message) {
		if (message != null) {
			return !message.getContent().contains(keyword);
		}
		return true;
	}

}
