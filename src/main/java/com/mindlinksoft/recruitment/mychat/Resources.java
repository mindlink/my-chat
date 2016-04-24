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

    // Output log strings
    public static final String EXPORT_SUCCESS = "Conversation exported from '%s' to '%s'\n";
    public static final String USER_FILTER_MESSAGE = "Only showing users named %s\n";
}
