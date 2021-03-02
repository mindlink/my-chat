package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

class ConversationEditor {

    ConversationExporterConfiguration config;

    public ConversationEditor(ConversationExporterConfiguration config) {
        this.config = config;
    }

    public Collection<Activity> composeReport(Conversation c) {
        Map<String, Integer> counts = new HashMap<String, Integer>();
        for (Message msg : c.messages) {
            if (counts.containsKey(msg.senderId)) {
                counts.put(msg.senderId, counts.get(msg.senderId) + 1);
            }
            else {
                counts.put(msg.senderId, 1);
            }
        }

        Collection<Activity> activities = new ArrayList<Activity>();
        for (Map.Entry<String, Integer> count : counts.entrySet()) {
            activities.add(new Activity(count.getKey(), count.getValue()));
        }
        return activities;
    }

    public void editConversation(Conversation c) {
        // Censoring should happen first, otherwise there is information leak
        // e.g. I would be able to filter by "pie" and then redact "pie", and I
        // would know what the redacted words were
        if (this.config.blacklist != null) {
            c.messages = c.getMessagesCensored(this.config.blacklist);
        }
        if (this.config.filterUser != null) {
            c.messages = c.getMessagesFilteredByUser(this.config.filterUser);
        }
        if (this.config.keyword != null) {
            c.messages = c.getMessagesFilteredByKeyword(this.config.keyword);
        }
        if (this.config.report) {
            c.activities = composeReport(c);
        }
    }
}
