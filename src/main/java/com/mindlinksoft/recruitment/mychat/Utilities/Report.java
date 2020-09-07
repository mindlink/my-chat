package com.mindlinksoft.recruitment.mychat.Utilities;

import com.mindlinksoft.recruitment.mychat.Objects.Conversation;
import com.mindlinksoft.recruitment.mychat.Objects.ConversationReport;
import com.mindlinksoft.recruitment.mychat.Objects.Message;
import com.mindlinksoft.recruitment.mychat.Objects.User;

import java.util.*;

import static java.util.stream.Collectors.toMap;

public class Report {

    public ConversationReport generateReport(Conversation conversation) {
        List<User> users = new ArrayList<>();

        HashMap userActivity = sortUsers(generateActivityData(conversation));

        for (Object o : userActivity.entrySet()) {
            Map.Entry obj = (Map.Entry) o;
            users.add(new User((Integer) obj.getValue(), (String) obj.getKey()));
        }

        return new ConversationReport(conversation.name, conversation.messages, users);
    }

    private HashMap<String, Integer> generateActivityData(Conversation conversation) {
        HashMap<String, Integer> userActivity = new HashMap<>();

        for (Message message : conversation.messages) {
            if (!userActivity.containsKey(message.senderId)) {
                userActivity.put(message.senderId, 0);
            }
        }

        for (Message message : conversation.messages) {
            for (Object o : userActivity.entrySet()) {
                Map.Entry obj = (Map.Entry) o;
                if (message.senderId.contains((CharSequence) obj.getKey())) {
                    Integer currentValue = (Integer) obj.getValue();
                    currentValue += 1;
                    obj.setValue(currentValue);
                }
            }
        }

        return userActivity;
    }


    private HashMap sortUsers(HashMap<String, Integer> userActivity) {
        Map<String, Object> sorted = userActivity
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        return (HashMap) sorted;
    }
}
