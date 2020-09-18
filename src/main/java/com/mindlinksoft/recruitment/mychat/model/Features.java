package com.mindlinksoft.recruitment.mychat.model;

import java.util.Collection;

public class Features {
    /**
     * Filter for user
     */
    public String user;

    /**
     * Filter for Word
     */
    public String word;

    /**
     * Filter for word to redact
     */
    public String redactWord;

    /**
     * Flag to obfuscate senderId
     */
    public boolean obfuscate;

    /**
     * Flag to write a report of user activity
     */
    public boolean report;

    /**
     * Initializes a new instance of the {@link Features} class.
     *
     * @param user       is the filter for user
     * @param word       is the filter for word
     * @param redactWord is the filter of the word to redact
     * @param obfuscate  is the flag to obfuscate senderIDs
     * @param report     is the flag to write a report of user activity
     */
    public Features(String user, String word, String redactWord, boolean obfuscate, boolean report) {
        this.user = user;
        this.word = word;
        this.redactWord = redactWord;
        this.obfuscate = obfuscate;
        this.report = report;
    }
}
