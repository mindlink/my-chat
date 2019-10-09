package com.mindlinksoft.recruitment.mychat;

import java.util.Map;

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
     * Map of flags and their arguments
     */
    public Map<String, String> flagMap;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }

    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, Map<String, String> flagMap){
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.flagMap = flagMap;
    }
}
