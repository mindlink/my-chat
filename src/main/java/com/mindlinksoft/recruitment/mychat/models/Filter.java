package com.mindlinksoft.recruitment.mychat.models;

import java.util.Arrays;
import java.util.List;

/**
 * The superclass that dictates how filters should work
 * Any additional filters should extend this class
 */
public abstract class Filter {
    
    // The filtering word/words
    public List<String> filterWords;

    /**
     * Helper method that normalizes strings to only contain alphanumeric characters
     * @param str Input string
     * @return Normalized string
     */
    public String normalizeString(String str) {
        return str.replaceAll("[^a-zA-Z0-9]", "");
    }

    /**
     * The abstract method which should be implemented depending on the filter
     * @param conversation The conversation object which will be altered in place
     */
    public abstract void runFilter(Conversation conversation);


    /**
     * Constructors which can take a singular string or list of words and will save it as a list of words
     * @param word The filtering word/words
     */
    public Filter(String word) {
        filterWords = Arrays.asList(word);
    }

    public Filter(List<String> words) {
        filterWords = words;
    }

}
