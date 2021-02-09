/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mindlink;

import java.util.ArrayList;

/**
 *
 * @author andyt
 */
public class UserFeature extends Feature{

    public UserFeature(ArrayList<Message> messages) {
        super(messages);
    }

    /**
     *
     */
    public ArrayList<Message> doFeature(String s) {
    for (Message msg:original) {
               String user = msg.getSenderId();
               if (user.equals(s)){
                   altered.add(msg);

               }

           }
           original = altered;   
           return original;
 }

    @Override
    public void doFeature() {
    }

    
    
}
