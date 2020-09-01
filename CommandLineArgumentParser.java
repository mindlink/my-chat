package com.mindlinksoft.recruitment.mychat;
import com.mindlinksoft.recruitment.mychat.optionSettings.OptionSetting;
import com.mindlinksoft.recruitment.mychat.optionSettings.OptionSettingsFactory;

import java.util.ArrayList;

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

        //ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        ///////CommandLineParser = new DefaultParser();
        //Path to chat input .txt file
        String inputFilePath = null;
        //Path to chat output .JSON file
        String outputFilePath = null;

        //Array of selected options that are applied to the output .JSON file
        ArrayList<OptionSetting> options = new ArrayList<>();

        for (String argument : arguments) {
            //arguments start with "**"
            if (argument.startsWith("**")) {
                OptionSetting newOption = OptionSettingsFactory.getOptionSetting(argument.charAt(2));

                if (newOption.argumentRequired()) {
                    if (argument.length() <= 3) {
                        throw new IllegalArgumentException("Option '" + argument + "' needs an argument");
                    }
                    options.add(OptionSettingsFactory.getOptionSetting(argument.charAt(2)));
                    options.get(options.size() - 1).setArgument(argument.substring(3));
                } else { //instantiate option without argument
                    options.add(OptionSettingsFactory.getOptionSetting(argument.charAt(2)));
                }
            }else {
                //assume any argument not starting with '**' is an input/output file path
                //only accept the first 2 encountered file paths
                if (inputFilePath == null) {
                    inputFilePath = argument;
                } else if (outputFilePath == null) {
                    outputFilePath = argument;
                }
            }
        }

        if (inputFilePath == null) {
            throw new IllegalArgumentException("File path was not found for input.");
        }
        if (outputFilePath == null) {
            throw new IllegalArgumentException("File path was not found for output.");
        }

        return new ConversationExporterConfiguration(arguments[0], arguments[1], options);
    }
}
