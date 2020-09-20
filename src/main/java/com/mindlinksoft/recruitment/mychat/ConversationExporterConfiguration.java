package com.mindlinksoft.recruitment.mychat;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {
	
	//Input text file delimiter
	private final String DELIMITER;
	//Input delimiter limit, allows for reading in of message content
	private final int DELIMITER_LIMIT;
	//Used to search for flag in command line arguments
	private final String FLAG_INDICATOR;
	//Start of custom arguments to parse
	private final int ARGS_START;
	//Input file path
    private String inputFilePath;
    //Output file path
    private String outputFilePath;
    //Regular expression for matching credit cards
    private String CREDIT_REGEX;
    //Replacement for obfuscated content
    private String REDACT_REPLACMENT;
    private String FAILED_CONVO_NAME;
    
    /**
     * Argument flags and target arrays
     */
    private boolean FILTER_USER;
    private String[] usersToFilter;
    private boolean FILTER_WORD;
    private String[] wordsToFilter;
    private boolean BLACKLIST_WORD;
    private String[] wordsToBlacklist;
    private boolean OBFS_CREDIT_CARD;
    private boolean OBFS_USER;
    private String[] usersToObfuscate;
    private boolean GEN_REPORT;
    

    /**
     * Initialises a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.DELIMITER = " ";
        this.DELIMITER_LIMIT = 3;
		this.FLAG_INDICATOR = "-";
		this.ARGS_START = 2;
		this.CREDIT_REGEX = "\\b((\\d{4})-? ?(\\d{4})-? ?(\\d{4})-? ?(\\d{4}))\\b";
		this.REDACT_REPLACMENT = "*REDACTED*";
		this.FAILED_CONVO_NAME = "No Chat File Found";
		
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

	public String getDELIMITER() {
		return DELIMITER;
	}

	public int getDELIMITER_LIMIT() {
		return DELIMITER_LIMIT;
	}

	public String getFLAG_INDICATOR() {
		return FLAG_INDICATOR;
	}

	public int getARGS_START() {
		return ARGS_START;
	}

	public boolean isFILTER_USER() {
		return FILTER_USER;
	}

	public void setFILTER_USER(boolean fILTER_USER) {
		FILTER_USER = fILTER_USER;
	}

	public String[] getUsersToFilter() {
		return usersToFilter;
	}

	public void setUsersToFilter(String[] usersToFilter) {
		this.usersToFilter = usersToFilter;
	}

	public boolean isFILTER_WORD() {
		return FILTER_WORD;
	}

	public void setFILTER_WORD(boolean fILTER_WORD) {
		FILTER_WORD = fILTER_WORD;
	}

	public String[] getWordsToFilter() {
		return wordsToFilter;
	}

	public void setWordsToFilter(String[] wordsToFilter) {
		this.wordsToFilter = wordsToFilter;
	}

	public boolean isBLACKLIST_WORD() {
		return BLACKLIST_WORD;
	}

	public void setBLACKLIST_WORD(boolean bLACKLIST_WORD) {
		BLACKLIST_WORD = bLACKLIST_WORD;
	}

	public String[] getWordsToBlacklist() {
		return wordsToBlacklist;
	}

	public void setWordsToBlacklist(String[] usersToBlacklist) {
		this.wordsToBlacklist = usersToBlacklist;
	}

	public boolean isOBFS_CREDIT_CARD() {
		return OBFS_CREDIT_CARD;
	}

	public void setOBFS_CREDIT_CARD(boolean oBFS_CREDIT_CARD) {
		OBFS_CREDIT_CARD = oBFS_CREDIT_CARD;
	}

	public boolean isOBFS_USER() {
		return OBFS_USER;
	}

	public void setOBFS_USER(boolean oBFS_USER) {
		OBFS_USER = oBFS_USER;
	}

	public String[] getUsersToObfuscate() {
		return usersToObfuscate;
	}

	public void setUsersToObfuscate(String[] usersToObfuscate) {
		this.usersToObfuscate = usersToObfuscate;
	}

	public boolean isGEN_REPORT() {
		return GEN_REPORT;
	}

	public void setGEN_REPORT(boolean gEN_REPORT) {
		GEN_REPORT = gEN_REPORT;
	}

	public String getCREDIT_REGEX() {
		return CREDIT_REGEX;
	}

	public String getREDACT_REPLACMENT() {
		return REDACT_REPLACMENT;
	}

	public String getFAILED_CONVO_NAME() {
		return FAILED_CONVO_NAME;
	}

}
