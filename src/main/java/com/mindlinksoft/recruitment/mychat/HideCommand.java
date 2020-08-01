/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete command HideCommand, hides a keyword and replaces it with
 * "*redacted*"
 *
 * @author esteban
 */
public class HideCommand implements Command {

    String[] argument;

    public HideCommand(String[] argument) {
        this.argument = argument;
    }

    @Override
    public void execute(Model model) {

        try {

//            Conversation conversation = model.hideKeyword(model.getInputFile(), argument[0]);
            Conversation conversation = model.readConversation(model.getInputFile());

            model.writeConversation(hideKeyw(conversation), model.getOutputFile());
//            System.out.println("Conversation exported from '" + model.getInputFile() + "' to '" + model.getOutputFile() + " and hiding the keyword: " + argument[0]);

        } catch (Exception e) {
            System.out.println("Invalid or empty argument, please try again");
        }

    }

    /**
     * Represents a helper to hide specific keywords from a conversation
     *
     * @param conversation the conversation
     * @return the conversation containing the keywords hidden.
     */
    public Conversation hideKeyw(Conversation conversation) {

        List<Message> messages = new ArrayList<Message>(conversation.getMessages());

        List<Message> key = conversationHiddenKey(messages, argument[0]);

//        key = messages.stream().map(user -> user.getContent().replaceAll(keyword, "*redacted*"))).collect(Collectors.toList());
//        System.out.println(key);
        return new Conversation(conversation.getName(), key);
    }

    private static List<Message> conversationHiddenKey(List<Message> messages, String keyword) {
        List<Message> result = new ArrayList<>();

        for (Message temp : messages) {
            String filtered = temp.getContent().replaceAll(keyword, "*redacted*");
            System.out.println(temp);
            result.add(new Message(temp.getTimestamp(), temp.getSenderId(), filtered));
        }
        System.out.println(result);
        return result;
    }
}
