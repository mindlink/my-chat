package com.mindlinksoft.recruitment.mychat.chatFeatures;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

public interface ChatFeature {

    /**
     * Passes the argument provided on the command-line by the user.
     * @param argument The argument.
     */
    void setArgument(String argument);

    void duringExport(Conversation conversation);

    Message beforeExport(Message message);
}
