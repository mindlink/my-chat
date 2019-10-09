package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

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
        List<String> instructions = new ArrayList<String>();
        if(arguments.length>2) for(int i = 2; i<arguments.length;++i) instructions.add(arguments[i]);

        /**
         * The List 'instructions' now contains the instructions, or is empty if no instructions were given.
         */

        return new ConversationExporterConfiguration(arguments[0], arguments[1], instructions);
    }
}
