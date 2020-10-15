package com.mindlinksoft.recruitment.mychat;

import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

/**
 * Represents the configuration for the exporter.
 */
@Command(name = "export", mixinStandardHelpOptions = true, version = "reporter 1.0",
        description = "Exports a plain text chat log into a JSON file.")

public final class ConversationExporterConfiguration {
    /**
     * Gets the input file path.
     */
    @Option(names = { "-i", "--inputFilePath" }, description = "The path to the input chat log file.", required = true)
    private String inputFilePath;

    /**
     * Gets the output file path.
     */
    @Option(names = { "-o", "--outputFilePath" }, description = "The path to the output JSON file.", required = true)
    private String outputFilePath;

    @Option(names = { "-fu", "--filterByUser=<user>" }, description = "Name of user to filter by", required = false)
    private String user;

    @Option(names = { "-fk", "--filterByKeyword=<keyword>" }, description = "Keyword to filter by", required = false)
    private String keyword;

    @Option(names = { "-bl", "--blacklist=<word>"}, description = "Words to be replaced by *redacted*", required = false)
    private String[] blackList;

    @Option(names = { "-r", "--frequencyReport"}, description = "Report on each sender's message frequency", required = false)
    private Boolean isRequested;

    //Getters and Setters following

    public String getInputFilePath() {
        return inputFilePath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public String getUser() {
        return user;
    }

    public String getKeyword() {
        return keyword;
    }

    public String[] getBlackList() {
        return blackList;
    }

    public Boolean reportRequested() {
        return isRequested;
    }

    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setBlackList(String[] blackList) {
        this.blackList = blackList;
    }

    public void setRequested(Boolean isRequested) {
        isRequested = isRequested;
    }

    public void setIO(String inputFilePath, String outputFilePath){
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }

    public void setFilters(String user, String keyword, String[] blackList, Boolean isRequested){
        this.user = user;
        this.keyword = keyword;
        this.blackList = blackList;
        this.isRequested = isRequested;
    }
}













