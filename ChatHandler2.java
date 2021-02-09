/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mindlink;

import java.util.ArrayList;
import static java.util.Collections.reverseOrder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

/**
 *
 * @author andyt
 */
public class ChatHandler2 {
    private ArrayList<Message> original;
    private ArrayList<Message> altered = new ArrayList<Message>();
    private String[] flags;
    private HashMap<String,Integer> obfuscatedMap = new HashMap<String,Integer>();
    private boolean obfuscated = false;
    private String phoneRegex = "[0-9]{11,16}";
    private String redacted = "redacted"; 
    private HashMap<String,Integer> activity = new HashMap<String,Integer>();
 
    
    public ChatHandler2(String[] args, ArrayList<Message> messages){
        this.flags = args;
        this.original = messages;
    }
    
    public ArrayList<Message> applyFilters(String[] args){
        for (int i = 2; i<args.length; i++){
            if (args[i].contains("-u")){
                String user = args[i].split(":")[1];

                //create class ->  feature = 
                UserFeature f = new UserFeature(original);
                //apply method on text???
                original = f.doFeature(user);
            }
            if (args[i].contains("-k")){
                String keyword = args[i].split(":")[1];

                KeywordFeature f = new KeywordFeature(original);
                original = f.doFeature(keyword);
            }
            if (args[i].contains("-b")){
                String blacklist = args[i].split(":")[1];
                
                BlackListFeature f = new BlackListFeature(original);
                original = f.doFeature(blacklist);
                
                
            }
            if (args[i].contains("-h")){
                HideFeature f = new HideFeature(original);
                original = f.doF();
                
            }
            if (args[i].contains("-o")){
                ObfuscateFeature f = new ObfuscateFeature(original);
                original = f.doF();
                
        }
    
    
        }
        return original;
    }   

    public HashMap<String, Integer> activity(){
        for(Message msg:original){
            Integer count = this.activity.containsKey(msg.getSenderId()) ? this.activity.get(msg.getSenderId()) : 0 ;
            this.activity.put(msg.getSenderId(), count+1);
        }
        HashMap<String, Integer> sorted= new HashMap<String,Integer>();
        this.activity = this.activity.entrySet().
                stream().sorted(reverseOrder(HashMap.Entry.comparingByValue()))
                .collect(Collectors.toMap(HashMap.Entry::getKey, HashMap.Entry::getValue,(oldValue, newValue) -> oldValue, LinkedHashMap::new));
    return this.activity;
    }


}

