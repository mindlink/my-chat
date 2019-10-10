package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Redaction {

    static List<Message> redact(Collection<Message> messages, Map<String, String> map){
        if (map != null){
            if (map.containsKey("-b")){
                messages = redactBlacklistWords(messages, map.get("-b"));
            }
        }

        messages = redactCreditCardNumbers(messages);


        return (List<Message>) messages;
    }

    /**
     * Redacts blacklisted words
     * @param messages
     * @param blacklistedWord
     * @return
     */
    private static Collection<Message> redactBlacklistWords(Collection<Message> messages, String blacklistedWord) {

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

    private static Collection<Message> redactCreditCardNumbers(Collection<Message> messages){

        String regex = "((?:(?:\\d{4}[- ]){3}\\d{4}|\\d{16}))(?![\\d])";
        return messages.stream()
                .map(message -> {
                    message.setContent(message.getContent().replaceAll("((?:(?:\\d{4}[- ]){3}\\d{4}|\\d{16}))(?![\\d])", "*redacted*"));
                return message;
                })
                .collect(Collectors.toList());
    }
}

