package com.mindlinksoft.recruitment.mychat;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {
    /**
     * Holds the input file path.
     */
    private String inputFilePath;

    /**
     * Holds the output file path.
     */
    private String outputFilePath;

    /**
     * Constructor for initialising a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }

    /**
     * @return the inputFilePath
     */
    public String getInputFilePath() {
        return inputFilePath;
    }

    /**
     * @param inputFilePath the inputFilePath to set
     */
    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    /**
     * @return the outputFilePath
     */
    public String getOutputFilePath() {
        return outputFilePath;
    }

    /**
     * @param outputFilePath the outputFilePath to set
     */
    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }
}
