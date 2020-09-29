package com.mindlinksoft.recruitment.mychat.model;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {

	private String inputFilePath;

	private String outputFilePath;

	private String userFilter;

	private String keywordFilter;

	private String blacklist;

	private boolean hidePersonalDeatils;

	private boolean obfuscateUsers;

	private boolean userActivity;

	/*
	 * Default OFF-case for string-based filters.
	 */
	public static final String NO_FILTER = "";

	/**
	 * Initializes a new instance of the {@link ConversationExporterConfiguration}
	 * class.
	 * 
	 * @param inputFilePath  The input file path.
	 * @param outputFilePath The output file path.
	 */
	public ConversationExporterConfiguration(String inputFilePath, String outputFilePath) {
		this.inputFilePath = inputFilePath;
		this.outputFilePath = outputFilePath;
		this.userFilter = NO_FILTER;
		this.keywordFilter = NO_FILTER;
		this.blacklist = NO_FILTER;
		this.hidePersonalDeatils = false;
		this.obfuscateUsers = false;
		this.userActivity = false;
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

	public void setUserFilter(String userFilter) {
		this.userFilter = userFilter;
	}

	public String getKeywordFilter() {
		return keywordFilter;
	}

	public void setKeywordFilter(String keywordFilter) {
		this.keywordFilter = keywordFilter;
	}

	public String getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(String blacklist) {
		this.blacklist = blacklist;
	}

	public boolean isHidePersonalDeatilsOn() {
		return hidePersonalDeatils;
	}

	public void setHidePersonalDeatils(boolean hidePersonalDeatils) {
		this.hidePersonalDeatils = hidePersonalDeatils;
	}

	public boolean isObfuscateUsersOn() {
		return obfuscateUsers;
	}

	public void setObfuscateUsers(boolean obfuscateUsers) {
		this.obfuscateUsers = obfuscateUsers;
	}

	public boolean isUserActivityOn() {
		return userActivity;
	}

	public void setUserActivity(boolean countMostActiceUsers) {
		this.userActivity = countMostActiceUsers;
	}

}
