package com.mindlinksoft.recruitment.mychat;

/**
 * Represents the model for users to generate report.
 */
public class User implements Comparable<User>{

    public String name;

    public Integer count;

    public User(){
        name="";
        count=0;
    }

    public User(String name, int count){
        this.name=name;
        this.count=count;
    }

    public String getName(){
        return name;
    }

    public int getCount(){
        return count;
    }

    @Override
    public int compareTo(User user) {
        return this.count.compareTo(user.count);
    }
}
