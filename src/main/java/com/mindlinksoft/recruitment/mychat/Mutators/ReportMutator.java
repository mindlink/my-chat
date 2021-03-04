package com.mindlinksoft.recruitment.mychat;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Represents a mutator which attaches a report to a conversation.
 */
class ReportMutator implements ConversationMutator {
    /**
     * Mutates a conversation such that a report of users' activities is added.
     * @param c The conversation to which the report is attached.
     */
    public void mutateConversation(Conversation c) {
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
        c.activities = activities;
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
