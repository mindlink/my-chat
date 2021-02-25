package com.mindlinksoft.recruitment.mychat.options;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mindlinksoft.recruitment.mychat.ConversationExporter;
import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;
import com.mindlinksoft.recruitment.mychat.models.User;

public class Report {
    Conversation conversation;

    /**
     * Initializes a new instance of the {@link Report} class.
     * 
     * @param conversation  The conversation.
     * @param configuration To get the filters that need to be applied.
     */
    public Report(Conversation conversation) {
        this.conversation = conversation;
    }

    /**
     * Generate users' activity report
     */
    public Conversation process() {
        // TODO: find a way to not create two different data types to improve efficiency
        ConversationExporter.logger.trace("Generating activity report...");
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

        // TODO: find better way to update the activity report may need restructure
        this.conversation = this.conversation.updateActivity(activity);

        ConversationExporter.logger.info("Activity report generated");

        return this.conversation;
    }

}
