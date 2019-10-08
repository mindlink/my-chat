package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {

    private String inputFilePath;
    private String outputFilePath;
	private String userFilter;
    private String keyWord;
    private ArrayList<String> blackList;
    private boolean hideNumbers;
    private boolean obfuscateIDs;

    /**
     * Initialises a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath Location of the text file to be read from
     * @param outputFilePath Destination of the .JSON file to be written to
     * @param userFilter Name of the user to filter messages by
     * @param keyWord Keyword to filter by
     * @param blackList	Words to be censored from the output
     * @param hideNumbers Flag to censor phone and credit card numbers
     * @param obfuscateIDs Flag to obfuscate IDs
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, String userFilter,
    		String keyWord, ArrayList<String> blackList, boolean hideNumbers, boolean obfuscateIDs) {
    	super();
    	this.inputFilePath = inputFilePath;
    	this.outputFilePath = outputFilePath;
    	this.userFilter = userFilter;
    	this.keyWord = keyWord;
    	this.blackList = blackList;
    	this.hideNumbers = hideNumbers;
    	this.obfuscateIDs = obfuscateIDs;
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

	public String getKeyWord() {
		return keyWord;
	}

	public ArrayList<String> getBlackList() {
		return blackList;
	}

	public boolean isHideNumbers() {
		return hideNumbers;
	}

	public boolean isObfuscateIDs() {
		return obfuscateIDs;
	}
}
