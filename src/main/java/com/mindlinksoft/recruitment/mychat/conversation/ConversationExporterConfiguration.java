package com.mindlinksoft.recruitment.mychat.conversation;

import java.util.ArrayList;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {

    private String inputFilePath;
    private String outputFilePath;
    private String user;
    private String keyword;
    private String[] blacklistedWords;
    private Boolean hideCCandPhoneno;
    private Boolean obfuscateUserID;
    
    

    public Boolean isObfuscateUserID() {
		return obfuscateUserID;
	}

	public void setObfuscateUserID(Boolean obfuscateUserID) {
		this.obfuscateUserID = obfuscateUserID;
	}

	public Boolean isHideCCandPhoneno() {
		return hideCCandPhoneno;
	}

	public void setHideCCandPhoneno(Boolean hideCCandPhoneno) {
		this.hideCCandPhoneno = hideCCandPhoneno;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	public String[] getBlacklistedWords() {
		return blacklistedWords;
	}

	public void setBlacklistedWords(String[] blacklistedWords) {
		this.blacklistedWords = blacklistedWords;
	}

	public String getInputFilePath() {
		return inputFilePath;
	}

	public void setInputFilePath(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}

	public String getOutputFilePath() {
		return outputFilePath;
	}

	public void setOutputFilePath(String outputFilePath) {
		this.outputFilePath = outputFilePath;
	}

	/**
	 * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
	 * @param inputFilePath
	 * @param outputFilePath
	 * @param user
	 * @param keyword
	 * @param blacklistedWords
	 * @param hideCCandPhoneno
	 * @param obfuscateUserID
	 */

    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath,String user,String keyword,String[] blacklistedWords,Boolean hideCCandPhoneno,Boolean obfuscateUserID) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.user = user;
        this.keyword = keyword;
        this.blacklistedWords = blacklistedWords;
    	this.hideCCandPhoneno = hideCCandPhoneno;
    	this.obfuscateUserID = obfuscateUserID;
    }

}
