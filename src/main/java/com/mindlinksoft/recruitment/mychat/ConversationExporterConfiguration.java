package com.mindlinksoft.recruitment.mychat;

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
     * Applies filters to a conversation
     */
    public ConversationFilter filter = null;

    /**
     * Initialises a new instance of the {@link ConversationExporterConfiguration} class with a filter applied.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param filter The filter to be used on this conversation
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, ConversationFilter filter) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.filter = filter;
    }
    
    /**
     * Initialises a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }
}
