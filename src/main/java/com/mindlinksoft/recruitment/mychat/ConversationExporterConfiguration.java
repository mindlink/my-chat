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
     * Gets the user name to search.
     */
    
    public String userName;
    
    /**
     * Gets the search string.
     */
    public String searchItem;
    
    /**
     * blacklist string in a CSV sort of way
     */
    public String blackList;
    
    /**
     * blacklist string in a CSV sort of way
     */
    public String cardPattern;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param arguments 
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, String userName, String searchItem, String blackList, String cardPattern) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.userName = userName;
        this.searchItem = searchItem;
        this.blackList = blackList;
        this.cardPattern = cardPattern;
    }

	@Override
	public String toString() {
		return "ConversationExporterConfiguration [inputFilePath=" + inputFilePath + ", outputFilePath="
				+ outputFilePath + ", userName=" + userName + ", searchItem=" + searchItem + ", blackList=" + blackList
				+ ", cardPattern=" + cardPattern + "]";
	}


	
}
