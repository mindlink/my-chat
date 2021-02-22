package com.mindlinksoft.recruitment.mychat.report;

import com.mindlinksoft.recruitment.mychat.Message;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ReportGenerator {

    public String name;
    public Collection<Message> messages;
    public HashMap<String, Integer> activity = new HashMap<String, Integer>();

    public ReportGenerator(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }

    public void generateReport(){

        Iterator<Message> iterator = messages.iterator();

        while(iterator.hasNext()){
            Message m = iterator.next();
            if (activity.containsKey(m.senderId)){
                activity.put(m.senderId, activity.get(m.senderId) + 1);
            }
            else{
                activity.put(m.senderId, 1);
            }
        }

        for (Map.Entry entry : activity.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

    }



}
