package com.mindlinksoft.recruitment.mychat;

public class Log implements Comparable<Log> {
    private String userName;
    private int messageCount;

    public Log(String userName, int messageCount) {
        this.userName = userName;
        this.messageCount = messageCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public void incrementMessageCount(int increment) {
        this.messageCount += increment;
    }

    @Override
    public int compareTo(Log log) {
        return Integer.compare(log.messageCount, this.messageCount);
    }
}
