package com.mindlinksoft.recruitment.mychat.optionClasses;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

import java.util.*;

/**
 * ChatOption used to add a a report to the conversation detailing the most active users.
 */
public class ActiveUsers implements ChatOption {
    /**
     * Used to store the number of messages in a conversation sent by each userID.
     */
    private Map<String, Integer> messageCount = new HashMap<String, Integer>();

    /**
     * Update the number of messages sent by the sender of this message.
     * @param message A message in the conversation.
     * @return Unchanged message parameter.
     */
    @Override
    public Message applyDuring(Message message) {

        if (messageCount.containsKey(message.senderId)) {
            messageCount.put(message.senderId, messageCount.get(message.senderId) + 1);
        } else {
            messageCount.put(message.senderId, 1);
        }

        return message;
    }

    /**
     * Sort userIDs in messageCount by decreasing #messages sent and attach messageCount to the conversation, so
     * that it will be included in the JSON output.
     * @param conversation The conversation.
     * @return The conversation with messageCount attached.
     */
    @Override
    public Conversation applyAfter(Conversation conversation) {

        //sort messageCount by value
        List<Map.Entry<String, Integer>> list = new ArrayList<>(messageCount.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list); //sort values by decreasing order

        Map<String, Integer> sortedMessageCount = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMessageCount.put(entry.getKey(), entry.getValue());
        }
        conversation.activeUsers = sortedMessageCount;

        return conversation;
    }

    /**
     * Set option argument (this option doesn't require an argument so no action is taken).
     */
    @Override
    public void setArgument(String argument) {
        return;
    }

    /**
     * Returns a boolean representing whether this option requires an argument.
     */
    @Override
    public boolean needsArgument() {
        return false;
    }

}
