package com.mindlinksoft.recruitment.mychat.main;

import java.util.Arrays;

import com.mindlinksoft.recruitment.mychat.exporter.modifier.Modifier;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {

    public static final String FILTER_USER_ARGUMENT = "-fu";
    public static final String FILTER_KEYWORD_ARGUMENT = "-fw";
    public static final String HIDE_KEYWORD_ARGUMENT = "-hw";
    public static final String OBFUSCATE_USERS_ARGUMENT = "-ob";
    public static final String HIDE_CREDIT_CARD_PHONE_NUMBERS_ARGUMENT = "-hn";

    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public ConversationExporterConfiguration parse(String[] arguments) {
        if (arguments.length == 2) {
            return parseSimpleArguments(arguments);
        } else if (arguments.length == 3) {
            return parseThreeOptions(arguments);
        } else if (arguments.length >= 4) {
            return parseFourOptions(arguments);
        } else {
            throw new IllegalArgumentException("Incorrect number of arguments given. Must be two to four arguments.");
        }
    }

    /**
     * Helper method, quickly creates a simple config with no modifiers
     * @param arguments input and output file path, respectively
     * @return configuration with supplied arguments
     */
    private ConversationExporterConfiguration parseSimpleArguments(String[] arguments) {
        return new ConversationExporterConfiguration(arguments[0], arguments[1]);
    }

    /**
     * Helper method, creates a three-argument configuration, 
     * provided the arguments supplied are valid. For use
     * with modifier options that do not require specific users/words.
     * @param arguments Three command line arguments.
     * @throws IllegalArgumentException if third argument is not valid
     * @return configuration with supplied arguments
     */
    private ConversationExporterConfiguration parseThreeOptions(String[] arguments) {
        Modifier modifier;

        if (arguments[2].equalsIgnoreCase(OBFUSCATE_USERS_ARGUMENT)) {
            modifier = Modifier.OBFUSCATE_USERS;
        } else if (arguments[2].equalsIgnoreCase(HIDE_CREDIT_CARD_PHONE_NUMBERS_ARGUMENT)) {
            modifier = Modifier.HIDE_CREDIT_CARD_AND_PHONE_NUMBERS;
        } else {
            throw new IllegalArgumentException("Incorrect arguments supplied. If using three arguments, write: [inputFilePath] [outputFilePath] [-ob|-hn]");
        }

        return new ConversationExporterConfiguration(arguments[0], arguments[1], modifier, null);
    }

    /**
     * Helper method, creates a four-argument configuration, provided the arguments supplied
     * are valid. For use with modifier options that require specific users/words.`
     * @param arguments Four command line arguments.
     * @throws IllegalArgumentException if third argument is not valid
     * @return configuration with supplied arguments
     */
    private ConversationExporterConfiguration parseFourOptions(String[] arguments) {
        Modifier modifier;

        if (arguments[2].equalsIgnoreCase(FILTER_USER_ARGUMENT)) {
            modifier = Modifier.FILTER_USER;
        } else if (arguments[2].equalsIgnoreCase(FILTER_KEYWORD_ARGUMENT)) {
            modifier = Modifier.FILTER_KEYWORD;
        } else if (arguments[2].equalsIgnoreCase(HIDE_KEYWORD_ARGUMENT)) {
            modifier = Modifier.HIDE_KEYWORD;
        } else {
            throw new IllegalArgumentException("Incorrect arguments supplied. If using four arguments, write: [inputFilePath] [outputFilePath] [-fu|-fw|-hw] [user|keyword]");
        }

        return new ConversationExporterConfiguration(arguments[0], arguments[1], modifier, Arrays.copyOfRange(arguments, 3, arguments.length));
    }
}
