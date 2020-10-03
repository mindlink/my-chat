package com.mindlinksoft.recruitment.mychat.config;

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

    /**
     * Option to filter messages sent by user
     */
    @Option(names = { "-fu", "--filterByUser" }, description = "The flag to filter messages sent by the specified user", required = false)
    public String userFilter;

    /**
     * Option to filter messages only containing the keyword
     */
    @Option(names = { "-fw", "--filterByKeyword" }, description = "The flag to filter messages which contain the specified keyword", required = false)
    public String wordFilter;

    /**
     * Repeating option that replaces all occurrences of the words regardless of case with *redacted*
     */
    @Option(names = { "-b", "--blacklist" }, description = "The flag to replace specified words with *redacted*", required = false)
    public ArrayList<String> blacklistWords;

    /**
     * Option to add on summary
     */
    @Option(names = { "-r", "--report" }, description = "The flag to add a summary of messages sent", required = false)
    public boolean report;
}
