package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;

public final class UserReport {

    public String name;

    public Collection<User> users;


    public UserReport(Collection<User> users) {    	
        name = "User Report";
        this.users = users;
    }
}