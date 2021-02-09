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
public class BlackListFeature extends Feature{

    private String redacted = "*redacted*";
    
    public BlackListFeature(ArrayList<Message> messages){
    super(messages);
    }

    
    @Override
    public void doFeature() {
    }
    
    public ArrayList<Message> doFeature(String blacklist){
        if(blacklist.contains(",")){ 
                   String[] wordlist = blacklist.split(",");
                   for(String word:wordlist){
                       blackList(word); }
        }
        else blackList(blacklist);
        
        return original;
    }
    
    public void blackList(String s){
        for(Message msg : this.original) {
                String content = msg.getContent();
                if (content.toLowerCase().contains(s)){
                        msg.setContent(msg.getContent().replaceAll(s, this.redacted)); 
                        
                    }

                }
                    

                    }
}
