/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindlinksoft.recruitment.mychat;

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

            Conversation conversation = model.hideUsers(model.getInputFile());

            model.writeConversation(conversation, model.getOutputFile());

        } catch (Exception e) {
            System.out.println("Invalid argument");
        }

    }
}