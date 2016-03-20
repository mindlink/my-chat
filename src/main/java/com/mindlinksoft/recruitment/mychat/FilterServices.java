
package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;

/**
 *
 * @author Orry Edwards
 */
public class FilterServices {
    
    private ConversationExporterConfiguration config;
      
    /**
     * Constructor for initialising a new instance of the {@link Filter} class.
     * @param config The configuration settings
     */
    public FilterServices(ConversationExporterConfiguration config){
        this.config = config;
    }
    
    /**
     * Filters the conversation by the keyword entered by the user.
     * @param config The configuration settings
     * @param conversation The conversations object representing the data imported from a file
     * @return a filtered version of the conversation
     */
    public Conversation FilterByKeyWord(ConversationExporterConfiguration config, Conversation conversation)
    {  
       //Create a array to hold only the filtered messages
       ArrayList<Message> filteredMessages = new ArrayList();
       
       //Cycle through the whole conversation and add only the messages with the key word to the filtered array
       for(Message convo : conversation.getMessages())
       {   
           if(convo.getContent().toLowerCase().contains(config.getfilterKeyWord())){
               filteredMessages.add(convo);
           }
       }
       
       //Create a new conversation with the filtered messages and name
       Conversation filteredConversation = new Conversation(conversation.getName(), filteredMessages);
       
       //pass it through to be written to file
       return filteredConversation;
    }
    
    /**
     * Filters the conversation by the userName entered by the user.
     * @param config The configuration settings
     * @param conversation The conversations object representing the data imported from a file
     * @return a filtered version of the conversation
     */
    public Conversation FilterByUsername(ConversationExporterConfiguration config, Conversation conversation)throws Exception
    {
       //Create a array to hold only the filtered messages
       ArrayList<Message> filteredMessages = new ArrayList();
        
       //Cycle through the whole conversation and add only the messages with the key word to the filtered array
       for(Message convo : conversation.getMessages())
       {   
           if(convo.getSenderId().toLowerCase().contains(config.getFilterUserName())){
               filteredMessages.add(convo);
           }
       }    
       //Create a new conversation with the filtered messages and name
       Conversation filteredConversation = new Conversation(conversation.getName(), filteredMessages);
       
       //pass it through to be written to file
       return filteredConversation;
    }
    
    /**
     * Filters the conversation by the userName entered by the user.
     * @param config The configuration settings
     * @param blackList The black list of words to be censored. 
     * @param conversation The conversations object representing the data imported from a file
     * @return a filtered version of the conversation
     */
    public Conversation FilterByBlackList(ConversationExporterConfiguration config, ArrayList<String> blackList, Conversation conversation)throws Exception
    {     
        for(Message convo: conversation.getMessages())
        {
            for(String blackListedWord : blackList)
            {
                String filteredMessage = convo.getContent().replaceAll("(?i)\\b" + blackListedWord + "\\b", Resources.REDACTED);
                convo.setContent(filteredMessage);
            }
        }
        return conversation;
    }   

    /**
     * @return the config
     */
    public ConversationExporterConfiguration getConfig() {
        return config;
    }

    /**
     * @param config the config to set
     */
    public void setConfig(ConversationExporterConfiguration config) {
        this.config = config;
    }
    
    
}
