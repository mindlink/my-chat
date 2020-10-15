package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

public class Processor {

    //Member variables

    /**
     * String List used for logging
     */
    private List<String> logging;

    private ObjectToExport objectToExport;

    private Conversation originalConversation;

    private Conversation processedConversation;

    private ConversationExporterConfiguration config;

    private Report report;

    //Constructor

    protected Processor(Conversation conversation, ConversationExporterConfiguration configuration){
        this.originalConversation = conversation;
        this.config = configuration;
        this.logging = new ArrayList<String> ();
        this.processedConversation = new Conversation(conversation.name, conversation.messages);
    }


    //Methods

    protected void processConversation (){

        //Tell the user that no filtering commands were given, if none were.
        if((this.config.getUser() == null) && (this.config.getKeyword() == null) && (this.config.getBlackList() == null)){
            this.logging.add("No filtering commands, exporting original conversation...");
        }

        //Perform filtering while also keeping a log

        String user = this.config.getUser();
        if(user != null) {
            this.logging.add("Filtering messages from user: " + user);
            UserFilterer userFilter = new UserFilterer(this.processedConversation, user);
            userFilter.process();
            this.processedConversation = userFilter.getProcessedConversation();
        }

        String keyword = this.config.getKeyword();
        if(keyword != null){
            this.logging.add("Filtering messages with keyword: " + keyword);
            KeywordFilterer keywordFilterer = new KeywordFilterer(this.processedConversation, keyword);
            keywordFilterer.process();
            this.processedConversation = keywordFilterer.getProcessedConversation();
        }

        //Possible bugs here if the user blacklist input is strange, would have to test extensively and correct accordingly
        String[] hiddenWords = this.config.getBlackList();
        if(hiddenWords != null && !hiddenWords.equals("")) {
            this.logging.add("Censoring following keywords: ");
            for (String word: hiddenWords) {
                this.logging.add("- " + word);
            }
            WordRedactor redactor = new WordRedactor(this.processedConversation, hiddenWords);
            redactor.process();
            this.processedConversation = redactor.getProcessedConversation();
        }

        //If no messages follow the filtering requirements, tell the user
        if(this.processedConversation.messages.isEmpty()) {
            this.logging.add("No messages with current filtering requirements exist");
        }

        //generate report
        if((this.config.reportRequested() != null) && (this.config.reportRequested())){
            this.logging.add("Generating report.");
            Reporter reporter = new Reporter(this.originalConversation);
            reporter.generateReport();
            this.report = reporter.getReport();
        }

        //Create the object to export
        this.objectToExport = new ObjectToExport(this.processedConversation, this.report, this.logging);
    }


    //Getters and Setters

    public ObjectToExport getObjectToExport() {
        return objectToExport;
    }

    public void setObjectToExport(ObjectToExport objectToExport) {
        this.objectToExport = objectToExport;
    }

    public List<String> getLogging() {
        return logging;
    }

    public void setLogging(List<String> logging) {
        this.logging = logging;
    }

    public Conversation getOriginalConversation() {
        return originalConversation;
    }

    public void setOriginalConversation(Conversation originalConversation) {
        this.originalConversation = originalConversation;
    }

    public Conversation getProcessedConversation() {
        return processedConversation;
    }

    public void setProcessedConversation(Conversation processedConversation) {
        this.processedConversation = processedConversation;
    }

    public ConversationExporterConfiguration getConfig() {
        return config;
    }

    public void setConfig(ConversationExporterConfiguration config) {
        this.config = config;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}