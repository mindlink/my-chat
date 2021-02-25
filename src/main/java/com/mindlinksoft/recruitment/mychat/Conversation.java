package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {

    //Making variables final (and private for messages) to make the class immutable.

    /**
     * The name of the conversation.
     */
    public final String name;

    /**
     * The messages in the conversation.
     */
    private final Collection<Message> messages;

    //By returning a copy of the original list, this prevents people from modifing the contents of the messages variable
    //Note: Messages are immutable as well, so one still can't change the elements of the orginal list by ref shenanigans.
    public Collection<Message> getMessages() {
        return new ArrayList<Message>(messages);
    }

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }

    /**
     * A method that returns a Conversation object that is the result of filtering this Conversation's messages by the userId that sent them.
     * @param userID The userId used to filter the messages of this Conversation
     * @return {@link Conversation} freshly constructed with its list of messages filtered to those sent by userId param.
     */
	public Conversation filterConvoByUser(String userId) {
        Collection<Message> filteredMsgs = new ArrayList<Message>();

        for(Message msg : this.messages) {
            if(msg.senderId.equals(userId)){
                filteredMsgs.add(msg);
            }
        }

		return new Conversation(this.name, filteredMsgs);
	}

    /**
     * A method that returns a Conversation object that is the result of filtering this Conversation's messages to those that contain a keyword.
     * @param keyword The keyword used to filter the messages of this Conversation
     * @return {@link Conversation} freshly constructed with its list of messages filtered to those with keyword param.
     */
	public Conversation filterConvoByKeyword(String keyword) {
        Collection<Message> filteredMsgs = new ArrayList<Message>();

        for(Message msg : this.messages) {
            if(msg.contains(keyword)){
                filteredMsgs.add(msg);
            }
        }

		return new Conversation(this.name, filteredMsgs);
	}

    /**
     * A method that returns a new Conversation object of this conversation but with certain words on the blacklist censored by replacing them with *redacted*
     * Case sensitive.
     * @param blacklist The list of words that will be censored.
     * @return {@link Conversation} freshly constructed with the blacklist words cencored from its of messages.
     */
	public Conversation censorConvo(List<String> blacklist) {
		Collection<Message> filteredMsgs = new ArrayList<Message>();

        for(Message msg : this.messages) {
            String content = msg.content;

            for(String s : blacklist) {
                
                content = content.replace(s, "*redacted*");
                
            }
            
            filteredMsgs.add(new Message(msg.timestamp, msg.senderId, content));
            
        }

		return new Conversation(this.name, filteredMsgs);
	}
}
