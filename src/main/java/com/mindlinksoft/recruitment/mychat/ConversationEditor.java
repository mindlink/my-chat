package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Represents a helper which takes care of mutating a conversation
 * according to command line arguments.
 */
class ConversationEditor {

    ConversationExporterConfiguration config;

    /**
     * Constructor for a ConversationEditor
     * @param config The command line arguments specifying desired editing behaviour.
     */
    public ConversationEditor(ConversationExporterConfiguration config) {
        this.config = config;
    }

    /**
     * Represents a helper to create a collection of Activity objects for a conversation.
     * Each of these shows the number of messages sent by a user in that conversation.
     * @param c The conversation for which we are creating a report
     */
    public Collection<Activity> composeReport(Conversation c) {
        // LinkedHashMap below preserves order of insertion for testing,
        // i.e. if two sender have same count, the one which appeared
        // first in the conversation is first in report
        Map<String, Integer> counts = new LinkedHashMap<String, Integer>();
        for (Message msg : c.messages) {
            if (counts.containsKey(msg.senderId)) {
                counts.put(msg.senderId, counts.get(msg.senderId) + 1);
            }
            else {
                counts.put(msg.senderId, 1);
            }
        }

        List<Activity> activities = new ArrayList<Activity>();
        for (Map.Entry<String, Integer> count : counts.entrySet()) {
            activities.add(new Activity(count.getKey(), count.getValue()));
        }

        Collections.sort(activities, new CountComparator());
        return activities;
    }

    /**
     * A helper which checks command line arguments and edits a conversation accordingly.
     * @param c The conversation to be edited
     */
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

    /*
     * Comparator used for comparing counts of activities for sorting
     */
    class CountComparator implements Comparator<Activity> {
        @Override
        public int compare(Activity x, Activity y) {
            return x.count < y.count ? 1
                 : x.count > y.count ? -1
                 : 0;
        }
    }
}
