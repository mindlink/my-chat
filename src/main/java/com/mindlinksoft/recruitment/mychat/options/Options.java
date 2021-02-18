package com.mindlinksoft.recruitment.mychat.options;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mindlinksoft.recruitment.mychat.ConversationExporter;
import com.mindlinksoft.recruitment.mychat.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;
import com.mindlinksoft.recruitment.mychat.models.User;

public class Options {
    Conversation conversation;
    String user;
    String keyword;
    String[] blacklist;
    Boolean report = false;

    /**
     * Initializes a new instance of the {@link Options} class.
     * 
     * @param conversation  The conversation.
     * @param configuration To get the filters that need to be applied.
     */
    public Options(Conversation conversation, ConversationExporterConfiguration configuration) {
        this.user = configuration.filterUser;
        this.keyword = configuration.filterKeyword;
        this.blacklist = configuration.blacklist;
        this.report = configuration.report;
        this.conversation = conversation;
    }

    /**
     * Apply the defined filters to the conversation
     */
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
        if (this.report) {
            this.conversation = this.generateActivityReport();
        }
        return this.conversation;
    }

    /**
     * Filter the conversation based on the defined user filter
     */
    public Collection<Message> filterByUser() {
        ConversationExporter.logger.info("Filtering (user)...");
        Collection<Message> newMessages = new ArrayList<Message>();

        Collection<Message> messages = this.conversation.getMessages();
        for (Message message : messages) {
            if (message.getSenderId().equals(this.user)) {
                newMessages.add(message);
            }
        }
        ConversationExporter.logger.info("Filtered to only show messages sent by " + this.user);
        return newMessages;
    }

    /**
     * Filter the conversation based on the defined keyword filter
     */
    public Collection<Message> filterByKeyword() {
        ConversationExporter.logger.info("Filtering (keyword)...");
        Collection<Message> newMessages = new ArrayList<Message>();

        Collection<Message> messages = this.conversation.getMessages();
        for (Message message : messages) {
            if (message.content.toUpperCase().indexOf(this.keyword.toUpperCase()) != -1) {
                newMessages.add(message);
            }
        }
        ConversationExporter.logger
                .info("Filtered to only show messages containing the keyword '" + this.keyword + "'");
        return newMessages;
    }

    /**
     * Update the conversation based on the defined blacklist
     */
    public Collection<Message> blacklist() {
        ConversationExporter.logger.info("Filtering (blacklist)...");
        Collection<Message> messages = this.conversation.getMessages();
        for (Message message : messages) {
            for (String word : blacklist) {
                if (message.content.toUpperCase().indexOf(word.toUpperCase()) != -1) {
                    // TODO: revisit as the replace code only redacts if the word if lead by a space
                    // (depends if the stakeholder wants just the combination of letters to be
                    // redacted or if its a fully isolated word)
                    message.content = message.content.replaceAll("(?i)\\b" + word, "\\*redacted\\*");
                }
            }
        }
        ConversationExporter.logger.info("Filtered to censor the occurances of blacklisted words");
        return messages;
    }

    /**
     * Generate users' activity report
     */
    public Conversation generateActivityReport() {
        // TODO: find a way to not create two different data types
        ConversationExporter.logger.info("Generating activity report...");
        Collection<Message> messages = this.conversation.getMessages();
        Map<String, Integer> activityReport = new HashMap<String, Integer>();

        for (Message message : messages) {
            String senderId = message.getSenderId();
            if (activityReport.containsKey(senderId)) {
                activityReport.put(senderId, activityReport.get(senderId) + 1);
            } else {
                activityReport.put(senderId, 1);
            }
        }

        List<User> activity = new ArrayList<User>();
        User userDetails;
        for (String senderId : activityReport.keySet()) {
            userDetails = new User(senderId, activityReport.get(senderId));
            activity.add(userDetails);
        }

        // https://stackoverflow.com/questions/16252269/how-to-sort-an-arraylist
        Collections.sort(activity);
        Collections.reverse(activity);

        // TODO: find better way to update the activity report
        this.conversation = this.conversation.updateActivity(activity);

        ConversationExporter.logger.info("Activity report generated");

        return this.conversation;
    }

    /**
     * Getter method for retrieving the user for the conversation to be filtered by.
     */
    public String getFilterUser() {
        return this.user;
    }

    /**
     * Getter method for retrieving the keyword for the conversation to be filtered
     * by.
     */
    public String getFilterKeyword() {
        return this.keyword;
    }

    /**
     * Getter method for retrieving the blacklist for the conversation to be
     * filtered by.
     */
    public String[] getBlacklist() {
        return this.blacklist;
    }

    /**
     * Getter method for retrieving the boolean to decide if the report should be
     * generated.
     */
    public Boolean getReport() {
        return this.report;
    }

}
