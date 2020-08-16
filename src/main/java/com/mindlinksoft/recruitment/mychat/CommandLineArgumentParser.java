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
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) {
        //TODO: change how command line arguments are parsed i.e make it user=<user> keyword=<keyword> and blacklist=[list of words]
        return new ConversationExporterConfiguration(arguments[0], arguments[1],arguments[2]);
    }
}
