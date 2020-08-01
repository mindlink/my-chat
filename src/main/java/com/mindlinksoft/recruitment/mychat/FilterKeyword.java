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

            model.writeConversation(filterConvo(conversation), model.getOutputFile());

        } catch (Exception e) {
            System.out.println("Invalid or empty argument, please try again");
        }
    }

    /**
     * Represents a helper to filter a conversation by a specified keyword
     *
     * @param conversation the conversation to be filtered
     * @return the conversation filtered with messages that meet the filter
     * condition
     */
    public Conversation filterConvo(Conversation conversation) {

        List<Message> messages = new ArrayList<Message>(conversation.getMessages());

        List<Message> key = new ArrayList<Message>();
        key = messages.stream().filter(keyword -> keyword.getContent().contains(argument[0])).collect(Collectors.toList());

        return new Conversation(conversation.getName(), key);
    }
}
