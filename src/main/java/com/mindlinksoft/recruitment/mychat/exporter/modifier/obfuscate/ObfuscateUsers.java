package com.mindlinksoft.recruitment.mychat.exporter.modifier.obfuscate;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.ModifierBase;

/**
 * Represents a modifier that will obfuscate users from messages
 */
public class ObfuscateUsers extends ModifierBase implements Obfuscate {

    /**
     * Returns a modifier that hides phone numbers and credit card numbers in messages
     * @param conversation contains the messages you wish to hide numbers from
     */
    public ObfuscateUsers(Conversation conversation) {
        super(conversation);
    }

    @Override
    protected Conversation modify() {
        return obfuscate();
    }

    /**
     * Returns a copy of this conversation with the senders obfuscated
     * @return conversation with senders obfuscated
     */
    @Override
    public Conversation obfuscate() {
        // TODO Auto-generated method stub
        return null;
    }

    
}