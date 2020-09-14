package com.mindlinksoft.recruitment.mychat;

/**
 * Represents the activity of a single user
 */
public final class UserNode implements Comparable<UserNode> {
	/**
	 * @param userId The ID of the user in question
	 * @param count The number of messages the user has sent
	 * @return The <String, int> pair of the userId and their message count
	 * Implements comparable to allow an ArrayList of these nodes to be sorted in descending order
	 * Overrides the toString() function to return a nicer string.
	 */
	String userId;
	int count;
	
	public UserNode(String userId, int count) {
		this.userId = userId;
		this.count = count;
	}
	
	@Override
	public int compareTo(UserNode nodeComp) {
		int compareCount = nodeComp.count;
		// descending order
		return compareCount - this.count;
	}
	
	@Override
	public String toString() {
		return this.userId + " has sent " + this.count + " messages.";
	}
}
