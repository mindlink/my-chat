package com.mindlinksoft.recruitment.mychat;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration{

	//Input File Path.
	public String inputFilePath;
	public boolean hasInput;

	//Output File Path.
	public String outputFilePath;
	public boolean hasOutput;

	//Blacklist File Path.
	public String blackList;
	public boolean hasBlackList;

	//Username Filter.
	public String username;
	public boolean hasUsername;

	//Keyword Filter.
	public String keyword;
	public boolean hasKeyword;

	//Flag, Usernames to be Redacted.
	public boolean rUsername;

	//Flag, Confidential Details to be Redacted (Phone numbers and card numbers).
	public boolean rConfidential;

	/**
	 * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
	 * @param inputFilePath The input file path.
	 * @param outputFilePath The output file path.
	 * @param blackList The Blacklist file path.
	 * @param username The username string to be used as a filter.
	 * @param keyword The Keyword to be used as a filter.
	 */
	public ConversationExporterConfiguration(
			String inputFilePath,
			String outputFilePath,
			String blackList,
			String username,
			String keyword,
			boolean hasInput,
			boolean hasOutput,
			boolean hasBlackList,
			boolean hasUsername,
			boolean hasKeyword,
			boolean rUsername,
			boolean rConfidential) {

		//Setup String Variables
		this.inputFilePath = inputFilePath;
		this.outputFilePath = outputFilePath;
		this.blackList = blackList;
		this.username = username;
		this.keyword = keyword;

		//Initialize Boolean Variables
		this.hasInput = hasInput;
		this.hasOutput = hasOutput;
		this.hasBlackList = hasBlackList;
		this.hasUsername = hasUsername;
		this.hasKeyword = hasKeyword;
		this.rUsername = rUsername;
		this.rConfidential = rConfidential;
	}


}
