package com.mindlinksoft.recruitment.mychat.conversation;


import java.util.Comparator;

/**
 * Represents the stats of the user
 */
public class UserStats {
    /**
     * the user id
     */
    private String userId;
    /**
     * the messageCount
     */
    private Integer messageCount;

    /**
     * Comparator to compare UserStats objects based on messageCount property
     */
    //TODO: move outside
    public static Comparator<UserStats> COMPARE_BY_SENT_MESSAGES = new Comparator<UserStats>() {
        public int compare(UserStats one, UserStats other) {
            return other.messageCount.compareTo(one.messageCount);
        }
    };

    /**
     * User stats constructor
     * @param userId takes the user id
     * @param messageCount takes the message count
     */
    public UserStats(String userId, Integer messageCount ){
        this.userId = userId;
        this. messageCount = messageCount;
    }

    /**
     * gets the message count
     * @return Integer messageCount
     */
    public Integer getMessageCount() {
        return messageCount;
    }

    /**
     * gets the userId
     * @return String userid
     */
    public String getUserId() {
        return userId;
    }

    /**
     * set the messageCount
     * @param messageCount int the message count
     */
    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    /**
     * set the userId
     * @param userId String the user Id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
