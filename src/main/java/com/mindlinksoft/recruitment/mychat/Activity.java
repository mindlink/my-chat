package com.mindlinksoft.recruitment.mychat;

/**
 * Represents an entry in a conversation's "activites" report
 */
class Activity {
    public String sender;
    public int count;

    public Activity(String s, int c) {
        this.sender = s;
        this.count = c;
    }
}
