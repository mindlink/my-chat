package com.mindlinksoft.recruitment.mychat;

import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.util.Collection;

/**
 * Represents the configuration for the exporter.
 */
@Command(name = "export", version = "exporter 1.0",
         description = "A tool to export a plain text chat log into a JSON file.\nWith additional filtering and reporting functions.",
         requiredOptionMarker = '*',
         mixinStandardHelpOptions = true, sortOptions = false,
         usageHelpWidth = 100, usageHelpAutoWidth = true
)

public final class ConversationExporterConfiguration {

    /**
     * The list of extra commands to carry out for the export
     */
    private Collection<ConversationExporterConfiguration> listOfCommands;

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
     * Command to include a report of the number of messages each user sent
     */
    @Option(names = { "-r", "--report" }, description = "To Include a report of the number of messages each user sent. Sorted by descending order of messages sent. Report can be found under the `activity` property in the JSON output")
    public boolean reportState;

    /**
     * Command to filter output by specific user
     */
    @Option(names = { "-u", "--filterByUser" }, description = "To filter messages by a single specific user.")
    public String filterUserID;

    /**
     * Command to filter output by specific keyword
     */
    @Option(names = { "-k", "--filterByKeyword" }, description = "To filter messages by a single specific keyword.")
    public String filterKeyword;

    /**
     * Command to blacklist (hide) a specific word
     */
    @Option(names = { "-b", "--blacklist" }, description = "To blacklist (hide) a specific word. (Note: case insensitive)")
    public String[] blacklistWord;

    public Collection<ConversationExporterConfiguration> getListOfCommands() {
        return listOfCommands;
    }
}
