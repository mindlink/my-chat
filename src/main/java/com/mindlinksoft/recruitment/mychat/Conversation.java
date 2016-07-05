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
     * The messages in the conversation. Only field in the class with package-
     * wide visibility.<p>
     * This field has package visibility because given the amount of modification
     * that it is subjected to by the filters, making this private would then
     * require the definition of a set of methods to access it so extensive that
     * it would be equivalent to not making it private to the package this class
     * resides in.<p>
     * A possible solution would be to make the Conversation class apply filters
     * to itself, so that it could pass a reference to this private field into
     * the "apply" method of each {@link ConversationFilter} (provided this was
     * modified to take a list of messages instead of a Conversation; this 
     * approach was part of Jamie Matthews' solution: 
     * {@link https://github.com/Jamie-Matthews/}).<p>
     * But it should not be responsibility of the Conversation to apply filters
     * to itself, because the Conversation is responsible for data representation,
     * and to make it responsible for applying filters to itself would introduce
     * issues when the way filters are applied to a conversation needs to change,
     * but the data model of a conversation does not.<p>
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
    
    void setName(String name) {
    	this.name = name;
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
