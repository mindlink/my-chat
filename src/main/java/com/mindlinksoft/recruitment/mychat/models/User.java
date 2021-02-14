package com.mindlinksoft.recruitment.mychat.models;

public class User implements Comparable<User> {
    /**
     * The ID of this sender.
     */
    private String senderID;

    /**
     * A count of the number of messages sent by this user.
     */
    private Integer messageCount;

    public User(String userID, Integer messageCount) {
        this.senderID = userID;
        this.messageCount = messageCount;
    }

    public String getSenderID() {
        return senderID;
    }

    public int getMessageCount() {
        return messageCount;
    }

    @Override
    public int compareTo(User o) {
        return Integer.compare(this.messageCount, o.messageCount);
    }
}
