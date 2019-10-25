package com.mindlinksoft.recruitment.mychat.chatFeatures;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class UserActivityReport implements ChatFeature{
    private HashMap<String,Integer> userMessageCountMap = new HashMap<>();

    @Override
    public void setArgument(String argument) {
    }

    @Override
    public void duringExport(Conversation conversation) {
        conversation.userMessagesCount = userMessageCountMap.entrySet().stream()
        .sorted(Map.Entry.comparingByValue())
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (e1,e2) -> e1, LinkedHashMap::new));
    }

    @Override
    public Message beforeExport(Message message) {
        if(userMessageCountMap.containsKey(message.senderId)){
            userMessageCountMap.put(message.senderId, userMessageCountMap.get(message.senderId) + 1);
        } else {
            userMessageCountMap.put(message.senderId, 1);
        }
        return null;
    }
}
