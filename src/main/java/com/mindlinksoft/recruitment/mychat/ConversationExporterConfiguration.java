package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {
    /**
     * Input file path declaration.
     */
    private String inputFilePath;

    /**
     * Output file path declaration.
     */
    private String outputFilePath;

    /**
     * Username declaration/initialization
     */
    private String username = null;
    
    /**
     * Keyword declaration/initialization
     */
    private String keyword = null;
    
    /**
     * Blacklisted list declaration
     */
    private List<String> blacklisted = new ArrayList<String>();
    
    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath) {
        this.setInputFilePath(inputFilePath);
        this.setOutputFilePath(outputFilePath);
    }
    
    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class including a username.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param user The username.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, User user) {
        this.setInputFilePath(inputFilePath);
        this.setOutputFilePath(outputFilePath);
        this.setUsername(user.getUsername());
    }
    
    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class including a username.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param keyword The keyword.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, String keyword) {
        this.setInputFilePath(inputFilePath);
        this.setOutputFilePath(outputFilePath);
        this.setKeyword(keyword);
    }
    
    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class including a username.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param blacklisted List of blacklisted words.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, List<String> blacklisted) {
        this.setInputFilePath(inputFilePath);
        this.setOutputFilePath(outputFilePath);
        this.setBlacklisted(blacklisted);
    }

    /**
     * Blacklisted getter method.
     * @return Blacklisted list.
     */
    public List<String> getBlacklisted() {
        return blacklisted;
    }

    /**
     * Blacklisted list setter method.
     * @param blacklisted Set the blacklisted words.
     */
    public void setBlacklisted(List<String> blacklisted) {
        this.blacklisted = blacklisted;
    }
    
    /**
     * Input file path getter method.
     * @return input file path.
     */
    public String getInputFilePath() {
        return inputFilePath;
    }

    /**
     * Inout file path setter method. 
     * @param inputFilePath Sets the input file path.
     */
    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    /**
     * Output file path getter method.
     * @return output file path.
     */
    public String getOutputFilePath() {
        return outputFilePath;
    }

    /**
     * Output file path setter method.
     * @param outputFilePath Sets the output file path.
     */
    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    /**
     * Username getter method.
     * @return username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Username setter method.
     * @param username Sets the username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Keyword getter method.
     * @return keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * Keyword setter method.
     * @param keyword Sets the keyword.
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
