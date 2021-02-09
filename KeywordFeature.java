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
public class KeywordFeature extends Feature{

    public KeywordFeature(ArrayList<Message> messages) {
        super(messages);
        
    }

    @Override
    public void doFeature() {
    }
    
    public ArrayList<Message> doFeature(String s){
        boolean ok = false;
        for(Message msg : original) {
            String content = msg.getContent();
                if (content.contains(s)){
                    altered.add(msg);
                }
                }
                
        original = altered;
        return original;
    
    
    }
    
}
