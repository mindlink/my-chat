package com.mindlinksoft.recruitment.mychat.message;

import java.util.Objects;

import org.apache.commons.lang3.Validate;

/**
 * Filters out any messages that were NOT sent by the specified user.
 *
 */
public class UserIdMessageFilter implements IMessageFilter {

	private final String userId;
	
	public UserIdMessageFilter(String userId) {
		this.userId = Validate.notNull(userId);
	}
	
	@Override
	public boolean filterMessage(IMessage message) {
		return !Objects.equals(message.getSenderId(), userId);
	}

}
