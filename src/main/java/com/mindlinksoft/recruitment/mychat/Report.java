package com.mindlinksoft.recruitment.mychat;

import java.util.*;

/**
 * Created by Bishowakiran on 18/12/2015.
 */
public class Report {

    public Hashtable userMessage = new Hashtable<String,Integer>();//Stores username as key and user's message count as value.

    public Report(){}

    public void put(String username, int messageCount){
        userMessage.put(username,messageCount);
    }

    public ArrayList<Map.Entry<?, Integer>> sortValue(){
        ArrayList<Map.Entry<?, Integer>> l = new ArrayList(userMessage.entrySet());
        Collections.sort(l, new Comparator<Map.Entry<?, Integer>>() {
            public int compare(Map.Entry<?, Integer> o1, Map.Entry<?, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        System.out.println(l);
        return l;
    }

}

