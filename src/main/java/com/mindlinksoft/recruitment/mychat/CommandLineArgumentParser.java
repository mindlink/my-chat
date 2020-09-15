package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.constructs.ConversationExporterConfiguration;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser
{
    // The flags used on the command line.
    private final Set<String> FLAGS;
    // The index in a message split where the content starts.
    private final int CONTENT_START_INDEX;
    // The separator used when specifying multiple words to hide.
    private final String SEP_WORDS_TO_HIDE;

    /**
     * Initializes a new instance of the {@link CommandLineArgumentParser} class.
     */
    public CommandLineArgumentParser()
    {
        FLAGS = new HashSet<String>()
        {{
            add("-u");
            add("-k");
            add("-w");
            add("-hideCCPN");
            add("-obfUsers");
            add("-report");
        }};
        CONTENT_START_INDEX = 2;
        SEP_WORDS_TO_HIDE = ",";
    }

    /**
     * Parses the given {@code arguments} into the exporter configuration.
     *
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments)
    {
        ConversationExporterConfiguration c = new ConversationExporterConfiguration(arguments[0], arguments[1]);
        c.setContentStartIndex(CONTENT_START_INDEX);
        for (int i = CONTENT_START_INDEX; i < arguments.length; ) {
            switch (arguments[i]) {
                case "-u":
                    if ((i + 1) < arguments.length && !FLAGS.contains(arguments[i + 1])) {
                        c.setUser(arguments[i + 1]);
                        i += 2;
                    } else {
                        throw new IllegalArgumentException("No user specified after: " + arguments[i]);
                    }
                    break;
                case "-k":
                    if ((i + 1) < arguments.length && !FLAGS.contains(arguments[i + 1])) {
                        c.setKeyword(arguments[i + 1]);
                        i += 2;
                    } else {
                        throw new IllegalArgumentException("No keyword specified after: " + arguments[i]);
                    }
                    break;
                case "-w":
                    if ((i + 1) < arguments.length && !FLAGS.contains(arguments[i + 1])) {
                        c.setWordsToHide(arguments[i + 1].split(SEP_WORDS_TO_HIDE));
                        i += 2;
                    } else {
                        throw new IllegalArgumentException("No words to hide (blacklisted words) specified after: " + arguments[i]);
                    }
                    break;
                case "-hideCCPN":
                    c.setHideCCPN(true);
                    i++;
                    break;
                case "-obfUsers":
                    c.setObf(true);
                    i++;
                    break;
                case "-report":
                    c.setReport(true);
                    i++;
                    break;
                default:
                    throw new IllegalStateException("Unexpected command line parameter: " + arguments[i]);
            }
        }
        return c;
    }
}
