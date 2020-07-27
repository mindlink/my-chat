/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindlinksoft.recruitment.mychat;

/**
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

            Conversation conversation = model.hideKeyword(model.getInputFile(), argument[0]);

            model.writeConversation(conversation, model.getOutputFile());
//            System.out.println("Conversation exported from '" + model.getInputFile() + "' to '" + model.getOutputFile() + " and hiding the keyword: " + argument[0]);

        } catch (Exception e) {
            System.out.println("Invalid argument");
        }

    }

}
