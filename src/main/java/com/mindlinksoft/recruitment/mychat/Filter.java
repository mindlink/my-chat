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

            if (map.containsKey("-k")){
                messages = byKeyword(messages, map.get("-k"));
            }

            if (map.containsKey("-b")){
                messages = blacklistWords(messages, map.get("-b"));
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

    /**
     * A helper function that filters by keyword
     * @param messages
     * @param keyword
     * @return
     */
    private static List<Message> byKeyword(Collection<Message> messages, String keyword){

        String finalKeyword = keyword.toLowerCase();
        return messages.stream()
                .filter(message -> message.getContent().toLowerCase().contains(finalKeyword))
                .collect(Collectors.toList());
    }

    /**
     * A helper function that replaces the blacklisted word with '*redacted*'
     * @param messages
     * @param blacklistedWord
     * @return
     */
    private static List<Message> blacklistWords(Collection<Message> messages, String blacklistedWord){

        String finalBlacklistedWord = blacklistedWord.toLowerCase();
        return messages.stream()
                .map(message -> {
                    if ( message.getContent().toLowerCase().contains(finalBlacklistedWord)){
                        message.setContent(message.getContent().replace(finalBlacklistedWord, "*redacted*"));
                    }
                    return message;
                })
                .collect(Collectors.toList());
    }
}
