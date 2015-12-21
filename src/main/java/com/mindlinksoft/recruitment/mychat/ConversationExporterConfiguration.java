package com.mindlinksoft.recruitment.mychat;

import java.util.List;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {
	//variables
    /**
     * Represents the input file path.
     */
    private String inputFilePath;

    /**
     * Represents the output file path.
     */
    private String outputFilePath;
    
    /**
     * Represents a sender ID.
     */
    private String senderId;
    
    /**
     * Represents a keyword.
     */
    private String keyword;
    
    /**
     * Represents the a list of blacklisted word.
     */
    private List<String> blacklist;

    //constructor
    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param senderId The senderId to be filtered.
     * @param keyword The keyword to be filtered.
     * @param blacklist Comma-separated list of blacklisted words
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, String senderId, String keyword, List<String> blacklist) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.senderId = senderId;
        this.keyword = keyword;
        this.blacklist = blacklist;
    }
    
    //getters and setters
    /**
     * Gets inputFilePath.
     */   
    public String getInputFilePath() {
        return inputFilePath;
    }
    
    /**
     * Sets inputFilePath.
     */ 
	public void setInputFilePath(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}

    /**
     * Gets outputFilePath.
     */   
    public String getOutputFilePath() {
        return outputFilePath;
    }

    /**
     * Sets outputFilePath.
     */   
	public void setOutputFilePath(String outputFilePath) {
		this.outputFilePath = outputFilePath;
	}

    /**
     * Sets senderId.
     */   
	public String getSenderId() {
		return senderId;
	}

    /**
     * Gets senderId.
     */   
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

    /**
     * Sets keyword.
     */  
	public String getKeyword() {
		return keyword;
	}

    /**
     * Gets keyword.
     */  
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

    /**
     * Gets blacklist.
     */  
	public List<String> getBlacklist() {
		return blacklist;
	}

    /**
     * Sets blacklist.
     */  
	public void setBlacklist(List<String> blacklist) {
		this.blacklist = blacklist;
	}
    
}
