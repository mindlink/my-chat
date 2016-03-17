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
     * Gets the username.
     */
    public String username;

    /**
     * Gets the keyword.
     */
    public String keyword;

    /**
     * Gets the blacklist string
     */
    public String blacklist;

    /**
     * Gets the blacklist string
     */
    public boolean hideNumbers;

    /**
     * Gets the blacklist string
     */
    public boolean obfuscateID;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param username filtering value
     * @param keyword filtering value
     * @param blacklist blacklist value
     * @param hideNumbers flag to hide numbers
     * @param obfuscateID flag to hide id by obfuscate strategy
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, String username, String keyword, String blacklist, boolean hideNumbers, boolean obfuscateID) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.username = username;
        this.keyword = keyword;
        this.blacklist = blacklist;
        this.hideNumbers = hideNumbers;
        this.obfuscateID = obfuscateID;
    }
}
