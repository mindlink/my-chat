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
abstract class Feature {
    
    ArrayList<Message> original;
    ArrayList<Message> altered = new ArrayList<Message>();

    public Feature(ArrayList<Message> messages){
        this.original = messages;
        
    }
    
    public abstract void doFeature();
}
