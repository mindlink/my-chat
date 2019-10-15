package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class Obfuscator {
    // I wasn't clear on what the obfuscated ID was meant to be replaced by
    static String obfuscateString = "XXXXXXXXXX";

    public static Collection<Message> obfuscate(Collection<Message> messages,  Map<String, String> map){
        if (map != null){
            if (map.containsKey("-o")){
                messages = obfuscateHelper(messages, map.get("-o"));
            }
        }
        return messages;
    }

    /**
     * A helper function for obfuscate to break up nested conditionals
     * @param messages
     * @param flagStatus
     * @return
     */
    private static Collection<Message> obfuscateHelper(Collection<Message> messages, String flagStatus) {
        String  finalFlagStatus = flagStatus.toLowerCase();
        if (finalFlagStatus == "y"){
            messages = messages.stream()
                    .map(message -> {
                        message.setSenderId(obfuscateString);
                        return message;
                    })
                    .collect(Collectors.toList());
        }
        return messages;
    }
}
