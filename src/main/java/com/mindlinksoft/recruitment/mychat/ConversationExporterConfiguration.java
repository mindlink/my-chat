package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {
    /**
     * Gets the input file path.
     */
    public final String inputFilePath;

    /**
     * Gets the output file path.
     */
    public final String outputFilePath;

    /**
     * Gets username for filtering
     */
    public String user;

    /**
     * Gets keyword for filtering
     */
    public String keyword;
    /**
     * Stores words in blacklist
     */
    public List<String> blacklist = new ArrayList<>();
    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     *
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath,String user) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.user = user;
    }



    final String userStart = "user=";
    final String keyWordStart = "keyword=";
    final String blacklistStart = "blacklist=";
    // command line arguments  inputFile outputFile (user=<user>)? (keyword=<keyword>)? (blacklist=[list of words])?
    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param arguments list of command line arguments
     */
    public ConversationExporterConfiguration(String[] arguments){
        try{
            assert(arguments.length >= 2);
            this.inputFilePath = arguments[0];
            this.outputFilePath = arguments[1];
            if(arguments.length > 2){
                for (int i = 2; i < arguments.length; i++) {
                    String argument = arguments[i];
                    if(argument.startsWith(userStart)){
                        this.user = argument.substring(userStart.length());
                    }else if(argument.startsWith(keyWordStart)){
                        this.keyword = argument.substring(keyWordStart.length());
                    }else if(argument.startsWith(blacklistStart)){
                        String wordTerm = argument.substring(blacklistStart.length());
                        //ensure start and closing brackets are there
                        assert(wordTerm.charAt(0)=='[' && wordTerm.charAt(wordTerm.length()-1) == ']');
                        //remove the opening and closing brackets
                        wordTerm = wordTerm.substring(1, wordTerm.length()-1);
                        if(!wordTerm.isEmpty())this.blacklist = Arrays.asList(wordTerm.split(","));
                    }else{
                        throw new IllegalArgumentException("Invalid command line arguments\n" +
                                "Arguments are in the form inputFile outputFile (user=<user>)? (keyword=<keyword>)? (blacklist=[list of words])?");
                    }
                }
            }
        }catch (AssertionError e){
            throw new IllegalArgumentException("Missing/Invalid command line arguments\n" +
                    "Arguments are in the form inputFile outputFile (user=<user>)? (keyword=<keyword>)? (blacklist=[list of words])?");
        }

    }
}
