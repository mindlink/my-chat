package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A class that handles all the filtering of messages
 */
class Filter {

    static List<Message> filter(Collection<Message> messages, Map<String, String> map){
        if (map != null){
            if (map.containsKey("-u")){
                messages = byUser(messages, map.get("-u"));
            }
        }

        return (List<Message>) messages;
    }

    /**
     * A helper function that filters users
     * @param messages
     * @param user
     * @return
     */
    private static List<Message> byUser(Collection<Message> messages, String user){

        String finalUser = user.toLowerCase();
        return messages.stream()
                .filter(message -> message.getSenderId().toLowerCase().contains(finalUser))
                .collect(Collectors.toList());
    }
}
