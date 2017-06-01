package com.mindlinksoft.recruitment.mychat.editor.formatters;

import com.mindlinksoft.recruitment.mychat.message.MessageInterface;

import java.util.HashMap;

/**
 * Formatter to obfuscate sender ids.
 * uses an internal hashmap to keep references to obfuscated ids
 */
public class ObfuscateIdFormatter implements MessageFormatterInterface {

    /**
     * HashMap to keep user ids with the related obfuscated id
     */
    private HashMap<String, String> obfuscatedIdMap = new HashMap<>();

    /**
     * method to format the message sender
     * @param message object that implements {@link MessageInterface}
     * @return the formatted message object that implements {@link MessageInterface}
     */
    @Override
    public MessageInterface formatMessage(MessageInterface message) {

        String userId = message.getSenderId();

        //if message sender is not already added in the map add
        if(!this.obfuscatedIdMap.containsKey(userId)){
            this.obfuscatedIdMap.put(userId, "user" + (this.obfuscatedIdMap.size() + 1) );
        }

        //get the obfuscated id from the map using the userId
        String obfuscatedId = this.obfuscatedIdMap.get(userId);
        message.setSenderId(obfuscatedId);

        return message;
    }
}
