package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.exceptions.InsufficientArgumentsException;
import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.model.Features;
import com.mindlinksoft.recruitment.mychat.utils.AppInformation;

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
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) throws InsufficientArgumentsException {
        if (arguments.length < 2) throw new InsufficientArgumentsException();

        String inputFile = arguments[0];
        String outputFile = arguments[1];

        Features features = readCommandLineData(arguments);
        return new ConversationExporterConfiguration(inputFile, outputFile, features);
    }

    /**
     * Reads the command line arguments
     *
     * @param arguments
     * @return a Features containing all the filters and flags present in @param arguments
     */
    private Features readCommandLineData(String[] arguments) {
        String user = null, word = null, redactWord = null;
        boolean report = false, obfuscate = false;

        for (int i = 2; i < arguments.length; i++) {
            String[] argsSplit = arguments[i].split(":");
            String feature = argsSplit[0];

            if (feature.equals(AppInformation.USER)) user = argsSplit[1];
            if (feature.equals(AppInformation.WORD)) word = argsSplit[1];
            if (feature.equals(AppInformation.REDACT_WORD)) redactWord = argsSplit[1];
            if (feature.equals(AppInformation.REPORT)) report = true;
            if (feature.equals(AppInformation.OBFUSCATE)) obfuscate = true;
        }

        return new Features(user, word, redactWord, obfuscate, report);
    }
}
