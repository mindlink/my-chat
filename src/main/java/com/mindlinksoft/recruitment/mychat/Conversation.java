package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {
    /**
     * The name of the conversation.
     */
    public String name;

    /**
     * The messages in the conversation.
     */
    public Collection<Message> messages;

    /**
     * The activities to be included in the report for this conversation.
     * ( It is null by default so that it is excluded by gson when writing.
     *   If the ConversationEditor populates activities then it will be
     *   included. )
     */
    public Collection<Activity> activities = null;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }

    /*
     * Returns only the messages from this conversation which are sent by the specified user.
     * @param name The name of the user.
     */
    public Collection<Message> getMessagesFilteredByUser(String name) {
        List<Message> filteredMsgs = new ArrayList<Message>();
        for (Message msg : this.messages) {
            if (msg.senderId.toLowerCase().equals(name.toLowerCase())) {
                filteredMsgs.add(msg);
            }
        }
        return filteredMsgs;
    }

    /*
     * Returns only the messages from this conversation which contain the specified keyword.
     * @param keyword The keyword to be included.
     */
    public Collection<Message> getMessagesFilteredByKeyword(String keyword) {
        List<Message> filteredMsgs = new ArrayList<Message>();
        for (Message msg : this.messages) {
            if (msg.content.toLowerCase().contains(keyword.toLowerCase())) {
                filteredMsgs.add(msg);
            }
        }
        return filteredMsgs;
    }

    /*
     * Returns this conversation's messages with blacklisted words redacted.
     * @param blacklist The blacklist of words to be redacted.
     */
    public Collection<Message> getMessagesCensored(String[] blacklist) {
        List<Message> censoredMsgs = new ArrayList<Message>();
        for (Message msg : this.messages) {
            for (String word : blacklist) {
                msg.content = msg.content.replaceAll("(?i)" + word, "*redacted*");
            }
            censoredMsgs.add(msg);
        }
        return censoredMsgs;
    }
}
