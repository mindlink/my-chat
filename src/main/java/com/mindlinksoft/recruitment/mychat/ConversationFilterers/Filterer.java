package com.mindlinksoft.recruitment.mychat.ConversationFilterers;

import com.mindlinksoft.recruitment.mychat.Conversation;

public abstract class Filterer {
    public abstract Conversation filter(Conversation conversation);
}
