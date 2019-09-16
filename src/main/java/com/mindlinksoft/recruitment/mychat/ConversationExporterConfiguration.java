package com.mindlinksoft.recruitment.mychat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents the configuration for the exporter.
 */
public class ConversationExporterConfiguration {
    /**
     * Gets the input file path.
     */
    public final String inputFilePath;

    /**
     * Gets the output file path.
     */
    public final String outputFilePath;
    
    public final OptionalArgumentManager optionalArgumentManager;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.optionalArgumentManager = new OptionalArgumentManager();
    }
    
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, String[] stringOptionalArguments) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;

    	OptionalArguments optionalArgs = OptionalArgumentValidator.getValidatedArguments(stringOptionalArguments);
        this.optionalArgumentManager = new OptionalArgumentManager(optionalArgs);
    }
}
