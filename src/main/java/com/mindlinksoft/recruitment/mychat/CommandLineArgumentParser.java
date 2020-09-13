package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.constructs.ConversationExporterConfiguration;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser
{
    // The separator used when specifying multiple words to hide.
    private final String wordsToHideSep;
    // Filter messages from this user.
    private String user;
    // Filter messages containing this word.
    private String keyword;
    // List of blacklisted words which need redacting.
    private String[] wordsToHide;

    /**
     * Initializes a new instance of the {@link CommandLineArgumentParser} class.
     */
    public CommandLineArgumentParser()
    {
        wordsToHideSep = ",";
        user = "";
        keyword = "";
        wordsToHide = new String[0];
    }

    /**
     * Parses the given {@code arguments} into the exporter configuration.
     *
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments)
    {
        for (int i = 2; i < arguments.length; i += 2) {
            switch (arguments[i]) {
                case "-u":
                    if ((i + 1) < arguments.length) {
                        user = arguments[i + 1];
                    } else {
                        throw new IllegalArgumentException("No user specified after " + arguments[i]);
                    }
                    break;
                case "-k":
                    if ((i + 1) < arguments.length) {
                        keyword = arguments[i + 1];
                    } else {
                        throw new IllegalArgumentException("No keyword specified after " + arguments[i]);
                    }
                    break;
                case "-w":
                    if ((i + 1) < arguments.length) {
                        wordsToHide = arguments[i + 1].split(wordsToHideSep);
                    } else {
                        throw new IllegalArgumentException("No words specified after " + arguments[i]);
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected command line parameter: " + arguments[i]);
            }
        }
        return new ConversationExporterConfiguration(arguments[0], arguments[1], user, keyword, wordsToHide);
    }
}
