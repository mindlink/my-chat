package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

public final class ObjectToBeExported extends Utilities {

    /**
     * String List used for logging
     */
    private List<String> logging;

    /**
     * Original Copy of conversation,
     */
    private static Conversation originalConv;

    /**
     * The processed conversation
     */
    private Conversation conversationToExport;

    /**
     * The requested analysis metrics
     */
    private Report report;


    /**
     * Initializes a new instance of the {@link ObjectToBeExported} class.
     * @param conversation The original conversation as read from file and put into Conversation object.
     * @param config The conversation configuration settings.
     */
    public ObjectToBeExported(Conversation conversation, ConversationExporterConfiguration config) {

        this.logging = new ArrayList<>();

        //store copy of original
        originalConv = new Conversation(conversation.name,conversation.messages);

        //process conversation
        this.conversationToExport = conversation;
        processConv(this.conversationToExport, config);

        //generate report
        if((config.reportRequested() != null) && (config.reportRequested())){
            this.report = new Report(this.originalConv);
            this.logging.add("Generating report.");
        }
    }

    protected void processConv (Conversation conv, ConversationExporterConfiguration config){

        //Tell the user that no filtering commands were given, if none were.
        if((config.getUser() == null) && (config.getKeyword() == null) && (config.getBlackList() == null)){
            this.logging.add("No filtering commands, exporting original conversation...");
        }

        //Perform filtering while also keeping a log
        String user = config.getUser();
        if(user != null) {
            this.logging.add("Filtering messages from user: " + user);
            filterByUser(conv, config);
        }

        String keyword = config.getKeyword();
        if(keyword != null){
            this.logging.add("Filtering messages with keyword: " + keyword);
            filterByKeyword(conv, config);
        }

        //Possible bugs here if the user blacklist input is strange, would have to test extensively and correct accordingly
        String[] hiddenWords = config.getBlackList();
        if(hiddenWords != null && !hiddenWords.equals("")) {
            this.logging.add("Censoring following keywords: ");
            for (String word: hiddenWords) {
                this.logging.add("- " + word);
            }
            hideWords(conv, config);
        }

        //If no messages follow the filtering requirements, tell the user
        if(conv.messages.isEmpty()) {
            this.logging.add("No messages with current filtering requirements exist");
        }
    }

    //Getters and Setters
    public List<String> getLogging() {
        return logging;
    }

    public static Conversation getOriginalConv() {
        return originalConv;
    }

    public Conversation getConversationToExport() {
        return conversationToExport;
    }

    public Report getReport() {
        return report;
    }
}
