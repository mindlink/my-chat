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
        String[] parsed = new String[7];
        
        for (int i=0;i<parsed.length;i++) {
            if (arguments.length>i) {
                parsed[i]=arguments[i];
            } else{
                parsed[i]="";
            }
        }
        return new ConversationExporterConfiguration(parsed[0], parsed[1], parsed[2], parsed[3], parsed[4], parsed[5],parsed[6]);
    }
}
