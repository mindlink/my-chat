package com.mindlinksoft.recruitment.mychat.chatFeatures;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

public class SanitizeUserID implements ChatFeature{

    @Override
    public void setArgument(String argument) {}
    /**
     * Called for tasks needed just prior to JSON export. Not needed for this feature.
     */
    @Override
    public void duringExport(Conversation conversation) {
    }

    @Override
    public Message beforeExport(Message message) {
        message.senderId = String.valueOf(message.senderId.hashCode());
        return null;
    }
}
