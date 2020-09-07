package com.mindlinksoft.recruitment.mychat.Constructs;

public class User {

    public Integer messageCount;
    public String senderId;

    public User(Integer messageCount, String senderId) {
        this.messageCount = messageCount;
        this.senderId = senderId;
    }
}
