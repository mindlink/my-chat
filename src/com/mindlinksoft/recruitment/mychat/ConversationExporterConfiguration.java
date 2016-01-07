package com.mindlinksoft.recruitment.mychat;

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
     * filterUser flag indicates whether messages should be filtered for the given user
     */
    public boolean filterUser = false;
    public String username;
    
    /**
     * filterKeyword flag indicates whether messages should be filtered for the given keyword
     */
    
    public boolean filterKeyword = false;
    public String keyword;
    
    /**
     * filterBlacklist flag indicates whether given keyword should be redacted
     */
    
    public boolean filterBlacklist = false;
    public String blacklist;
    
    /**
     * filterNumbers
     */
    public boolean filterNumbers = false;
    
    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }
}
