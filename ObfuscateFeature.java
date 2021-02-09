/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mindlink;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author andyt
 */
public class ObfuscateFeature extends Feature{
        private HashMap<String, String> obfuscatedMap = new HashMap<String,String>();


    public ObfuscateFeature(ArrayList<Message> messages) {
        super(messages);
    }

    @Override
    public void doFeature(){};
    
    
    public ArrayList<Message> doF() {
        
        
        for(Message msg:original){
            Random r = new Random();
            Integer obf = r.nextInt(1000);
            while(obfuscatedMap.containsValue(obf)){
                obf = r.nextInt(1000);
            }
            obfuscatedMap.putIfAbsent(msg.getSenderId(), obf.toString());
            
        }
        for(Message msg:original){
            msg.setSenderId(obfuscatedMap.get(msg.getSenderId()));
            
            
        }
        
        return original;
    }
    
    
}
