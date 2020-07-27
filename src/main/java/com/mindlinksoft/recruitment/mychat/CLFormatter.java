/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindlinksoft.recruitment.mychat;

/**
 *
 * @author esteban
 */
public class CLFormatter {

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
