package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;

import com.mindlinksoft.recruitment.mychat.features.ChatFeature;

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
     * Gets a list of features based on CLI
     */
    public ArrayList<ChatFeature> features;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, ArrayList<ChatFeature> features) 
    {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.features = features;
    }
}
