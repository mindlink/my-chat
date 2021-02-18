package com.mindlinksoft.recruitment.mychat.models;

// implemented "Comparable<User>" with help from https://stackoverflow.com/questions/35688550/error-message-the-method-sortlistt-in-the-type-collections-is-not-applicabl
public class User implements Comparable<User> {
    /**
     * The sender's ID.
     */
    private String sender;

    /**
     * The total of messages sent by the user
     */
    private Integer count;

    /**
     * New instance of a user.
     * 
     * @param sender The sender of a user.
     * @param count  The number of messages sent by a user.
     */
    public User(String sender, Integer count) {
        this.sender = sender;
        this.count = count;
    }

    /**
     * Getter method for retrieving the sender ID.
     */
    public String getSender() {
        return this.sender;
    }

    /**
     * Getter method for retrieving the count of messages that user sent.
     */
    public Integer getCount() {
        return this.count;
    }

    /**
     * Override to compare the message count of each user
     */
    @Override
    public int compareTo(User o) {
        return Integer.compare(this.count, o.count);
    }
}
