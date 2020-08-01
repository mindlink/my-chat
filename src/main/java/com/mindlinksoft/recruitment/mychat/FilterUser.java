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
 * Concrete command FilterUser, filters a conversation by a specific users
 *
 * @author esteban
 */
public class FilterUser implements Command {

    String[] argument;

    public FilterUser(String[] argument) {
        this.argument = argument;
    }

    @Override
    public void execute(Model model) {
        try {

//            Conversation conversation = model.filterConversationByUser(model.getInputFile(), argument[0]);
            Conversation conversation = model.readConversation(model.getInputFile());

            model.writeConversation(filterByUser(conversation), model.getOutputFile());
        } catch (Exception e) {
            System.out.println("Invalid or empty argument, please try again");
        }
    }

    /**
     * Represents a helper to filter a conversation by a specified users
     *
     *
     * @param conversation the conversation to be filtered
     * @return the conversation filtered with messages that meet the filter
     * condition
     */
    public Conversation filterByUser(Conversation conversation) {

        List<Message> messages = new ArrayList<Message>(conversation.getMessages());

        List<Message> key = new ArrayList<Message>();
        key = messages.stream().filter(user -> user.getSenderId().equals(argument[0])).collect(Collectors.toList());
//        System.out.println(key);
        return new Conversation(conversation.getName(), key);
    }

}
