package com.mindlinksoft.recruitment.mychat.Objects;

import java.util.Collection;


public abstract class Conversation {

    public String name;
    public Collection<Message> messages;

    Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }
}

