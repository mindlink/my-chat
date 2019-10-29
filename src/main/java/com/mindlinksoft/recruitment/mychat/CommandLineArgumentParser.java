package com.mindlinksoft.recruitment.mychat;

import java.util.Scanner;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     *
     * @param userReply The command line arguments.
     * @return String if reply not recognised.
     */
    public void parseCommandLineArguments(String userReply) throws Exception {
        switch (userReply) {
            case ("username"):
                System.out.println("Please enter the " + userReply + " whose messages you wish to read.");
                Filter userSearch = new Filter(Filter.FilterMethod.USERNAME);
                Scanner s2 = new Scanner(System.in);
                String userReply2 = s2.nextLine();
                userSearch.searchUserMessages(userReply2);
                break;
            case ("specific word"):
                System.out.println("Please enter the word you wish to highlight from the conversation.");
                Filter messageSearch = new Filter(Filter.FilterMethod.SPECIFIC_WORD);
                Scanner s3 = new Scanner(System.in);
                String userReply3 = s3.nextLine();
                messageSearch.searchSpecificWord(userReply3);
                break;
            case ("hide word"):
                System.out.println("Please enter the word you wish to remove from the conversation.");
                Filter hideWord = new Filter(Filter.FilterMethod.REMOVE_WORDS);
                Scanner s4 = new Scanner(System.in);
                String userReply4 = s4.nextLine();
                hideWord.hideSpecificWord(userReply4);
                break;
            case ("no filter"):
                Filter noFilter = new Filter(Filter.FilterMethod.NO_FILTER);
                noFilter.noFilter();
                break;
            default:
                System.out.println("The keyword you entered was not recognised, please try again.");


        }


    }

}
