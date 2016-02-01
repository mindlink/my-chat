package com.mindlinksoft.recruitment.mychat;

import java.util.*;

/**
 *Represents the model for report to be generated.
 */
public class Report {


    public ArrayList<User> users = new ArrayList();

    public Report(){}

    public void addUsers(User user){
        users.add(user);
    }

    public ArrayList<User> getUsersList(){
        return users;
    }

    public void sort(){
        ArrayList<User> newList = new ArrayList();
        int index=0;
        for(int i=0;i<users.size()-1;i++){
            for(int y=i+1;y<users.size();y++){

            }
        }

    }
}

