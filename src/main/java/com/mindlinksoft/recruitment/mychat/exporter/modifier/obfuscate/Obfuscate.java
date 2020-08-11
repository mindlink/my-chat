package com.mindlinksoft.recruitment.mychat.exporter.modifier.obfuscate;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;

/**
 * Represents a modifier which will hide certain words, users or other argument
 * by obfuscating. The obfuscated term retains the same "new" term throughout
 * the conversation e.g. userId bob will continue to be referred to as 808 in
 * the resulting Conversation
 */
public interface Obfuscate {
    /**
     * Implementing methods will create a new Conversation
     * with terms obfuscated.
     * @return new Conversation with obfuscated key terms
     */
    Conversation obfuscate();
}