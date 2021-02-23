package com.mindlinksoft.recruitment.mychat;

import picocli.CommandLine.Option;

import java.util.List;

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
     * Gets the user to filter the output file by.
     */
    @Option(names = { "-u", "--filterByUser" }, description = "The user to filter by", required = false)
    public String userFilter;
    
    /**
     * Gets the keyword to filter the output file by.
     */
    @Option(names = { "-k", "--filterByKeyword" }, description = "The word to filter by", required = false)
    public String keywordFilter;
    
    
    /**
     * Gets the list of blacklisted words.
     */
    @Option(names = {"-b", "--blacklist"}, description = "The word to add to the blacklist", required = false)
    public List<String> blacklist;
    
    
    /**
     * Toggle for the attached report.
     */
    @Option(names = {"-r", "--report"}, description = "Attach a report of the number of messages sent by users", required = false)
    public Boolean report;
}
