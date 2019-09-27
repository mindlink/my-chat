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
     * Gets the user to filter with.
     */
    public String userFilter;

    /**
     * Gets the keyword to filter with.
     */
    public String keyword;

    /**
     * Gets the redacted keyword.
     */
    public String redacted;

    /**
     * flag to redact credit cards.
     */
    public Boolean credit;

    /**
     * flag to redact credit cards.
     */
    public Boolean obfuscate;

    /**
     * flag to counts messages.
     */
    public Boolean count;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration}
     * class.
     * 
     * @param inputFilePath  The input file path.
     * @param outputFilePath The output file path.
     * @param userFilter     The user to filter by.
     * @param keyword        The keyword to filter by.
     * @param credit         Flag to hide credit and phone numbers.
     * @param obfuscate      Flag to obfuscate ids
     * @param count          Flag to obfuscate ids
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, String userFilter,
            String keyword, String redacted, Boolean credit, Boolean obfuscate, Boolean count) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.userFilter = userFilter;
        this.keyword = keyword;
        this.redacted = redacted;
        this.credit = credit;
        this.obfuscate = obfuscate;
        this.count = count;
    }
}
