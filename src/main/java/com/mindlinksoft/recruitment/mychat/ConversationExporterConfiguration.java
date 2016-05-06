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
     * Gets the user (if specified).
     */
    public String user;

    /**
     * Gets the keyword (if specified).
     */
    public String keyword;

    /**
     * Gets the blacklist (if specified).
     */
    public String[] blacklist;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param user A specified user (for filtering)
     * @param keyword A specified keyword (for filtering)
     * @param blacklist List of words to be redacted in output
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, String user, String keyword, String[] blacklist) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.user = user;
        this.keyword = keyword;
        this.blacklist = null;
        if ( blacklist.length > 0 )
        {
        	this.blacklist = new String[blacklist.length];
        	System.arraycopy(blacklist, 0, this.blacklist, 0, blacklist.length);
        }
    }
}
