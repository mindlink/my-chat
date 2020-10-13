package com.mindlinksoft.recruitment.mychat;

import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

/**
 * Represents the configuration for the exporter.
 */
@Command(name = "export", mixinStandardHelpOptions = true, version = "exporter 1.0",
         description = "Exports a plain text chat log into a JSON file.")
public final class ConversationExporterConfiguration {
    /**
     * Gets the input file path.
     */
    @Option(names = { "-i", "--inputFilePath" }, description = "The path to the input chat log file.", required = true)
    public String inputFilePath;

    /**
     * Gets the output file path.
     */
    @Option(names = { "-o", "--outputFilePath" }, description = "The path to the output JSON file.", required = true)
    public String outputFilePath;
	
	/**
     * Gets the blacklisted words.
     */
    @Option(names = { "--blacklist" }, description = "Blacklisted words", required = false)
    public String[] blacklist;
	
	/**
     * Gets the user to filter messages by.
     */
    @Option(names = { "--filterByUser" }, description = "User to filter messages by", required = false)
    public String filter_user;
	
	/**
     * Gets the word to filter messages by.
     */
    @Option(names = { "--filterByWord" }, description = "Word to filter messages by", required = false)
    public String filter_word;
	
	/**
     * Gets the optionb to generate report.
     */
    @Option(names = { "--report" }, description = "Option to generate report", required = false)
    public boolean report;
	
	//Constructor
	public ConversationExporterConfiguration(){}
	
	//Constructor
	public ConversationExporterConfiguration(String inputFilePath, String outputFilePath){
		this.inputFilePath = inputFilePath;
		this.outputFilePath = outputFilePath;
	}
}

