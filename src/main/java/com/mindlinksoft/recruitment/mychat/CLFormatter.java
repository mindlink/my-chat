package com.mindlinksoft.recruitment.mychat;

/**
 *
 * @author esteban
 */
/**
 * A helper class for the current prototype helps format Command Line messages.
 */
public class CLFormatter {

    /* Following are the methods for formatting the UI text */
    static String formatExplanation() {
        return "\nHi, now that the whole conversation has been exported "
                + "you can filter the messages by users or keywords.\n"
                + "You can also hide specific keywords within the messages or "
                + "export a conversation with anonymous users.\n"
                + "(ex. command [argument])\n";

    }

    static String formatMainMenuPrompt() {
        return "\nEnter command: "
                + "user [userID], "
                + "keyword [keyword], "
                + "hide [keyword], "
                + "hideUsers, "
                + "exit"
                + "\n> ";
    }

}
