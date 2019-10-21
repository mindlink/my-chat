package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.optionClasses.ChatOption;
import com.mindlinksoft.recruitment.mychat.optionClasses.ChatOptionFactory;

import java.util.ArrayList;

/**
 * Represents a helper to parse command line arguments.
 */
final class CommandLineArgumentParser {

    /**
     * Parses the given {@code arguments} into the exporter configuration (separating IO files and options).
     *
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) {

        //Path to input chat file (expecting *.txt)
        String inputFilePath = null;
        //Path to output file (expecting *.JSON)
        String outputFilePath = null;

        //List of options that the user has specified to be applied to the output file
        ArrayList<ChatOption> options = new ArrayList<>();

        for (String argument : arguments) {
            //if argument is an option it will begin with '-'
            if (argument.charAt(0) == '-') {
                //instantiate option specified by argument, if it does not exist, 'IllegalArgumentException' will be thrown
                ChatOption newOption = ChatOptionFactory.getChatOption(argument.charAt(1));

                //some options are invalid without an argument
                if (newOption.needsArgument()) {
                    if (argument.length() <= 3) { //argument must be specified for this option type
                        throw new IllegalArgumentException("Option '" + argument + "' needs an argument");
                    }
                    if (argument.charAt(2) != ':' && argument.charAt(2) != '=') { //option argument must be preceded by ':' or '='
                        throw new IllegalArgumentException("Option '" + argument + "' needs an argument separated by '=' or ':'");
                    }

                    options.add(ChatOptionFactory.getChatOption(argument.charAt(1)));
                    options.get(options.size() - 1).setArgument(argument.substring(3));

                } else { //instantiate option without argument
                    options.add(ChatOptionFactory.getChatOption(argument.charAt(1)));
                }
            } else {
                //assume any argument not starting with '-' is a input/output file path
                //only accept the first 2 encountered file paths
                if (inputFilePath == null) {
                    inputFilePath = argument;
                } else if (outputFilePath == null) {
                    outputFilePath = argument;
                }
            }
        }

        if (inputFilePath == null) {
            throw new IllegalArgumentException("No input file path was found.");
        }
        if (outputFilePath == null) {
            throw new IllegalArgumentException("No output file path was found.");
        }

        return new ConversationExporterConfiguration(inputFilePath, outputFilePath, options);
    }
}
