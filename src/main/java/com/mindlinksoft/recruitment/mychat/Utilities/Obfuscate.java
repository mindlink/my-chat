package com.mindlinksoft.recruitment.mychat.Utilities;

import com.mindlinksoft.recruitment.mychat.Objects.Conversation;
import com.mindlinksoft.recruitment.mychat.Objects.Message;

import java.util.*;

public class Obfuscate {

    private static HashMap<String, Integer> obfUsers = new HashMap<>();
    private static Conversation conversationActual;

    public static void generateUserData(Conversation conversation) {
        conversationActual = conversation;

        for (Message message : conversation.messages) {
            if (!obfUsers.containsKey(message.senderId)) {

                obfUsers.put(message.senderId, generateId(obfUsers));
            }
        }
    }

    public static Conversation obfuscateSenderId() {
        List<Message> messages = new ArrayList<>();

        for (Message message : conversationActual.messages) {
            for (Object o : obfUsers.entrySet()) {
                Map.Entry obj = (Map.Entry) o;
                if (message.senderId.contains((CharSequence) obj.getKey())) {
                    messages.add(new Message(message.timestamp, obj.getValue().toString(), message.content));
                }
            }
        }

        System.out.println(obfUsers.size());
        return new Conversation(conversationActual.name, messages);
    }

    private static Integer generateId(HashMap exisitngUsers) {
        Integer n = 10000 + new Random().nextInt(90000);
        while (exisitngUsers.containsValue(n)) {
            n = 10000 + new Random().nextInt(90000);
        }
        return n;
    }
}
