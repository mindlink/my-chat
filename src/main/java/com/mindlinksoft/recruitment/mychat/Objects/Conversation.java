package com.mindlinksoft.recruitment.mychat.Objects;

import java.util.Collection;

public class Conversation extends ConversationAbs {

    public Conversation(String name, Collection<Message> messages) {
        super(name, messages);
    }
}
