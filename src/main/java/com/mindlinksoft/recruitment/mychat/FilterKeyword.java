/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindlinksoft.recruitment.mychat;

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

            Conversation conversation = model.filterConversationByKeyword(model.getInputFile(), argument[0]);

            model.writeConversation(conversation, model.getOutputFile());

        } catch (Exception e) {
            System.out.println("Invalid argument");
        }
    }
}
