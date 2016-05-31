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
public class FilterKeyword extends Filter {

    @Override
    void filterAction(Message message, Object filterKeyword, Object replacement) {
        if (!message.content.contains((String)filterKeyword)) {
            conversation.messages.remove(message);
        }

    }
}
