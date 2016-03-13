package com.mindlinksoft.recruitment.mychat.models;

/**
 * Represents a model of the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {

    private final String inputFilePath;
    private final String outputFilePath;
    private final String user;
    
    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * 
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.user = null;
    }

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * 
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param user The user to filter the conversation by.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, String user) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.user = user;
    }
    
    /**
     * Gets the input file path for the conversation.
     * 
     * @return The conversation input file path.
     */
    public String getInputFilePath() {
    	return inputFilePath;
    }
    
    /**
     * Gets the output file path for the conversation.
     * 
     * @return The conversation output file path.
     */
    public String getOutputFilePath() {
    	return outputFilePath;
    }
    
    /**
     * Gets the user that the conversation will be filtered by.
     * 
     * @return The user to filter by
     */
    public String getUser() {
    	return user;
    }
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
    	// TODO: Implement a to string method.
    	return "";
    }
}
