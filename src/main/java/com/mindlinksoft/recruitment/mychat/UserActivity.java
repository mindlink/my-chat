package com.mindlinksoft.recruitment.mychat;

/**
 * Represents the activity of a user in a conversation.
 */
public class UserActivity implements Comparable<UserActivity>{
	
	private String userID;
	private Integer messagesSent;
	
	/**
	 * Initialises a new instance of the {@link UserActivity} class.
	 * @param userID UserID in the chat (may be obfuscated)
	 * @param messagesSent The number of messages sent in this conversation
	 */
	public UserActivity(String userID, Integer messagesSent) {
		this.userID = userID;
		this.messagesSent = messagesSent;
	}
	
	public String getUserID() {
		return userID;
	}

	public Integer getMessagesSent() {
		return messagesSent;
	}

	/**
	 * User activity is compared based on number of messages sent
	 * @param The userActivity to compare to
	 * return As per super
	 */
	@Override
	public int compareTo(UserActivity userActivity) {
		return messagesSent.compareTo(userActivity.getMessagesSent());
	}
	
	/**
	 * Print friendly version of activity for logging
	 */
	@Override
	public String toString() {
		return "User: " + userID + ", Messages sent: " + messagesSent;
	}
}
