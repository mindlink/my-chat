package com.mindlinksoft.recruitment.mychat.models;

/**
 * Class which represents the activity for one sender
 */
public class Activity {
    
    public String sender;
    public Integer count;

    public String getSender() {
        return sender;
    }

    public Integer getCount() {
        return count;
    }

    public Activity(String sender, Integer count) {
        this.sender = sender;
        this.count = count;
    }

}
