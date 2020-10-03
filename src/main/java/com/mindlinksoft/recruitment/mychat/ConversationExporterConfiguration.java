package com.mindlinksoft.recruitment.mychat;

import picocli.CommandLine.Option;

import java.util.ArrayList;

import picocli.CommandLine.Command;

/**
 * Represents the configuration for the exporter.
 */
@Command(name = "export", mixinStandardHelpOptions = true, version = "exporter 1.0", description = "Exports a plain text chat log into a JSON file.")
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


    @Option(names = { "-fu", "--filterByUser" }, description = "The flag to filter messages sent by the specified user", required = false)
    public String userFilter;

    @Option(names = { "-fw", "--filterByKeyword" }, description = "The flag to filter messages sent by the specified keyword", required = false)
    public String wordFilter;

    @Option(names = { "-b", "--blacklist" }, description = "The flag to filter all messages", required = false)
    public ArrayList<String> blacklistWords;

    @Option(names = { "-r", "--report" }, description = "The flag to add a report", required = false)
    public boolean report;
}
