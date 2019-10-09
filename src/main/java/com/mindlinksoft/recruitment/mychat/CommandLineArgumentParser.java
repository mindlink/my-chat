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
        Map<String, String> flagMap = new HashMap<>();

        //if additional arguments exist add them to map
        if (arguments.length > 2){
            for (int i = 2; i < arguments.length; i+=2){
                flagMap.put(arguments[i], arguments[i+1]);
            }
            return new ConversationExporterConfiguration(arguments[0], arguments[1], flagMap);
        }

        return new ConversationExporterConfiguration(arguments[0], arguments[1]);
    }
}
