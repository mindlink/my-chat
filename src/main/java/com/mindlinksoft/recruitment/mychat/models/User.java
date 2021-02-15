package com.mindlinksoft.recruitment.mychat.models;

/**
 * Represents an individual user.
 */
public class User implements Comparable<User> {
    /**
     * The ID of this sender.
     */
    private String senderID;

    /**
     * A count of the number of messages sent by this user.
     */
    private Integer messageCount;

    /**
     * Initializes a new instance of the {@link User} class.
     * @param userID       The senderID of a user.
     * @param messageCount The number of messages sent by a user.
     */
    public User(String userID, Integer messageCount) {
        this.senderID = userID;
        this.messageCount = messageCount;
    }

    /**
     * Getter method for access to the {@code senderID} of a user
     */
    public String getSenderID() {
        return senderID;
    }

    /**
     * Getter method for access to the {@code messageCount} of a user
     */
    public int getMessageCount() {
        return messageCount;
    }

    /**
     * A compare to method to allow for the sorting of users based on message count
     */
    @Override
    public int compareTo(User o) {
        return Integer.compare(this.messageCount, o.messageCount);
    }
}
