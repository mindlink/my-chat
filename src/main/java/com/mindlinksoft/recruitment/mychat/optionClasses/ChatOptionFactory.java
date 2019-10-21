package com.mindlinksoft.recruitment.mychat.optionClasses;

import java.util.HashMap;
import java.util.Map;

/**
 * Return a ChatOption depending on the flag encountered on the commandline.
 */
public class ChatOptionFactory {

    /**
     * Stores all implemented ChatOptions and the flags relating to them
     */
    public static Map<Character, ChatOption> options = new HashMap<Character, ChatOption>() {{
        put('k', new KeywordFilter());
        put('u', new UserFilter());
        put('b', new BlacklistFilter());
        put('c', new PhoneCardFilter());
        put('o', new UserObfuscate());
        put('a', new ActiveUsers());
    }};

    /**
     * Return a ChatOption depending on the flag encountered on the commandline.
     * @param optionType A char representing a flag encountered on the commandline, relating to a ChatOption (hopefully).
     * @return The ChatOption related to the optionType flag.
     */
    public static ChatOption getChatOption(char optionType) {

        ChatOption returnedOption = options.get(optionType);

        if (returnedOption == null) {
            throw new IllegalArgumentException("Invalid option: '-" + optionType + "'");
        }

        return returnedOption;
    }
}
