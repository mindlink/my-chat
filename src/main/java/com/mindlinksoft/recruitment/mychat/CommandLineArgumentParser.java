package com.mindlinksoft.recruitment.mychat;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser
{
    // The separator used when specifying multiple words to hide.
    private final String wordsToHideSep;

    /**
     * Initializes a new instance of the {@link CommandLineArgumentParser} class.
     *
     * @param wordsToHideSep The separator of words to hide.
     */
    public CommandLineArgumentParser(String wordsToHideSep)
    {
        this.wordsToHideSep = wordsToHideSep;
    }

    /**
     * Parses the given {@code arguments} into the exporter configuration.
     *
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments)
    {
        String u = null;
        String k = null;
        String[] w = null;
        for (int i = 2; i < arguments.length; i += 2) {
            switch (arguments[i]) {
                case "-u":
                    if ((i + 1) < arguments.length) {
                        u = arguments[i + 1];
                    } else {
                        throw new IllegalArgumentException("No user specified after " + arguments[i]);
                    }
                    break;
                case "-k":
                    if ((i + 1) < arguments.length) {
                        k = arguments[i + 1];
                    } else {
                        throw new IllegalArgumentException("No keyword specified after " + arguments[i]);
                    }
                    break;
                case "-w":
                    if ((i + 1) < arguments.length) {
                        w = arguments[i + 1].split(wordsToHideSep);
                    } else {
                        throw new IllegalArgumentException("No words specified after " + arguments[i]);
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected command line parameter: " + arguments[i]);
            }
        }
        return new ConversationExporterConfiguration(arguments[0], arguments[1], u, k, w);
    }
}
