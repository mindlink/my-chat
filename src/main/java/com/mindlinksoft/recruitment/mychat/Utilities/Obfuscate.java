package com.mindlinksoft.recruitment.mychat.Utilities;

import com.mindlinksoft.recruitment.mychat.Objects.Conversation;
import com.mindlinksoft.recruitment.mychat.Objects.Message;

import java.util.*;

public class Obfuscate {

    private static HashMap<String, Integer> obfUsers = new HashMap<>();

    public static Conversation generateUserData(Conversation conversation) {
        List<Message> messages = new ArrayList<>();
        Conversation obfuscatedCon = new Conversation(conversation.name, messages);

        for (Message message : conversation.messages){
            if (!obfUsers.containsKey(message.senderId)) {
                // check if doesnt have user in hashmap

                obfUsers.put(message.senderId, generateId(obfUsers));
                // populate hashmap, add user with random id
            }
            obfuscatedCon.messages.add(message);
        }

        if(!obfUsers.isEmpty()) {
            for (Object o : obfUsers.entrySet()) {
                Map.Entry obj = (Map.Entry) o;
                System.out.println(obj.getKey() + " " + obj.getValue());
            }
        }
        return obfuscatedCon;
    }

    private static Integer generateId(HashMap exisitngUsers){
        Integer n = 10000 + new Random().nextInt(90000);
        // check if random id is already assigned
        while (exisitngUsers.containsValue(n)){
            n = 10000 + new Random().nextInt(90000);
        }
        return n;
    }
}
