package com.mindlinksoft.recruitment.mychat;

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

    private String user = "";
    private String keyword = "";
    private String blacklist = "";
    private Boolean credit = false;
    private Boolean obfuscate = false;
    private Boolean count = false;

    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) {

        for (int arg = 0; arg < arguments.length - 1; arg++) {
            switch (arguments[arg]) {
            case "-u":
                user = arguments[arg + 1];
                break;
            case "-w":
                keyword = arguments[arg + 1];
                break;
            case "-b":
                blacklist = arguments[arg + 1];
                break;
            case "-c":
                if (arguments[arg + 1].equals("true")) {
                    credit = true;
                }
                break;
            case "-o":
                if (arguments[arg + 1].equals("true")) {
                    obfuscate = true;
                }
                break;
            case "-a":
                if (arguments[arg + 1].equals("true")) {
                    count = true;
                }
                break;
            default:
                break;
            }
        }

        return new ConversationExporterConfiguration(arguments[0], arguments[1], user, keyword, blacklist, credit,
                obfuscate, count);
    }
}
