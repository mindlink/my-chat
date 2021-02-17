package com.mindlinksoft.recruitment.mychat;

import picocli.CommandLine.Option;
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
     * Command to filter output by specific user
     */
    @Option(names = { "-u", "--filterByUser" }, description = "Filters messages by specific user.")
    public String filterUser;

    /**
     * Command to filter output by specific keyword
     */
    @Option(names = { "-k", "--filterByKeyword" }, description = "Filters messages by keyword.")
    public String filterKeyword;

    /**
     * Command to blacklist (hide) a specific word
     */
    @Option(names = { "-b", "--blacklist" }, description = "Blacklists a specific word.")
    public String[] blacklist;

    /**
     * Command to include a report of the number of messages each user sent
     */
    @Option(names = { "-r", "--report" }, description = "Creates a report of the number of messages each user sent.")
    public boolean report;
}
