package com.mindlinksoft.recruitment.mychat.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mindlinksoft.recruitment.mychat.exporter.modifier.Modifier;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {

    // valid modifier arguments
    public static final String FILTER_USER_ARGUMENT = "-fu";
    public static final String FILTER_KEYWORD_ARGUMENT = "-fw";
    public static final String HIDE_KEYWORD_ARGUMENT = "-hw";
    public static final String OBFUSCATE_USERS_ARGUMENT = "-ob";
    public static final String HIDE_CREDIT_CARD_PHONE_NUMBERS_ARGUMENT = "-hn";
    public static final String REPORT_ACTIVE_USERS_ARGUMENT = "-rp";

    private int endIndex; // used to set startIndex after parsing sub arguments

    /**
     * Parses the given {@code arguments} into the exporter configuration.
     *
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public ConversationExporterConfiguration parse(String[] arguments) {
        if (arguments.length < 2) {
            throw new IllegalArgumentException("Too few arguments given. Must specify input and output file path.");
        } else {
            ConversationExporterConfiguration configuration = parseSimpleArguments(arguments);

            Set<Modifier> modifiers = configuration.getModifiers();
            Map<Modifier, List<String>> modifierArguments = configuration.getModifierArguments();

            for (int startIndex = 2; startIndex < arguments.length; startIndex++) {
                String argument = arguments[startIndex];

                // parse modifier, and check if it requires additional arguments
                Modifier modifier = parseArgument(argument);

                // check if modifier not already specified, else throw exception
                if (!modifiers.contains(modifier)) {
                    modifiers.add(modifier);
                } else {
                    throw new IllegalArgumentException("Duplicate modifier supplied. The argument \"" +
                            argument + "\" at index " + startIndex + " has already been specified.");
                }

                // if the specified modifier requires usernames or key words, parse them here
                if (requiresSubArguments(modifier)) {
                    List<String> subArguments = parseSubArguments(arguments, startIndex);
                    modifierArguments.put(modifier, subArguments);
                    startIndex = endIndex - 1; // moves start index forward to where sub argument search ended
                }
            }

            return configuration;
        }
    }

    /**
     * Checks if the specified modifier requires additional arguments e.g. usernames for FILTER_USER
     *
     * @param modifier which may require additional arguments
     * @return true if it does, else false
     */
    private boolean requiresSubArguments(Modifier modifier) {
        return modifier == Modifier.FILTER_USER || modifier == Modifier.FILTER_KEYWORD || modifier == Modifier.HIDE_KEYWORD;
    }

    /**
     * Returns the Modifier type from the given argument
     *
     * @param argument command line argument
     * @return relevant modifier type
     * @throws IllegalArgumentException if argument is invalid
     */
    private Modifier parseArgument(String argument) {
        Modifier modifier;
        if (argument.equalsIgnoreCase(FILTER_USER_ARGUMENT)) {
            modifier = Modifier.FILTER_USER;
        } else if (argument.equalsIgnoreCase(FILTER_KEYWORD_ARGUMENT)) {
            modifier = Modifier.FILTER_KEYWORD;
        } else if (argument.equalsIgnoreCase(HIDE_KEYWORD_ARGUMENT)) {
            modifier = Modifier.HIDE_KEYWORD;
        } else if (argument.equalsIgnoreCase(OBFUSCATE_USERS_ARGUMENT)) {
            modifier = Modifier.OBFUSCATE_USERS;
        } else if (argument.equalsIgnoreCase(HIDE_CREDIT_CARD_PHONE_NUMBERS_ARGUMENT)) {
            modifier = Modifier.HIDE_CREDIT_CARD_AND_PHONE_NUMBERS;
        } else if (argument.equalsIgnoreCase(REPORT_ACTIVE_USERS_ARGUMENT)) {
            modifier = Modifier.REPORT_ACTIVE_USERS;
        } else {
            // argument is not a valid modifier
            throw new IllegalArgumentException("Incorrect argument supplied. The argument \"" + argument + "\" is not a valid modifier.");
        }
        return modifier;
    }

    /**
     * Parses sub arguments and returns them in a list
     *
     * @param arguments  command line arguments
     * @param startIndex index to start parsing given arguments from
     * @return list of sub arguments
     */
    private List<String> parseSubArguments(String[] arguments, int startIndex) {
        List<String> subArguments = new ArrayList<>();

        for (endIndex = startIndex + 1; endIndex < arguments.length; endIndex++) {
            String subArgument = arguments[endIndex];

            if (subArgument.charAt(0) != '-') {
                subArguments.add(subArgument); // not a modifier, add subArgument
            } else {
                break; // another modifier is found, break
            }
        }

        return subArguments;
    }

    /**
     * Helper method, quickly creates a simple config with no modifiers
     *
     * @param arguments input and output file path, respectively
     * @return configuration with supplied arguments
     */
    private ConversationExporterConfiguration parseSimpleArguments(String[] arguments) {
        return new ConversationExporterConfiguration(arguments[0], arguments[1]);
    }

}
