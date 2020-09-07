package com.mindlinksoft.recruitment.mychat.Objects;

import java.util.Collection;


public abstract class ConversationAbs {

    public String name;
    public Collection<Message> messages;

    ConversationAbs(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }
}

