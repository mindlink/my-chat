package com.mindlinksoft.recruitment.mychat.chatFeatures;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * ChatFeature for including only messages from a requested userID in the exported conversation.
 */
public class FilterUser implements ChatFeature{
    //The userID to keep messages from.
    private static String userID;

    @Override
    public void setArgument(String argument) {
        userID = argument;
    }

    /**
     * Called to append something to the exported JSON file. Not needed for this feature.
     */
    @Override
    public void duringExport(Conversation conversation) {
    }

    /**
     * Remove a message from the conversation if it has the requested userID.
     * @param message A message to check against the userID.
     * @return Null if userID does not match, the message if it does match.
     */
    @Override
    public Message beforeExport(Message message) {
        if(message.senderId.equalsIgnoreCase(userID)){
            return message;
        } else {
            return null;
        }
    }
}
