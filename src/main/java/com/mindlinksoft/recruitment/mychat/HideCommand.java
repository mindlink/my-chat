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

            Conversation conversation = model.readConversation(model.getInputFile());
            Conversation filtConv = hideKeyw(conversation, argument[0]);

            model.writeConversation(filtConv, model.getOutputFile());
            System.out.println("Conversation exported from '" + model.getInputFile() + "' to '" + model.getOutputFile() + " and hiding the keyword: " + argument[0]);

        } catch (Exception e) {
            System.out.println("Invalid or empty argument, please try again");
        }

    }

    /**
     * Represents a helper to hide specific keywords from a conversation
     *
     * @param conversation the conversation
     * @param argument the parameter that will be hidden
     * @return the conversation containing the keywords hidden.
     */
    public Conversation hideKeyw(Conversation conversation, String argument) {

        List<Message> messages = new ArrayList<Message>(conversation.getMessages());

        List<Message> key = conversationHiddenKey(messages, argument);

        return new Conversation(conversation.getName(), key);
    }

    /**
     * Method used to replace all the keywords that match the argument given
     * with redacted.
     *
     * @param messages messages from a conversation.
     * @param keyword to be replaced.
     * @return the messages with the keywords replaced.
     */
    private static List<Message> conversationHiddenKey(List<Message> messages, String keyword) {
        List<Message> result = new ArrayList<>();

        for (Message temp : messages) {
            String filtered = temp.getContent().replaceAll(keyword, "*redacted*");
            result.add(new Message(temp.getTimestamp(), temp.getSenderId(), filtered));
        }
        return result;
    }
}
