package com.mindlinksoft.recruitment.mychat;

import java.util.Scanner;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
    /**
     * Parses the given {@code userReply} into the appropriate methods of filtering.
     *
     * @param filterType The command line arguments.
     * @return String if reply not recognised.
     */
    public void parseCommandLineArguments(String filterType, String[] stringToFilterBy, String whetherToHideCardAndPhoneNumbers, String whetherToObfuscateUserIds) throws Exception {
        switch (filterType) {
            case ("username"):
                Filter userSearch = new Filter(Filter.FilterMethod.USERNAME);
                userSearch.searchUserMessages(stringToFilterBy[0], whetherToHideCardAndPhoneNumbers, whetherToObfuscateUserIds);
                break;
            case ("specific_word"):
                Filter specWord = new Filter(Filter.FilterMethod.SPECIFIC_WORD);
                specWord.searchSpecificWord(stringToFilterBy[0], whetherToHideCardAndPhoneNumbers, whetherToObfuscateUserIds);
                break;
            case ("hide_word"):
                Filter hideWord = new Filter(Filter.FilterMethod.REMOVE_WORDS);
                hideWord.hideSpecificWord(stringToFilterBy, whetherToHideCardAndPhoneNumbers, whetherToObfuscateUserIds);
                break;
            case ("no_filter"):
                Filter noFilter = new Filter(Filter.FilterMethod.NO_FILTER);
                noFilter.noFilter(whetherToHideCardAndPhoneNumbers, whetherToObfuscateUserIds);
                break;
            default:
                System.out.println("The keyword you entered was not recognised, please try again.");


        }


    }

}
