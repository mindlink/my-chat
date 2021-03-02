package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mindlinksoft.recruitment.mychat.Model.Conversation;
import com.mindlinksoft.recruitment.mychat.Model.Message;


public class ConversationTransformer {

    Conversation convo;

    /**
     * Class for Transforming a handed conversation.
     * Each method returns a new Conversation that has been transformed from the original by some means.
     * @param convo Initial conversation.
     */
    public ConversationTransformer(Conversation convo) {
        this.convo = convo;
    }

    /**
     * A method that returns a Conversation object that is the result of filtering this Conversation's messages by the userId that sent them.
     * @param userID The userId used to filter the messages of this Conversation
     * @return {@link Conversation} freshly constructed with its list of messages filtered to those sent by userId param.
     */
	public Conversation filterConvoByUser(String userId) {
        Collection<Message> filteredMsgs = new ArrayList<Message>();

        for(Message msg : convo.getMessages()) {
            if(msg.senderId.equals(userId)){
                filteredMsgs.add(msg);
            }
        }

		return new Conversation(convo.name, filteredMsgs);
	}

    /**
     * A method that returns a Conversation object that is the result of filtering this Conversation's messages to those that contain a keyword.
     * @param keyword The keyword used to filter the messages of this Conversation
     * @return {@link Conversation} freshly constructed with its list of messages filtered to those with keyword param.
     */
	public Conversation filterConvoByKeyword(String keyword) {
        Collection<Message> filteredMsgs = new ArrayList<Message>();

        for(Message msg : convo.getMessages()) {
            if(msg.content.contains(keyword)){
                filteredMsgs.add(msg);
            }
        }

		return new Conversation(convo.name, filteredMsgs);
	}

    /**
     * A method that returns a new Conversation object of this conversation but with certain words on the blacklist censored by replacing them with *redacted*
     * Case sensitive.
     * @param blacklist The list of words that will be censored.
     * @return {@link Conversation} freshly constructed with the blacklist words cencored from its of messages.
     */
	public Conversation censorConvo(List<String> blacklist) {
		Collection<Message> filteredMsgs = new ArrayList<Message>();

        for(Message msg : convo.getMessages()) {
            String content = msg.content;

            for(String s : blacklist) {
                
                content = content.replace(s, "*redacted*");
                
            }
            
            filteredMsgs.add(new Message(msg.timestamp, msg.senderId, content));
            
        }

		return new Conversation(convo.name, filteredMsgs);
	}
    
}
