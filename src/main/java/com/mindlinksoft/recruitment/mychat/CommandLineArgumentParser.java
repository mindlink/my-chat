package com.mindlinksoft.recruitment.mychat;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) throws IllegalArgumentException
    {
        if (arguments.length < 2)
            throw new IllegalArgumentException("Provide at least 2 command line arguments - " +
                    "one input filepath and one output filepath.");
        
        return new ConversationExporterConfiguration(arguments[0], arguments[1]);
    }
}
