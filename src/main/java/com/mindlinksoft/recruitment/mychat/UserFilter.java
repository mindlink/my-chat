package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

/**
 * User Filter removes all users' messages from conversation apart from specified user.
 */
public class UserFilter implements Filter {

	/**
	 * User by which to filter .
	 */
	private final String user;
	
	/**
	 * Constructor for User Filter.
	 * @param user by which to filter.
	 */
	public UserFilter(String user) {
		this.user = user;
	}
	
	/**
	 * Apply User Filter to conversation.
	 * @param conversation.
	 * @return conversation.
	 */
	public Conversation apply(Conversation c) {
		List<Message> messages = c.getMessages();
		List<Message> filteredMessages = new ArrayList<Message>();
		
		for(Message m: messages) {
			if(m.getSenderId().equalsIgnoreCase(user)) {
				filteredMessages.add(m);
			}
		}
		
		return new Conversation(c.getName(), filteredMessages);
	}
}
