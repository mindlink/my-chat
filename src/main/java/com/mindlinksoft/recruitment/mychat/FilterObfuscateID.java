/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michael
 */
public class FilterObfuscateID extends Filter {
    List<String[]> users = new ArrayList<String[]>();
    @Override
    void filterAction(Message message, Object filterUser, Object replacement) {
        if(message.senderId.startsWith("ObfuscateID-"))
        return;  
        
        boolean userObfuscated=false;
        for (String[] user : users) {
            if (user[0].equals(message.senderId)){
                message.senderId=user[1];
                userObfuscated=true;
                break;
            }
        }    
        if (!userObfuscated){
            String usersString[] = {message.senderId,"ObfuscateID-"+users.size()};
            users.add(usersString);
            message.senderId=usersString[1];
        }
    }
}
