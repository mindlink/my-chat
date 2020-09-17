package com.mindlinksoft.recruitment.mychat;

public final class User {
    public String sender_Id;
    public Integer no_of_messages;

    public User(String sender_Id, Integer no_of_messages){
        this.sender_Id = sender_Id;
        this.no_of_messages = no_of_messages;
    }

    @Override
    public String toString() {
        return "User sender_id is: " + sender_Id+" , no of messages is: " + no_of_messages;
    }
}
