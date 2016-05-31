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
     * Gets the user to be filtered.
     */
    public String userFilter="";
    
    /**
     * Gets the output file path.
     */
    public String keywordFilter="";
    
    /**
     * Gets the output file path.
     */
    public String blacklistFilePath="";
    
    /**
     * Gets the Hide CreditCard/Phone Number flag.
     */
    public Boolean hideNumbers=false;
    
    /**
     * Gets the obfuscate ID flag.
     */
    public Boolean obfuscateID=false;

    /**
     * Initialises a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, String userFilter, String keywordFilter, String blacklistFilePath, String hideNumbers, String obfuscateID) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.userFilter = userFilter;
        this.keywordFilter = keywordFilter;
        this.blacklistFilePath = blacklistFilePath;
        this.hideNumbers=Boolean.valueOf(hideNumbers);
        this.obfuscateID=Boolean.valueOf(obfuscateID);
    }

}
