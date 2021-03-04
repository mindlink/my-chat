package com.mindlinksoft.recruitment.mychat;

import java.util.List;

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
     * Filters msgs to only those sent by given user.
     */
    @Option(names = { "-u", "--filterByUser"}, description = "Filters messeges o/p to JSON file to only those sent by provided user.")
    public String userIdFilter;

    /**
     * Filters msgs to only those containing keyword.
     */
    @Option(names = { "-k", "--filterByKeyword"}, description = "Filters messeges o/p to JSON file to only those containing the keyword.")
    public String keyWordFilter;

    /**
     * Replaces any word that is blacklisted with *redacted*.
     */
    @Option(names = { "-b", "--blacklist"}, description = "If word is in o/p JSON file, it is replaced with *redacted*")
    public List<String> blacklist;
}
