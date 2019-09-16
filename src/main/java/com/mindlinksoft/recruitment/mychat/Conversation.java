package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {
    /**
     * The name of the conversation.
     */
    private String name;

    /**
     * The messages in the conversation.
     */
    private ArrayList<Message> messages;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, ArrayList<Message> messages) {
        this.name = name;
        this.messages = messages;
    }

    /**
     * Obfuscates the conversation depending on values found in the config object
     * @param config the {@Link ConversationExporterConfiguration}
     */

    public void obfuscate(ConversationExporterConfiguration config) {
        if(config.isObfuscateInfo())
            // Because of the nature of implementation of redact, can just pass in a regex for phone number and CC here and it will work
            // This regex will also block CC numbers but leave a few trailing digits. This would be easy to remedy but I left it in as it actually looks quite nice
            this.redact("(?:(?:(?:\\+|00)44[\\s\\-\\.]?)?(?:(?:\\(?0\\)?)[\\s\\-\\.]?)?(?:\\d[\\s\\-\\.]?){10})|(?=\\(?\\d*\\)?[\\x20\\-\\d]*)(\\(?\\)?\\x20*\\-*\\d){11}", "*redacted*");

        if(config.isObfuscateUID())
            for (Message msg : messages) {
                // just set the username equal to the user id instead. In that way, obfuscating it.
                msg.getSender().setName(String.valueOf(msg.getSender().hashCode()));
            }
    }

    /**
     * Filters the conversation by sender dependent on sender specified in the {@Link ConversationExporterConfiguration} object
     * @param config the {@Link ConversationExporterConfiguration} object
     */

    public void filterBySender(ConversationExporterConfiguration config){
        if(config.getFilterSender() != null){
            ArrayList<Message> nMessages = new ArrayList<>();
            for(int x = 0; x < messages.size(); x++){
                if(messages.get(x).getSender().equals(config.getFilterSender())){
                    nMessages.add(messages.get(x));
                }
            }
            messages = nMessages;
        }
    }

    /**
     * Filters the conversation by keyword dependent on keyword specified in the {@Link ConversationExporterConfiguration} object
     * @param config the {@Link ConversationExporterConfiguration} object
     */

    public void filterByKeyword(ConversationExporterConfiguration config){
        if(config.getFilterKeyword() != null) {
            // Check the whole word exists using regex boundaries
            Pattern pattern = Pattern.compile("(?i)"+config.getFilterKeyword()+"\\b");
            ArrayList<Message> nMessages = new ArrayList<>();
            for(int x = 0; x < messages.size(); x++){
                Matcher m = pattern.matcher(messages.get(x).getContent());
                if(m.find()){
                    nMessages.add(messages.get(x));
                }
            }
            messages = nMessages;
        }
    }

    /**
     * Utility method for redacting certain keywords - used for blacklist amongst other things
     * @param keyword the keyword to be redacted, can also pass a regex so long as it conforms to the boundaries
     * @param filler the redaction string i.e. "*redacted*"
     */

    public void redact(String keyword, String filler){
        // Find messages containing that word
        String regex = "(?i)"+keyword+"\\b";
        Pattern pattern = Pattern.compile(regex);

        for(int x = 0; x < messages.size(); x++){
            Message msg = messages.get(x);
            Matcher m = pattern.matcher(msg.getContent());
            if(m.find()){
                messages.get(x).setContent(msg.getContent().replaceAll(regex, filler));
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Message x: messages){
            sb.append(x.getSender().getName() + " " + x.getContent() + "\n");
        }
        return sb.toString();
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
}
