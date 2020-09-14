/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mindlink;

import java.util.ArrayList;
import static java.util.Collections.reverseOrder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *
 * @author andyt
 */
public class ChatHandler {
    private ArrayList<Message> original;
    private ArrayList<Message> altered = new ArrayList<Message>();
    private String[] flags; //aka arguments parsed
    private HashMap<String, String> obfuscatedMap = new HashMap<String,String>();
    private boolean obfuscated = false;
    private HashMap<String,Integer> activity = new HashMap<String,Integer>();
    private String phoneRegex = "[0-9]{11,16}";
    private String redacted = "*redacted";
    

    public ChatHandler(String[] args, ArrayList<Message> messages) {
        this.flags = args;
        this.original = messages;
    }


    public ArrayList<Message> applyFilters(String[] args){
        for (int i = 2; i<args.length; i++){
            if(args[i].contains("-u")){
                String user = args[i].split(":")[1];
                byUser(user);
        }
            if(args[i].contains("-k")){
            String key = args[i].split(":")[1];
            byKeyword(key);
        }
           if(args[i].contains("-b")){
               String blacklist = args[i].split(":")[1];
               //could do contains comma instead
               if(blacklist.contains(",")){ 
                   String[] wordlist = blacklist.split(",");
                   for(String word:wordlist){
                   blackList(word);}
               }
               else blackList(args[i].split(":")[1]);
           }
           if(args[i].contains("-h")){
               hide();
           }
           if(args[i].contains("-o")){
               
               obfuscateID();
               this.obfuscated=true;
           }
        
        }
        
        
        
        return original;
}
    public void byUser(String s){
        boolean  ok = false;
        System.out.println(s);
        for (Message msg:original) {
            String user = msg.getSenderId();
            if (user.equals(s)){
                altered.add(msg);
                
            }
            
        }
        this.original = this.altered;
    }
    
    public void byKeyword(String s){
        boolean ok = false;
        for(Message msg : this.original) {
            String content = msg.getContent();
                if (content.contains(s)){
                    this.altered.add(msg);
                }
                }
                
        this.original = this.altered;
    
        }
    
    public void blackList(String s){
        for(Message msg : this.original) {
                String content = msg.getContent();
                if (content.toLowerCase().contains(s)){
                        msg.setContent(msg.getContent().replaceAll(s, this.redacted)); 
                        
                    }

                }
                    

                    }
    
    
    
    public void hide(){
        for(Message msg : original) {
                   String content = msg.getContent();
                           msg.setContent(msg.getContent().replaceAll(this.phoneRegex, this.redacted)); 

                           altered.add(msg);
                       
                       }
        original = altered;

    
    
    }
    
    public void obfuscateID(){
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

