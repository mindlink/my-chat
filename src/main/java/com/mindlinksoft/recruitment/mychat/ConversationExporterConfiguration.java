package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;

import com.mindlinksoft.recruitment.mychat.filters.ConversationFilter;


/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {
    /**
     * Gets the input file path.
     */
    public String inputFilePath;

    /**
     * Gets the output file path.
     */
    public String outputFilePath;
    
    /**
     * Stores the filters we want to use for our conversation.
     */
    public ArrayList<ConversationFilter> filters;
    
    /**
     * Sets the flag to generate a report that details the most active users.
     */
    public boolean flagReport;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param filters The filters used to process the messages.
     * @param flagReport Flag that indicates method to generate an activity report.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, ArrayList<ConversationFilter> filters, boolean flagReport) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.filters = filters;
        this.flagReport = flagReport;
        
    }
}


