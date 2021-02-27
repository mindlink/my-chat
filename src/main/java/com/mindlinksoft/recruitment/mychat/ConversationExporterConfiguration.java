package com.mindlinksoft.recruitment.mychat;

import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.util.List;

/**
 * Represents the configuration for the exporter.
 */
@Command(name = "export", mixinStandardHelpOptions = true, version = "exporter 1.0",
         description = "Exports a plain text chat log into a JSON file.")
public final class ConversationExporterConfiguration {
    public final String userOpt = "--filterByUser";
    public final String keywordOpt = "--filterByKeyword";
    public final String blacklistOpt = "--blacklist";

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
     * Filters conversation with messages only from a given user
     */
    @Option(names = {userOpt}, description = "The userId used to filter the conversation")
    public String filterUserId;

    /**
     * Filters conversation with messages that contain a given keyword
     */
    @Option(names = {keywordOpt}, description = "The keyword used to filter the conversation")
    public String filterKeyword;

    /**
     * Replaces a given word with *redacted*
     * May be called multiple times to blacklist different words
     */
    @Option(names= {blacklistOpt}, description = "word to be redacted within the conversation")
    public List<String> blacklist;
}
