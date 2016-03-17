package com.mindlinksoft.recruitment.mychat;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> argumentsmap = new HashMap<String, String>();

        for(int i=0;i<arguments.length;i++){
            argumentsmap.put(arguments[i].substring(1), arguments[i+1]);
        }

        return new ConversationExporterConfiguration(argumentsmap);
    }
}
