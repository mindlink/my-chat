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

    /*
     * Gets the name of the user which we are filtering for
     */
    @Option(names = { "--filterByUser"}, description = "The user whose messages should be output.", required = false)
    public String filterUser;

    /*
     * Gets the keyword which we are filtering for
     */
    @Option(names = { "--filterByKeyword"}, description = "The keyword which should be present in all output messages.", required = false)
    public String keyword;

    /*
     * Gets the blacklist of words to be redacted (specified as --blacklist=<word1> --blacklist=<word2> ...)
     */
    @Option(names = { "--blacklist"}, description = "The keyword which should be present in all output messages.", required = false)
    public String[] blacklist;
}
