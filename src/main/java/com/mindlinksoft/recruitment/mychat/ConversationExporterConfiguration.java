package com.mindlinksoft.recruitment.mychat;

/**
 * A config object built by the command line parser
 */
final class ConversationExporterConfiguration {

    private String inputFilePath;

    private String outputFilePath;

    private boolean obfuscateUID;

    private boolean obfuscateInfo;

    private String filterKeyword;

    private Sender filterSender;

    private String[] blacklist;

    public String getInputFilePath() {
        return inputFilePath;
    }

    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    public boolean isObfuscateUID() {
        return obfuscateUID;
    }

    public void setObfuscateUID(boolean obfuscateUID) {
        this.obfuscateUID = obfuscateUID;
    }

    public boolean isObfuscateInfo() {
        return obfuscateInfo;
    }

    public void setObfuscateInfo(boolean obfuscateInfo) {
        this.obfuscateInfo = obfuscateInfo;
    }

    public String getFilterKeyword() {
        return filterKeyword;
    }

    public void setFilterKeyword(String filterKeyword) {
        this.filterKeyword = filterKeyword;
    }

    public Sender getFilterSender() {
        return filterSender;
    }

    public void setFilterSender(Sender filterSender) {
        this.filterSender = filterSender;
    }

    public String[] getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(String[] blacklist) {
        this.blacklist = blacklist;
    }
}
