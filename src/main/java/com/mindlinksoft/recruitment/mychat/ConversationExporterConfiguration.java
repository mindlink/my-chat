package com.mindlinksoft.recruitment.mychat;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {
    /**
     * Gets the input file path.
     */
    @Argument(index = Resources.INPUT_INDEX,
            required = true,
            metaVar = Resources.INPUT_METAVAR,
            usage = Resources.INPUT_USAGE)
    public String inputFilePath;

    /**
     * Gets the output file path.
     */
    @Argument(index = Resources.OUTPUT_INDEX,
            required = true,
            metaVar = Resources.OUTPUT_METAVAR,
            usage = Resources.OUTPUT_USAGE)
    public String outputFilePath;

    /**
     * Username filter, ignored if null
     */
    @Option(name = Resources.USER_FILTER_SWITCH,
            metaVar = Resources.USER_FILTER_METAVAR,
            usage = Resources.USER_FILTER_USAGE)
    public String userFilter;

    /**
     * Message keyword filter, ignored if null
     */
    @Option(name = Resources.KEYWORD_FILTER_SWITCH,
            metaVar = Resources.KEYWORD_FILTER_METAVAR,
            usage = Resources.KEYWORD_FILTER_USAGE)
    public String keywordFilter;

    /**
     * File containing a list of words to be hidden
     */
    @Option(name = Resources.BLACKLIST_FILTER_SWITCH,
            metaVar = Resources.BLACKLIST_FILTER_METAVAR,
            usage = Resources.BLACKLIST_FILTER_USAGE)
    public String blacklistFile;

    /**
     * Switch to enable obfuscating user names
     */
    @Option(name = Resources.OBFUSCATE_SWITCH,
            usage = Resources.OBFUSCATE_USAGE)
    public boolean isObfuscate;

    /**
     * Switch to enable hiding personal information
     */
    @Option(name = Resources.PERSONAL_SWITCH,
            usage = Resources.PERSONAL_USAGE)
    public boolean isHidingPersonalInfo;
}
