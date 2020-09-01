package com.mindlinksoft.recruitment.mychat.optionSettings;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

import java.util.*;

/**
 * ChatOption used to add a a report to the conversation detailing the most active users.
 */
public class ActivityReport implements OptionSetting{
    /**
     * Used to store the number of messages in a conversation sent by each userID.
     */
    private Map<String, Integer> messageCounter = new HashMap<String, Integer>();

    /**
     * Update the number of messages sent by the sender of this message.
     * @param message A message in the conversation.
     * @return Unchanged message parameter.
     */
    @Override
    public Message duringIteration(Message message) {

        if (messageCounter.containsKey(message.userID)) {
            messageCounter.put(message.userID, messageCounter.get(message.userID) + 1);
        } else {
            messageCounter.put(message.userID, 1);
        }

        return message;
    }

    /**
     * Sort userIDs in count number of sent messages and attach messageCount to the conversation for the JSON file.
     * @param conversation The conversation.
     * @return The conversation with messageCounter attached.
     */
    @Override
    public Conversation postIteration(Conversation conversation) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(messageCounter.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<String, Integer> sortedMessageCounter = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMessageCounter.put(entry.getKey(), entry.getValue());
        }
        conversation.activityReport = sortedMessageCounter;

        return conversation;
    }

    /**
     * The option in question does not require an argument, meaning that no action is taken.
     */
    @Override
    public void setArgument(String argument) {
        return;
    }

    /**
     * Returns a boolean to state whether the option in question needs an argument.
     */
    @Override
    public boolean argumentRequired() {
        return false;
    }

}