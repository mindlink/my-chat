package com.mindlinksoft.recruitment.mychat.Utilities.AdditionalFeatures;

import com.mindlinksoft.recruitment.mychat.Constructs.ConversationDefault;
import com.mindlinksoft.recruitment.mychat.Constructs.Message;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Obfuscate implements AdditionalFeatures {


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

    @Override
    public ConversationDefault processAndReturn(ConversationDefault conversationDefault) {
        List<Message> messages = new ArrayList<>();
        HashMap<String, Integer> obfUsers = generateData(conversationDefault);

        for (Message message : conversationDefault.messages) {
            for (Object o : obfUsers.entrySet()) {
                Map.Entry obj = (Map.Entry) o;
                if (message.senderId.contains((CharSequence) obj.getKey())) {
                    messages.add(new Message(message.timestamp, obj.getValue().toString(), message.content));
                }
            }
        }

        return new ConversationDefault(conversationDefault.name, messages);
    }

    @Override
    public HashMap<String, Integer> generateData(ConversationDefault conversationDefault) {
        HashMap<String, Integer> obfUsers = new HashMap<>();

        for (Message message : conversationDefault.messages) {
            if (!obfUsers.containsKey(message.senderId)) {

                obfUsers.put(message.senderId, generateId(obfUsers));
            }
        }
        outputUsers(obfUsers);

        return obfUsers;
    }
}
