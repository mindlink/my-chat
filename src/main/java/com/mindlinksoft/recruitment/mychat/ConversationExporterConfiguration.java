package com.mindlinksoft.recruitment.mychat;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {
	
    //Gets the input file path.
    private String inputFilePath;
    //Gets the output file path.
    private String outputFilePath;
    //Stores the name of the user to filter the conversation for, should be null if no user to filter by
    private String userFilter;
    //stores the keyword so only messages that contain this keyword can be filtered through, should be null if no keyword required
    private String keywordFilter;
    //stores the list of blacklisted words to censor from the conversation
    private String[] blacklist;
    //tracks whether to censor phone and bank account numbers from the conversation
    //true to censor these numbers, false not to
    private boolean censorNumbers;
    //false to not censor sender ids
    //true to censor sender ids
    private boolean censorSenderIds;

    
    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, String userFilter, String keywordFilter, String[] blacklist, boolean censorNumbers, boolean censorSenderIds) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.userFilter = userFilter;
        this.keywordFilter = keywordFilter;
        this.blacklist = blacklist;
        this.censorNumbers = censorNumbers;
        this.censorSenderIds = censorSenderIds;
    }
    
    public String getInputFilePath() {
    	return inputFilePath;
    }
    
    public String getOutputFilePath() {
    	return outputFilePath;
    }
    
    public String getUserFilter() {
    	return userFilter;
    }
    
    public String getKeywordFilter() {
    	return keywordFilter;
    }
    
    public String[] getBlacklist() {
    	return blacklist;
    }
    
    public boolean getCensorNumbers() {
    	return censorNumbers;
    }
    
    public boolean getCensorSenderIds() {
    	return censorSenderIds;
    }
}
