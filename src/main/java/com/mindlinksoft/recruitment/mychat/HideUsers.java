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
public class HideUsers implements Command {

    @Override
    public void execute(Model model) {

        try {

            Conversation conversation = model.hideUsers(model.getInputFile());

            model.writeConversation(conversation, model.getOutputFile());
            System.out.println("Conversation exported from '" + model.getInputFile() + "' to '" + model.getOutputFile() + " and hiding the phone numbers ");

        } catch (Exception e) {
            System.out.println("Invalid argument");
        }

    }
}
