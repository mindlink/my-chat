package com.mindlinksoft.recruitment.mychat;

import java.util.List;

/**
 * Represents the model of a conversation. Being an application specific data 
 * modelling class (like {@link Message}, its fields are liberally accessible to 
 * the entirety of the package.
 */
class Conversation {
    /**
     * The name of the conversation.
     */
    private String name;

    /**
     * The messages in the conversation.
     */
    List<Message> messages;
    
    private ReportEntry[] report;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    Conversation(String name, List<Message> messages) {
        this.name = name;
        this.messages = messages;
    }

    String getName() {
    	return this.name;
    }
    
    ReportEntry[] getReport() {
    	ReportEntry[] copy = new ReportEntry[this.report.length];
    	System.arraycopy(this.report, 0, copy, 0, this.report.length);
    	return copy;
    }
    
    void setReport(ReportEntry[] report) {
    	this.report = report;
    }
}
