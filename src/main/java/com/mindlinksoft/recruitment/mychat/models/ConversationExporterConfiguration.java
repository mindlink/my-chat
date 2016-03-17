package com.mindlinksoft.recruitment.mychat.models;

import java.util.List;

/**
 * Represents a model of the configuration for the exporter.
 */
public class ConversationExporterConfiguration {

    private final String inputFilePath;
    private final String outputFilePath;
    private final String user;
    private final String keyword;
    private final List<String> blacklist;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * 
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param user The user to filter the conversation by.
     * @param keyword The keyword to filter the conversation by.
     * @param blacklist The list of words to redact.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, String user, String keyword, List<String> blacklist) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.user = user;
        this.keyword = keyword;
        this.blacklist = blacklist;
    }
    
    /**
     * Gets the input file path for the exporter.
     * 
     * @return The conversation input file path.
     */
    public String getInputFilePath() {
    	return inputFilePath;
    }
    
    /**
     * Gets the output file path for the exporter.
     * 
     * @return {@link String} - The conversation output file path.
     */
    public String getOutputFilePath() {
    	return outputFilePath;
    }
    
    /**
     * Gets the user that the conversation will be filtered by.
     * 
     * @return {@link String} - The user to filter by
     */
    public String getUser() {
    	return user;
    }
    
    /**
     * Gets the keyword that the conversation will be filtered by.
     * 
     * @return {@link String} - The keyword to filter by
     */
    public String getKeyword() {
    	return keyword;
    }
    
    /**
     * Gets the list of words to redact.
     * 
     * @return A {@link List} containing the words to redact.
     */
    public List<String> getBlacklist() {
    	return blacklist;
    }
    
    /**
     * Create a readable string for the exporter configuration.
     * 
     * @return The conversation exporter configuration as a string
     */
    @Override
    public String toString() {
    	// TODO: Implement a to string method...
    	return "";
    }
}
