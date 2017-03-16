package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
    
    /**
     * The arguments number passed through the command line.
     */
    private int arguments_num;
    
    /**
     * Argument type  is an integer value that seperates the purpose of the a third argument passed through the command line
     * 0 -> filter by user
     * 1 -> filter by keyword
     * 2 -> hide blacklisted words
     */ 
    private int arguments_type;
    
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) {
        
        this.arguments_num = arguments.length;
        
        if (this.arguments_num > 2) {
            this.arguments_type = Integer.parseInt(arguments[2]);
            switch (arguments_type) {
                case 0: {
                    return new ConversationExporterConfiguration(arguments[0], arguments[1], new User(arguments[3]));
                }
                case 1: {
                    return new ConversationExporterConfiguration(arguments[0], arguments[1], arguments[3]);
                }
                case 2: {
                    List<String> blacklisted = new ArrayList<String>();
                    int i = 0;
                    for(String words : arguments) {
                        if (!(i == 0 || i == 1 || i == 2)) {
                            blacklisted.add(words);
                        }    
                    }
                    return new ConversationExporterConfiguration(arguments[0], arguments[1], blacklisted);
                }
            }    
        }
        
        return new ConversationExporterConfiguration(arguments[0], arguments[1]);
    }
}   
