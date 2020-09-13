package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {
	
	/**
	 * Stores the username to be filtered by if the filter option is specified
	 */
	private boolean userFilter = false;
	private ArrayList<String> userFilterValue = new ArrayList<String>();
	
	/**
	 * Stores the keyword to filter by if specified
	 */
	private boolean keywordFilter = false;
	private ArrayList<String> keywordFilterValue = new ArrayList<String>();
	
	/**
	 * Stores the blacklisted words if specified
	 */
	private boolean blacklistFilter = false;
	private ArrayList<String> blacklistKeywords = new ArrayList<String>();
	
    /**
     * Gets the input file path.
     */
    public String inputFilePath;

    /**
     * Gets the output file path.
     */
    public String outputFilePath;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }
    
    //Set optional arguments
    public void setUserFilterSetting(boolean boolValue) {
    	this.userFilter = boolValue;
    }
    
    public void setUserFilterValue(String filterValue) {
    	this.userFilterValue.add(filterValue);
    }
    
    public void setKeywordFilterSetting(boolean boolValue) {
    	this.keywordFilter = boolValue;
    }
    
    public void setKeywordFilterValue(String filterValue) {
    	this.keywordFilterValue.add(filterValue);
    }
    
    public void setBlacklistSetting(boolean boolValue) {
    	this.blacklistFilter = boolValue;
    }
    
    public void setBlacklistValue(String wordToBlacklist) {
    	this.blacklistKeywords.add(wordToBlacklist);
    }
    
    //Get optional arguments
    public boolean getUserFilterSetting() {
    	return this.userFilter;
    }
    
    public ArrayList<String> getUserFilterValue() {
    	return this.userFilterValue;
    }
    
    public boolean getKeywordFilterSetting() {
    	return this.keywordFilter;
    }
    
    public ArrayList<String> getKeywordFilterValue() {
    	return this.keywordFilterValue;
    }
    
    public boolean getBlacklistSetting() {
    	return this.blacklistFilter;
    }
    
    public ArrayList<String> getBlacklistedWords() {
    	return this.blacklistKeywords;
    }
    
    public ArrayList<Boolean> getFilterSettings() {
    	ArrayList<Boolean> filterSettings = new ArrayList<Boolean>();
    	filterSettings.add(getUserFilterSetting());
    	filterSettings.add(getKeywordFilterSetting());
    	filterSettings.add(getBlacklistSetting());
    	
    	return filterSettings;
    }
    
    public ArrayList<ArrayList<String>> getFilterValues() {
    	ArrayList<ArrayList<String>> filterValues = new ArrayList<ArrayList<String>>();
    	filterValues.add(getUserFilterValue());
    	filterValues.add(getKeywordFilterValue());
    	filterValues.add(getBlacklistedWords());
    	
    	return filterValues;
    }
}
