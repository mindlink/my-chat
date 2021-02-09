package mindlink;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {
    /**
     * The name of the conversation.
     * 
     * For example "My Conversation"
     */
    public String name;

    /**
     * The messages in the conversation.
     * 
     */
    public ArrayList<Message> messages;
    private HashMap<String,Integer> activity = new HashMap<String,Integer>();


    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    
    public Conversation(String name, ArrayList<Message> messages) {
        this.name = name;
        this.messages = (ArrayList<Message>) messages;
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public HashMap<String, Integer> getActivity() {
        return activity;
    }

    public void setActivity(HashMap<String, Integer> activity) {
        this.activity = activity;
    }
    
}
