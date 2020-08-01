/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete command HideUsers, exports the conversation with all the usernames
 * replaced it with "anonymous"
 *
 * @author esteban
 */
public class HideUsers implements Command {

    @Override
    public void execute(Model model) {

        try {

            Conversation conversation = model.readConversation(model.getInputFile());

            model.writeConversation(hideUser(conversation), model.getOutputFile());
            System.out.println("Conversation exported with anonymous users.");

        } catch (Exception e) {
            System.out.println("Invalid argument");
        }

    }

    public Conversation hideUser(Conversation conversation) {

        List<Message> messages = new ArrayList<Message>(conversation.getMessages());

        List<Message> key = conversationHideUsers(messages);

        return new Conversation(conversation.getName(), key);
    }

    private static List<Message> conversationHideUsers(List<Message> messages) {
        List<Message> result = new ArrayList<>();

        for (Message temp : messages) {
            String filtered = temp.getSenderId().replace(temp.getSenderId(), "Anonymous");
            result.add(new Message(temp.getTimestamp(), filtered, temp.getContent()));
        }
        return result;
    }

}
