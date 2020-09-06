package com.mindlinksoft.recruitment.mychat.Utilities;

import com.mindlinksoft.recruitment.mychat.Objects.ConversationExporterConfiguration;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     *
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) {
        Integer totalArguments = 7;

        ArrayList<String> ar = new ArrayList<>(Arrays.asList(arguments));

        int factor = totalArguments - arguments.length;

        for (int i = 0; i < factor; i++) {
            ar.add("");
        }

        System.out.println("Arguments: " + ar);

        return new ConversationExporterConfiguration(ar.get(0), ar.get(1), ar.get(2), ar.get(3), ar.get(4), ar.get(5), ar.get(6));
    }
}
