package com.mindlinksoft.recruitment.mychat.Utilities;

import com.mindlinksoft.recruitment.mychat.Objects.Conversation;
import com.mindlinksoft.recruitment.mychat.Objects.Message;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Obfuscate {


    public Conversation obfuscateSenderId(Conversation conversation) {
        List<Message> messages = new ArrayList<>();
        HashMap<String, Integer> obfUsers = generateUserData(conversation);

        for (Message message : conversation.messages) {
            for (Object o : obfUsers.entrySet()) {
                Map.Entry obj = (Map.Entry) o;
                if (message.senderId.contains((CharSequence) obj.getKey())) {
                    messages.add(new Message(message.timestamp, obj.getValue().toString(), message.content));
                }
            }
        }

        return new Conversation(conversation.name, messages);
    }

    private HashMap<String, Integer> generateUserData(Conversation conversation) {
        HashMap<String, Integer> obfUsers = new HashMap<>();

        for (Message message : conversation.messages) {
            if (!obfUsers.containsKey(message.senderId)) {

                obfUsers.put(message.senderId, generateId(obfUsers));
            }
        }
        outputUsers(obfUsers);

        return obfUsers;
    }

    private Integer generateId(HashMap exisitngUsers) {
        Integer n = 10000 + new Random().nextInt(90000);
        while (exisitngUsers.containsValue(n)) {
            n = 10000 + new Random().nextInt(90000);
        }
        return n;
    }

    private void outputUsers(HashMap<String, Integer> obfUsers) {
        try {
            FileWriter writer = new FileWriter("users.txt");
            for (Object o : obfUsers.entrySet()) {
                Map.Entry obj = (Map.Entry) o;
                writer.write("senderID: " + obj.getKey() + " generatedID: " + obj.getValue().toString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
