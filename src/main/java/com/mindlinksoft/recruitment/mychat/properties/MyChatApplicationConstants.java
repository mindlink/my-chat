package com.mindlinksoft.recruitment.mychat.properties;

/**
 * Created by EL on 29/05/2017.
 */
public class MyChatApplicationConstants {
    private MyChatApplicationConstants(){} //Prevent instantiation
    /**
     * Command line options constants
     */

    /**
     * help option settings
     */
    public static final String CLI_HELP_SHORT_OPTION = "h";
    public static final String CLI_HELP_LONG_OPTION = "help";
    public static final Boolean CLI_HELP_HAS_ARGS = false;
    public static final String CLI_HELP_DESC = "display help options - when the help flag is present the application exits";

    /**
     * hide sensitive data option settings
     */
    public static final String CLI_HIDE_SD_SHORT_OPTION = "x";
    public static final String CLI_HIDE_SD_LONG_OPTION = "hide-sensitive-data";
    public static final Boolean CLI_HIDE_SD_HAS_ARGS = false;
    public static final String CLI_HIDE_SD_DESC = "replace credit card and phone numbers with *redacted*";

    /**
     * obfuscate user ids option settings
     */
    public static final String CLI_OBFUSCATE_SHORT_OPTION = "o";
    public static final String CLI_OBFUSCATE_LONG_OPTION = "obfuscate-user-ids";
    public static final Boolean CLI_OBFUSCATE_HAS_ARGS = false;
    public static final String CLI_OBFUSCATE_DESC = "obfuscate user ids";

    /**
     * filter by user name option settings
     */
    public static final String CLI_USER_FILTER_SHORT_OPTION = "u";
    public static final String CLI_USER_FILTER_LONG_OPTION = "username";
    public static final String CLI_USER_FILTER_ARGNAME = "USERNAME";
    public static final String CLI_USER_FILTER_DESC = "filter messages by user";

    /**
     * filter by keyword option settings
     */
    public static final String CLI_KEYWORD_FILTER_SHORT_OPTION = "f";
    public static final String CLI_KEYWORD_FILTER_LONG_OPTION = "filter-keyword";
    public static final String CLI_KEYWORD_FILTER_ARGNAME = "KEYWORD";
    public static final String CLI_KEYWORD_FILTER_DESC = "filter messages by keyword";

    /**
     * blacklist option settings
     */
    public static final String CLI_BLACKLIST_SHORT_OPTION = "b";
    public static final String CLI_BLACKLIST_LONG_OPTION = "blacklist";
    public static final String CLI_BLACKLIST_ARGNAME = "KEYWORD1,KEYWORD2,KEYWORD3,..";
    public static final String CLI_BLACKLIST_DESC = "specify list of blacklisted words to be replaced with *redacted*";
}
