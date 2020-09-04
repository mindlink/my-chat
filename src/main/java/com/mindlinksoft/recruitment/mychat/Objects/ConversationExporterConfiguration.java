package com.mindlinksoft.recruitment.mychat.Objects;

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

    public String argument;

    public String value;
    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, String argument, String value) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.argument = argument;
        this.value = value;
    }
}
