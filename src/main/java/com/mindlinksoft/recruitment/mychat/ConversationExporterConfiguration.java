package com.mindlinksoft.recruitment.mychat;

import java.util.Arrays;

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

    public String user;

    public String keyword;

    public String[] blacklist;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class with
     * variable arguments. .
     * @param arguments Arguments array the program receives.
     */
    public ConversationExporterConfiguration(String[] arguments){
        try {
            this.inputFilePath = arguments[0];
            this.outputFilePath = arguments[1];
            if (inputFilePath.trim().isEmpty() || outputFilePath.trim().isEmpty()){
                throw new IllegalArgumentException("Inputfile path and Outputfile path cannot be empty");
            }
            for (int i=2;i<arguments.length;i++){
                try{
                    if (arguments[i].startsWith("user=")){
                        this.user = arguments[i].substring(5).trim();
                    }
                    else if (arguments[i].startsWith("keyword=")){
                        this.keyword = arguments[i].substring(8).trim();
                    }
                    else if (arguments[i].startsWith("blacklist=")){
                        if (arguments[i].charAt(10) == '['
                                && arguments[i].charAt(arguments[i].length()-1) == ']'){
                            arguments[i] = arguments[i].replaceAll("\\s+","");
                            if (arguments[i].substring(11,arguments[i].length()-1).isEmpty()) {
                                this.blacklist = null;
                                continue;
                            }
                            this.blacklist = arguments[i].substring(11,arguments[i].length()-1).split(",");
                        }
                        else {
                            throw new IllegalArgumentException("Brackets malformed for blacklist use [] with words " +
                                    "separated by commas");
                        }
                    }
                    else {
                        throw new IllegalArgumentException("Optional Arguments invalid");
                    }
                }
                catch (StringIndexOutOfBoundsException e){
                    throw new IllegalArgumentException("Should be in form inputFilePath outputFilePath " +
                            "user=... keyword=.. blacklist=[] with the last 3 being optional");
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Need to provide an input file path and output file path");
        }
    }
}
