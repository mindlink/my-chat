/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Concrete command FilterKeyword, filters a conversation by a specified keyword
 *
 *
 * @author esteban
 */
public class FilterKeyword implements Command {

    String[] argument;

    public FilterKeyword(String[] argument) {
        this.argument = argument;
    }

    @Override
    public void execute(Model model) {
        try {

            Conversation conversation = model.readConversation(model.getInputFile());
            Conversation filtConv = filterConversation(conversation, argument[0]);

            if (!filtConv.messages.isEmpty()) {
                model.writeConversation(filtConv, model.getOutputFile());
                System.out.println("message filtered by keyword " + argument[0]);
            } else {
                System.out.println("\nconversation not exported, messages do not include the keyword specified.");
                System.out.println("try again with a different keyword.");
            }
        } catch (Exception e) {
            System.out.println("Invalid or empty argument, please try again.");
        }
    }

    /**
     * Represents a helper to filter a conversation by a specified keyword
     *
     * @param conversation the conversation to be filtered
     * @param argument the argument to be used to filter the messages
     * @return the conversation filtered with messages that meet the filter
     * condition
     */
    public Conversation filterConversation(Conversation conversation, String argument) {

        List<Message> messages = new ArrayList<Message>(conversation.getMessages());

        List<Message> key = new ArrayList<Message>();
        key = messages.stream().filter(keyword -> keyword.getContent().contains(argument)).collect(Collectors.toList());

        return new Conversation(conversation.getName(), key);

    }
}
