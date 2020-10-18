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
     * Gets the senderID String used as a filter.
     */
    @Option(names = { "fU", "--filterByUser" }, description = "The username to filter messages", required = false)
    public String userName;

    /**
     * Gets the KeyWord String used as a filter.
     */
    @Option(names = { "fW", "--filterByWord" }, description = "The word used to filter messages", required = false)
    public String keyWord;
}
