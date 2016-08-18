package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;

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
     * Gets the user to filter on.
     */
    public String filteredUser;
    
    /**
     * Gets the word to filter on.
     */
    public String filtererWord;
    
    
    /**
     * Gets the words to hide.
     */
    ArrayList<String> blackList;
    
    
    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, String filteredUser, 
    		String filteredWord, ArrayList<String> blackList) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.filteredUser = filteredUser;
        this.filtererWord = filteredWord;
        this.blackList = blackList;
    }
}
