package com.mindlinksoft.recruitment.mychat.Objects;

import java.util.Collection;

public class ConversationExtended {
    public String name;
    public Collection<Message> messages;
    public Collection<User> users;

    public ConversationExtended(String name, Collection<Message> messages, Collection<User> users) {
        this.name = name;
        this.messages = messages;
        this.users = users;
    }

}
