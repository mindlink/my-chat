package com.mindlinksoft.recruitment.mychat.chatFeatures;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

public class FilterKeyword implements ChatFeature{

    private static String keyword;

    @Override
    public void setArgument(String argument) {
        keyword = argument;
    }

    /**
     * Called to append something to the exported JSON file. Not needed for this feature.
     */
    @Override
    public void duringExport(Conversation conversation) {
    }
    /**
     * Remove a message from the conversation if it contains the requested keyword.
     * @param message A message to check against the keyword.
     * @return Null if keyword is not present in the message, the message if it is present.
     */
    @Override
    public Message beforeExport(Message message) {
        if(message.content.contains(keyword)){
            return message;
        } else {
            return null;
        }
    }
}
