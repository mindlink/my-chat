package com.mindlinksoft.recruitment.mychat.Objects;

import java.util.Collection;

public class ConversationReport extends Conversation {
    public Collection<User> users;

    public ConversationReport(String name, Collection<Message> messages, Collection<User> users) {
        super(name, messages);
        this.users = users;
    }
}
