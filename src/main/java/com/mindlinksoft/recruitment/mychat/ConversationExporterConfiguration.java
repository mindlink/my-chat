package com.mindlinksoft.recruitment.mychat;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration
{
    private final String inputFilePath, outputFilePath;

    /**
     * Initialises a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath)
    {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }
    
    public String GetInputFilePath()    { return this.inputFilePath; }
    public String GetOutputFilePath()   { return this.outputFilePath; }
}
