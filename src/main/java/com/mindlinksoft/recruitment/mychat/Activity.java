package com.mindlinksoft.recruitment.mychat;

public class Activity {
    
    public String sender;
    public Integer count;


    public void setSender(String name) {
        this.sender = name;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


    public void upCount() {
        this.count++;
    }

    
    public Activity(String sender, Integer count) {
        this.sender = sender;
        this.count = count;
    }

    public Activity(String sender) {
        this.sender = sender;
        this.count = 0;
    }

}
