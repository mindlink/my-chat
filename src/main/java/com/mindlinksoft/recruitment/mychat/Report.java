package com.mindlinksoft.recruitment.mychat;

public final class Report {
        /**
     * senderId of activity report
     */
    public String senderId;

    /**
     * Count of messages sent by user
     */
    public int count;

    /**
     * Initilialises the activity report
     * @param senderId the sender 
     * @param count The number of times the sender sent messages in the convo.
     */
    public Report(String senderId, int count) {
        this.senderId = senderId;
        this.count = count;
    }
}