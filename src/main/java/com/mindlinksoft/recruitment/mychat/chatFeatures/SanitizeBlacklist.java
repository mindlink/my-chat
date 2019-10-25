package com.mindlinksoft.recruitment.mychat.chatFeatures;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

public class SanitizeBlacklist implements ChatFeature {

    private static String blacklist;

    @Override
    public void setArgument(String argument) {
        blacklist = argument;
    }
    /**
     * Called for tasks needed just prior to JSON export. Not needed for this feature.
     */
    @Override
    public void duringExport(Conversation conversation) {
    }
    /**
     * Redact any blacklisted words from the message.
     * @param message A message to check against the blacklist.
     * @return The message with any blacklist words redacted.
     */
    @Override
    public Message beforeExport(Message message) {
        if(message.content.contains(blacklist)){
            message.content = message.content.replace(blacklist, "*redacted*");
        }
        return message;
    }
}
