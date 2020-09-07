package com.mindlinksoft.recruitment.mychat.Constructs;

import java.util.Collection;

public class ConversationReport extends Conversation {
    public Collection<User> users;

    public ConversationReport(String name, Collection<Message> messages, Collection<User> users) {
        super(name, messages);
        this.users = users;
    }
}
