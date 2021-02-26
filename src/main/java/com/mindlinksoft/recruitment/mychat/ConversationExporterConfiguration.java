package com.mindlinksoft.recruitment.mychat;

import org.apache.commons.lang.ObjectUtils;
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
     * NEW FEATURE: Gets the specified user whose messages should be output.
     */
    @Option(names = { "-u", "--filterByUser" }, description = "Filter Messages by User - only messages sent by the specified user are output")
    public String user = null;

    /**
     * NEW FEATURE: Gets the specified keyword whose containing messages should be output.
     */
    @Option(names = { "-k", "--filterByKeyword" }, description = "Filter Messages by Keyword - only messages containing the specified keyword are output")
    public String keyword = null;

    /**
     * NEW FEATURE: Gets the list of blacklisted words who are redacted from the output.
     */
    @Option(names = { "-b", "--blacklist" }, description = "Blacklist Word - redacts any occurrence of the blacklisted word in the output")
    public String[] blacklistedWords = null;

    @Option(names = { "-r", "--report" }, description = "Include Report - adds a report detailing the number of messages sent by each user in the output")
    public boolean report = false;
}
