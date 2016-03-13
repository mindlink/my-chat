package com.mindlinksoft.recruitment.mychat.models;

import java.time.Instant;

/**
 * Represents a model of the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {

    private final String inputFilePath;
    private final String outputFilePath;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
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
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
    	// TODO: Implement a to string method.
    	return "";
    }
}
