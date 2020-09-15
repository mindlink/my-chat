package com.mindlinksoft.recruitment.mychat.model;

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
     * Gets the input essential features
     */
    public String commandInput;

    /**
     * Gets the input additional features
     */
    public String features;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath,String info,String features) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.commandInput=info;
        this.features=features;
    }
}
