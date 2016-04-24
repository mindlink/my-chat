package com.mindlinksoft.recruitment.mychat;

/**
 * Class to hold constants
 */
public class Resources {
    // Command line arguments
    public static final String COMMAND_USAGE = "Usage:";

    public static final int INPUT_INDEX = 0;
    public static final String INPUT_METAVAR = "input";
    public static final String INPUT_USAGE = "Path to original chat log";

    public static final int OUTPUT_INDEX = 1;
    public static final String OUTPUT_METAVAR = "output";
    public static final String OUTPUT_USAGE = "Path for output JSON";

    public static final String USER_FILTER_SWITCH = "-u";
    public static final String USER_FILTER_METAVAR = "username";
    public static final String USER_FILTER_USAGE = "Filter exported messages by username";

    public static final String KEYWORD_FILTER_SWITCH = "-s";
    public static final String KEYWORD_FILTER_METAVAR = "keyword";
    public static final String KEYWORD_FILTER_USAGE = "Filter exported message contents by keyword";

    public static final String BLACKLIST_FILTER_SWITCH = "-b";
    public static final String BLACKLIST_FILTER_METAVAR = "blacklist";
    public static final String BLACKLIST_FILTER_USAGE = "Blacklist file used to censor certain words";

    public static final String OBFUSCATE_SWITCH = "-o";
    public static final String OBFUSCATE_USAGE = "Obfuscate user names with user numbers instead";

    // Output log strings
    public static final String EXPORT_SUCCESS = "Conversation exported from '%s' to '%s'\n";
    public static final String USER_FILTER_MESSAGE = "Only showing users named %s\n";
    public static final String KEYWORD_FILTER_MESSAGE = "Only showing messages containing %s\n";
    public static final String BLACKLIST_FILTER_MESSAGE = "Hiding blacklisted words\n";
    public static final String OBFUSCATE_MESSAGE = "Obfuscating user names\n";

    // Implementation constants
    public static final String REDACTED = "*redacted*";
    public static final String OBFUSCATE_PREFIX = "User ";

    // Errors
    public static final String FILE_NOT_FOUND = "The input file was not found";
    public static final String BLACKLIST_NOT_FOUND = "The blacklist file was not found";
    public static final String FILE_IO_ERROR = "Error while reading/writing file";
}
