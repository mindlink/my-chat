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
        if (this.blacklist != null) {
            this.conversation.messages = this.blacklist();
        }
        // for (Message message : this.conversation.messages) {
        // System.out.println(message.senderId + ": " + message.content);
        // }
        return this.conversation;
    }

    public Collection<Message> filterByUser() {
        Collection<Message> newMessages = new ArrayList<Message>();

        Collection<Message> messages = this.conversation.getMessages();
        for (Message message : messages) {
            if (message.getSenderId().equals(this.user)) {
                newMessages.add(message);
            }
        }
        ConversationExporter.logger.info("filtered to only show messages sent by " + this.user);
        return newMessages;
    }

    public Collection<Message> filterByKeyword() {
        Collection<Message> newMessages = new ArrayList<Message>();

        Collection<Message> messages = this.conversation.getMessages();
        for (Message message : messages) {
            if (message.content.toUpperCase().indexOf(this.keyword.toUpperCase()) != -1) {
                newMessages.add(message);
            }
        }
        ConversationExporter.logger
                .info("filtered to only show messages containing the keyword '" + this.keyword + "'");
        return newMessages;
    }

    public Collection<Message> blacklist() {
        Collection<Message> messages = this.conversation.getMessages();
        for (Message message : messages) {
            for (String word : blacklist) {
                if (message.content.toUpperCase().indexOf(word.toUpperCase()) != -1) {
                    message.content = message.content.replaceAll("(?i)\\b" + word, "\\*redacted\\*");
                    System.out.println(message.content);
                }
            }
        }
        ConversationExporter.logger.info("filtered to censor the occurances of blacklisted words");
        return messages;
    }

}
