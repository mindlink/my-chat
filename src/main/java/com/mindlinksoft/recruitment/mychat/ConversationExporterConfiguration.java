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
     * Gets the username to be sorted.
     */
    public String username;
    
    /**
     * Gets the keyword for messages to be sorted.
     */
    public String keyword;
    
    /**
     * Gets the blacklist value
     */
    public String blacklist;
    
    /**
     * Gets if the credit card must be hidden
    */
    public boolean hideCC;
    
    /**
     * Gets if the credit userId must be obfuscated
    */
    public boolean hideId;
    
    /**
     * Gets if we must include report
    */
    public boolean includeReport;
    
    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param filter to be applied
     * @param blacklist term to be blacklisted
     * @param hideCC flag to hide credit card
     * @param hideId flag to hide user id
     * @param includeReport flag to include report   
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, 
    		String username, String keyword, String blacklist, boolean hideCC, boolean hideId, boolean includeReport) {
    	
	        this.inputFilePath = inputFilePath;
	        this.outputFilePath = outputFilePath;
	        this.username = username;
	        this.keyword = keyword;
	        this.blacklist = blacklist;
	        this.hideCC = hideCC;
	        this.hideId = hideId;
	        this.includeReport = includeReport;
    }
}
