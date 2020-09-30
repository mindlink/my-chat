package com.mindlinksoft.recruitment.mychat.conversation;

/**
 * Class containing information on how many messages a user has sent in a conversation,
 * for the ConversationReport
 * @author iris
 *
 */
public class UserActivity implements Comparable<UserActivity> {
	
	/**
	 * The user name/userID
	 */
	private String username;
	
	/**
	 * The number of messages this user has sent in the Conversation
	 */
	private int messageCount;

	public UserActivity(String user, int messages) {
		username = user;
		messageCount = messages;
	}

	public String getUsername() {
		return username;
	}

	public int getMessageCount() {
		return messageCount;
	}

	/**
	 * Compare UserActivity objects by checking the messageCount
	 */
	@Override
	public int compareTo(UserActivity other) {
		return Integer.compare(this.messageCount, other.getMessageCount());
	}
	
	

}
