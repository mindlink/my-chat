package mychat;

import java.util.Collection;

public final class Conversation {

    public void setName(String name) {
        this.name = name;
    }

    public void setMessages(Collection<Message> messages) {
        this.messages = messages;
    }

    public String getName() {

        return name;
    }

    public Collection<Message> getMessages() {
        return messages;
    }

    private String name;

    private Collection<Message> messages;

    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }
}
