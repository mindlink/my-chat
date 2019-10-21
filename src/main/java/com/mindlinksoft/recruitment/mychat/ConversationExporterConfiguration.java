package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.optionClasses.ChatOption;

import java.util.ArrayList;

/**
 * Represents the configuration for the exporter.
 */
final class ConversationExporterConfiguration {
    /**
     * Gets the input file path.
     */
    String inputFilePath;

    /**
     * Gets the output file path.
     */
    String outputFilePath;

    /**
     * Gets the commandline options.
     */
    ArrayList<ChatOption> options;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param options Commandline options
     */
    ConversationExporterConfiguration(String inputFilePath, String outputFilePath, ArrayList<ChatOption> options) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.options = options;
    }
}
