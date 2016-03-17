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



    public Map<String, String> additionalArgs;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(Map<String, String> argumentsmap) {

        this.inputFilePath = argumentsmap.get("input");
        this.outputFilePath = argumentsmap.get("output");

        argumentsmap.remove("input");
        argumentsmap.remove("output");

        this.additionalArgs = argumentsmap;

    }
}
