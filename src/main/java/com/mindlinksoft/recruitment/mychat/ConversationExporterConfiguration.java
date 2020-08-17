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
    public String inputFilePath;

    /**
     * Gets the output file path.
     */
    public String outputFilePath;

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

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param arguments list of command line arguments
     */
    // command line arguments  inputFile outputFile (user=<user>)? (keyword=<keyword>)? (blacklist=[list of words])?
    public ConversationExporterConfiguration(String[] arguments){
        assert(arguments.length >= 2);
        this.inputFilePath = arguments[0];
        this.outputFilePath = arguments[1];
        if(arguments.length > 2){
            for (int i = 2; i < arguments.length; i++) {
                String argument = arguments[i];
                if(argument.startsWith("user=")){
                    this.user = argument.substring(5);
                }else if(argument.startsWith("keyword=")){
                    this.keyword = argument.substring(8);
                }else if(argument.startsWith("blacklist=")){
                    String wordTerm = argument.substring(10);
                    //remove the opening and closing brackets
                    wordTerm = wordTerm.substring(1, wordTerm.length()-1);
                    this.blacklist = Arrays.asList(wordTerm.split(","));
                }else{
                    throw new IllegalArgumentException("Invalid command line arguments\n" +
                            "Arguemnts are in the form inputFile outputFile (user=<user>)? (keyword=<keyword>)? (blacklist=[list of words])?");
                }
            }
        }
    }
}
