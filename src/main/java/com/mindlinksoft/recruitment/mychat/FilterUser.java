/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindlinksoft.recruitment.mychat;

/**
 *
 * @author Michael
 */
public class FilterUser extends Filter {

    @Override
    void filterAction(Message message, Object filterUser, Object replacement) {
        if (!message.senderId.equals((String)filterUser)) {
            conversation.messages.remove(message);
        }

    }
}
