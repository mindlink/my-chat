package com.mindlinksoft.recruitment.mychat.options;

import java.util.ArrayList;
import java.util.Collection;

import com.mindlinksoft.recruitment.mychat.ConversationExporter;
import com.mindlinksoft.recruitment.mychat.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;

public class Options {
    Conversation conversation;
    String user;
    String keyword;
    String[] blacklist;
    Boolean report = false;

    public Options(Conversation conversation, ConversationExporterConfiguration configuration) {
        this.user = configuration.filterUser;
        this.keyword = configuration.filterKeyword;
        this.blacklist = configuration.blacklist;
        this.report = configuration.report;
        this.conversation = conversation;
    }

    public Conversation applyOptionsToConversation() {
        if (this.user != null) {
            this.conversation.messages = this.filterByUser();
        }
        if (this.keyword != null) {
            this.conversation.messages = this.filterByKeyword();
        }
        for (Message message : this.conversation.messages) {
            System.out.println(message.senderId + ": " + message.content);
        }
        return this.conversation;
    }

    public Collection<Message> filterByUser() {
        Collection<Message> newConversation = new ArrayList<Message>();

        Collection<Message> messages = conversation.getMessages();
        for (Message message : messages) {
            if (message.getSenderId().equals(this.user)) {
                newConversation.add(message);
            }
        }
        ConversationExporter.logger.info("filtered to only show messages sent by " + this.user);
        return newConversation;
    }

    public Collection<Message> filterByKeyword() {
        Collection<Message> newConversation = new ArrayList<Message>();

        Collection<Message> messages = conversation.getMessages();
        for (Message message : messages) {
            if (message.content.toUpperCase().indexOf(this.keyword.toUpperCase()) != -1) {
                newConversation.add(message);
            }
        }
        ConversationExporter.logger
                .info("filtered to only show messages containing the keyword '" + this.keyword + "'");
        return newConversation;
    }

}
