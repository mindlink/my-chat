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
public class HideFeature extends Feature {
    
        private String redacted = "*redacted*";
        private String phoneRegex = "[0-9]{11,16}";


    public HideFeature(ArrayList<Message> messages) {
        super(messages);
    }

    @Override 
    public void doFeature(){};
    
    
    
    public ArrayList<Message> doF(){
        for(Message msg : original) {
                       String content = msg.getContent();
                               msg.setContent(msg.getContent().replaceAll(this.phoneRegex, this.redacted)); 

                               altered.add(msg);

                           }
            original = altered;


            return original;
        }
    
}
