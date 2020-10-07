package com.mindlinksoft.recruitment.mychat;

/**
 * Represents the frequency of messages of a sender.
 */
public class UserActivity {

    //Class fields
    private String sender;
    private Integer count;

    /**
     * Initializes a new instance of the {@link UserActivity} class.
     * @param sender The name of the sender under question.
     * @param count The number of messages of the sender.
     */
    public UserActivity(String sender, Integer count){
        this.count = count;
        this.sender = sender;
    }


    //Getter and Setter methods
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
