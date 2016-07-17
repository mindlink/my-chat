package com.mindlinksoft.recruitment.mychat.bean;

/**
 * Holds a username and the number of messages sent by that user
 */
public class UserActivity {

	private final String user_;
	private final int messages_;

	public UserActivity(String user, int messages) {
		user_ = user;
		messages_ = messages;
	}

	public String getUser() {
		return user_;
	}

	public int getMessages() {
		return messages_;
	}
}
