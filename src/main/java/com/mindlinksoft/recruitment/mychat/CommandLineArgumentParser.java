package com.mindlinksoft.recruitment.mychat;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
    /**
     * Parses the given {@code userReply} into the appropriate methods of filtering.
     *
     * @param filterType The command line arguments.
     * @param hideCardAndPhoneNumbers
     * @param obfuscateUserIds
     * @return String if reply not recognised.
     */
    public void parseCommandLineArguments(String filterType, String[] stringToFilterBy, Boolean hideCardAndPhoneNumbers, Boolean obfuscateUserIds) throws Exception {
        switch (filterType) {
            case ("username"):
                Filter userSearch = new Filter(Filter.FilterMethod.USERNAME);
                userSearch.searchUserMessages(stringToFilterBy[0], hideCardAndPhoneNumbers, obfuscateUserIds);
                break;
            case ("specific_word"):
                Filter specWord = new Filter(Filter.FilterMethod.SPECIFIC_WORD);
                specWord.searchSpecificWord(stringToFilterBy[0], hideCardAndPhoneNumbers, obfuscateUserIds);
                break;
            case ("hide_word"):
                Filter hideWord = new Filter(Filter.FilterMethod.REMOVE_WORDS);
                hideWord.hideSpecificWord(stringToFilterBy, hideCardAndPhoneNumbers, obfuscateUserIds);
                break;
            case ("no_filter"):
                Filter noFilter = new Filter(Filter.FilterMethod.NO_FILTER);
                noFilter.noFilter(hideCardAndPhoneNumbers, obfuscateUserIds);
                break;
            default:
                System.out.println("The keyword you entered was not recognised, please try again.");

        }


    }

}
