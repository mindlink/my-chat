package com.mindlinksoft.recruitment.mychat;

public class FormatCL {

   
    static String formatExplanation() {
        return "\nHello, now that the entire conversation has been exported "
                + "you can have messages filtered based on users or keywords.\n"
                + "In addition, you conceal particular keywords in a message or"
                + "export a conversation with unknown users.\n"
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
