package com.mindlinksoft.recruitment.mychat.models;

import java.util.Arrays;
import java.util.List;

public abstract class Filter {
    
    public List<String> filterWords;

    /**
     * Helper method that normalizes strings to only contain alphanumeric characters
     * @param str Input string
     * @return Normalized string
     */
    public String normalizeString(String str) {
        return str.replaceAll("[^a-zA-Z0-9]", "");
    }

    public abstract void runFilter(Conversation conversation);

    public Filter(String word) {
        filterWords = Arrays.asList(word);
    }

    public Filter(List<String> words) {
        filterWords = words;
    }


}
