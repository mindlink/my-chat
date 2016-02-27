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
    
    //Gets the feature
    public String feature;
    
    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param feature The feature of the program, could be either essential or additional
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, String feature) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.feature = feature;
    }
}

