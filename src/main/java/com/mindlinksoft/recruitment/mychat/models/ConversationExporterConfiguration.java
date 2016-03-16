package com.mindlinksoft.recruitment.mychat.models;

/**
 * Represents a model of the configuration for the exporter.
 */
public class ConversationExporterConfiguration {

    private final String inputFilePath;
    private final String outputFilePath;
    private final String user;
    private final String keyword;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * 
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param user The user to filter the conversation by.
     * @param keyword The keyword to filter the conversation by.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, String user, String keyword) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.user = user;
        this.keyword = keyword;
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
