package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

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
     * Gets the instructions, if there are any.
     */
    public List<String> instructions;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param instructions The instructions.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, List<String> instructions) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.instructions = instructions;
    }
}
