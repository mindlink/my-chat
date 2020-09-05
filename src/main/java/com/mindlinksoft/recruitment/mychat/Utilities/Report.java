package com.mindlinksoft.recruitment.mychat.Utilities;

import com.mindlinksoft.recruitment.mychat.Objects.Conversation;
import com.mindlinksoft.recruitment.mychat.Objects.ConversationExtended;
import com.mindlinksoft.recruitment.mychat.Objects.Message;
import com.mindlinksoft.recruitment.mychat.Objects.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Report {

    private static Conversation conversation;
    private static HashMap<String, Integer> userActivity = new HashMap<>();

    public static void generateActivityData(Conversation conversation) {
        Report.conversation = conversation;

        // populate unique id
        for (Message message : conversation.messages) {
            if (!userActivity.containsKey(message.senderId)) {
                userActivity.put(message.senderId, 0);
            }
        }

        for (Message message : conversation.messages) {
            for (Object o : userActivity.entrySet()) {
                Map.Entry obj = (Map.Entry) o;
                if (message.senderId.contains((CharSequence) obj.getKey())){
                    Integer currentValue = (Integer) obj.getValue();
                    currentValue += 1;
                    obj.setValue(currentValue);
                }
            }
        }
        System.out.println(userActivity);
    }

    public static ConversationExtended generateReport() {
        List<User> users = new ArrayList<>();
        ConversationExtended outputConExt = new ConversationExtended(conversation.name,conversation.messages, users);


        return outputConExt;
    }
}
